package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.BitSet;
import java.util.Random;
import net.minecraft.Block;
import net.minecraft.Material;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityRisingBubbleSpawnerFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterCorals;

public class BetterCoralsRenderer extends BetterBlockRenderer implements IBetterCorals {
    public static Icon[] iconBetterCorals;
    public static Icon[] iconBetterCrust;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterCorals = registerBlockIcons("better_coral");
        iconBetterCrust = registerBlockIcons("better_crust");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterCorals.value || BetterGrassAndLeavesMod.coralPopulation.value == 0.0f || !BetterGrassAndLeavesMod.coralHostingBiomes.value.get(iBlockAccess.getBiomeGenForCoords(x, z).biomeID) || y < BetterGrassAndLeavesMod.maximumCoralDepth.value || y > BetterGrassAndLeavesMod.minimumCoralDepth.value) {
            return false;
        }
        IBetterCorals betterCorals = block instanceof IBetterCorals ? (IBetterCorals) block : this;
        if (iBlockAccess.isAirBlock(x, y + 1, z)) {
            return false;
        }
        long offset = getRandomOffsetForPosition(x, y, z);
        if (((offset >> 24) & 15) / 15.0f > BetterGrassAndLeavesMod.coralPopulation.value) {
            return false;
        }
        Icon iconCoral = betterCorals.getIconBetterCoral(0, ((offset >> 20) & 15) / 15.0f);
        Icon iconCrust = betterCorals.getIconBetterCrust(0, ((offset >> 24) & 15) / 15.0f);
        if (iconCoral == null || iconCrust == null) {
            return false;
        }
        tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterCoralsBrightness.value, BetterGrassAndLeavesMod.betterCoralsBrightness.value, BetterGrassAndLeavesMod.betterCoralsBrightness.value);
        double uS = (((offset & 15) / 15.0f) - 0.5d) * 0.3d;
        double hS = (((offset >> 4) & 15) / 15.0f) * 0.16d;
        double vS = ((((offset >> 8) & 15) / 15.0f) - 0.5d) * 0.3d;
        boolean flipU = ((float) ((offset >> 12) & 15)) / 15.0f < 0.5f;
        boolean flipV = ((float) ((offset >> 16) & 15)) / 15.0f < 0.5f;
        setRotationalOffsetMap(offsetMap16px, x, y, z);
        if (iBlockAccess.getBlockMaterial(x, y + 1, z) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
            setHeightOffsetMap(0.707d, x, z);
            renderFaceYPos(iconCrust, x + uS, y, z + vS, flipU, flipV);
            renderCrossedQuadsY(iconCoral, x + 0.5d + uS, (y + 1.4714d) - hS, z + 0.5d + uS, flipU, false);
        }
        if (iBlockAccess.getBlockMaterial(x - 1, y, z) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x - 1, y, z));
            setHeightOffsetMap(0.707d, z, y);
            renderFaceXNeg(iconCrust, x, y + vS, z - uS, flipU, flipV);
            renderCrossedQuadsX(iconCoral, (x - 0.4714d) + hS, y + 0.5d + vS, z + 0.5d + uS, flipU, true);
        }
        if (iBlockAccess.getBlockMaterial(x + 1, y, z) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x + 1, y, z));
            setHeightOffsetMap(0.707d, z, y);
            renderFaceXPos(iconCrust, x, y - uS, z - vS, flipU, flipV);
            renderCrossedQuadsX(iconCoral, (x + 1.4714d) - hS, y + 0.5d + vS, z + 0.5d + uS, flipU, false);
        }
        if (iBlockAccess.getBlockMaterial(x, y, z - 1) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z - 1));
            setHeightOffsetMap(0.707d, x, y);
            renderFaceZNeg(iconCrust, x + vS, y + uS, z, flipU, flipV);
            renderCrossedQuadsZ(iconCoral, x + 0.5d + uS, y + 0.5d + vS, (z - 0.4714d) + hS, flipU, true);
        }
        if (iBlockAccess.getBlockMaterial(x, y, z + 1) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z + 1));
            setHeightOffsetMap(0.707d, x, y);
            renderFaceZPos(iconCrust, x + uS, y - vS, z, flipU, flipV);
            renderCrossedQuadsZ(iconCoral, x + 0.5d + uS, y + 0.5d + vS, (z + 1.4714d) - hS, flipU, false);
        }
        if (iBlockAccess.getBlockMaterial(x, y - 1, z) == Material.water) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y - 1, z));
            setHeightOffsetMap(0.707d, x, z);
            renderFaceYNeg(iconCrust, x - uS, y, z + vS, flipU, flipV);
            renderCrossedQuadsY(iconCoral, x + 0.5d + uS, (y - 0.4714d) + hS, z + 0.5d + uS, flipU, true);
            return false;
        }
        return false;
    }

    @Override
    public boolean onRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterCorals.value || BetterGrassAndLeavesMod.coralPopulation.value == 0.0f || !BetterGrassAndLeavesMod.coralHostingBiomes.value.get(world.getBiomeGenForCoords(x, z).biomeID) || y < BetterGrassAndLeavesMod.maximumCoralDepth.value || world.getBlockMaterial(x, y + 1, z) != Material.water) {
            return false;
        }
        long offset = getRandomOffsetForPosition(x, y, z);
        if (((offset >> 24) & 15) / 15.0f <= BetterGrassAndLeavesMod.coralPopulation.value && ((offset >> 12) & 15) / 15.0f > 0.7f && random.nextFloat() > 1.0f - (Float) BetterGrassAndLeavesMod.bubblesFXSpawnRate.value && (((int) (world.getTotalWorldTime() / 60)) & 4) == ((int) (((((offset >> 8) & 15) / 15.0f) * 7.0f) + 0.5f))) {
            double xS = x + 0.4d + (random.nextDouble() * 0.2d) + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.4d);
            double yS = y + 1.2d;
            double zS = z + 0.4d + (random.nextDouble() * 0.2d) + (((((offset >> 20) & 15) / 15.0f) - 0.5d) * 0.4d);
            float streamAngleOffset = (((offset >> 24) & 15) / 15.0f) * 6.0f;
            this.minecraft.effectRenderer.addEffect(new EntityRisingBubbleSpawnerFX(world, xS, yS, zS, streamAngleOffset, 9));
            return false;
        }
        return false;
    }

    @Override
    public Icon getIconBetterCoral(int metadata, float randomIndex) {
        if (iconBetterCorals == null) {
            return null;
        }
        return iconBetterCorals[(int) ((randomIndex * (iconBetterCorals.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterCrust(int metadata, float randomIndex) {
        if (iconBetterCrust == null) {
            return null;
        }
        return iconBetterCrust[(int) ((randomIndex * (iconBetterCrust.length - 1)) + 0.5f)];
    }
}
