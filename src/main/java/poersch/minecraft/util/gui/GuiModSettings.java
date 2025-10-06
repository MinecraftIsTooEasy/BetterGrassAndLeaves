package poersch.minecraft.util.gui;

import net.minecraft.GuiScreen;
import net.minecraft.EnumChatFormatting;
import net.minecraft.I18n;
import net.minecraftforge.common.Configuration;
import poersch.minecraft.util.options.Option;
import poersch.minecraft.util.options.OptionInterval;

public class GuiModSettings extends GuiScreen {
    private final String screenTitle;
    private final Option<?>[] modOptions;
    private final Configuration modConfig;
    private final ISettingsUpdatedCallback callback;
    private GuiScrollPanel optionPanel;
    private GuiToolTip guiToolTip;
    private boolean saveOptions;

    public GuiModSettings(String modName, Option<?>[] modOptions, Configuration modConfig) {
        this(modName, modOptions, modConfig, null);
    }

    public GuiModSettings(String modName, Option<?>[] modOptions, Configuration modConfig, ISettingsUpdatedCallback callback) {
        this.saveOptions = false;
        this.screenTitle = EnumChatFormatting.BLUE + modName;
        this.modOptions = modOptions;
        this.modConfig = modConfig;
        this.callback = callback;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.optionPanel = new GuiScrollPanel(100, 0, 34, this.width, this.height - 84, 5);
        this.buttonList.add(this.optionPanel);
        this.buttonList.add(new GuiButton(200, (this.width / 2) - 100, this.height - 44, I18n.getString("gui.done")));
        int index = 0;
        int yOffset = 0;
        for (int n = 0; n < this.modOptions.length; n++) {
            if (this.modOptions[n] == null) {
                int yOffset2 = yOffset + (((index + 1) >> 1) * 24);
                this.optionPanel.buttonList.add(new GuiHorizontalBar(n, (this.optionPanel.getWidth() / 2) - 175, yOffset2, 350, 4));
                yOffset = yOffset2 + 8;
                index = 0;
            } else {
                if (this.modOptions[n] instanceof OptionInterval) {
                    this.optionPanel.buttonList.add(new GuiSlider(n, ((this.optionPanel.getWidth() / 2) - 155) + ((index & 1) * 160), yOffset + ((index >> 1) * 24), this.modOptions[n].name, ((Float) this.modOptions[n].value).floatValue(), ((OptionInterval) this.modOptions[n]).minValue, ((OptionInterval) this.modOptions[n]).maxValue, Float.valueOf(((OptionInterval) this.modOptions[n]).defaultValue)).setToolTip(this.modOptions[n].description));
                } else {
                    this.optionPanel.buttonList.add(new GuiOptionButton(n, ((this.optionPanel.getWidth() / 2) - 155) + ((index & 1) * 160), yOffset + ((index >> 1) * 24), this.modOptions[n].name, this.modOptions[n].getPossibleValues(), this.modOptions[n].getValue()).setToolTip(this.modOptions[n].description));
                }
                index++;
            }
        }
        this.optionPanel.updateSliderSize();
        this.guiToolTip = new GuiToolTip();
    }

    @Override
    protected void actionPerformed(net.minecraft.GuiButton guiButton) {
        if (guiButton.enabled && guiButton.id == 200) {
            this.saveOptions = true;
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void onGuiClosed() {
        if (!this.saveOptions) {
            return;
        }
        for (GuiButton guiButton : this.optionPanel.buttonList) {
            if (guiButton instanceof GuiSlider) {
                this.modOptions[guiButton.id].setValue(((GuiSlider) guiButton).getValue());
            } else if (guiButton instanceof GuiOptionButton) {
                this.modOptions[guiButton.id].setValue(((GuiOptionButton) guiButton).getValue());
            }
        }
        for (Option<?> modOption : this.modOptions) {
            if (modOption != null) {
                modOption.write();
            }
        }
        this.modConfig.save();
        if (this.callback != null) {
            this.callback.onSettingsUpdated();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        drawDefaultBackground();
        drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        GuiButton toolTipButton = null;
        for (Object o : this.buttonList) {
            net.minecraft.GuiButton guiButton = (net.minecraft.GuiButton) o;
            guiButton.drawButton(this.mc, mouseX, mouseY);
            if (guiButton instanceof GuiButton button) {
                if (button.mouseOver() != null) {
                    toolTipButton = button.mouseOver();
                }
            }
        }
        this.guiToolTip.setButton(this.mc, toolTipButton);
        this.guiToolTip.draw(this.mc, mouseX, mouseY);
    }
}
