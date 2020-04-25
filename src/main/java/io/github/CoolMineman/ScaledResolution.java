package io.github.CoolMineman;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class ScaledResolution {
    private Window window;

    public ScaledResolution(MinecraftClient minecraftClient) {
        this.window = new Window(minecraftClient);
    }
    public int getScaledWidth()
    {
        return this.window.getScaledWidth();
    }

    public int getScaledHeight()
    {
        return this.window.getScaledHeight();
    }

    public double getScaledWidth_double()
    {
        return this.window.method_2467();
    }

    public double getScaledHeight_double()
    {
        return this.window.method_2468();
    }

    public int getScaleFactor()
    {
        return this.window.method_2469();
    }
}