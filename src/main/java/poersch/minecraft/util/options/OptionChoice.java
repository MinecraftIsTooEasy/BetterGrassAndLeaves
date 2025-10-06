package poersch.minecraft.util.options;

import java.util.List;

public class OptionChoice extends Option<Integer> {
    public final String[][] options;

    public OptionChoice(String id, String name, String description, String defaultValue, String[] options) {
        this((List<Option<?>>) null, id, name, description, defaultValue, options);
    }

    public OptionChoice(String id, String name, String description, String defaultValue, String[][] options) {
        this((List<Option<?>>) null, id, name, description, defaultValue, options);
    }

    public OptionChoice(List<Option<?>> optionList, String id, String name, String description, String defaultValue, String[] options) {
        super(optionList, id, name, description + " (" + getOptionsAsString(options) + ")", defaultValue);
        this.options = new String[options.length][1];
        for (int n = 0; n < options.length; n++) {
            this.options[n][0] = options[n];
        }
        this.value = 0;
        initValue(defaultValue);
    }

    public OptionChoice(List<Option<?>> optionList, String id, String name, String description, String defaultValue, String[][] options) {
        super(optionList, id, name, description + " (" + getOptionsAsString(options) + ")", defaultValue);
        this.options = options;
        this.value = 0;
        initValue(defaultValue);
    }


    @Override
    public void setValue(String newValue) {
        int buffer = Option.getAllowedInteger(newValue, this.options);
        this.changed = buffer != ((Integer) this.value).intValue();
        this.value = Integer.valueOf(buffer);
    }

    @Override
    public String getValue() {
        return this.options[((Integer) this.value).intValue()][0];
    }

    @Override
    public String[] getPossibleValues() {
        String[] buffer = new String[this.options.length];
        for (int n = 0; n < this.options.length; n++) {
            buffer[n] = this.options[n][0];
        }
        return buffer;
    }

    public void setToNextValue() {
        Integer valueOf = Integer.valueOf(((Integer) this.value).intValue() + 1);
        this.value = valueOf;
        if (valueOf.intValue() >= this.options.length) {
            this.value = 0;
        }
    }

    protected static String getOptionsAsString(String[] options) {
        StringBuilder buffer = new StringBuilder(options[0]);
        for (int n = 1; n < options.length; n++) {
            buffer.append(", ").append(options[n]);
        }
        return buffer.toString();
    }

    protected static String getOptionsAsString(String[][] options) {
        StringBuilder buffer = new StringBuilder(options[0][0]);
        for (int n = 1; n < options.length; n++) {
            buffer.append(", ").append(options[n][0]);
        }
        return buffer.toString();
    }
}
