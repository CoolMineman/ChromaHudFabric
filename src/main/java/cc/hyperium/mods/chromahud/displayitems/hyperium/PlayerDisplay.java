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

package cc.hyperium.mods.chromahud.displayitems.hyperium;

import cc.hyperium.mods.chromahud.api.DisplayItem;
import cc.hyperium.utils.JsonHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SurvivalInventoryScreen;
import net.minecraft.client.render.DiffuseLighting;

import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;

/*
 * Created by Cubxity on 22/04/2018
 */
public class PlayerDisplay extends DisplayItem {

    public PlayerDisplay(JsonHolder data, int ordinal) {
        super(data, ordinal);
        width = 51;
        height = 100;
    }


    @Override
    public void draw(int x, double y, boolean config) {
        GlStateManager.pushMatrix();
        GlStateManager.color3f(1, 1, 1);

        GlStateManager.translated(x, y, 0);
        DiffuseLighting.enableNormally();
        GlStateManager.enableAlphaTest();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlphaTest();
        GlStateManager.enableDepthTest();

        GlStateManager.rotatef(30, 0, 1.0F, 0);
        SurvivalInventoryScreen.renderEntity(0, 100, 50, 0, 0, MinecraftClient.getInstance().player);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.clearColor();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableDepthTest();
        GlStateManager.popMatrix();
    }
}
