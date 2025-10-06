package poersch.minecraft.util.options;

import java.util.Arrays;
import java.util.List;

public class OptionStringList extends Option<String[]> {
    public OptionStringList(String id, String name, String description, String defaultValue) {
        this(null, id, name, description, defaultValue);
    }

    public OptionStringList(List<Option<?>> optionList, String id, String name, String description, String defaultValue) {
        super(optionList, id, name, description, defaultValue);
        this.value = new String[0];
        initValue(defaultValue);
    }


    @Override
    public void setValue(String newValue) {
        String[] tokens = getTokens(newValue);
        this.changed = !Arrays.equals(tokens, this.value);
        this.value = tokens;
    }

    @Override
    public String getValue() {
        if (this.value.length > 0) {
            StringBuilder buffer = new StringBuilder(((String[]) this.value)[0]);
            for (int n = 1; n < this.value.length; n++) {
                buffer.append(", ").append(((String[]) this.value)[n]);
            }
            return buffer.toString();
        }
        return "";
    }

    @Override
    public String[] getPossibleValues() {
        return new String[]{""};
    }

    @Override
    public void setToNextValue() {
    }
}
