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
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 5/29/2017.
 */
public class ArmourHud extends DisplayItem {
    private List<ItemStack> list = new ArrayList<>();
    private boolean dur;
    private boolean hand;
    private boolean armourOnTop;

    public ArmourHud(JsonHolder raw, int ordinal) {
        super(raw, ordinal);
        dur = raw.optBoolean("dur");
        armourOnTop = raw.optBoolean("armourOnTop");
        hand = raw.optBoolean("hand");
        width = 16;
    }

    @Override
    public void save() {
        data.put("dur", dur);
        data.put("hand", hand);
        data.put("armourOnTop", armourOnTop);
    }

    @Override
    public void draw(int starX, double startY, boolean isConfig, MatrixStack matrixStack) {
        list.clear();
        if (isConfig) {
            if (armourOnTop) {
                list.add(new ItemStack(Item.byRawId(310), 1));
                list.add(new ItemStack(Item.byRawId(311), 1));
                list.add(new ItemStack(Item.byRawId(312), 1));
                list.add(new ItemStack(Item.byRawId(313), 1));
                list.add(new ItemStack(Item.byRawId(276), 1));
                list.add(new ItemStack(Item.byRawId(261), 1));
                list.add(new ItemStack(Item.byRawId(262), 64));

            } else {
                list.add(new ItemStack(Item.byRawId(276), 1));
                list.add(new ItemStack(Item.byRawId(261), 1));
                list.add(new ItemStack(Item.byRawId(262), 64));
                list.add(new ItemStack(Item.byRawId(310), 1));
                list.add(new ItemStack(Item.byRawId(311), 1));
                list.add(new ItemStack(Item.byRawId(312), 1));
                list.add(new ItemStack(Item.byRawId(313), 1));
            }
        } else {
            list = itemsToRender();
        }

        drawArmour(starX, startY, matrixStack);
        height = getArmourHeight();
    }

    private void drawArmour(int x, double y, MatrixStack matrixStack) {
        ItemRenderer renderItem = MinecraftClient.getInstance().getItemRenderer();
        ClientPlayerEntity thePlayer = MinecraftClient.getInstance().player;
        if (thePlayer == null || renderItem == null)
            return;
        ElementRenderer.render(list, x, y, dur, matrixStack);
    }

    private int getArmourHeight() {
        return list.size() * 13;
    }

    private List<ItemStack> itemsToRender() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack heldItem = MinecraftClient.getInstance().player.inventory.getCursorStack();
        if (hand && heldItem != null && !armourOnTop)
            items.add(heldItem);
        DefaultedList<ItemStack> inventory = MinecraftClient.getInstance().player.inventory.armor;

        for (int i = 3; i >= 0; i--) {
            if (inventory.get(i) != null && inventory.get(i).getItem() != null) {
                items.add(inventory.get(i));
            }
        }

        if (hand && heldItem != null && armourOnTop) items.add(heldItem);
        return items;
    }


    public void toggleDurability() {
        dur = !dur;
    }

    public void toggleHand() {
        hand = !hand;
    }

    public boolean isArmourOnTop() {
        return armourOnTop;
    }

    public void setArmourOnTop(boolean armourOnTop) {
        this.armourOnTop = armourOnTop;
    }
}
