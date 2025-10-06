package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterLilyPad;

public class BetterLilyPadRenderer extends BetterBlockRenderer implements IBetterLilyPad {
    public static Icon[] iconBetterLilyPadFlower;
    public static Icon[] iconBetterLilyPadRoots;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterLilyPadFlower = registerBlockIcons("better_lilypad_flower");
        iconBetterLilyPadRoots = registerBlockIcons("better_lilypad_roots");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        Icon icon;
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterLilyPads.value) {
            return false;
        }
        long offset = getRandomOffsetForPosition(x, y, z);
        double offsetX = tessellator.xOffset;
        double offsetY = tessellator.yOffset;
        double offsetZ = tessellator.zOffset;
        Tessellator var10000 = tessellator;
        var10000.xOffset += ((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d;
        var10000.yOffset += 0.015625d + (((((offset >> 20) & 15) / 15.0f) - 0.5d) * 0.007d);
        var10000.zOffset += ((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d;
        block.setBlockBoundsBasedOnStateAndNeighbors(iBlockAccess, x, y, z);
        renderBlocks.XXXsetRenderBoundsFromBlock(block);
        renderBlocks.renderBlockLilyPad(block, x, y, z);
        IBetterLilyPad betterLilyPad = block instanceof IBetterLilyPad ? (IBetterLilyPad) block : this;
        boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
        setRotationalOffsetMap(offsetMap16px, x, y, z);
        tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterLilyPadsBrightness.value, BetterGrassAndLeavesMod.betterLilyPadsBrightness.value, BetterGrassAndLeavesMod.betterLilyPadsBrightness.value);
        if (((offset >> 4) & 15) / 15.0f < BetterGrassAndLeavesMod.lilyPadFlowerPopulation.value && (icon = betterLilyPad.getIconBetterLilyPadFlower(0, ((offset >> 12) & 15) / 15.0f)) != null) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z));
            renderCrossedQuadsY(icon, x + 0.5d, y + 0.487025d, z + 0.5d, flipU, false);
        }
        Icon icon2 = betterLilyPad.getIconBetterLilyPadRoots(0, ((offset >> 12) & 15) / 15.0f);
        if (icon2 != null) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y - 1, z));
            renderCrossedQuadsY(icon2, x + 0.5d, y - 0.455775d, z + 0.5d, flipU, false);
        }
        tessellator.xOffset = offsetX;
        tessellator.yOffset = offsetY;
        tessellator.zOffset = offsetZ;
        return true;
    }

    @Override
    public Icon getIconBetterLilyPadFlower(int metadata, float randomIndex) {
        if (iconBetterLilyPadFlower == null) {
            return null;
        }
        return iconBetterLilyPadFlower[(int) ((randomIndex * (iconBetterLilyPadFlower.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterLilyPadRoots(int metadata, float randomIndex) {
        if (iconBetterLilyPadRoots == null) {
            return null;
        }
        return iconBetterLilyPadRoots[(int) ((randomIndex * (iconBetterLilyPadRoots.length - 1)) + 0.5f)];
    }
}
