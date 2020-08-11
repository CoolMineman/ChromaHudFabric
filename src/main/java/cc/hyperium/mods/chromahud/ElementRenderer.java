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

package cc.hyperium.mods.chromahud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.item.ItemStack;
//import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public class ElementRenderer {

    private static final List<Long> clicks = new ArrayList<>();
    private static final List<Long> rClicks = new ArrayList<>();
    private static double currentScale = 1.0;
    private static int color;
    private static DisplayElement current;
    private static TextRenderer fontRendererObj = MinecraftClient.getInstance().textRenderer;
    private static String cValue;
    private final ChromaHUD mod;
    private final MinecraftClient minecraft;
    private boolean last;
    private boolean rLast;

    public ElementRenderer(ChromaHUD mod) {
        this.mod = mod;
        minecraft = MinecraftClient.getInstance();
    }

    public static String getCValue() {
        return cValue;
    }

    public static double getCurrentScale() {
        return currentScale;
    }

    public static int getColor(int c) {
        return c;
    }

    public static void draw(int x, double y, String string, MatrixStack matrixStack) {
        List<String> tmp = new ArrayList<>();
        tmp.add(string);
        draw(x, y, tmp, matrixStack);
    }

    public static void draw(int x, double y, List<String> list, MatrixStack matrixStack) {
        double ty = y;
        for (String string : list) {
            int shift = current.isRightSided()
                ? fontRendererObj.getWidth(string)
                : 0;

            if (current.isHighlighted()) {
                int stringWidth = fontRendererObj.getWidth(string);
                DrawableHelper.fill(matrixStack, (int) ((x - 1) / currentScale - shift), (int) ((ty - 1) / currentScale), (int) ((x + 1) / currentScale)
                    + stringWidth - shift, (int) ((ty + 1) / currentScale) + 8, new Color(0, 0, 0, 120).getRGB());
            }

            if (current.isChroma()) {
                drawChromaString(string, x - shift, (int) ty, matrixStack);
            } else {
                if (current.isShadow()) {
                    fontRendererObj.drawWithShadow(matrixStack, string, (int) (x / currentScale - shift), (int) (ty / currentScale), getColor(color));
                } else {
                    fontRendererObj.draw(matrixStack, string, (int) (x / currentScale - shift), (int) (ty / currentScale), getColor(color));
                }
            }

            ty += 10D * currentScale;
        }
    }

    // Don't shift, by the time it is here it is already shifted
    private static void drawChromaString(String text, int xIn, int y, MatrixStack matrixStack) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        int x = xIn;

        for (char c : text.toCharArray()) {
            long dif = (x * 10) - (y * 10);
            if (current.isStaticChroma()) dif = 0;
            long l = System.currentTimeMillis() - dif;
            float ff = current.isStaticChroma() ? 1000.0F : 2000.0F;
            int i = Color.HSBtoRGB((float) (l % (int) ff) / ff, 0.8F, 0.8F);
            String tmp = String.valueOf(c);
            if (current.isShadow()) {
                renderer.drawWithShadow(matrixStack, tmp, (float) ((double) x / currentScale), (float) ((double) y / currentScale), i);
            } else {
                renderer.draw(matrixStack, tmp, (float) ((double) x / currentScale), (float) ((double) y / currentScale), i);
            } 
            x += (double) renderer.getWidth(String.valueOf(c)) * currentScale;
        }
    }


    public static int maxWidth(List<String> list) {
        int max = 0;

        for (String s : list) {
            max = Math.max(max, MinecraftClient.getInstance().textRenderer.getWidth(s));
        }

        return max;
    }

    public static int getColor() {
        return color;
    }

    public static int getCPS() {
        clicks.removeIf(aLong -> System.currentTimeMillis() - aLong > 1000L);
        return clicks.size();
    }

    public static DisplayElement getCurrent() {
        return current;
    }

    public static void render(List<ItemStack> itemStacks, int x, double y, boolean showDurability, MatrixStack matrixStack) {
        GlStateManager.pushMatrix();
        int line = 0;
        ItemRenderer renderItem = MinecraftClient.getInstance().getItemRenderer();

        for (ItemStack stack : itemStacks) {
            if (stack.getMaxDamage() == 0) continue;
            String dur = stack.getMaxDamage() - stack.getDamage() + "";
            //todo fix
            //renderItem.method_3988(stack, (int) (x / ElementRenderer.getCurrentScale() - (current.isRightSided() ? (showDurability ? currentScale + fontRendererObj.getWidth(dur) : -8) : 0)), (int) ((y + (16 * line * ElementRenderer.getCurrentScale())) / ElementRenderer.getCurrentScale()));
            if (showDurability) ElementRenderer.draw((int) (x + (double) 20 * currentScale), y + (16 * line) + 4, dur, matrixStack);
            line++;
        }

        GlStateManager.popMatrix();
    }

    public static void startDrawing(DisplayElement element) {
        GlStateManager.pushMatrix();
        GlStateManager.scaled(element.getScale(), element.getScale(), 1.0 / element.getScale());
        currentScale = element.getScale();
        color = element.getColor();
        current = element;
    }

    public static void endDrawing(DisplayElement element) {
        GlStateManager.scaled(1.0 / element.getScale(), 1.0 / element.getScale(), 1.0 / element.getScale());

        GlStateManager.popMatrix();
    }


    public static TextRenderer getFontRenderer() {
        return fontRendererObj;
    }

    public static int getRightCPS() {
        rClicks.removeIf(aLong -> System.currentTimeMillis() - aLong > 1000L);
        return rClicks.size();
    }

    //@InvokeEvent
    public void tick() {
        try  {
            cValue = MinecraftClient.getInstance().worldRenderer.getChunksDebugString().split("/")[0].trim();
        } catch (Exception no) {
            
        }
    }

    // Right CPS Counter

    //@InvokeEvent
    public void onRenderTick(MatrixStack matrixStack) {
        if (/*minecraft.field_2600 || */minecraft.options.debugEnabled) return;
        renderElements(matrixStack);
        //GlStateManager.clearColor();

    }

    private void renderElements(MatrixStack matrixStack) {
        if (fontRendererObj == null) fontRendererObj = MinecraftClient.getInstance().textRenderer;

        //todo fix
        // // Mouse Button Left
        // boolean m = Mouse.isButtonDown(0);
        // if (m != last) {
        //     last = m;
        //     if (m) clicks.add(System.currentTimeMillis());
        // }

        // // Mouse Button Right
        // boolean rm = Mouse.isButtonDown(1);
        // if (rm != rLast) {
        //     rLast = rm;
        //     if (rm) rClicks.add(System.currentTimeMillis());
        // }

        // Others
        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        List<DisplayElement> elementList = mod.getDisplayElements();
        elementList.forEach(element -> {
            startDrawing(element);
            try {
                element.draw(matrixStack);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            endDrawing(element);
        });
    }
}
