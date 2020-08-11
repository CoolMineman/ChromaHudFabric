package net.minecraftforge.fml.client.config;

import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

// /**
//  * This class provides several methods and constants used by the Config GUI classes.
//  *
//  * @author bspkrs
//  */
// public class GuiUtils {

//     /**
//      * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with continuous borders
//      * and filler. The provided ResourceLocation object will be bound using
//      * Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation).
//      *
//      * @param res           the ResourceLocation object that contains the desired image
//      * @param x             x axis offset
//      * @param y             y axis offset
//      * @param u             bound resource location image x offset
//      * @param v             bound resource location image y offset
//      * @param width         the desired box width
//      * @param height        the desired box height
//      * @param textureWidth  the width of the box texture in the resource location image
//      * @param textureHeight the height of the box texture in the resource location image
//      * @param topBorder     the size of the box's top border
//      * @param bottomBorder  the size of the box's bottom border
//      * @param leftBorder    the size of the box's left border
//      * @param rightBorder   the size of the box's right border
//      * @param zLevel        the zLevel to draw at
//      */
//     public static void drawContinuousTexturedBox(Identifier res, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight,
//                                                  int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) { try {
//         MinecraftClient.getInstance().getTextureManager().bindTexture(res);
//         drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
//         } catch(Exception e) {
//                 System.out.println("Murica");
//         } 
//     }

//     /**
//      * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with continuous borders
//      * and filler. It is assumed that the desired texture ResourceLocation object has been bound using
//      * Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation).
//      *
//      * @param x             x axis offset
//      * @param y             y axis offset
//      * @param u             bound resource location image x offset
//      * @param v             bound resource location image y offset
//      * @param width         the desired box width
//      * @param height        the desired box height
//      * @param textureWidth  the width of the box texture in the resource location image
//      * @param textureHeight the height of the box texture in the resource location image
//      * @param topBorder     the size of the box's top border
//      * @param bottomBorder  the size of the box's bottom border
//      * @param leftBorder    the size of the box's left border
//      * @param rightBorder   the size of the box's right border
//      * @param zLevel        the zLevel to draw at
//      */
//     public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight,
//                                                  int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) { try{
//         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//         GlStateManager.enableBlend();
//         GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

//         int fillerWidth = textureWidth - leftBorder - rightBorder;
//         int fillerHeight = textureHeight - topBorder - bottomBorder;
//         int canvasWidth = width - leftBorder - rightBorder;
//         int canvasHeight = height - topBorder - bottomBorder;
//         int xPasses = canvasWidth / fillerWidth;
//         int remainderWidth = canvasWidth % fillerWidth;
//         int yPasses = canvasHeight / fillerHeight;
//         int remainderHeight = canvasHeight % fillerHeight;

//         // Draw Border
//         // Top Left
//         drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
//         // Top Right
//         drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder,
//                 zLevel);
//         // Bottom Left
//         drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder,
//                 bottomBorder, zLevel);
//         // Bottom Right
//         drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth,
//                 v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

//         for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
//             // Top Border
//             drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y, u + leftBorder, v,
//                     (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel);
//             // Bottom Border
//             drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder,
//                     v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel);

//             // Throw in some filler for good measure
//             for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
//                 drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight),
//                         u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth),
//                         (j == yPasses ? remainderHeight : fillerHeight), zLevel);
//         }

//         // Side Borders
//         for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
//             // Left Border
//             drawTexturedModalRect(x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder,
//                     (j == yPasses ? remainderHeight : fillerHeight), zLevel);
//             // Right Border
//             drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight),
//                     u + leftBorder + fillerWidth, v + topBorder, rightBorder,
//                     (j == yPasses ? remainderHeight : fillerHeight), zLevel);
//         }
// } catch(Exception e) {
//         System.out.println("Murica");
// }
//     }

//     public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float zLevel) { try{
//         float uScale = 1f / 0x100;
//         float vScale = 1f / 0x100;
//         Tessellator tessellator = Tessellator.getInstance();
//         BufferBuilder wr = tessellator.getBuffer();
//         wr.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
//         wr.vertex(x, y + height, zLevel).texture(u * uScale, ((v + height) * vScale)).end();
//         wr.vertex(x + width, y + height, zLevel).texture((u + width) * uScale, ((v + height) * vScale)).end();
//         wr.vertex(x + width, y, zLevel).texture((u + width) * uScale, (v * vScale)).end();
//         wr.vertex(x, y, zLevel).texture(u * uScale, (v * vScale)).end();
//         tessellator.draw();
// } catch(Exception e) {
//         System.out.println("Murica");
// }
//     }

//     public static void drawGradientRect(int zLevel, int left, int top, int right, int bottom, int startColor,
//             int endColor) { try{
//         float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
//         float startRed = (float) (startColor >> 16 & 255) / 255.0F;
//         float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
//         float startBlue = (float) (startColor & 255) / 255.0F;
//         float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
//         float endRed = (float) (endColor >> 16 & 255) / 255.0F;
//         float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
//         float endBlue = (float) (endColor & 255) / 255.0F;

//         GlStateManager.disableTexture();
//         GlStateManager.enableBlend();
//         GlStateManager.disableAlphaTest();
//         GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
//         GlStateManager.shadeModel(GL11.GL_SMOOTH);

//         Tessellator tessellator = Tessellator.getInstance();
//         BufferBuilder worldrenderer = tessellator.getBuffer();
//         worldrenderer.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
//         worldrenderer.vertex(right, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).end();
//         worldrenderer.vertex(left, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).end();
//         worldrenderer.vertex(left, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).end();
//         worldrenderer.vertex(right, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).end();
//         tessellator.draw();

//         GlStateManager.shadeModel(GL11.GL_FLAT);
//         GlStateManager.disableBlend();
//         GlStateManager.enableAlphaTest();
//         GlStateManager.enableTexture();
// } catch(Exception e) {
//         System.out.println("Murica");
// }
//     }
// }
