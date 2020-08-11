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

package cc.hyperium.mods.chromahud.displayitems.chromahud;

import cc.hyperium.mods.chromahud.ElementRenderer;
import cc.hyperium.mods.chromahud.api.DisplayItem;
import cc.hyperium.utils.JsonHolder;
import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Sk1er
 */
//todo fix this lol
public class PotionEffects extends DisplayItem {

    private boolean potionIcon;

    public PotionEffects(JsonHolder raw, int ordinal) {
        super(raw, ordinal);
        potionIcon = raw.optBoolean("potionIcon");
    }

    public void draw(int x, double y, boolean isConfig, MatrixStack matrixStack) {
        // int row = 0;
        // double scale = ElementRenderer.getCurrentScale();
        // Collection<StatusEffectInstance> effects = new ArrayList<>();

        // if (isConfig) {
        //     effects.add(new StatusEffectInstance(1, 100, 1));
        //     effects.add(new StatusEffectInstance(3, 100, 2));
        // } else {
        //     effects = MinecraftClient.getInstance().player.method_7134();
        // }

        // List<String> tmp = new ArrayList<>();

        // for (StatusEffectInstance potioneffect : effects) {
        //     StatusEffect potion =  StatusEffect.field_7293[potioneffect.method_6872()];

        //     if (potionIcon) {
        //         GlStateManager.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        //         MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("textures/gui/container/inventory.png"));

        //         if (potion.method_6865()) {
        //             int potionStatusIconIndex = potion.method_6866();
        //             drawTexturedModalRect(!ElementRenderer.getCurrent().isRightSided() ? (int) (x / scale) - 20 :
        //                     (int) (x / scale), (int) ((y + row * 16)) - 4, potionStatusIconIndex % 8 * 18,
        //                 198 + potionStatusIconIndex / 8 * 18, 18, 18);
        //         }
        //     }

        //     StringBuilder s1 = new StringBuilder(I18n.translate(potion.method_6849()));

        //     switch (potioneffect.getAmplifier()) {
        //         case 1:
        //             s1.append(" ").append(I18n.translate("enchantment.level.2"));
        //             break;
        //         case 2:
        //             s1.append(" ").append(I18n.translate("enchantment.level.3"));
        //             break;
        //         case 3:
        //             s1.append(" ").append(I18n.translate("enchantment.level.4"));
        //             break;
        //         default:
        //             break;
        //     }

        //     String s = StatusEffect.method_6853(potioneffect);
        //     String text = s1 + " - " + s;
        //     tmp.add(text);
        //     ElementRenderer.draw((int) (x / scale), ((y + row * 16)), text);
        //     row++;
        // }

        // width = isConfig ? ElementRenderer.maxWidth(tmp) : 0;
        // height = row * 16;
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        // float f = 0.00390625F;
        // float f1 = 0.00390625F;
        // Tessellator tessellator = Tessellator.getInstance();
        // BufferBuilder worldrenderer = tessellator.getBuffer();
        // worldrenderer.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
        // worldrenderer.vertex(x, y + height, 0).texture((float) (textureX) * f, (float) (textureY + height) * f1).end();
        // worldrenderer.vertex(x + width, y + height, 0).texture((float) (textureX + width) * f, (float) (textureY + height) * f1).end();
        // worldrenderer.vertex(x + width, y, 0).texture((float) (textureX + width) * f, (float) (textureY) * f1).end();
        // worldrenderer.vertex(x, y, 0).texture((float) (textureX) * f, (float) (textureY) * f1).end();
        // tessellator.draw();
    }

    public void togglePotionIcon() {
        potionIcon = !potionIcon;
    }

    @Override
    public void save() {
        data.put("potionIcon", potionIcon);
    }

    @Override
    public String toString() {
        return "PotionEffects{" +
            "potionIcon=" + potionIcon +
            '}';
    }
}
