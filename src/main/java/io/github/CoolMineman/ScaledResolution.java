package io.github.CoolMineman;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class ScaledResolution {
    private Window window;

    public ScaledResolution(MinecraftClient minecraftClient) {
        this.window = new Window(minecraftClient);
    }
    public int getScaledWidth() {return (int) this.window.getScaledHeight();}

    public int getScaledHeight() {return (int) this.window.getScaledWidth();}

    public double getScaledWidth_double()
    {
        return this.window.getScaledWidth();
    }

    public double getScaledHeight_double()
    {
        return this.window.getScaledHeight();
    }

    public int getScaleFactor()
    {
        return this.window.getScaleFactor();
    }
}