package poersch.minecraft.util.options;

import java.util.List;

public class OptionOffOn extends Option<Boolean> {
    private static final String[][] options = {new String[]{"关闭", "OFF", "False"}, new String[]{"开启", "ON", "True"}};

    public OptionOffOn(String id, String name, String description, String defaultValue) {
        this(null, id, name, description, defaultValue);
    }

    public OptionOffOn(List<Option<?>> optionList, String id, String name, String description, String defaultValue) {
        super(optionList, id, name, description + " (" + options[0][0] + ", " + options[1][0] + ")", defaultValue);
        this.value = Boolean.FALSE;
        initValue(defaultValue);
    }

    @Override
    public void setValue(String newValue) {
        boolean buffer = Option.getAllowedInteger(newValue, options) == 1;
        this.changed = buffer != this.value;
        this.value = buffer;
    }

    @Override
    public String getValue() {
        return options[this.value ? (char) 1 : (char) 0][0];
    }

    @Override
    public String[] getPossibleValues() {
        return new String[]{options[0][0], options[1][0]};
    }

    @Override
    public void setToNextValue() {
        this.value = !(Boolean) this.value;
    }
}
