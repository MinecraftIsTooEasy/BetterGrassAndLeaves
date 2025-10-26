package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.EnumParticle;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import net.moddedmite.bettergarassandleaves.util.EnumExtra;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.ResourceHelper;
import poersch.minecraft.util.texture.ITextureLoadingCallback;

import java.util.Random;

public abstract class BetterBlockRenderer {
    private static IconRegister currentIconRegister;
    protected Minecraft minecraft = Minecraft.getMinecraft();
    private static double[][] currentRotationalOffsetMap;
    private static double[] currentHeightOffsetMap;
    private static int currentRotationalOffsetIndex;
    private static double currentPolyRadius;
    private static double currentHeightOffset;
    public static BetterBlockRendererList grassRenderer = new BetterBlockRendererList("better-grass", 5).setRendererChoice(BetterGrassAndLeavesMod.currentGrassRenderer.value).addRenderer(new BetterGrassRenderer(), new BetterGrassExperimentalRenderer());
    public static BetterBlockRendererList myceliumRenderer = new BetterBlockRendererList("better-mycelium", 5).addRenderer(new BetterMyceliumRenderer());
    public static BetterBlockRendererList leavesRenderer = new BetterBlockRendererList("better-leaves", 3).setRendererChoice(BetterGrassAndLeavesMod.currentLeavesRenderer.value).addRenderer(new BetterLeavesRenderer(), new BetterLeavesAdvancedRenderer(), new BetterLeavesExperimentalRenderer());
    public static BetterBlockRendererList cactusRenderer = new BetterBlockRendererList("better-cacti", 1).addRenderer(new BetterCactiRenderer());
    public static BetterBlockRendererList seaweedRenderer = new BetterBlockRendererList("better-seaweed", 1).addRenderer(new BetterSeaweedRenderer());
    public static BetterBlockRendererList coralsRenderer = new BetterBlockRendererList("better-corals", 3).addRenderer(new BetterCoralsRenderer());
    public static BetterBlockRendererList soulsandRenderer = new BetterBlockRendererList("better-soulsand", 2).addRenderer(new BetterSoulsandRenderer());
    public static BetterBlockRendererList footprintsRenderer = new BetterBlockRendererList("better-footprints", 4).addRenderer(new BetterFootprintsRenderer());
    public static BetterBlockRendererList lilyPadRenderer = new BetterBlockRendererList("better-lily-pad", 1).addRenderer(new BetterLilyPadRenderer());
    public static BetterBlockRendererList netherrackRenderer = new BetterBlockRendererList("better-netherrack", 1).addRenderer(new BetterNetherrackRenderer());
    public static BetterBlockRendererList waterRenderer = new BetterBlockRendererList("better-water", 2).assignToParticleSpawner(EnumParticle.suspended).addRenderer(new BetterWaterRenderer());
    public static BetterBlockRendererList bloodRenderer = new BetterBlockRendererList("better-blood", 0).assignToParticleSpawner(EnumExtra.blood).addRenderer(new BetterBloodRenderer());
    public static BetterBlockRendererList ladderRenderer = new BetterBlockRendererList("better-ladder", 1).addRenderer(new BetterLadderRenderer());
    public static BetterBlockRendererList oreRenderer = new BetterBlockRendererList("better-ore", 1).addRenderer(new BetterOreRenderer());
    private static final String[][] domains = {new String[]{"minecraft"}, new String[]{"minecraft", "bettergrassandleaves"}, new String[]{"bettergrassandleaves"}};
    private static int domainIndex = 0;
    private static boolean textureGeneratorAllowed = false;
    public static Tessellator tessellator = Tessellator.instance;
    public static final double[][] offsetMap16px = getRotationalOffsetMap(0.4714d, 0.012d);
    public static final double[][] offsetMap24px = getRotationalOffsetMap(0.707d, 0.012d);
    public static final double[][] offsetMap32px = getRotationalOffsetMap(0.9d, 0.012d);
    public static boolean registerIconsBlockWise = true;

    public static void initiateIconRegistration(IconRegister iconRegister) {
        currentIconRegister = iconRegister;
        switch (ResourceHelper.getCurrentResourcepack().equals("Default") ? 4 : ((Integer) BetterGrassAndLeavesMod.textureSource.value).intValue()) {
            case 0:
                domainIndex = 0;
                textureGeneratorAllowed = false;
                break;
            case 2:
                domainIndex = 1;
                textureGeneratorAllowed = true;
                break;
            case 3:
                domainIndex = 1;
                textureGeneratorAllowed = false;
                break;
            case 4:
                domainIndex = 2;
                textureGeneratorAllowed = false;
                break;
            case 1:
            default:
                domainIndex = 0;
                textureGeneratorAllowed = true;
                break;
        }
    }

    private static double[][] getRotationalOffsetMap(double radius, double step) {
        double[][] offsetMap = new double[9][4];
        offsetMap[0][0] = radius;
        double angle = 3.141592653589793d * (0.25d - (step * 4.0d));
        for (int n = 1; n < 9; n++) {
            angle += 3.141592653589793d * step;
            offsetMap[n][0] = Math.sin(angle) * radius;
            offsetMap[n][1] = Math.cos(angle) * radius;
            offsetMap[n][2] = Math.sin(angle + 1.5707963267948966d) * radius;
            offsetMap[n][3] = Math.cos(angle + 1.5707963267948966d) * radius;
        }
        return offsetMap;
    }

    protected static void setRotationalOffsetMap(double[][] offsetMap, int x, int y, int z) {
        currentRotationalOffsetIndex = 1 + Math.abs((x & 1) + ((z & 1) * 2) + ((y & 1) * 4));
        currentRotationalOffsetMap = offsetMap;
    }

    private static double[] getHeightOffsetMap(double min, double step) {
        double[] offsetMap = new double[5];
        offsetMap[0] = min;
        for (int n = 1; n < 5; n++) {
            offsetMap[n] = min;
            System.out.println(offsetMap[n]);
            min += step;
        }
        return offsetMap;
    }

    protected static void setHeightOffsetMap(double radius, int u, int v) {
        currentPolyRadius = radius;
        currentHeightOffset = 0.06d + (0.03d * Math.abs((u & 1) + ((v & 1) * 2)));
    }

    protected static long getRandomOffsetForPosition(int x, int y, int z) {
        long offset = ((x * 3129871L) ^ (z * 116129781L)) ^ y;
        return (offset * offset * 42317861) + (offset * 11);
    }

    public static float combineSpawnRates(float blockBased, float userBased) {
        float userBased2 = (userBased * 2.0f) - 1.0f;
        if (userBased2 > 0.0f) {
            return blockBased + ((1.0f - blockBased) * userBased2);
        }
        return userBased2 < 0.0f ? (blockBased + (blockBased * userBased2)) - 0.001f : blockBased;
    }

    public static Icon registerBlockIcon(String name) {
        return ResourceHelper.registerIcon(currentIconRegister, "textures/blocks/",  name);
    }

    public static Icon[] registerBlockIcons(String name) {
        return ResourceHelper.registerIcons(currentIconRegister, "textures/blocks/", name);
    }

    public static Icon[] registerBlockIconsCallback(String targetName, String sourceName, ITextureLoadingCallback callback) {
        return ResourceHelper.registerIconsCallback(currentIconRegister, targetName, "textures/blocks/", sourceName, callback);
    }

    public void onRegisterIcons(IconRegister iconRegister) {
    }

    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        return false;
    }

    public boolean onEntityWalking(Block block, World world, int x, int y, int z, Entity entity) {
        return false;
    }

    public boolean onRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        return false;
    }

    public boolean onSpawnParticle(EnumParticle name, World world, double x, double y, double z, double motionX, double motionY, double motionZ, Entity entity) {
        return false;
    }

    protected void renderStandardBlock(RenderBlocks renderBlocks, Block block, int x, int y, int z, float brightnessFactor) {
        int color = block.colorMultiplier(renderBlocks.blockAccess, x, y, z);
        float red = ((color >> 16) & 255) * 0.00392f * brightnessFactor;
        float green = ((color >> 8) & 255) * 0.00392f * brightnessFactor;
        float blue = (color & 255) * 0.00392f * brightnessFactor;
        if (Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[block.blockID] == 0) {
            renderBlocks.renderStandardBlockWithAmbientOcclusion(block, x, y, z, red, green, blue);
        } else {
            renderBlocks.renderStandardBlockWithColorMultiplier(block, x, y, z, red, green, blue);
        }
    }

    protected void renderCrossedQuadsX(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = x - currentRotationalOffsetMap[0][0];
        double maxX = x + currentRotationalOffsetMap[0][0];
        double minZ = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxZ = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minY = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxY = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        double minZ2 = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxZ2 = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minY2 = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxY2 = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.addVertexWithUV(maxX, maxY2, maxZ2, minU, minV);
        tessellator.addVertexWithUV(maxX, minY2, minZ2, maxU, minV);
        tessellator.addVertexWithUV(minX, minY2, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY2, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY2, minZ2, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY2, maxZ2, minU, minV);
        tessellator.addVertexWithUV(minX, maxY2, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX, minY2, minZ2, maxU, maxV);
    }

    protected void renderCrossedQuadsY(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minY = y - currentRotationalOffsetMap[0][0];
        double maxY = y + currentRotationalOffsetMap[0][0];
        double minX = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxX = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minZ = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxZ = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        double minX2 = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxX2 = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minZ2 = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxZ2 = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.addVertexWithUV(maxX2, maxY, maxZ2, minU, minV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX2, maxY, minZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2, maxY, minZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX2, maxY, maxZ2, minU, minV);
    }

    protected void renderCrossedQuadsZ(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minZ = z - currentRotationalOffsetMap[0][0];
        double maxZ = z + currentRotationalOffsetMap[0][0];
        double minX = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxX = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minY = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxY = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        double minX2 = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxX2 = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minY2 = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxY2 = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.addVertexWithUV(maxX2, maxY2, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX2, minY2, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY2, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, maxY2, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY2, maxZ, maxU, minV);
        tessellator.addVertexWithUV(maxX2, maxY2, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX2, maxY2, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY2, minZ, maxU, maxV);
    }

    protected void renderCrossedQuadsShadedX(Icon icon, double x, double y, double z, boolean flipU, boolean flipV, float r, float g, float b, float rS, float gS, float bS) {
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
        double minX = x - currentRotationalOffsetMap[0][0];
        double maxX = x + currentRotationalOffsetMap[0][0];
        double minZ = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxZ = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minY = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxY = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        double minZ2 = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxZ2 = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minY2 = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxY2 = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX, maxY2, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY2, maxZ2, minU, minV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX, minY2, minZ2, maxU, minV);
        tessellator.addVertexWithUV(minX, minY2, minZ2, maxU, maxV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX, maxY2, maxZ2, minU, minV);
        tessellator.addVertexWithUV(minX, maxY2, maxZ2, minU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX, minY2, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY2, minZ2, maxU, minV);
    }

    protected void renderCrossedQuadsShadedY(Icon icon, double x, double y, double z, boolean flipU, boolean flipV, float r, float g, float b, float rS, float gS, float bS) {
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
        double minY = y - currentRotationalOffsetMap[0][0];
        double maxY = y + currentRotationalOffsetMap[0][0];
        double minX = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxX = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minZ = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxZ = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        double minX2 = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxX2 = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minZ2 = z - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxZ2 = z + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX2, maxY, minZ2, maxU, minV);
        tessellator.addVertexWithUV(maxX2, maxY, maxZ2, minU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX2, maxY, maxZ2, minU, minV);
        tessellator.addVertexWithUV(minX2, maxY, minZ2, maxU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
    }

    protected void renderCrossedQuadsShadedZ(Icon icon, double x, double y, double z, boolean flipU, boolean flipV, float r, float g, float b, float rS, float gS, float bS) {
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
        double minZ = z - currentRotationalOffsetMap[0][0];
        double maxZ = z + currentRotationalOffsetMap[0][0];
        double minX = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double maxX = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][0];
        double minY = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        double maxY = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][1];
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, maxV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, minV);
        double minX2 = x - currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double maxX2 = x + currentRotationalOffsetMap[currentRotationalOffsetIndex][2];
        double minY2 = y - currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        double maxY2 = y + currentRotationalOffsetMap[currentRotationalOffsetIndex][3];
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX2, maxY2, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX2, maxY2, maxZ, minU, minV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX2, minY2, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY2, minZ, maxU, maxV);
        tessellator.setColorOpaque_F(rS, gS, bS);
        tessellator.addVertexWithUV(maxX2, maxY2, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX2, maxY2, minZ, minU, maxV);
        tessellator.setColorOpaque_F(r, g, b);
        tessellator.addVertexWithUV(minX2, minY2, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX2, minY2, maxZ, maxU, minV);
    }

    public static void renderFaceXNeg(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = x - currentHeightOffset;
        double minZ = (z + 0.5d) - currentPolyRadius;
        double maxZ = z + 0.5d + currentPolyRadius;
        double minY = (y + 0.5d) - currentPolyRadius;
        double maxY = y + 0.5d + currentPolyRadius;
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
    }

    public static void renderFaceXPos(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double maxX = x + 1.0d + currentHeightOffset;
        double minZ = (z + 0.5d) - currentPolyRadius;
        double maxZ = z + 0.5d + currentPolyRadius;
        double minY = (y + 0.5d) - currentPolyRadius;
        double maxY = y + 0.5d + currentPolyRadius;
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
    }

    public static void renderFaceYNeg(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = (x + 0.5d) - currentPolyRadius;
        double maxX = x + 0.5d + currentPolyRadius;
        double minZ = (z + 0.5d) - currentPolyRadius;
        double maxZ = z + 0.5d + currentPolyRadius;
        double minY = y - currentHeightOffset;
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
    }

    public static void renderFaceYPos(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = (x + 0.5d) - currentPolyRadius;
        double maxX = x + 0.5d + currentPolyRadius;
        double minZ = (z + 0.5d) - currentPolyRadius;
        double maxZ = z + 0.5d + currentPolyRadius;
        double maxY = y + 1.0d + currentHeightOffset;
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
    }

    public static void renderFaceZNeg(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = (x + 0.5d) - currentPolyRadius;
        double maxX = x + 0.5d + currentPolyRadius;
        double minZ = z - currentHeightOffset;
        double minY = (y + 0.5d) - currentPolyRadius;
        double maxY = y + 0.5d + currentPolyRadius;
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
    }

    public static void renderFaceZPos(Icon icon, double x, double y, double z, boolean flipU, boolean flipV) {
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
        double minX = (x + 0.5d) - currentPolyRadius;
        double maxX = x + 0.5d + currentPolyRadius;
        double maxZ = z + 1.0d + currentHeightOffset;
        double minY = (y + 0.5d) - currentPolyRadius;
        double maxY = y + 0.5d + currentPolyRadius;
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
    }

    public static void renderBlock(Icon icon, double x, double y, double z, double radius, float red, float green, float blue, float alpha, boolean flipU, boolean flipV) {
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
        double minY = y - radius;
        double maxY = y + radius;
        double minZ = z - radius;
        double maxZ = z + radius;
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
    }

    public static void render3DFaceYPos(Icon icon, double x, double y, double z, int uSegments, int vSegments, double height, float red, float green, float blue, boolean flipU, boolean flipV) {
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
        double maxX = x + 1.0d;
        double maxZ = z + 1.0d;
        double maxY = y + height;
        double uStep = (maxU - minU) / uSegments;
        double vStep = (maxV - minV) / vSegments;
        double xStep = (maxX - x) / uSegments;
        double zStep = (maxZ - z) / vSegments;
        tessellator.setColorOpaque_F(red, green, blue);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        tessellator.addVertexWithUV(x, maxY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, z, maxU, minV);
        tessellator.addVertexWithUV(x, maxY, z, minU, minV);
        tessellator.setColorOpaque_F(red * 0.6f, green * 0.6f, blue * 0.6f);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int n = 0; n < uSegments; n++) {
            double x2 = x + (n * xStep);
            double v = minU + ((n + 0.5d) * uStep);
            tessellator.addVertexWithUV(x2, maxY, z, v, minV);
            tessellator.addVertexWithUV(x2, y, z, v, minV);
            tessellator.addVertexWithUV(x2, y, maxZ, v, maxV);
            tessellator.addVertexWithUV(x2, maxY, maxZ, v, maxV);
        }
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int n2 = 0; n2 < uSegments; n2++) {
            double x3 = maxX - (n2 * xStep);
            double v2 = maxU - ((n2 + 0.5d) * uStep);
            tessellator.addVertexWithUV(x3, maxY, maxZ, v2, maxV);
            tessellator.addVertexWithUV(x3, y, maxZ, v2, maxV);
            tessellator.addVertexWithUV(x3, y, z, v2, minV);
            tessellator.addVertexWithUV(x3, maxY, z, v2, minV);
        }
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        for (int n3 = 0; n3 < vSegments; n3++) {
            double z2 = z + (n3 * zStep);
            double v3 = minV + ((n3 + 0.5d) * vStep);
            tessellator.addVertexWithUV(maxX, maxY, z2, maxU, v3);
            tessellator.addVertexWithUV(maxX, y, z2, maxU, v3);
            tessellator.addVertexWithUV(x, y, z2, minU, v3);
            tessellator.addVertexWithUV(x, maxY, z2, minU, v3);
        }
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        for (int n4 = 0; n4 < vSegments; n4++) {
            double z3 = maxZ - (n4 * zStep);
            double v4 = maxV - ((n4 + 0.5d) * vStep);
            tessellator.addVertexWithUV(x, maxY, z3, minU, v4);
            tessellator.addVertexWithUV(x, y, z3, minU, v4);
            tessellator.addVertexWithUV(maxX, y, z3, maxU, v4);
            tessellator.addVertexWithUV(maxX, maxY, z3, maxU, v4);
        }
    }

    public static void render3DFaceX(Icon icon, double x, double y, double z, boolean[][] segments, double height, float red, float green, float blue, boolean flipU, boolean flipV) {
        double minX;
        double maxX;
        double minY;
        double maxY;
        double minZ;
        double maxZ;
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        if (flipU ^ flipV) {
            minX = x + (height * 0.5d);
            maxX = x - (height * 0.5d);
        } else {
            minX = x - (height * 0.5d);
            maxX = x + (height * 0.5d);
        }
        if (flipV) {
            minY = y + 0.5d;
            maxY = y - 0.5d;
        } else {
            minY = y - 0.5d;
            maxY = y + 0.5d;
        }
        if (flipU) {
            minZ = z + 0.5d;
            maxZ = z - 0.5d;
        } else {
            minZ = z - 0.5d;
            maxZ = z + 0.5d;
        }
        int segmentCount = segments.length;
        double uStep = (maxU - minU) / segmentCount;
        double vStep = (maxV - minV) / segmentCount;
        double zStep = (maxZ - minZ) / segmentCount;
        double yStep = (maxY - minY) / segmentCount;
        tessellator.setColorOpaque_F(red, green, blue);
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.setColorOpaque_F(red * 0.8f, green * 0.8f, blue * 0.8f);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int n = 0; n < segmentCount; n++) {
            if (segments[n][0]) {
                double y2 = maxY - (n * yStep);
                double u = minV + ((n + 0.5d) * vStep);
                tessellator.addVertexWithUV(minX, y2, maxZ, minU, u);
                tessellator.addVertexWithUV(maxX, y2, maxZ, minU, u);
                tessellator.addVertexWithUV(maxX, y2, minZ, maxU, u);
                tessellator.addVertexWithUV(minX, y2, minZ, maxU, u);
            }
        }
        tessellator.setColorOpaque_F(red * 0.56f, green * 0.56f, blue * 0.56f);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int n2 = 0; n2 < segmentCount; n2++) {
            if (segments[n2][1]) {
                double y3 = minY + (n2 * yStep);
                double u2 = maxV - ((n2 + 0.5d) * vStep);
                tessellator.addVertexWithUV(minX, y3, minZ, maxU, u2);
                tessellator.addVertexWithUV(maxX, y3, minZ, maxU, u2);
                tessellator.addVertexWithUV(maxX, y3, maxZ, minU, u2);
                tessellator.addVertexWithUV(minX, y3, maxZ, minU, u2);
            }
        }
        tessellator.setColorOpaque_F(red * 0.7f, green * 0.7f, blue * 0.7f);
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        for (int n3 = 0; n3 < segmentCount; n3++) {
            if (segments[n3][2]) {
                double z2 = maxZ - (n3 * zStep);
                double u3 = minU + ((n3 + 0.5d) * uStep);
                tessellator.addVertexWithUV(minX, maxY, z2, u3, minV);
                tessellator.addVertexWithUV(minX, minY, z2, u3, maxV);
                tessellator.addVertexWithUV(maxX, minY, z2, u3, maxV);
                tessellator.addVertexWithUV(maxX, maxY, z2, u3, minV);
            }
        }
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        for (int n4 = 0; n4 < segmentCount; n4++) {
            if (segments[n4][3]) {
                double z3 = minZ + (n4 * zStep);
                double u4 = maxU - ((n4 + 0.5d) * uStep);
                tessellator.addVertexWithUV(maxX, maxY, z3, u4, minV);
                tessellator.addVertexWithUV(maxX, minY, z3, u4, maxV);
                tessellator.addVertexWithUV(minX, minY, z3, u4, maxV);
                tessellator.addVertexWithUV(minX, maxY, z3, u4, minV);
            }
        }
    }

    public static void render3DFaceZ(Icon icon, double x, double y, double z, boolean[][] segments, double height, float red, float green, float blue, boolean flipU, boolean flipV) {
        double minX;
        double maxX;
        double minY;
        double maxY;
        double minZ;
        double maxZ;
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        if (flipU) {
            minX = x + 0.5d;
            maxX = x - 0.5d;
        } else {
            minX = x - 0.5d;
            maxX = x + 0.5d;
        }
        if (flipV) {
            minY = y + 0.5d;
            maxY = y - 0.5d;
        } else {
            minY = y - 0.5d;
            maxY = y + 0.5d;
        }
        if (flipU ^ flipV) {
            minZ = z + (height * 0.5d);
            maxZ = z - (height * 0.5d);
        } else {
            minZ = z - (height * 0.5d);
            maxZ = z + (height * 0.5d);
        }
        int segmentCount = segments.length;
        double uStep = (maxU - minU) / segmentCount;
        double vStep = (maxV - minV) / segmentCount;
        double xStep = (maxX - minX) / segmentCount;
        double yStep = (maxY - minY) / segmentCount;
        tessellator.setColorOpaque_F(red, green, blue);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        tessellator.setColorOpaque_F(red * 0.8f, green * 0.8f, blue * 0.8f);
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int n = 0; n < segmentCount; n++) {
            if (segments[n][0]) {
                double y2 = maxY - (n * yStep);
                double u = minV + ((n + 0.5d) * vStep);
                tessellator.addVertexWithUV(minX, y2, maxZ, minU, u);
                tessellator.addVertexWithUV(maxX, y2, maxZ, maxU, u);
                tessellator.addVertexWithUV(maxX, y2, minZ, maxU, u);
                tessellator.addVertexWithUV(minX, y2, minZ, minU, u);
            }
        }
        tessellator.setColorOpaque_F(red * 0.56f, green * 0.56f, blue * 0.56f);
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int n2 = 0; n2 < segmentCount; n2++) {
            if (segments[n2][1]) {
                double y3 = minY + (n2 * yStep);
                double u2 = maxV - ((n2 + 0.5d) * vStep);
                tessellator.addVertexWithUV(minX, y3, minZ, minU, u2);
                tessellator.addVertexWithUV(maxX, y3, minZ, maxU, u2);
                tessellator.addVertexWithUV(maxX, y3, maxZ, maxU, u2);
                tessellator.addVertexWithUV(minX, y3, maxZ, minU, u2);
            }
        }
        tessellator.setColorOpaque_F(red * 0.7f, green * 0.7f, blue * 0.7f);
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int n3 = 0; n3 < segmentCount; n3++) {
            if (segments[n3][2]) {
                double x2 = minX + (n3 * xStep);
                double u3 = minU + ((n3 + 0.5d) * uStep);
                tessellator.addVertexWithUV(x2, maxY, minZ, u3, minV);
                tessellator.addVertexWithUV(x2, minY, minZ, u3, maxV);
                tessellator.addVertexWithUV(x2, minY, maxZ, u3, maxV);
                tessellator.addVertexWithUV(x2, maxY, maxZ, u3, minV);
            }
        }
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int n4 = 0; n4 < segmentCount; n4++) {
            if (segments[n4][3]) {
                double x3 = maxX - (n4 * xStep);
                double u4 = maxU - ((n4 + 0.5d) * uStep);
                tessellator.addVertexWithUV(x3, maxY, maxZ, u4, minV);
                tessellator.addVertexWithUV(x3, minY, maxZ, u4, maxV);
                tessellator.addVertexWithUV(x3, minY, minZ, u4, maxV);
                tessellator.addVertexWithUV(x3, maxY, minZ, u4, minV);
            }
        }
    }
}
