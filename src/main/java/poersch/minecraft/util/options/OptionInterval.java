package poersch.minecraft.util.options;

import java.util.List;

public class OptionInterval extends Option<Float> {
    public final float minValue;
    public final float maxValue;
    public final float defaultValue;

    public OptionInterval(String id, String name, String description, String defaultValue) {
        this(null, id, name, description, defaultValue);
    }

    public OptionInterval(List<Option<?>> optionList, String id, String name, String description, String defaultValue) {
        super(optionList, id, name, description + " (0.0 - 1.0)", defaultValue);
        this.defaultValue = Option.getAllowedFloat(defaultValue, 0.0f, 1.0f, 0.5f);
        this.minValue = 0.0f;
        this.maxValue = 1.0f;
        this.value = Float.valueOf(this.defaultValue);
    }

    public OptionInterval(String id, String name, String description, String defaultValue, float minValue, float maxValue) {
        this(null, id, name, description, defaultValue, minValue, maxValue);
    }

    public OptionInterval(List<Option<?>> optionList, String id, String name, String description, String defaultValue, float minValue, float maxValue) {
        super(optionList, id, name, description + " (" + minValue + " - " + maxValue + ")", defaultValue);
        this.defaultValue = Option.getAllowedFloat(defaultValue, minValue, maxValue, minValue + ((maxValue - minValue) * 0.5f));
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = Float.valueOf(this.defaultValue);
    }

    @Override
    public void setValue(String newValue) {
        float buffer = Option.getAllowedFloat(newValue, this.minValue, this.maxValue, this.defaultValue);
        this.changed = buffer != ((Float) this.value).floatValue();
        this.value = Float.valueOf(buffer);
    }

    @Override
    public String getValue() {
        return Float.toString(((Float) this.value).floatValue());
    }

    @Override
    public String[] getPossibleValues() {
        return new String[]{"" + this.minValue, "" + this.maxValue};
    }

    @Override
    public void setToNextValue() {
        float step = (this.maxValue - this.minValue) * 0.2f;
        if (((Float) this.value).floatValue() + step > this.maxValue) {
            this.value = Float.valueOf(this.minValue);
        } else {
            this.value = Float.valueOf(((Float) this.value).floatValue() + step);
        }
    }
}
