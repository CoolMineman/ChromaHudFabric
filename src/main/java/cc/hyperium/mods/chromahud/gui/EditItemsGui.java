// /*
//  *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
//  *
//  *       This program is free software: you can redistribute it and/or modify
//  *       it under the terms of the GNU Lesser General Public License as published
//  *       by the Free Software Foundation, either version 3 of the License, or
//  *       (at your option) any later version.
//  *
//  *       This program is distributed in the hope that it will be useful,
//  *       but WITHOUT ANY WARRANTY; without even the implied warranty of
//  *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  *       GNU Lesser General Public License for more details.
//  *
//  *       You should have received a copy of the GNU Lesser General Public License
//  *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
//  */

// package cc.hyperium.mods.chromahud.gui;

// import cc.hyperium.mods.chromahud.ChromaHUD;
// import cc.hyperium.mods.chromahud.ChromaHUDApi;
// import cc.hyperium.mods.chromahud.DisplayElement;
// import cc.hyperium.mods.chromahud.ElementRenderer;
// import cc.hyperium.mods.chromahud.api.ButtonConfig;
// import cc.hyperium.mods.chromahud.api.DisplayItem;
// import cc.hyperium.mods.chromahud.api.StringConfig;
// import cc.hyperium.mods.chromahud.api.TextConfig;
// import cc.hyperium.mods.sk1ercommon.ResolutionUtil;
// import io.github.CoolMineman.ScaledResolution;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.font.TextRenderer;
// import net.minecraft.client.gui.screen.Screen;
// import net.minecraft.client.gui.widget.ButtonWidget;
// import net.minecraft.client.gui.widget.TextFieldWidget;
// import com.mojang.blaze3d.platform.GlStateManager;

// import java.awt.*;
// import java.io.IOException;
// import java.util.List;
// import java.util.*;
// import java.util.function.Consumer;

// public class EditItemsGui extends Screen {
//     private final DisplayElement element;
//     private final Map<ButtonWidget, Consumer<ButtonWidget>> clicks = new HashMap<>();
//     private final Map<ButtonWidget, Consumer<ButtonWidget>> updates = new HashMap<>();
//     private final ChromaHUD mod;
//     private DisplayItem modifying;
//     private int tmpId;

//     EditItemsGui(DisplayElement element, ChromaHUD mod) {
//         this.element = element;
//         this.mod = mod;
//     }

//     private int nextId() {
//         return (++tmpId);
//     }

//     @Override
//     public void init() {
//         reg(new ButtonWidget(nextId(), 2, 2, 100, 20, "Add Items"), button ->
//             client.openScreen(new AddItemsGui(mod, element)));

//         reg(new ButtonWidget(nextId(), 2, 23, 100, 20, "Remove Item"), button -> {
//             if (modifying != null) {
//                 element.removeDisplayItem(modifying.getOrdinal());

//                 if (modifying.getOrdinal() >= element.getDisplayItems().size()) {
//                     modifying = null;
//                 }
//             }
//         });

//         reg(new ButtonWidget(nextId(), 2, 23 + 21, 100, 20, "Move Up"), button -> {
//             if (modifying != null) {
//                 if (modifying.getOrdinal() > 0) {
//                     Collections.swap(element.getDisplayItems(), modifying.getOrdinal(), modifying.getOrdinal() - 1);
//                     element.adjustOrdinal();
//                 }
//             }
//         });

//         reg(new ButtonWidget(nextId(), 2, 23 + 21 * 2, 100, 20, "Move Down"), button -> {
//             if (modifying != null) {
//                 if (modifying.getOrdinal() < element.getDisplayItems().size() - 1) {
//                     Collections.swap(element.getDisplayItems(), modifying.getOrdinal(), modifying.getOrdinal() + 1);
//                     element.adjustOrdinal();
//                 }
//             }
//         });

//         reg(new ButtonWidget(nextId(), 2, ResolutionUtil.current().getScaledHeight() - 22, 100, 20, "Back"), button ->
//             client.openScreen(new DisplayElementConfig(element, mod)));

//     }

//     @Override
//     protected void buttonPressed(ButtonWidget button) {
//         Consumer<ButtonWidget> guiButtonConsumer = clicks.get(button);
//         if (guiButtonConsumer != null) guiButtonConsumer.accept(button);
//     }

//     private void reg(ButtonWidget button, Consumer<ButtonWidget> consumer) {
//         buttons.removeIf(button1 -> button1.id == button.id);
//         clicks.keySet().removeIf(button1 -> button1.id == button.id);
//         buttons.add(button);
//         if (consumer != null) clicks.put(button, consumer);
//     }

//     @Override
//     public void tick() {
//         super.tick();
//         buttons.forEach(guiButton -> {
//             Consumer<ButtonWidget> guiButtonConsumer = updates.get(guiButton);
//             if (guiButtonConsumer != null) guiButtonConsumer.accept(guiButton);
//         });
//     }

//     @Override
//     public void removed() {
//         mod.saveState();
//     }

//     @Override
//     protected void keyPressed(char typedChar, int keyCode) {
//         super.keyPressed(typedChar, keyCode);
//         if (modifying == null) return;
//         List<TextConfig> textConfigs = ChromaHUDApi.getInstance().getTextConfigs(modifying.getType());
//         if (textConfigs != null && !textConfigs.isEmpty()) {
//             textConfigs.stream().map(TextConfig::getTextField).forEach(textField -> textField.keyPressed(typedChar, keyCode));
//         }
//     }

//     @Override
//     protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
//         if (modifying != null) {
//             List<ButtonConfig> configs = ChromaHUDApi.getInstance().getButtonConfigs(modifying.getType());
//             if (configs != null && !configs.isEmpty()) {
//                 for (ButtonConfig config : configs) {
//                     ButtonWidget button = config.getButton();
//                     if (button.isMouseOver(client, mouseX, mouseY)) {
//                         config.getAction().accept(button, modifying);
//                         return;
//                     }
//                 }
//             }

//             List<TextConfig> textConfigs = ChromaHUDApi.getInstance().getTextConfigs(modifying.getType());
//             if (textConfigs != null && !textConfigs.isEmpty()) {
//                 for (TextConfig config : textConfigs) {
//                     TextFieldWidget textField = config.getTextField();
//                     textField.mouseClicked(mouseX, mouseY, mouseButton);
//                     if (textField.isFocused()) {
//                         return;
//                     }
//                 }
//             }
//         }

//         super.mouseClicked(mouseX, mouseY, mouseButton);
//         if (mouseButton == 0) {
//             DisplayItem item1 = null;
//             //Check X range first since it is easy
//             ScaledResolution current = ResolutionUtil.current();
//             int xCenter = current.getScaledWidth() / 2;
//             if (mouseX >= xCenter - 80 && mouseX <= xCenter + 80) {
//                 //now some super janky code
//                 int yPosition = 40;

//                 for (DisplayItem displayItem : element.getDisplayItems()) {
//                     if (mouseY >= yPosition && mouseY <= yPosition + 20) {
//                         item1 = displayItem;
//                         break;
//                     }

//                     //Adjust for 3 pixel gap
//                     yPosition += 23;
//                 }
//             }

//             for (ButtonWidget guiButton : super.buttons) {
//                 if (guiButton.isFocused()) return;
//             }

//             modifying = item1;
//             if (modifying != null) {
//                 ChromaHUDApi.getInstance().getTextConfigs(modifying.getType()).forEach((config) -> config.getLoad().accept(config.getTextField(), modifying));
//                 ChromaHUDApi.getInstance().getButtonConfigs(modifying.getType()).forEach((button) -> button.getLoad().accept(button.getButton(), modifying));
//                 ChromaHUDApi.getInstance().getStringConfigs(modifying.getType()).forEach((button) -> button.getLoad().accept(modifying));
//             }
//         }
//     }

//     @Override
//     protected void mouseReleased(int mouseX, int mouseY, int state) {
//         super.mouseReleased(mouseX, mouseY, state);
//         if (modifying != null) {
//             List<ButtonConfig> configs = ChromaHUDApi.getInstance().getButtonConfigs(modifying.getType());
//             if (configs != null && !configs.isEmpty()) {
//                 configs.stream().map(ButtonConfig::getButton).forEach(button -> button.mouseReleased(mouseX, mouseY));
//             }
//         }
//     }

//     @Override
//     public void render(int mouseX, int mouseY, float partialTicks) {
//         ScaledResolution current = ResolutionUtil.current();
//         fill(0, 0, current.getScaledWidth(), current.getScaledHeight(), new Color(0, 0, 0, 150).getRGB());
//         super.render(mouseX, mouseY, partialTicks);
//         ElementRenderer.startDrawing(element);
//         element.renderEditView();
//         ElementRenderer.endDrawing(element);
//         int xPosition = ResolutionUtil.current().getScaledWidth() / 2 - 80;
//         int yPosition = 40;
//         int width = 160;
//         int height = 20;

//         Color defaultColor = new Color(255, 255, 255, 100);
//         Color otherColor = new Color(255, 255, 255, 150);

//         for (DisplayItem displayItem : element.getDisplayItems()) {
//             TextRenderer fontrenderer = client.textRenderer;
//             GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//             boolean hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
//             fill(xPosition, yPosition, xPosition + width, yPosition + height, modifying != null && modifying.getOrdinal() == displayItem.getOrdinal() ||
//                 hovered ? otherColor.getRGB() : defaultColor.getRGB());
//             int j = Color.RED.getRGB();
//             String displayString = ChromaHUDApi.getInstance().getName(displayItem.getType());
//             fontrenderer.draw(displayString, (xPosition + (width >> 1) - (fontrenderer.getStringWidth(displayString) >> 1)), yPosition + ((height - 8) >> 1), j,
//                 false);
//             yPosition += 23;
//         }

//         if (modifying != null) {
//             List<ButtonConfig> configs = ChromaHUDApi.getInstance().getButtonConfigs(modifying.getType());
//             xPosition = 3;
//             yPosition = 5 + 21 * 4;

//             if (configs != null && !configs.isEmpty()) {
//                 for (ButtonConfig config : configs) {
//                     ButtonWidget button = config.getButton();
//                     button.x = xPosition;
//                     button.y = yPosition;
//                     button.render(client, mouseX, mouseY);
//                     yPosition += 23;
//                 }
//             }

//             List<TextConfig> textConfigs = ChromaHUDApi.getInstance().getTextConfigs(modifying.getType());
//             if (textConfigs != null && !textConfigs.isEmpty()) {
//                 for (TextConfig config : textConfigs) {
//                     TextFieldWidget textField = config.getTextField();
//                     textField.x = xPosition;
//                     textField.y = yPosition;
//                     textField.render();
//                     yPosition += 23;
//                     config.getAction().accept(textField, modifying);
//                 }
//             }

//             int rightBound = (int) (ResolutionUtil.current().getScaledWidth_double() / 2 - 90);
//             List<StringConfig> stringConfigs = ChromaHUDApi.getInstance().getStringConfigs(modifying.getType());
//             if (stringConfigs != null && !stringConfigs.isEmpty()) {
//                 for (StringConfig config : stringConfigs) {
//                     config.getDraw().accept(modifying);
//                     String draw = config.getString();
//                     List<String> lines = new ArrayList<>();
//                     String[] split = draw.split(" ");
//                     TextRenderer fontRendererObj = MinecraftClient.getInstance().textRenderer;
//                     StringBuilder currentLine = new StringBuilder();
//                     for (String s : split) {
//                         if (!s.contains("\n")) {
//                             if (fontRendererObj.getWidth(" " + currentLine.toString()) +
//                                 fontRendererObj.getWidth(s) + xPosition < rightBound - 10)
//                                 currentLine.append(" ").append(s);
//                             else {
//                                 lines.add(currentLine.toString());
//                                 currentLine = new StringBuilder();
//                                 currentLine.append(s);
//                             }
//                         } else {
//                             String[] split1 = s.split("\n");
//                             Iterator<String> iterator = Arrays.asList(split1).iterator();

//                             while (iterator.hasNext()) {
//                                 currentLine.append(" ").append(iterator.next());
//                                 if (iterator.hasNext()) {
//                                     lines.add(currentLine.toString());
//                                     currentLine = new StringBuilder();
//                                 }
//                             }
//                         }
//                     }
//                     lines.add(currentLine.toString());

//                     yPosition += 10;
//                     for (String string : lines) {
//                         MinecraftClient.getInstance().textRenderer.draw(string, xPosition, yPosition, Color.RED.getRGB());
//                         yPosition += 10;
//                     }
//                 }
//             }
//         }
//     }
// }
