package io.github.CoolMineman;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class NextTickDisplayer {
    private static Screen displayNextTick;

    public static void setDisplayNextTick(Screen displayNextTick2) {
        displayNextTick = displayNextTick2;
    }

    public static void tick() {
        if (displayNextTick != null) {
            MinecraftClient.getInstance().openScreen(displayNextTick);
            displayNextTick = null;
        }
    }
}