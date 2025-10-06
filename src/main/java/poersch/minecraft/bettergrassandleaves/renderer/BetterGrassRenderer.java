package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.BitSet;
import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import net.minecraft.BiomeGenBase;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingGrassFancyFX;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingGrassFastFX;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingTallGrassFancyFX;
import poersch.minecraft.bettergrassandleaves.entity.EntityMovingTallGrassFastFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterGrass;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterGrassBiome;

public class BetterGrassRenderer extends BetterBlockRenderer implements IBetterGrass {
    public static Icon[][] iconBetterGrass;
    public static Icon[] iconBetterGrassSide;
    public static Icon[] iconBetterMycelium;
    public static Icon[] iconBetterMyceliumSide;
    public static Icon[] iconBetterGrassSnowed;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterGrass = new Icon[][]{registerBlockIcons("better_grass"), null};
        if (iconBetterGrass[0] != null) {
            iconBetterGrass[1] = iconBetterGrass[0];
        } else {
            iconBetterGrass = new Icon[][]{registerBlockIcons("better_grass_short"), registerBlockIcons("better_grass_long")};
        }
        iconBetterGrassSnowed = registerBlockIcons("better_grass_snowed");
        iconBetterGrassSide = registerBlockIcons("better_grass_side");
        iconBetterMycelium = BetterGrassRenderer.registerBlockIcons("better_mycel");
        iconBetterMyceliumSide = BetterGrassRenderer.registerBlockIcons("better_mycel_side");
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterGrass.value) {
            return false;
        }
        int aboveBlockID = iBlockAccess.getBlockId(x, y + 1, z);
        if (aboveBlockID == Block.snow.blockID) {
            if ((Boolean) BetterGrassAndLeavesMod.renderSnowedGrass.value) {
                Object betterGrass = block instanceof IBetterGrass ? (IBetterGrass) block : this;
                long offset = getRandomOffsetForPosition(x, y, z);
                Icon icon = ((IBetterGrass) betterGrass).getIconBetterGrassSnowed(0, ((offset >> 12) & 15) / 15.0f);
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
        Object betterGrass2 = block instanceof IBetterGrass ? (IBetterGrass) block : this;
        long offset2 = getRandomOffsetForPosition(x, y, z);
        BiomeGenBase biomeGenForCoords = iBlockAccess.getBiomeGenForCoords(x, z);
        float averageGrassHeight = combineSpawnRates(biomeGenForCoords instanceof IBetterGrassBiome ? ((IBetterGrassBiome) biomeGenForCoords).getHeightBetterGrass() : (biomeGenForCoords.rainfall * 0.9f) + 0.05f, ((Float) BetterGrassAndLeavesMod.averageGrassHeight.value).floatValue());
        Icon icon2 = ((IBetterGrass) betterGrass2).getIconBetterGrass(((float) ((offset2 >> 4) & 15)) / 15.0f > averageGrassHeight ? 0 : 1, ((offset2 >> 12) & 15) / 15.0f);
        if (icon2 == null) {
            return false;
        }
        int color = block.colorMultiplier(iBlockAccess, x, y, z);
        float r = ((color >> 16) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        float g = ((color >> 8) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        float b = (color & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(r, g, b);
        if (!iBlockAccess.isBlockStandardFormOpaqueCube(x, y + 1, z) && BetterGrassAndLeavesMod.renderGrassSides.value > 0) {
            if (BetterGrassAndLeavesMod.renderGrassSides.value == 1) {
                Icon iconSide = ((IBetterGrass) betterGrass2).getIconBetterGrassSide(iBlockAccess.getBlockMetadata(x, y, z), ((offset2 >> 4) & 15) / 15.0f);
                if (iconSide != null) {
                    renderBetterGrassSidesFast(block, iBlockAccess, x, y, z, renderBlocks, iconSide);
                }
            } else {
                renderBetterGrassSidesFancy(block, iBlockAccess, x, y, z, icon2);
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
        if (BetterGrassAndLeavesMod.renderGrassFX.value == 0) {
            return false;
        }
        if (!world.isAirBlock(x, y + 1, z)) {
            if (world.getBlockId(x, y + 1, z) == Block.tallGrass.blockID) {
                long offset = getRandomOffsetForPosition(x, y + 1, z);
                Icon icon = Block.tallGrass.getIcon(0, world.getBlockMetadata(x, y + 1, z));
                double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.5d);
                double yS = y + 1.5d + (((((offset >> 20) & 15) / 15.0f) - 1.0d) * 0.2d);
                double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.5d);
                if (BetterGrassAndLeavesMod.renderGrassFX.value == 1) {
                    this.minecraft.effectRenderer.addEffect(new EntityMovingTallGrassFastFX(world, xS, yS, zS, -0.4f, Block.tallGrass.getMixedBrightnessForBlock(world, x, y + 1, z), block.colorMultiplier(world, x, y + 1, z), icon));
                    return false;
                }
                this.minecraft.effectRenderer.addEffect(new EntityMovingTallGrassFancyFX(world, entity.posX, yS, entity.posZ, -0.4f, Block.tallGrass.getMixedBrightnessForBlock(world, x, y + 1, z), Block.tallGrass.colorMultiplier(world, x, y + 1, z), icon, entity, block.blockID));
                return false;
            }
            return false;
        }
        IBetterGrass betterGrass = block instanceof IBetterGrass ? (IBetterGrass) block : this;
        long offset2 = getRandomOffsetForPosition(x, y, z);
        BiomeGenBase biomeGenForCoords = world.getBiomeGenForCoords(x, z);
        float averageGrassHeight = combineSpawnRates(biomeGenForCoords instanceof IBetterGrassBiome ? ((IBetterGrassBiome) biomeGenForCoords).getHeightBetterGrass() : (biomeGenForCoords.rainfall * 0.9f) + 0.05f, ((Float) BetterGrassAndLeavesMod.averageGrassHeight.value).floatValue());
        Icon icon2 = betterGrass.getIconBetterGrass(((float) ((offset2 >> 4) & 15)) / 15.0f > averageGrassHeight ? 0 : 1, ((offset2 >> 12) & 15) / 15.0f);
        if (icon2 == null) {
            return false;
        }
        double xS2 = x + 0.5d + (((((offset2 >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
        double yS2 = (y + 1.707d) - ((((offset2 >> 20) & 15) / 15.0f) * 0.16d);
        double zS2 = z + 0.5d + (((((offset2 >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
        boolean flipU = ((float) ((offset2 >> 8) & 15)) / 15.0f < 0.5f;
        int offsetIdx = Math.abs(x & (1 + ((z & 1) * 2) + ((y & 1) * 4)));
        if (BetterGrassAndLeavesMod.renderGrassFX.value == 1) {
            this.minecraft.effectRenderer.addEffect(new EntityMovingGrassFastFX(world, xS2, yS2, zS2, 0.46f, offsetIdx, block.getMixedBrightnessForBlock(world, x, y + 1, z), BetterGrassAndLeavesMod.betterGrassBrightness.value, block.colorMultiplier(world, x, y, z), icon2, flipU));
            return false;
        }
        this.minecraft.effectRenderer.addEffect(new EntityMovingGrassFancyFX(world, entity.posX, yS2, entity.posZ, 0.46f, offsetIdx, block.getMixedBrightnessForBlock(world, x, y + 1, z), BetterGrassAndLeavesMod.betterGrassBrightness.value, block.colorMultiplier(world, x, y, z), icon2, flipU, entity, block.blockID));
        return false;
    }

    public static void renderBetterGrassSidesFast(Block block, IBlockAccess iBlockAccess, int x, int y, int z, RenderBlocks renderBlocks, Icon icon) {
        float angleOffset = Math.abs((x & 1) + ((z & 1) * 2)) * 0.08f;
        if (Block.lightOpacity[iBlockAccess.getBlockId(x, y, z - 1)] == 0 && iBlockAccess.getBlockId(x, y - 1, z - 1) == block.blockID) {
            renderOvergrowthZNeg(icon, x + 0.5d, y + 1.0d, z, 0.5d, angleOffset - 3.1f, false, true);
        }
        if (Block.lightOpacity[iBlockAccess.getBlockId(x, y, z + 1)] == 0 && iBlockAccess.getBlockId(x, y - 1, z + 1) == block.blockID) {
            renderOvergrowthZPos(icon, x + 0.5d, y + 1.0d, z + 1.0d, 0.5d, angleOffset - 3.1f, false, true);
        }
        if (Block.lightOpacity[iBlockAccess.getBlockId(x - 1, y, z)] == 0 && iBlockAccess.getBlockId(x - 1, y - 1, z) == block.blockID) {
            renderOvergrowthXNeg(icon, x, y + 1.0d, z + 0.5d, 0.5d, angleOffset - 3.1f, false, true);
        }
        if (Block.lightOpacity[iBlockAccess.getBlockId(x + 1, y, z)] == 0 && iBlockAccess.getBlockId(x + 1, y - 1, z) == block.blockID) {
            renderOvergrowthXPos(icon, x + 1.0d, y + 1.0d, z + 0.5d, 0.5d, angleOffset - 3.1f, false, true);
        }
    }

    public static void renderBetterGrassSidesFancy(Block block, IBlockAccess iBlockAccess, int x, int y, int z, Icon icon) {
        float angleOffset = Math.abs((x & 1) + ((z & 1) * 2)) * 0.2f;
        int neighborBlockID = iBlockAccess.getBlockId(x, y, z - 1);
        if (neighborBlockID != block.blockID && !Block.opaqueCubeLookup[iBlockAccess.getBlockId(x, y + 1, z - 1)]) {
            renderOvergrowthZNeg(icon, x + 0.5d, y + 1.0d, z, 0.707d, angleOffset - 0.8f, false, false);
            if (!Block.opaqueCubeLookup[neighborBlockID]) {
                renderOvergrowthZNeg(icon, x + 0.5d, y + 1.0d, z, 0.707d, angleOffset - 2.8f, true, false);
            }
        }
        int neighborBlockID2 = iBlockAccess.getBlockId(x, y, z + 1);
        if (neighborBlockID2 != block.blockID && !Block.opaqueCubeLookup[iBlockAccess.getBlockId(x, y + 1, z + 1)]) {
            renderOvergrowthZPos(icon, x + 0.5d, y + 1.0d, z + 1.0d, 0.707d, angleOffset - 0.8f, false, false);
            if (!Block.opaqueCubeLookup[neighborBlockID2]) {
                renderOvergrowthZPos(icon, x + 0.5d, y + 1.0d, z + 1.0d, 0.707d, angleOffset - 2.8f, true, false);
            }
        }
        int neighborBlockID3 = iBlockAccess.getBlockId(x - 1, y, z);
        if (neighborBlockID3 != block.blockID && !Block.opaqueCubeLookup[iBlockAccess.getBlockId(x - 1, y + 1, z)]) {
            renderOvergrowthXNeg(icon, x, y + 1.0d, z + 0.5d, 0.707d, angleOffset - 0.8f, false, false);
            if (!Block.opaqueCubeLookup[neighborBlockID3]) {
                renderOvergrowthXNeg(icon, x, y + 1.0d, z + 0.5d, 0.707d, angleOffset - 2.8f, true, false);
            }
        }
        int neighborBlockID4 = iBlockAccess.getBlockId(x + 1, y, z);
        if (neighborBlockID4 != block.blockID && !Block.opaqueCubeLookup[iBlockAccess.getBlockId(x + 1, y + 1, z)]) {
            renderOvergrowthXPos(icon, x + 1.0d, y + 1.0d, z + 0.5d, 0.707d, angleOffset - 0.8f, false, false);
            if (!Block.opaqueCubeLookup[neighborBlockID4]) {
                renderOvergrowthXPos(icon, x + 1.0d, y + 1.0d, z + 0.5d, 0.707d, angleOffset - 2.8f, true, false);
            }
        }
    }

    public static void renderOvergrowthXNeg(Icon icon, double x, double y, double z, double radius, float angle, boolean flipU, boolean flipV) {
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!flipU) {
            minU = icon.getMinU();
            maxU = icon.getMaxU();
        } else {
            minU = icon.getMaxU();
            maxU = icon.getMinU();
        }
        if (!flipV) {
            minV = icon.getMinV();
            maxV = icon.getMaxV();
        } else {
            minV = icon.getMaxV();
            maxV = icon.getMinV();
        }
        double minX = x + (MathHelper.sin(angle) * radius * 2.0d);
        double maxY = y + (MathHelper.cos(angle) * radius * 2.0d);
        double minZ = z - radius;
        double maxZ = z + radius;
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(x, y, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(x, y, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(x, y, minZ, minU, maxV);
        tessellator.addVertexWithUV(x, y, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
    }

    public static void renderOvergrowthXPos(Icon icon, double x, double y, double z, double radius, float angle, boolean flipU, boolean flipV) {
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!flipU) {
            minU = icon.getMinU();
            maxU = icon.getMaxU();
        } else {
            minU = icon.getMaxU();
            maxU = icon.getMinU();
        }
        if (!flipV) {
            minV = icon.getMinV();
            maxV = icon.getMaxV();
        } else {
            minV = icon.getMaxV();
            maxV = icon.getMinV();
        }
        double minX = x - ((MathHelper.sin(angle) * radius) * 2.0d);
        double maxY = y + (MathHelper.cos(angle) * radius * 2.0d);
        double minZ = z - radius;
        double maxZ = z + radius;
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(x, y, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(x, y, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(x, y, minZ, minU, maxV);
        tessellator.addVertexWithUV(x, y, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
    }

    public static void renderOvergrowthZNeg(Icon icon, double x, double y, double z, double radius, float angle, boolean flipU, boolean flipV) {
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!flipU) {
            minU = icon.getMinU();
            maxU = icon.getMaxU();
        } else {
            minU = icon.getMaxU();
            maxU = icon.getMinU();
        }
        if (!flipV) {
            minV = icon.getMinV();
            maxV = icon.getMaxV();
        } else {
            minV = icon.getMaxV();
            maxV = icon.getMinV();
        }
        double minX = x - radius;
        double maxX = x + radius;
        double maxY = y + (MathHelper.cos(angle) * radius * 2.0d);
        double minZ = z + (MathHelper.sin(angle) * radius * 2.0d);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y, z, maxU, maxV);
        tessellator.addVertexWithUV(minX, y, z, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, y, z, minU, maxV);
        tessellator.addVertexWithUV(maxX, y, z, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
    }

    public static void renderOvergrowthZPos(Icon icon, double x, double y, double z, double radius, float angle, boolean flipU, boolean flipV) {
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!flipU) {
            minU = icon.getMinU();
            maxU = icon.getMaxU();
        } else {
            minU = icon.getMaxU();
            maxU = icon.getMinU();
        }
        if (!flipV) {
            minV = icon.getMinV();
            maxV = icon.getMaxV();
        } else {
            minV = icon.getMaxV();
            maxV = icon.getMinV();
        }
        double minX = x - radius;
        double maxX = x + radius;
        double maxY = y + (MathHelper.cos(angle) * radius * 2.0d);
        double minZ = z - ((MathHelper.sin(angle) * radius) * 2.0d);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y, z, maxU, maxV);
        tessellator.addVertexWithUV(minX, y, z, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, y, z, minU, maxV);
        tessellator.addVertexWithUV(maxX, y, z, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
    }

    @Override
    public Icon getIconBetterGrass(int metadata, float randomIndex) {
        if (iconBetterGrass == null || iconBetterGrass[metadata] == null) {
            return null;
        }
        return iconBetterGrass[metadata][(int) ((randomIndex * (iconBetterGrass[metadata].length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterGrassSnowed(int metadata, float randomIndex) {
        if (iconBetterGrassSnowed == null) {
            return null;
        }
        return iconBetterGrassSnowed[(int) ((randomIndex * (iconBetterGrassSnowed.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterGrassSide(int metadata, float randomIndex) {
        if (iconBetterGrassSide == null) {
            return null;
        }
        return iconBetterGrassSide[(int) ((randomIndex * (iconBetterGrassSide.length - 1)) + 0.5f)];
    }
}
