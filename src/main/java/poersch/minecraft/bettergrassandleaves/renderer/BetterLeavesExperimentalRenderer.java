package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;

public class BetterLeavesExperimentalRenderer extends BetterBlockRenderer {

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterLeaves.value) {
            return false;
        }
        Icon icon = block.getIcon(iBlockAccess.getBlockMetadata(x, y, z), 0);
        int color = block.colorMultiplier(iBlockAccess, x, y, z);
        float r = ((color >> 16) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        float g = ((color >> 8) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        float b = (color & 255) * 0.00392f * BetterGrassAndLeavesMod.betterLeavesBrightness.value;
        if (iBlockAccess.isAirBlock(x, y + 1, z)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
            for (int n = 1; n < 5; n++) {
                float brightness = 0.5f + (0.1f * n);
                tessellator.setColorOpaque_F(r * brightness, g * brightness, b * brightness);
                renderBlocks.renderFaceYPos(block, x, y + (n * 0.02d), z, icon);
            }
        }
        if (iBlockAccess.isAirBlock(x, y - 1, z)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y - 1, z));
            for (int n2 = 1; n2 < 5; n2++) {
                float brightness2 = 0.5f + (0.1f * n2);
                tessellator.setColorOpaque_F(r * brightness2, g * brightness2, b * brightness2);
                renderBlocks.renderFaceYNeg(block, x, y - (n2 * 0.02d), z, icon);
            }
        }
        if (iBlockAccess.isAirBlock(x + 1, y, z)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x + 1, y, z));
            for (int n3 = 1; n3 < 5; n3++) {
                float brightness3 = 0.5f + (0.1f * n3);
                tessellator.setColorOpaque_F(r * brightness3, g * brightness3, b * brightness3);
                renderBlocks.renderFaceXPos(block, x + (n3 * 0.02d), y, z, icon);
            }
        }
        if (iBlockAccess.isAirBlock(x - 1, y, z)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x - 1, y, z));
            for (int n4 = 1; n4 < 5; n4++) {
                float brightness4 = 0.5f + (0.1f * n4);
                tessellator.setColorOpaque_F(r * brightness4, g * brightness4, b * brightness4);
                renderBlocks.renderFaceXNeg(block, x - (n4 * 0.02d), y, z, icon);
            }
        }
        if (iBlockAccess.isAirBlock(x, y, z + 1)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z + 1));
            for (int n5 = 1; n5 < 5; n5++) {
                float brightness5 = 0.5f + (0.1f * n5);
                tessellator.setColorOpaque_F(r * brightness5, g * brightness5, b * brightness5);
                renderBlocks.renderFaceZPos(block, x, y, z + (n5 * 0.02d), icon);
            }
        }
        if (iBlockAccess.isAirBlock(x, y, z - 1)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z - 1));
            for (int n6 = 1; n6 < 5; n6++) {
                float brightness6 = 0.5f + (0.1f * n6);
                tessellator.setColorOpaque_F(r * brightness6, g * brightness6, b * brightness6);
                renderBlocks.renderFaceZNeg(block, x, y, z - (n6 * 0.02d), icon);
            }
            return false;
        }
        return false;
    }
}
