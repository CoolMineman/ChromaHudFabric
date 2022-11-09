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
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Sk1er
 */
public class ArrowCount extends DisplayItem {

    public ArrowCount(JsonHolder data, int ordinal) {
        super(data, ordinal);
        height = 16;
        width = 16;
    }


    @Override
    public void draw(int starX, double startY, boolean isConfig) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Item.byRawId(262), 64));
        ClientPlayerEntity thePlayer = MinecraftClient.getInstance().player;
        if (thePlayer != null) {
            int c = Arrays.stream(thePlayer.inventory.main).filter(Objects::nonNull).filter(is ->
                is.getTranslationKey().equalsIgnoreCase("item.arrow")).mapToInt(is -> is.count).sum();
            ElementRenderer.render(list, starX, startY, false);
            ElementRenderer.draw(starX + 16, startY + 8, "x" + (isConfig ? 64 : c));
        }
    }

}
