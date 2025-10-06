package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterNetherrack;

public class BetterNetherrackRenderer extends BetterBlockRenderer implements IBetterNetherrack {
    public static Icon[] iconBetterNetherrack;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterNetherrack = registerBlockIcons("better_netherrack");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterNetherrack.value || !iBlockAccess.isAirBlock(x, y - 1, z)) {
            return false;
        }
        IBetterNetherrack betterNetherrack = block instanceof IBetterNetherrack ? (IBetterNetherrack) block : this;
        long offset = getRandomOffsetForPosition(x, y, z);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y - 1, z));
        tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterNetherrackBrightness.value, BetterGrassAndLeavesMod.betterNetherrackBrightness.value, BetterGrassAndLeavesMod.betterNetherrackBrightness.value);
        Icon icon = betterNetherrack.getIconBetterNetherrack(0, ((offset >> 12) & 15) / 15.0f);
        if (icon == null) {
            return false;
        }
        double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
        double yS = (y - 0.707d) + ((((offset >> 20) & 15) / 15.0f) * 0.16d);
        double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
        boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
        setRotationalOffsetMap(offsetMap24px, x, y, z);
        renderCrossedQuadsY(icon, xS, yS, zS, flipU, false);
        return false;
    }

    @Override
    public Icon getIconBetterNetherrack(int metadata, float randomIndex) {
        if (iconBetterNetherrack == null) {
            return null;
        }
        return iconBetterNetherrack[(int) ((randomIndex * (iconBetterNetherrack.length - 1)) + 0.5f)];
    }
}
