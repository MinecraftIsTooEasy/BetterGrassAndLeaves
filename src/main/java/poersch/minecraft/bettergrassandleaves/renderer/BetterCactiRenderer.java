package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterCactus;

public class BetterCactiRenderer extends BetterBlockRenderer implements IBetterCactus {
    public static Icon[] iconBetterCactus;
    public static Icon[] iconBetterCactusArm;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterCactus = registerBlockIcons("better_cactus");
        iconBetterCactusArm = registerBlockIcons("better_cactus_arm");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterCacti.value) {
            return false;
        }
        IBetterCactus betterCactus = block instanceof IBetterCactus ? (IBetterCactus) block : this;
        long offset = getRandomOffsetForPosition(x, y, z);
        double xOffset = tessellator.xOffset;
        double zOffset = tessellator.zOffset;
        Tessellator var10000 = tessellator;
        var10000.xOffset += (((offset & 15) / 15.0f) - 0.5d) * 0.08d;
        var10000.zOffset += ((((offset >> 4) & 15) / 15.0f) - 0.5d) * 0.08d;
        block.setBlockBoundsBasedOnStateAndNeighbors(iBlockAccess, x, y, z);
        renderBlocks.XXXsetRenderBoundsFromBlock(block);
        renderBlocks.renderBlockCactus(block, x, y, z);
        tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterCactiBrightness.value * 0.78f, BetterGrassAndLeavesMod.betterCactiBrightness.value * 0.78f, BetterGrassAndLeavesMod.betterCactiBrightness.value * 0.78f);
        Icon icon = betterCactus.getIconBetterCactus(0, ((offset >> 8) & 15) / 15.0f);
        if (icon != null) {
            boolean flipU = ((float) ((offset >> 12) & 15)) / 15.0f < 0.5f;
            setRotationalOffsetMap(offsetMap32px, x, y, z);
            renderCrossedQuadsY(icon, x + 0.5d, y + 0.5d, z + 0.5d, flipU, false);
        }
        Icon icon2 = betterCactus.getIconBetterCactusArm(0, ((offset >> 12) & 15) / 15.0f);
        if (icon2 != null) {
            double uS = (((offset & 15) / 15.0f) - 0.5d) * 0.3d;
            double hS = (((offset >> 4) & 15) / 15.0f) * 0.16d;
            double vS = ((((offset >> 8) & 15) / 15.0f) - 0.5d) * 0.3d;
            boolean flipU2 = ((float) ((offset >> 16) & 15)) / 15.0f < 0.5f;
            setRotationalOffsetMap(offsetMap16px, x, y, z);
            switch ((int) (((((offset >> 8) & 15) / 15.0f) * 3.0f) + 0.5f)) {
                case 0:
                    tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x - 1, y, z));
                    renderCrossedQuadsX(icon2, (x - 0.4714d) + hS, y + 0.5d + vS, z + 0.5d + uS, flipU2, true);
                    break;
                case 1:
                    tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x + 1, y, z));
                    renderCrossedQuadsX(icon2, (x + 1.4714d) - hS, y + 0.5d + vS, z + 0.5d + uS, flipU2, false);
                    break;
                case 2:
                    tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z - 1));
                    renderCrossedQuadsZ(icon2, x + 0.5d + uS, y + 0.5d + vS, (z - 0.4714d) + hS, flipU2, true);
                    break;
                default:
                    tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z + 1));
                    renderCrossedQuadsZ(icon2, x + 0.5d + uS, y + 0.5d + vS, (z + 1.4714d) - hS, flipU2, false);
                    break;
            }
        }
        tessellator.xOffset = xOffset;
        tessellator.zOffset = zOffset;
        return true;
    }

    @Override // net.minecraft.plugin.bettergrassandleaves.interfaces.IBetterCactus
    public Icon getIconBetterCactus(int metadata, float randomIndex) {
        if (iconBetterCactus == null) {
            return null;
        }
        return iconBetterCactus[(int) ((randomIndex * (iconBetterCactus.length - 1)) + 0.5f)];
    }

    @Override // net.minecraft.plugin.bettergrassandleaves.interfaces.IBetterCactus
    public Icon getIconBetterCactusArm(int metadata, float randomIndex) {
        if (iconBetterCactusArm == null) {
            return null;
        }
        return iconBetterCactusArm[(int) ((randomIndex * (iconBetterCactusArm.length - 1)) + 0.5f)];
    }
}
