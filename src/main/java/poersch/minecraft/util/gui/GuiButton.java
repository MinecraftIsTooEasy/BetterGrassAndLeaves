package poersch.minecraft.util.gui;

import net.minecraft.Minecraft;
import net.minecraft.FontRenderer;

import org.lwjgl.opengl.GL11;

public class GuiButton extends net.minecraft.GuiButton {
    protected String toolTipString;

    public GuiButton(int id, int x, int y, String displayString) {
        this(id, x, y, 200, 20, displayString);
    }

    public GuiButton(int id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height, displayString);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public GuiButton setToolTip(String toolTipString) {
        this.toolTipString = toolTipString;
        return this;
    }

    public String getToolTip() {
        return this.toolTipString;
    }

    public GuiButton mouseOver() {
        if (this.drawButton && this.field_82253_i) {
            return this;
        }
        return null;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (!this.drawButton) {
            return;
        }
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(buttonTextures);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int k = getHoverState(this.field_82253_i);
        drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + (k * 20), this.width / 2, this.height);
        drawTexturedModalRect(this.xPosition + (this.width / 2), this.yPosition, 200 - (this.width / 2), 46 + (k * 20), this.width / 2, this.height);
        mouseDragged(minecraft, mouseX, mouseY);
        int l = 14737632;
        if (!this.enabled) {
            l = -6250336;
        } else if (this.field_82253_i) {
            l = 16777120;
        }
        int captionWidth = fontrenderer.getStringWidth(this.displayString);
        if (captionWidth > this.width - 8) {
            float factor = (float) (this.width - 8) / captionWidth;
            GL11.glPushMatrix();
            GL11.glScalef(factor, 1.0f, 1.0f);
            drawCenteredString(fontrenderer, this.displayString, (int) ((this.xPosition + ((float) this.width / 2)) / factor), this.yPosition + ((this.height - 8) / 2), l);
            GL11.glPopMatrix();
            return;
        }
        drawCenteredString(fontrenderer, this.displayString, this.xPosition + (this.width / 2), this.yPosition + ((this.height - 8) / 2), l);
    }
}
