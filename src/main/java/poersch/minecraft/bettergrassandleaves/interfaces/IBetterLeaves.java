package poersch.minecraft.bettergrassandleaves.interfaces;

import net.minecraft.Icon;

public interface IBetterLeaves {
    Icon getIconBetterLeaves(int i, float f);

    Icon getIconBetterLeavesSnowed(int i, float f);

    Icon getIconFallingLeaves(int i);

    float getSpawnChanceFallingLeaves(int i);
}
