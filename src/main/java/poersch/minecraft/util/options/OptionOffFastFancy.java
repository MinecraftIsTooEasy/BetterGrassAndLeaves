package poersch.minecraft.util.options;

import java.util.List;

public class OptionOffFastFancy extends OptionChoice {
    public OptionOffFastFancy(String id, String name, String description, String defaultValue) {
        this(null, id, name, description, defaultValue);
    }

    public OptionOffFastFancy(List<Option<?>> optionList, String id, String name, String description, String defaultValue) {
        super(optionList, id, name, description, defaultValue, new String[][]{new String[]{"关闭", "OFF", "False"}, new String[]{"普通", "Fast", "ON", "True"}, new String[]{"高品质", "Fancy"}});
    }
}
