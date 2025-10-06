package poersch.minecraft.util.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.Minecraft;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScrollPanel extends GuiButton {
    List<GuiButton> buttonList;
    public GuiButton selectedButton;
    protected float sliderValue;
    protected float sliderSize;
    protected float distToMouse;
    protected boolean dragged;
    protected int padding;
    protected int right;
    protected int bottom;

    public GuiScrollPanel(int id, int xPosition, int yPosition, int width, int height, int padding) {
        super(id, xPosition, yPosition, width, height, "");
        this.buttonList = new ArrayList();
        this.sliderValue = 0.0f;
        this.sliderSize = 1.0f;
        this.distToMouse = 0.0f;
        this.dragged = false;
        this.padding = padding;
        this.right = xPosition + width;
        this.bottom = yPosition + height;
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    protected int getButtonHeight(GuiButton guiButton) {
        try {
            return guiButton.getHeight();
        } catch (Exception e) {
            return 20;
        }
    }

    public void updateSliderSize() {
        int contentHeight = this.height;
        int totalPadding = this.padding * 2;
        for (GuiButton guiButton : this.buttonList) {
            if (guiButton.yPosition + guiButton.getHeight() + totalPadding > contentHeight) {
                contentHeight = guiButton.yPosition + guiButton.getHeight() + totalPadding;
            }
        }
        this.sliderSize = (float) this.height / contentHeight;
        if (this.sliderSize > 1.0f) {
            this.sliderSize = 1.0f;
        }
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.drawButton) {
            mouseDragged(minecraft, mouseX, mouseY);
            mouseScrolled(minecraft, mouseX, mouseY);
            drawGradientRect(this.xPosition, this.yPosition, this.right, this.bottom, -1072689136, -804253680);
            float xScale = (float) minecraft.displayWidth / minecraft.currentScreen.width;
            float yScale = (float) minecraft.displayHeight / minecraft.currentScreen.height;
            GL11.glEnable(3089);
            GL11.glScissor((int) (this.xPosition * xScale), (int) ((minecraft.currentScreen.height - this.bottom) * yScale), (int) (this.width * xScale), (int) (this.height * yScale));
            int xOffset = this.xPosition;
            int yOffset = (this.yPosition + this.padding) - ((int) (((this.sliderValue * this.height) / this.sliderSize) + 0.5f));
            for (GuiButton guiButton : this.buttonList) {
                if (guiButton.yPosition + yOffset + guiButton.getHeight() >= this.yPosition && guiButton.yPosition + yOffset <= this.bottom) {
                    int xBuffer = guiButton.xPosition;
                    int yBuffer = guiButton.yPosition;
                    guiButton.xPosition += xOffset;
                    guiButton.yPosition += yOffset;
                    guiButton.drawButton(minecraft, mouseX, mouseY);
                    guiButton.xPosition = xBuffer;
                    guiButton.yPosition = yBuffer;
                }
            }
            GL11.glDisable(3089);
            drawGradientRect(this.xPosition, this.yPosition, this.right, this.yPosition + 4, -16777216, 0);
            drawGradientRect(this.xPosition, this.bottom - 4, this.right, this.bottom, 0, -16777216);
            drawRect(this.right - 6, this.yPosition, this.right, this.bottom, -16777216);
            int sliderTop = this.yPosition + ((int) (this.sliderValue * this.height));
            int sliderBottom = this.yPosition + ((int) ((this.sliderValue + this.sliderSize) * this.height));
            int colorDark = -11250604;
            int colorLight = -8487298;
            if (mouseX >= this.right - 6 && mouseX <= this.right && mouseY >= this.yPosition && mouseY <= this.bottom) {
                colorDark = -10721635;
                colorLight = -8484673;
            }
            if (this.dragged) {
                int colorBuffer = colorDark;
                colorDark = colorLight;
                colorLight = colorBuffer;
            }
            drawRect(this.right - 6, sliderTop, this.right, sliderBottom, colorDark);
            drawRect(this.right - 6, sliderTop, this.right - 1, sliderBottom - 1, colorLight);
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (super.mousePressed(minecraft, mouseX, mouseY)) {
            if (mouseX >= this.right - 6) {
                float mouseValue = (mouseY - this.yPosition) / this.height;
                if (mouseValue < this.sliderValue) {
                    this.sliderValue = mouseValue;
                    this.distToMouse = 0.0f;
                } else if (mouseValue > this.sliderValue + this.sliderSize) {
                    this.sliderValue = mouseValue + this.sliderSize;
                    this.distToMouse = this.sliderSize;
                } else {
                    this.distToMouse = mouseValue - this.sliderValue;
                }
                this.dragged = true;
                return true;
            }
            int xOffset = this.xPosition;
            int yOffset = (this.yPosition + this.padding) - ((int) (((this.sliderValue * this.height) / this.sliderSize) + 0.5f));
            for (GuiButton guiButton : this.buttonList) {
                if (guiButton.yPosition + yOffset + 20 >= this.yPosition && guiButton.yPosition + yOffset <= this.bottom) {
                    int xBuffer = guiButton.xPosition;
                    int yBuffer = guiButton.yPosition;
                    guiButton.xPosition += xOffset;
                    guiButton.yPosition += yOffset;
                    if (guiButton.mousePressed(minecraft, mouseX, mouseY)) {
                        this.selectedButton = guiButton;
                        guiButton.xPosition = xBuffer;
                        guiButton.yPosition = yBuffer;
                        return true;
                    }
                    guiButton.xPosition = xBuffer;
                    guiButton.yPosition = yBuffer;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.dragged) {
            this.sliderValue = ((float) (mouseY - this.yPosition) / this.height) - this.distToMouse;
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            } else if (this.sliderValue > 1.0f - this.sliderSize) {
                this.sliderValue = 1.0f - this.sliderSize;
            }
        }
    }

    protected void mouseScrolled(Minecraft minecraft, int mouseX, int mouseY) {
        int mouseWheel = Mouse.getDWheel();
        if (!this.dragged && mouseWheel != 0 && mouseX >= this.xPosition && mouseX <= this.right && mouseY >= this.yPosition && mouseY <= this.bottom) {
            this.sliderValue += mouseWheel > 0 ? -0.06f : 0.06f;
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            } else if (this.sliderValue > 1.0f - this.sliderSize) {
                this.sliderValue = 1.0f - this.sliderSize;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        if (this.selectedButton != null) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
        this.dragged = false;
    }

    @Override
    public GuiButton mouseOver() {
        if (this.selectedButton == null) {
            for (GuiButton button : this.buttonList) {
                GuiButton guiButton = button.mouseOver();
                if (guiButton != null) {
                    return guiButton;
                }
            }
            return null;
        }
        return null;
    }
}
