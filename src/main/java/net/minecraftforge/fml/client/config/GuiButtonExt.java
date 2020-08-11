// package net.minecraftforge.fml.client.config;

// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.gui.widget.ButtonWidget;

// /**
//  * This class provides a button that fixes several bugs present in the vanilla GuiButton drawing code.
//  * The gist of it is that it allows buttons of any size without gaps in the graphics and with the
//  * borders drawn properly. It also prevents button text from extending out of the sides of the button by
//  * trimming the end of the string and adding an ellipsis.<br/><br/>
//  * <p>
//  * The code that handles drawing the button is in GuiUtils.
//  *
//  * @author bspkrs
//  */
// public class GuiButtonExt extends ButtonWidget {
//     public GuiButtonExt(int id, int xPos, int yPos, String displayString) {
//         super(id, xPos, yPos, displayString);
//     }

//     public GuiButtonExt(int id, int xPos, int yPos, int width, int height, String displayString) {
//         super(id, xPos, yPos, width, height, displayString);
//     }

//     @Override
//     public void render(MinecraftClient client, int mouseX, int mouseY) {
//         drawButton(client, mouseX, mouseY);
//     }
//     /**
//      * Draws this button to the Screen.
//      */
//     public void drawButton(MinecraftClient mc, int mouseX, int mouseY) {
//         if (visible) {
//             focused = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
//             int k = getYImage(focused);
//             GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, x, y, 0, 46 + k * 20, width, height,
//                 200, 20, 2, 3, 2, 2, zOffset);
//             renderBg(mc, mouseX, mouseY);
//             int color = 14737632;

//             if (!active) {
//                 color = 10526880;
//             } else if (focused) {
//                 color = 16777120;
//             }

//             String buttonText = message;
//             int strWidth = mc.textRenderer.getStringWidth(buttonText);
//             int ellipsisWidth = mc.textRenderer.getStringWidth("...");

//             if (strWidth > width - 6 && strWidth > ellipsisWidth) {
//                 buttonText = mc.textRenderer.trimToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
//             }

//             drawCenteredString(mc.textRenderer, buttonText, x + width / 2, y + (height - 8) / 2, color);
//         }
//     }
// }
