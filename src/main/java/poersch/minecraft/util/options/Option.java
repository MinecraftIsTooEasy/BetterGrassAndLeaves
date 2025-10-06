package poersch.minecraft.util.options;

import java.util.List;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import poersch.minecraft.util.BGALStringHelper;

public abstract class Option<T> {
    protected Property property;
    protected boolean changed = false;
    public final String id;
    public final String name;
    public final String description;
    public final String defaultValue;
    public T value;

    public abstract void setValue(String str);

    public abstract String getValue();

    public abstract String[] getPossibleValues();

    public abstract void setToNextValue();

    public Option(List<Option<?>> optionList, String id, String name, String description, String defaultValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        if (optionList != null) {
            optionList.add(this);
        }
    }

    public void read(Configuration config, String category) {
        initValue(getProperty(config, category));
    }

    public void write() {
        if (this.property != null) {
            setProperty(getValue());
        }
    }

    public boolean gotChanged() {
        if (this.changed) {
            this.changed = false;
            return true;
        }
        return false;
    }

    protected void initValue(String newValue) {
        setValue(newValue);
        this.changed = false;
    }

    protected String getProperty(Configuration config, String category) {
        this.property = config.get(category, this.id, this.defaultValue, this.description);
        return this.property.getString();
    }

    protected void setProperty(String value) {
        this.property.set(value);
    }

    public static int getAllowedInteger(String value, String[][] options) {
        String value2 = value.trim().toLowerCase();
        for (int i = 0; i < options.length; i++) {
            for (int n = 0; n < options[i].length; n++) {
                if (value2.equals(options[i][n].toLowerCase())) {
                    return i;
                }
            }
        }
        return 0;
    }

    public static int getAllowedInteger(String value, String[] options) {
        String value2 = value.trim().toLowerCase();
        for (int n = 0; n < options.length; n++) {
            if (value2.equals(options[n].toLowerCase())) {
                return n;
            }
        }
        return 0;
    }

    public static float getAllowedFloat(String value, float min, float max, float def) {
        float allowed = Float.parseFloat(value);
        return (allowed < min || allowed > max) ? def : allowed;
    }

    public static String[] getTokens(String value) {
        return BGALStringHelper.splitTrimToArray(value, ',');
    }

    public static int[] getTokensInt(String value) {
        return BGALStringHelper.splitTrimIntegerToArray(value, ',');
    }
}
