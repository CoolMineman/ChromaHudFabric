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

package cc.hyperium.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

public abstract class HyperiumGui { // Not the Full Thing

    /**
     * Draws an animated rainbow box in the specified range
     *
     * @param left   the x1 position
     * @param top    the y1 position
     * @param right  the x2 position
     * @param bottom the y2 position
     * @param alpha  the alpha the box should be drawn at
     * @author boomboompower
     */
    public static void drawChromaBox(int left, int top, int right, int bottom, float alpha) {
        // if (left < right) {
        //     int i = left;
        //     left = right;
        //     right = i;
        // }

        // if (top < bottom) {
        //     int j = top;
        //     top = bottom;
        //     bottom = j;
        // }

        // int startColor = Color.HSBtoRGB(System.currentTimeMillis() % 5000L / 5000.0f, 0.8f, 0.8f);
        // int endColor = Color.HSBtoRGB((System.currentTimeMillis() + 500) % 5000L / 5000.0f, 0.8f, 0.8f);

        // float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        // float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        // float f3 = (float) (startColor & 255) / 255.0F;
        // float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        // float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        // float f7 = (float) (endColor & 255) / 255.0F;
        // GlStateManager.disableTexture();
        // GlStateManager.enableBlend();
        // GlStateManager.disableAlphaTest();
        // GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        // GlStateManager.shadeModel(GL11.GL_SMOOTH);
        // Tessellator tessellator = Tessellator.getInstance();
        // BufferBuilder worldrenderer = tessellator.getBuffer();
        // worldrenderer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        // worldrenderer.vertex(right, top, 0).color(f1, f2, f3, alpha).end();
        // worldrenderer.vertex(left, top, 0).color(f1, f2, f3, alpha).end();
        // worldrenderer.vertex(left, bottom, 0).color(f5, f6, f7, alpha).end();
        // worldrenderer.vertex(right, bottom, 0).color(f5, f6, f7, alpha).end();
        // tessellator.draw();
        // GlStateManager.shadeModel(GL11.GL_FLAT);
        // GlStateManager.disableBlend();
        // GlStateManager.enableAlphaTest();
        // GlStateManager.enableTexture();
    }
}
