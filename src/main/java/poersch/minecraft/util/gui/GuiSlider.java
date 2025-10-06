package poersch.minecraft.util.gui;

import net.minecraft.Minecraft;
import net.minecraft.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiButton {
    public String caption;
    public boolean dragging;
    protected float value;
    protected float sliderValue;
    protected float minValue;
    protected float maxValue;
    protected Float defaultValue;

    public GuiSlider(int id, int xPosition, int yPosition, String displayString, float value) {
        this(id, xPosition, yPosition, displayString, value, 0.0f, 1.0f, null);
    }

    public GuiSlider(int id, int xPosition, int yPosition, String displayString, float value, float minValue, float maxValue, Float defaultValue) {
        super(id, xPosition, yPosition, 150, 20, displayString);
        this.caption = displayString;
        this.minValue = minValue;
        this.maxValue = maxValue;
        setValue(value);
        this.defaultValue = defaultValue != null ? (defaultValue - minValue) / (maxValue - minValue) : null;
    }

    public void setValue(float newValue) {
        this.value = newValue;
        this.sliderValue = (this.value - this.minValue) / (this.maxValue - this.minValue);
        updateCaption();
    }

    public String getValue() {
        return Float.toString(this.value);
    }

    protected void updateCaption() {
        this.displayString = this.caption + ": " + EnumChatFormatting.YELLOW + (this.maxValue - this.minValue < 20.0f ? Double.valueOf(Math.round(this.value * 100.0d) / 100.0d) : String.valueOf(Math.round(this.value)));
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.dragging) {
            this.sliderValue = (float) ((mouseX - this.xPosition) + 4) / (this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            } else if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            if (this.defaultValue != null && this.sliderValue > this.defaultValue - 0.05f && this.sliderValue < this.defaultValue.floatValue() + 0.05f) {
                this.sliderValue = this.defaultValue;
            }
            this.value = this.minValue + ((this.maxValue - this.minValue) * this.sliderValue);
            updateCaption();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        drawTexturedModalRect(this.xPosition + ((int) (this.sliderValue * (this.width - 8))), this.yPosition, 0, 66, 4, 20);
        drawTexturedModalRect(this.xPosition + ((int) (this.sliderValue * (this.width - 8))) + 4, this.yPosition, 196, 66, 4, 20);
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (super.mousePressed(minecraft, mouseX, mouseY)) {
            this.sliderValue = (float) ((mouseX - this.xPosition) + 4) / (this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            } else if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            this.value = this.minValue + ((this.maxValue - this.minValue) * this.sliderValue);
            updateCaption();
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }
}
