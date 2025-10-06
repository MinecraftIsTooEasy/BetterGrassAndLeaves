package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterLeaves;
import poersch.minecraft.bettergrassandleaves.renderer.BetterLeavesRenderer;

@Mixin(BlockLeavesBase.class)
public class BlockLeavesBaseMixin extends Block implements IBetterLeaves {
    protected BlockLeavesBaseMixin(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Override
    public Icon getIconBetterLeaves(int metadata, float randomIndex) {
        return null;
    }

    @Override
    public Icon getIconBetterLeavesSnowed(int metadata, float randomIndex) {
        if (BetterLeavesRenderer.iconBetterLeavesSnowed == null || ReflectHelper.dyCast(this) != Block.leaves) {
            return null;
        }
        return BetterLeavesRenderer.iconBetterLeavesSnowed[(int) ((randomIndex * (BetterLeavesRenderer.iconBetterLeavesSnowed.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconFallingLeaves(int metadata) {
        return getIcon(0, metadata);
    }

    @Override
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.008f;
    }
}
