package io.github.CoolMineman;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class ScaledResolution {
    private Window window;

    public ScaledResolution(MinecraftClient minecraftClient) {
        this.window = minecraftClient.getWindow();
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
        int i = (int)((double)window.getFramebufferWidth() / window.getScaleFactor());
        return (double)this.window.getFramebufferWidth() / (window.getScaleFactor() > (double)i ? i + 1 : i);
    }

    public double getScaledHeight_double()
    {
        int i = (int)((double)window.getFramebufferHeight() / window.getScaleFactor());
        return (double)this.window.getFramebufferHeight() / (window.getScaleFactor() > (double)i ? i + 1 : i);
    }

    public int getScaleFactor()
    {
        return MinecraftClient.getInstance().options.guiScale;
    }
}