/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mods.chromahud.gui;

import cc.hyperium.mods.chromahud.ChromaHUD;
import cc.hyperium.mods.chromahud.ChromaHUDApi;
import cc.hyperium.mods.chromahud.DisplayElement;
import cc.hyperium.mods.chromahud.ElementRenderer;
import cc.hyperium.mods.sk1ercommon.ResolutionUtil;
import cc.hyperium.utils.ChatColor;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import io.github.CoolMineman.NextTickDisplayer;
import io.github.CoolMineman.ScaledResolution;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Formatting;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Sk1er
 */
public class DisplayElementConfig extends Screen {
    public static final String GREEN = "green";

    private final Map<ButtonWidget, Consumer<ButtonWidget>> clicks = new HashMap<>();
    private final Map<ButtonWidget, Consumer<ButtonWidget>> updates = new HashMap<>();
    private final Map<String, ButtonWidget> nameMap = new HashMap<>();
    private DisplayElement element;
    private int ids;
    private int lastX, lastY;
    private NativeImageBackedTexture texture;
    private NativeImageBackedTexture texture2;
    private int hue = -1;
    private int saturation = -1;
    private int brightness = 5;
    private ChromaHUD mod;
    private int lastWidth;
    private int lastHeight;
    private boolean mouseLock;

    DisplayElementConfig(DisplayElement element, ChromaHUD mod) {
        assert element != null : "Display element is null!";
        this.mod = mod;
        this.element = element;
        regenImage();
        mouseLock = Mouse.isButtonDown(0);
    }

    private void regenImage() {
        int dim = 256;
        BufferedImage image = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                image.setRGB(x, y, Color.HSBtoRGB(x / 256F, 1.0F - y / 256F, 1F));
            }
        }

        texture = new NativeImageBackedTexture(image);
        if (hue != -1 && saturation != -1) {
            BufferedImage image1 = new BufferedImage(1, dim, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < dim; y++) {
                float hue = this.hue / 256F;
                float saturation = this.saturation / 256F;
                image1.setRGB(0, y, Color.HSBtoRGB(hue, saturation, 1.0F - y / 256F));
            }

            texture2 = new NativeImageBackedTexture(image1);
        }
    }

    private void reg(String name, ButtonWidget button, Consumer<ButtonWidget> consumer) {
        reg(name, button, consumer, button1 -> {
        });
    }

    private void reg(String name, ButtonWidget button, Consumer<ButtonWidget> consumer, Consumer<ButtonWidget> tick) {
        buttons.add(button);
        clicks.put(button, consumer);
        updates.put(button, tick);
        nameMap.put(name, button);
    }

    private int nextId() {
        return ++ids;
    }

    //! WTF
    // @Override
    // public void initGui() {
    //     super.initGui();
    // }

    private void repack() {
        buttons.clear();
        clicks.clear();
        updates.clear();
        nameMap.clear();
        ScaledResolution current = ResolutionUtil.current();
        int start_y = Math.max((int) (current.getScaledHeight_double() * .1) - 20, 5);
        int posX = (int) (current.getScaledWidth_double() * .5) - 100;
        reg("pos", new ButtonWidget(nextId(), posX, start_y, "Change Position"), button ->
            NextTickDisplayer.setDisplayNextTick(new MoveElementGui(mod, element)));
        reg("items", new ButtonWidget(nextId(), posX, start_y + 22, "Change Items"), button ->
            NextTickDisplayer.setDisplayNextTick(new EditItemsGui(element, mod)));

        //Highlighted
        reg("Highlight", new ButtonWidget(nextId(), posX, start_y + 22 * 2, "-"), button -> {
            //On click
            element.setHighlighted(!element.isHighlighted());
        }, button -> {
            //On Gui Update
            button.message = ChatColor.YELLOW.toString() + "Highlighted: " + (element.isHighlighted() ? ChatColor.GREEN + "Yes" : ChatColor.RED.toString() + "No");
        });


        //Shadow
        reg("shadow", new ButtonWidget(nextId(), posX, start_y + 22 * 3, "-"), button -> {
            //On click
            element.setShadow(!element.isShadow());
        }, button -> {
            //On Gui Update
            button.message = ChatColor.YELLOW.toString() + "Shadow: " + (element.isShadow() ? ChatColor.GREEN + "Yes" : ChatColor.RED.toString() + "No");
        });
        reg("Toggle Right", new ButtonWidget(nextId(), posX, start_y + 22 * 4, "-"), button -> {
            //On click
            element.setRightSided(!element.isRightSided());
        }, button -> {
            //On Gui Update
            button.message = ChatColor.YELLOW.toString() + "Right side: " + (element.isRightSided() ? ChatColor.GREEN + "Yes" : ChatColor.RED.toString() + "No");
        });
        //*4

        reg("Scale Slider", new GuiSlider(nextId(), 5, 5, 200, 20, "Scale: ", "",
            50, 200, element.getScale() * 100D, false, true), button -> {
            //clicked
            //Toggle between chroma types.

        }, button -> {
            //on tick
            element.setScale(((GuiSlider) button).getValue() / 100D);
            button.message = Formatting.YELLOW + "Scale: " + ((GuiSlider) button).getValueInt() + "%";
        });

        reg("color", new ButtonWidget(nextId(), posX, start_y + 22 * 5, "-"), button -> {
            //clicked
            //Chroma -> RGB -> Color Pallet -> Chroma
            if (element.isChroma()) {
                element.setChroma(false);
                element.setRgb(true);
            } else if (element.isRGB()) {
                element.setRgb(false);
                element.setColorPallet(true);
            } else {
                element.setColorPallet(false);
                element.setChroma(true);
            }
        }, button -> {
            //on tick
            String type = "Error";

            if (element.isRGB())
                type = "RGB";
            if (element.isColorPallet())
                type = "Color Pallet";
            if (element.isChroma())
                type = "Chroma";
            button.message = ChatColor.YELLOW + "Color mode: " + ChatColor.GREEN.toString() + type;
        });

        reg("chromaMode", new ButtonWidget(nextId(), posX, start_y + 22 * 6, "-"), button -> {
            //clicked
            //Toggle between chroma types.
            element.setStaticChroma(!element.isStaticChroma());
        }, button -> {
            //on tick
            if (!element.isChroma()) {
                button.active = false;
                button.visible = false;
            } else {
                button.visible = true;
                button.active = true;
                button.message= ChatColor.YELLOW + "Chroma mode: " + (element.isStaticChroma() ? ChatColor.GREEN + "Static" : ChatColor.GREEN + "Wave");
            }
        });
        reg("redSlider", new GuiSlider(nextId(), posX, start_y + 22 * 6, 200, 20, "Red: ", "", 0, 255,
            element.getData().optInt("red"), false, true), button -> {
        }, button -> {
            //on tick
            if (!element.isRGB()) {
                button.active = false;
                button.visible = false;
            } else {
                button.visible = true;
                button.active = true;
                element.getData().put("red", ((GuiSlider) button).getValueInt());
                button.message = Formatting.YELLOW + "Red: " + (element.getData().optInt("red"));
            }
        });


        reg("blueSlider", new GuiSlider(nextId(), posX, start_y + 22 * 8, 200, 20, "Blue: ", "", 0,
            255, element.getData().optInt("blue"), false, true), button -> {
            //clicked
            //Toggle between chroma types.
        }, button -> {
            //on tick
            if (!element.isRGB()) {
                button.active = false;
                button.visible = false;
            } else {
                button.visible = true;
                button.active = true;
                element.getData().put("blue", ((GuiSlider) button).getValueInt());
                button.message = Formatting.YELLOW + "Blue: " + (element.getData().optInt("blue"));
            }
        });
        reg("greenSlider", new GuiSlider(nextId(), posX, start_y + 22 * 7, 200, 20, "Green: ", "",
            0, 255, element.getData().optInt(GREEN), false, true), button -> {
            //clicked
            //Toggle between chroma types.

        }, button -> {
            //on tick
            if (!element.isRGB()) {
                button.active = false;
                button.visible = false;
            } else {
                button.visible = true;
                button.active = true;
                element.getData().put(GREEN, ((GuiSlider) button).getValueInt());
                button.message = Formatting.YELLOW + "Green: " + (element.getData().optInt(GREEN));
            }
        });
        reg("Back", new ButtonWidget(nextId(), 2, ResolutionUtil.current().getScaledHeight() - 22, 100, 20, "Back"),
            (guiButton) -> NextTickDisplayer.setDisplayNextTick(new GeneralConfigGui(mod)), (guiButton) -> {
            });
        reg("Delete", new ButtonWidget(nextId(), 2, ResolutionUtil.current().getScaledHeight() - 22 * 2, 100, 20, "Delete"), (guiButton) -> {

            NextTickDisplayer.setDisplayNextTick(new GeneralConfigGui(mod));
            ChromaHUDApi.getInstance().getElements().remove(element);
        }, (guiButton) -> {
        });


    }

    // @Override
    protected void buttonPressed(ButtonWidget button) {
        Consumer<ButtonWidget> guiButtonConsumer = clicks.get(button);
        if (guiButtonConsumer != null) guiButtonConsumer.accept(button);
    }

    @Override
    public void tick() {
        ScaledResolution current = ResolutionUtil.current();
        if (current.getScaledWidth() != lastWidth || current.getScaledHeight() != lastHeight) {
            repack();
            lastWidth = current.getScaledWidth();
            lastHeight = current.getScaledHeight();
        }

        if (element.isRGB()) element.recalculateColor();

        buttons.forEach(guiButton -> {
            Consumer<ButtonWidget> guiButtonConsumer = updates.get(guiButton);
            if (guiButtonConsumer != null) guiButtonConsumer.accept(guiButton);
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        apply(mouseX, mouseY);
    }

    public float scale() {
        return availableSpace() / 285F;
    }

    private void apply(int mouseX, int mouseY) {
        if (mouseLock) return;
        if (!Mouse.isButtonDown(0)) return;
        if (!element.isColorPallet()) return;

        float scale = scale();
        int left = posX(1);
        int right = posX(2);
        int top = posY(1);
        int bottom = posY(3);
        float x;
        float y;

        if (mouseX > left && mouseX < right) {
            if (mouseY > top && mouseY < bottom) {
                x = mouseX - left;
                y = mouseY - top;
                x /= scale;
                y /= scale;

                if (y > 0 && y <= 256) {
                    if (x < 256 && x > 0) {
                        hue = (int) x;
                        saturation = (int) (256 - y);
                        regenImage();
                        lastX = mouseX;
                        lastY = mouseY;
                    } else if (x > 256 + 15 && x < 256 + 15 + 15) {
                        brightness = (int) y;
                        regenImage();
                    }
                }
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        mod.saveState();
    }

    private void drawCircle(int x, int y) {
        GlStateManager.color4f(0F, 0F, 0F, 1.0F);
        DrawableHelper.fill(x - 2, y + 12, x + 2, y + 3, Color.BLACK.getRGB());
        DrawableHelper.fill(x - 2, y - 3, x + 2, y - 12, Color.BLACK.getRGB());
        DrawableHelper.fill(x + 12, y - 2, x + 3, y + 2, Color.BLACK.getRGB());
        DrawableHelper.fill(x - 12, y - 2, x - 3, y + 2, Color.BLACK.getRGB());
        DrawableHelper.fill(posX(2) + 5,

                (int) (startY() + (brightness - 2) * scale()), posX(2) + 15,
                (int) (startY() + (brightness + 2) * scale()), Color.BLACK.getRGB());
        element.setColor(Color.HSBtoRGB(hue / 255F, saturation / 255F, 1.0F - brightness / 255F));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution current = ResolutionUtil.current();
        mouseLock = mouseLock && Mouse.isButtonDown(0);
        fill(0, 0, current.getScaledWidth(), current.getScaledHeight(), new Color(0, 0, 0, 150).getRGB());
        super.render(mouseX, mouseY, partialTicks);

        ElementRenderer.startDrawing(element);
        element.renderEditView();
        ElementRenderer.endDrawing(element);
        int left = posX(1);
        int top = posY(2);
        int right = posX(2);
        int size = right - left;

        if (element.isRGB()) {
            int start_y = Math.max((int) (current.getScaledHeight_double() * .1) - 20, 5) + 22 * 8 + 25;
            int left1 = current.getScaledWidth() / 2 - 100;
            int right1 = current.getScaledWidth() / 2 + 100;
            DrawableHelper.fill(left1, start_y, right1, right1 - left1 + 200, element.getColor());
        }

        if (!element.isColorPallet())
            return;

        apply(mouseX, mouseY);
        GlStateManager.bindTexture(texture.getGlId());
        GlStateManager.enableTexture();
        GL11.glPushMatrix();
        GL11.glTranslatef(left, top, 0);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);//
        GlStateManager.scalef(size / 285F, size / 285F, 0);
        drawTexture(0, 0, 0, 0, 256, 256);

        if (texture2 != null) {
            GlStateManager.bindTexture(texture2.getGlId());
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(256 + 15, 0, 0);
            drawTexture(0, 0, 0, 0, 15, 256);
        }

        GlStateManager.scalef(285F / size, 285F / size, 0);
        GL11.glPopMatrix();
        if (lastX != 0 && lastY != 0) drawCircle(lastX, lastY);
    }


    //Method vars to make things nice and easy
    private int availableSpace() {
        ScaledResolution current = ResolutionUtil.current();
        int yMin = current.getScaledHeight() - 15 - startY();
        if (yMin + 20 > current.getScaledWidth()) return yMin - 50;
        return yMin;
    }


    private int startY() {
        ScaledResolution current = ResolutionUtil.current();
        return (int) (Math.max((current.getScaledHeight_double() * .1) - 20, 5) + 22 * 6);
    }

    /*
      1: top left
      2: top right
      3: bottom left
      4: bottom right
       */
    private int posX(int vertex) {
        int i = availableSpace();
        ScaledResolution current = ResolutionUtil.current();
        switch (vertex) {
            case 1:
            case 3: {
                return (current.getScaledWidth() - i + 30) / 2;
            }
            case 2:
            case 4: {
                return (current.getScaledWidth() + i + 30) / 2;
            }

            default:
                throw new IllegalArgumentException("Vertex not found " + vertex);
        }
    }

    private int posY(int vertex) {
        switch (vertex) {
            case 1:
            case 2: {
                return startY();
            }
            case 3:
            case 4: {
                return startY() + availableSpace();
            }

            default:
                throw new IllegalArgumentException("Vertex not found " + vertex);
        }
    }
}
