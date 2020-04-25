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

import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.BiConsumer;

/**
 * @author Sk1er
 */
public class TextConfig {
    /*
        Action is called when the text field is drawn. You cancel actions, please modify the given GuiTextField class.
        Load is called on load to initialize  to right state
     */
    private final BiConsumer<TextFieldWidget, DisplayItem> action;
    private final TextFieldWidget button;
    private final BiConsumer<TextFieldWidget, DisplayItem> load;

    public TextConfig(BiConsumer<TextFieldWidget, DisplayItem> action, TextFieldWidget button, BiConsumer<TextFieldWidget, DisplayItem> load) {
        this.action = action;
        this.button = button;
        this.load = load;
    }

    public BiConsumer<TextFieldWidget, DisplayItem> getAction() {
        return action;
    }

    public TextFieldWidget getTextField() {
        return button;
    }


    public BiConsumer<TextFieldWidget, DisplayItem> getLoad() {
        return load;
    }
}
