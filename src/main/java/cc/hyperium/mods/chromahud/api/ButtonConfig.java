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

package cc.hyperium.mods.chromahud.api;

import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.function.BiConsumer;

/**
 * @author Sk1er
 */
public class ButtonConfig {
    /*
        Called when the button is pressed. GuiButton is the gui button instance provided. StatsDisplayItem is the object of that display item
        Load is called on load to initalize to right state
     */
    private final BiConsumer<ButtonWidget, DisplayItem> action;
    private final ButtonWidget button;
    private final BiConsumer<ButtonWidget, DisplayItem> load;

    public ButtonConfig(BiConsumer<ButtonWidget, DisplayItem> action, ButtonWidget button, BiConsumer<ButtonWidget, DisplayItem> load) {
        this.action = action;
        this.button = button;
        this.load = load;
    }

    public BiConsumer<ButtonWidget, DisplayItem> getAction() {
        return action;
    }

    public ButtonWidget getButton() {
        return button;
    }

    public BiConsumer<ButtonWidget, DisplayItem> getLoad() {
        return load;
    }
}
