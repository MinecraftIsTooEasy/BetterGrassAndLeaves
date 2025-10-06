package poersch.minecraft.util.gui;

import net.minecraft.Minecraft;
import net.minecraft.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiHorizontalBar extends GuiButton {
    public GuiHorizontalBar(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height, "");
    }

    @Override // net.minecraft.client.gui.Gui
    protected void drawGradientRect(int xA, int yA, int xB, int yB, int colorA, int colorB) {
        float aA = ((colorA >> 24) & 255) / 255.0f;
        float rA = ((colorA >> 16) & 255) / 255.0f;
        float gA = ((colorA >> 8) & 255) / 255.0f;
        float bA = (colorA & 255) / 255.0f;
        float aB = ((colorB >> 24) & 255) / 255.0f;
        float rB = ((colorB >> 16) & 255) / 255.0f;
        float gB = ((colorB >> 8) & 255) / 255.0f;
        float bB = (colorB & 255) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(rA, gA, bA, aA);
        tessellator.addVertex(xA, yA, this.zLevel);
        tessellator.addVertex(xA, yB, this.zLevel);
        tessellator.setColorRGBA_F(rB, gB, bB, aB);
        tessellator.addVertex(xB, yB, this.zLevel);
        tessellator.addVertex(xB, yA, this.zLevel);
        tessellator.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.drawButton) {
            drawGradientRect(this.xPosition, this.yPosition, this.xPosition + (this.width / 2), this.yPosition + this.height, 6710886, -10066330);
            drawGradientRect(this.xPosition + (this.width / 2), this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -10066330, 6710886);
        }
    }
}
