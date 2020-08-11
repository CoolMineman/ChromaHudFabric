package cc.hyperium.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class ButtonWidgetIcon extends ButtonWidget {
    private final int sprite;
    private final Identifier icon;
    private final float scale;
    private boolean outline;

    public ButtonWidgetIcon(int buttonID, Identifier icon, int xPos, int yPos, int sprite, float scale) {
        super(xPos, yPos, (int) (52 * scale), (int) (52 * scale), new LiteralText(""), b -> {});
        this.icon = icon;
        this.sprite = sprite;
        this.scale = scale;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
    }

    // /**
    //  * Draws this button to the screen.
    //  */
    // @Override
    // public void render(MinecraftClient mc, int mouseX, int mouseY) {
    //     if (visible) {
    //         int width = 52;
    //         int height = 52;
    //         mc.getTextureManager().bindTexture(icon);
    //         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    //         GL11.glPushMatrix();
    //         GL11.glScalef(scale, scale, scale);
    //         GlStateManager.enableBlend();
    //         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    //         GL11.glTranslatef(x / scale + this.width / scale / 2, y / scale + this.height / scale / 2,
    //                 0);
    //         GlStateManager.color4f(0, 0, 0, 1.0F);
    //         float mag = 1.25F;
    //         GlStateManager.scalef(mag, mag, mag);
    //         drawTexture(-width / 2, -height / 2, 52 * sprite, 0, width, height);
    //         GlStateManager.scalef(1.0F / mag, 1.0F / mag, 1.0F / mag);
    //         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    //         drawTexture(-width / 2, -height / 2, 52 * sprite, 0, width, height);
    //         GlStateManager.disableBlend();
    //         GL11.glPopMatrix();
    //     }
    // }
}
