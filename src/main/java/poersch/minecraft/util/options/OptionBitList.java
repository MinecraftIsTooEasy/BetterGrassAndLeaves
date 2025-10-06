package poersch.minecraft.util.options;

import java.util.BitSet;
import java.util.List;

public class OptionBitList extends Option<BitSet> {
    protected final boolean defaultBit;
    protected final int size;

    public OptionBitList(String id, String name, String description, String defaultValue, boolean defaultBit, int size) {
        this(null, id, name, description, defaultValue, defaultBit, size);
    }

    public OptionBitList(List<Option<?>> optionList, String id, String name, String description, String defaultValue, boolean defaultBit, int size) {
        super(optionList, id, name, description, defaultValue);
        this.size = size;
        this.defaultBit = defaultBit;
        this.value = new BitSet(size);
        ((BitSet) this.value).set(0, size, defaultBit);
        initValue(defaultValue);
    }


    @Override
    public void setValue(String newValue) {
        BitSet bitSet = new BitSet(this.size);
        bitSet.set(0, this.size, this.defaultBit);
        int[] iDlist = getTokensInt(newValue);
        boolean negativeBit = !this.defaultBit;
        for (int id : iDlist) {
            if (id >= 0 && id < this.size) {
                bitSet.set(id, negativeBit);
            }
        }
        if (((BitSet) this.value).equals(bitSet)) {
            return;
        }
        this.changed = true;
        this.value = bitSet;
    }

    @Override
    public String getValue() {
        StringBuilder buffer = new StringBuilder();
        for (int n = 0; n < ((BitSet) this.value).length(); n++) {
            if (((BitSet) this.value).get(n) != this.defaultBit) {
                buffer.append(", ").append(n);
            }
        }
        return buffer.length() > 2 ? buffer.substring(2) : "";
    }

    @Override
    public String[] getPossibleValues() {
        return new String[]{"0", "" + (((BitSet) this.value).length() - 1)};
    }

    @Override
    public void setToNextValue() {
    }
}
