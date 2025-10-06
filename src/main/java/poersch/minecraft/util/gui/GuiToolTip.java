package poersch.minecraft.util.gui;

import java.util.List;
import net.minecraft.Minecraft;
import net.minecraft.Gui;

public class GuiToolTip extends Gui {
    private GuiButton guiButton = null;
    private List<String> infoText = null;
    private int infoWidth = 0;
    private int infoHeight = 0;
    private int timer = 0;

    public void setButton(Minecraft minecraft, GuiButton guiButton) {
        if (this.guiButton == guiButton) {
            return;
        }
        if (guiButton == null || guiButton.getToolTip() == null || guiButton.getToolTip().isEmpty()) {
            this.infoText = null;
        } else {
            this.infoText = minecraft.fontRenderer.listFormattedStringToWidth(guiButton.getToolTip(), 242);
            this.infoWidth = 0;
            for (String s : this.infoText) {
                int textWidth = minecraft.fontRenderer.getStringWidth(s);
                if (textWidth > this.infoWidth) {
                    this.infoWidth = textWidth;
                }
            }
            this.infoWidth += 8;
            this.infoHeight = (this.infoText.size() * minecraft.fontRenderer.FONT_HEIGHT) + 7;
        }
        this.guiButton = guiButton;
        this.timer = 0;
    }

    public void draw(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.infoText != null) {
            int i = this.timer;
            this.timer = i + 1;
            if (i <= 100) {
                return;
            }
            int infoX = (mouseX + 5) + this.infoWidth < minecraft.currentScreen.width ? mouseX + 5 : minecraft.currentScreen.width - this.infoWidth;
            int infoY = (mouseY + 5) + this.infoHeight < minecraft.currentScreen.height ? mouseY + 5 : minecraft.currentScreen.height - this.infoHeight;
            drawRect(infoX, infoY, infoX + this.infoWidth, infoY + this.infoHeight, -1157627904);
            for (int n = 0; n < this.infoText.size(); n++) {
                minecraft.fontRenderer.drawStringWithShadow(this.infoText.get(n), infoX + 4, infoY + 4 + (n * minecraft.fontRenderer.FONT_HEIGHT), -1);
            }
        }
    }
}
