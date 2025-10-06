package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.Random;
import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.RenderBlocks;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterLeaves;

public class BetterLeavesAdvancedRenderer extends BetterBlockRenderer {

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        Icon icon;
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterLeaves.value) {
            return false;
        }
        long offset = getRandomOffsetForPosition(x, y, z);
        int blockBrightness = BetterLeavesRenderer.blockHasVisibleSide(block, iBlockAccess, x, y, z);
        if (blockBrightness <= -1) {
            return true;
        }
        IBetterLeaves betterLeaves = block instanceof IBetterLeaves ? (IBetterLeaves) block : (IBetterLeaves) BetterBlockRenderer.leavesRenderer.get(0);
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        float randomIndex = ((offset >> 12) & 15) / 15.0f;
        Icon icon2 = betterLeaves.getIconBetterLeaves(metadata, randomIndex);
        if (icon2 == null) {
            icon2 = BetterLeavesRenderer.getIconFromMap(block.getIcon(0, metadata).getIconName(), randomIndex);
            if (icon2 == null) {
                icon2 = block.getIcon(0, metadata);
            }
        }
        double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.45d);
        double yS = y + 0.5d + (((((offset >> 20) & 15) / 15.0f) - 0.5d) * 0.3d);
        double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.45d);
        int color = block.colorMultiplier(iBlockAccess, x, y, z);
        float r = ((color >> 16) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        float g = ((color >> 8) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        float b = (color & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
        boolean flipV = ((float) ((offset >> 4) & 15)) / 15.0f < 0.5f;
        setRotationalOffsetMap(offsetMap32px, x, y, z);
        if (!block.isAlwaysOpaqueStandardFormCube()) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z));
        } else {
            tessellator.setBrightness(blockBrightness);
        }
        if (Minecraft.isAmbientOcclusionEnabled()) {
            renderCrossedQuadsShadedX(icon2, xS + (flipU ? -0.15d : 0.15d), yS, zS, flipU, flipV, r, g, b, r * 0.58f, g * 0.58f, b * 0.58f);
            renderCrossedQuadsShadedY(icon2, xS, yS, zS, flipU, flipV, r, g, b, r * 0.58f, g * 0.58f, b * 0.58f);
            renderCrossedQuadsShadedZ(icon2, xS, yS, zS + (flipV ? -0.15d : 0.15d), flipU, flipV, r, g, b, r * 0.58f, g * 0.58f, b * 0.58f);
        } else {
            tessellator.setColorOpaque_F(r * 0.78f, g * 0.78f, b * 0.78f);
            renderCrossedQuadsX(icon2, xS + (flipU ? -0.15d : 0.15d), yS, zS, flipU, flipV);
            renderCrossedQuadsY(icon2, xS, yS, zS, flipU, flipV);
            renderCrossedQuadsZ(icon2, xS, yS, zS + (flipV ? -0.15d : 0.15d), flipU, flipV);
        }
        if (!(Boolean) BetterGrassAndLeavesMod.renderSnowedLeaves.value || iBlockAccess.getBlockId(x, y + 1, z) != Block.snow.blockID || (icon = betterLeaves.getIconBetterLeavesSnowed(0, ((offset >> 12) & 15) / 15.0f)) == null) {
            return true;
        }
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            renderCrossedQuadsShadedY(icon, xS, yS, zS, flipU, false, 1.0f, 1.0f, 1.0f, 0.58f, 0.58f, 0.58f);
            return true;
        }
        tessellator.setColorOpaque_F(0.78f, 0.78f, 0.78f);
        renderCrossedQuadsY(icon, xS, yS, zS, flipU, false);
        return true;
    }

    @Override
    public boolean onRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        return BetterBlockRenderer.leavesRenderer.get(0).onRandomDisplayTick(block, world, x, y, z, random);
    }
}
