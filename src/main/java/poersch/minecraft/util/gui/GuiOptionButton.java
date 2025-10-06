package poersch.minecraft.util.gui;

import net.minecraft.Minecraft;
import net.minecraft.EnumChatFormatting;
import poersch.minecraft.util.options.Option;

public class GuiOptionButton extends GuiButton {
    public String caption;
    protected String[] options;
    protected int value;

    public GuiOptionButton(int id, int x, int y, String displayString, String[] options, String value) {
        this(id, x, y, 150, 20, displayString, options, value);
    }

    public GuiOptionButton(int id, int x, int y, int width, int height, String displayString, String[] options, String value) {
        super(id, x, y, width, height, displayString);
        this.value = 0;
        this.caption = displayString;
        this.options = options;
        setValue(value);
    }

    public void setValue(String newValue) {
        this.value = Option.getAllowedInteger(newValue, this.options);
        this.displayString = this.caption + ": " + EnumChatFormatting.YELLOW + getValue();
    }

    public String getValue() {
        return this.options[this.value];
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (super.mousePressed(minecraft, mouseX, mouseY)) {
            int i = this.value + 1;
            this.value = i;
            if (i >= this.options.length) {
                this.value = 0;
            }
            this.displayString = this.caption + ": " + EnumChatFormatting.YELLOW + getValue();
            return true;
        }
        return false;
    }
}
