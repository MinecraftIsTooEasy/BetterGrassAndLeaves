package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingGrassFancyFX;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingGrassFastFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterMycelium;

public class BetterMyceliumRenderer extends BetterBlockRenderer implements IBetterMycelium {
    public static Icon[] iconBetterMycelium;
    public static Icon[] iconBetterMyceliumSide;
    public static Icon[] iconBetterMyceliumSnowed;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterMycelium = registerBlockIcons("better_mycel");
        iconBetterMyceliumSide = registerBlockIcons("better_mycel_side");
        iconBetterMyceliumSnowed = registerBlockIcons("better_mycel_snowed");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterGrass.value) {
            return false;
        }
        int aboveBlockID = iBlockAccess.getBlockId(x, y + 1, z);
        if (aboveBlockID == Block.snow.blockID) {
            if (BetterGrassAndLeavesMod.renderSnowedGrass.value) {
                IBetterMycelium betterMycelium = block instanceof IBetterMycelium ? (IBetterMycelium) block : this;
                long offset = getRandomOffsetForPosition(x, y, z);
                Icon icon = betterMycelium.getIconBetterMyceliumSnowed(0, ((offset >> 12) & 15) / 15.0f);
                if (icon == null) {
                    return false;
                }
                tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
                tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
                double yS = (y + 1.707d) - ((((offset >> 20) & 15) / 15.0f) * 0.16d);
                double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
                boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
                setRotationalOffsetMap(offsetMap24px, x, y, z);
                renderCrossedQuadsY(icon, xS, yS, zS, flipU, false);
                return false;
            }
            return false;
        }
        IBetterMycelium betterMycelium2 = block instanceof IBetterMycelium ? (IBetterMycelium) block : this;
        long offset2 = getRandomOffsetForPosition(x, y, z);
        Icon icon2 = betterMycelium2.getIconBetterMycelium(0, ((offset2 >> 12) & 15) / 15.0f);
        if (icon2 == null) {
            return false;
        }
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        if (!iBlockAccess.isBlockStandardFormOpaqueCube(x, y + 1, z) && BetterGrassAndLeavesMod.renderGrassSides.value > 0) {
            if (BetterGrassAndLeavesMod.renderGrassSides.value == 1) {
                Icon iconSide = betterMycelium2.getIconBetterMyceliumSide(iBlockAccess.getBlockMetadata(x, y, z), ((offset2 >> 4) & 15) / 15.0f);
                if (iconSide != null) {
                    BetterGrassRenderer.renderBetterGrassSidesFast(block, iBlockAccess, x, y, z, renderBlocks, iconSide);
                }
            } else {
                BetterGrassRenderer.renderBetterGrassSidesFancy(block, iBlockAccess, x, y, z, icon2);
            }
        }
        if (BetterGrassAndLeavesMod.allowBetterGrass.value.get(aboveBlockID)) {
            double xS2 = x + 0.5d + (((((offset2 >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
            double yS2 = (y + 1.707d) - ((((offset2 >> 20) & 15) / 15.0f) * 0.16d);
            double zS2 = z + 0.5d + (((((offset2 >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
            boolean flipU2 = ((float) ((offset2 >> 8) & 15)) / 15.0f < 0.5f;
            setRotationalOffsetMap(offsetMap24px, x, y, z);
            renderCrossedQuadsY(icon2, xS2, yS2, zS2, flipU2, false);
            return false;
        }
        return false;
    }

    @Override
    public boolean onEntityWalking(Block block, World world, int x, int y, int z, Entity entity) {
        if (BetterGrassAndLeavesMod.renderGrassFX.value == 0 || !world.isAirBlock(x, y + 1, z)) {
            return false;
        }
        IBetterMycelium betterMycelium = block instanceof IBetterMycelium ? (IBetterMycelium) block : this;
        long offset = getRandomOffsetForPosition(x, y, z);
        Icon icon = betterMycelium.getIconBetterMycelium(0, ((offset >> 12) & 15) / 15.0f);
        if (icon == null) {
            return false;
        }
        double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
        double yS = (y + 1.707d) - ((((offset >> 20) & 15) / 15.0f) * 0.16d);
        double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
        boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
        int offsetIdx = Math.abs(x & (1 + ((z & 1) * 2) + ((y & 1) * 4)));
        if (BetterGrassAndLeavesMod.renderGrassFX.value == 1) {
            this.minecraft.effectRenderer.addEffect(new EntityMovingGrassFastFX(world, xS, yS, zS, 0.46f, offsetIdx, block.getMixedBrightnessForBlock(world, x, y + 1, z), ((Float) BetterGrassAndLeavesMod.betterGrassBrightness.value).floatValue(), block.colorMultiplier(world, x, y, z), icon, flipU));
            return false;
        }
        this.minecraft.effectRenderer.addEffect(new EntityMovingGrassFancyFX(world, entity.posX, yS, entity.posZ, 0.46f, offsetIdx, block.getMixedBrightnessForBlock(world, x, y + 1, z), ((Float) BetterGrassAndLeavesMod.betterGrassBrightness.value).floatValue(), block.colorMultiplier(world, x, y, z), icon, flipU, entity, block.blockID));
        return false;
    }

    @Override
    public Icon getIconBetterMyceliumSide(int metadata, float randomIndex) {
        if (iconBetterMyceliumSide == null) {
            return null;
        }
        return iconBetterMyceliumSide[(int) ((randomIndex * (iconBetterMyceliumSide.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterMycelium(int metadata, float randomIndex) {
        if (iconBetterMycelium == null) {
            return null;
        }
        return iconBetterMycelium[(int) ((randomIndex * (iconBetterMycelium.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterMyceliumSnowed(int metadata, float randomIndex) {
        if (iconBetterMyceliumSnowed == null) {
            return null;
        }
        return iconBetterMyceliumSnowed[(int) ((randomIndex * (iconBetterMyceliumSnowed.length - 1)) + 0.5f)];
    }
}
