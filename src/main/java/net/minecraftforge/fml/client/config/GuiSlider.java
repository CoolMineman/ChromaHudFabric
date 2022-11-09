package net.minecraftforge.fml.client.config;

import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlStateManager;

/**
 * This class is blatantly stolen from iChunUtils with permission.
 *
 * @author iChun
 */
public class GuiSlider extends GuiButtonExt {
    /**
     * The value of this slider control.
     */
    public double sliderValue;

    public String dispString;

    /**
     * Is this slider control being dragged.
     */
    public boolean dragging;
    public boolean showDecimal;

    public double minValue;
    public double maxValue;
    public int precision;

    public ISlider parent;

    public String suffix;

    public boolean drawString;

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal,
                     double currentVal, boolean showDec, boolean drawStr) {
        this(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null);
    }

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal,
                     double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, xPos, yPos, width, height, prefix);
        minValue = minVal;
        maxValue = maxVal;
        sliderValue = (currentVal - minValue) / (maxValue - minValue);
        dispString = prefix;
        parent = par;
        suffix = suf;
        showDecimal = showDec;
        String val;

        if (showDecimal) {
            val = Double.toString(sliderValue * (maxValue - minValue) + minValue);
            precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        } else {
            val = Integer.toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue));
            precision = 0;
        }

        message = dispString + val + suffix;

        drawString = drawStr;
        if (!drawString) message = "";
    }

    public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        this(id, xPos, yPos, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par);
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    @Override
    public int getYImage(boolean par1) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void renderBg(MinecraftClient par1Minecraft, int par2, int par3) {
        if (visible) {
            if (dragging) {
                sliderValue = (par2 - (x + 4)) / (float) (width - 8);
                updateSlider();
            }

            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexture(x + (int) (sliderValue * (float) (width - 8)), y, 0, 66, 4, 20);
            drawTexture(x + (int) (sliderValue * (float) (width - 8)) + 4, y, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public boolean isMouseOver(MinecraftClient par1Minecraft, int par2, int par3) {
        if (super.isMouseOver(par1Minecraft, par2, par3)) {
            sliderValue = (float) (par2 - (x + 4)) / (float) (width - 8);
            updateSlider();
            dragging = true;
            return true;
        } else {
            return false;
        }
    }

    public void updateSlider() {
        if (sliderValue < 0.0F) sliderValue = 0.0F;
        if (sliderValue > 1.0F) sliderValue = 1.0F;

        StringBuilder val;

        if (showDecimal) {
            val = new StringBuilder(Double.toString(sliderValue * (maxValue - minValue) + minValue));

            if (val.substring(val.indexOf(".") + 1).length() > precision) {
                val = new StringBuilder(val.substring(0, val.indexOf(".") + precision + 1));

                if (val.toString().endsWith(".")) {
                    val = new StringBuilder(val.substring(0, val.indexOf(".") + precision));
                }
            } else {
                while (val.substring(val.indexOf(".") + 1).length() < precision) {
                    val.append("0");
                }
            }
        } else {
            val = new StringBuilder(Integer.toString((int) Math.round(sliderValue * (maxValue - minValue) + minValue)));
        }

        if (drawString) message = dispString + val + suffix;
        if (parent != null) parent.onChangeSliderValue(this);
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public void mouseReleased(int par1, int par2) {
        dragging = false;
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double d) {
        sliderValue = (d - minValue) / (maxValue - minValue);
    }

    public interface ISlider {
        void onChangeSliderValue(GuiSlider slider);
    }
}
