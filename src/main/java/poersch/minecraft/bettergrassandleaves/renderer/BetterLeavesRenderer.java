package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.*;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityFallingLeavesFancyFX;
import poersch.minecraft.bettergrassandleaves.entity.EntityFallingLeavesFastFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterLeaves;
import poersch.minecraft.util.BGALStringHelper;
import poersch.minecraft.util.ResourceHelper;
import poersch.minecraft.util.texture.ITextureLoadingCallback;

public class BetterLeavesRenderer extends BetterBlockRenderer implements IBetterLeaves {
    public static Icon[][] iconBetterLeaves;
    public static Icon[] iconFallingLeaves;
    public static Icon[] iconBetterLeavesSnowed;
    public static Icon[] iconRoundedLeaves;
    protected static Map<String, Icon[]> iconMap = new HashMap();
    public static HashSet<Block> leafBlocks = new HashSet<>();

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        int len = BlockLeaves.LEAF_TYPES.length;
        iconBetterLeaves = new Icon[len][];
        for (int i = 0; i < len; i++) {
            iconBetterLeaves[i] = registerBlockIcons("better_leaves_" + BlockLeaves.LEAF_TYPES[i]);
        }
        iconFallingLeaves = new Icon[len];
        for (int i = 0; i < len; i++) {
            iconFallingLeaves[i] = registerBlockIcon("falling_leaves_" + BlockLeaves.LEAF_TYPES[i]);
        }
        iconBetterLeavesSnowed = registerBlockIcons("better_leaves_snowed");
        iconRoundedLeaves = new Icon[len];
        for (int i = 0; i < len; i++) {
            iconRoundedLeaves[i] = registerBlockIcon("leaves_" + BlockLeaves.LEAF_TYPES[i] + "_round");
        }
        for (Block block : leafBlocks) {
            leavesRenderer.assignToBlockID(block.blockID);
        }
    }

    public static void resetBetterLeavesLinkage() {
        iconMap.clear();
    }

    public static void linkBetterLeavesTo(IconRegister iconRegister, String name) {
        Icon[] icons;
        List<String> tokens = BGALStringHelper.splitTrimToList(name, ':');
        if (tokens.size() <= 1 || (icons = ResourceHelper.registerIconsOrCallback(iconRegister, "textures/blocks/", "better_" + tokens.get(1), "textures/blocks/", tokens.get(1), (ITextureLoadingCallback) leavesRenderer.get(0))) == null) {
            return;
        }
        iconMap.put(tokens.get(0).equals("minecraft") ? tokens.get(1).toLowerCase() : (tokens.get(0) + ":" + tokens.get(1)).toLowerCase(), icons);
    }

    protected static Icon getIconFromMap(String iconName, float randomIndex) {
        Icon[] icons = iconMap.get(iconName.toLowerCase());
        if (icons != null) {
            return icons[(int) ((randomIndex * (icons.length - 1)) + 0.5f)];
        }
        return null;
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterLeaves.value) {
            return false;
        }
        block.setBlockBoundsBasedOnStateAndNeighbors(iBlockAccess, x, y, z);
        renderBlocks.XXXsetRenderBoundsFromBlock(block);
        renderBlocks.renderStandardBlock(block, x, y, z);
        int blockBrightness = blockHasVisibleSide(block, iBlockAccess, x, y, z);
        if (blockBrightness <= -1) {
            return BetterGrassAndLeavesMod.renderOnlyOuterLeaves.value;
        }
        IBetterLeaves betterLeaves = block instanceof IBetterLeaves ? (IBetterLeaves) block : this;
        long offset = getRandomOffsetForPosition(x, y, z);
        int metadata = iBlockAccess.getBlockMetadata(x, y, z);
        float randomIndex = ((offset >> 12) & 15) / 15.0f;
        Icon icon = betterLeaves.getIconBetterLeaves(metadata, randomIndex);
        if (icon == null) {
            icon = getIconFromMap(block.getIcon(0, metadata).getIconName(), randomIndex);
            if (icon == null) {
                icon = block.getIcon(0, metadata);
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
            renderCrossedQuadsShadedY(icon, xS, yS, zS, flipU, flipV, r, g, b, r * 0.58f, g * 0.58f, b * 0.58f);
        } else {
            tessellator.setColorOpaque_F(r * 0.78f, g * 0.78f, b * 0.78f);
            renderCrossedQuadsY(icon, xS, yS, zS, flipU, flipV);
        }
        if (BetterGrassAndLeavesMod.renderSnowedLeaves.value && iBlockAccess.getBlockId(x, y + 1, z) == Block.snow.blockID) {
            Icon icon2 = betterLeaves.getIconBetterLeavesSnowed(0, ((offset >> 12) & 15) / 15.0f);
            if (icon2 == null) {
                return false;
            }
            tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                renderCrossedQuadsShadedY(icon2, xS, yS, zS, flipU, false, 1.0f, 1.0f, 1.0f, 0.58f, 0.58f, 0.58f);
                return true;
            }
            tessellator.setColorOpaque_F(0.78f, 0.78f, 0.78f);
            renderCrossedQuadsY(icon2, xS, yS, zS, flipU, false);
            return true;
        }
        return true;
    }

    @Override
    public boolean onRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderLeavesFX.value || !world.isAirBlock(x, y - 1, z)) {
            return false;
        }
        int metadata = world.getBlockMetadata(x, y, z);
        if (random.nextFloat() > 1.0f - BetterLeavesRenderer.combineSpawnRates(((IBetterLeaves) block).getSpawnChanceFallingLeaves(metadata), BetterGrassAndLeavesMod.leavesFXSpawnRate.value)) {
            Icon icon = ((IBetterLeaves) block).getIconFallingLeaves(metadata);
            if (icon == null) {
                icon = block.getIcon(0, metadata);
            }
            double xS = (double) x + 0.1 + random.nextDouble() * 0.8;
            double yS = (double) y + 0.16;
            double zS = (double) z + 0.1 + random.nextDouble() * 0.8;
            float particleScale = (float) icon.getIconWidth() / (float) block.getIcon(0, metadata).getIconWidth();
            if (BetterGrassAndLeavesMod.renderLeavesFX.value) {
                this.minecraft.effectRenderer.addEffect(new EntityFallingLeavesFastFX(world, xS, yS, zS, particleScale, BetterGrassAndLeavesMod.betterLeavesBrightness.value, block.colorMultiplier(world, x, y, z), icon));
            } else {
                this.minecraft.effectRenderer.addEffect(new EntityFallingLeavesFancyFX(world, xS, yS, zS, particleScale, BetterGrassAndLeavesMod.betterLeavesBrightness.value, block.colorMultiplier(world, x, y, z), icon));
            }
            return false;
        }
        return false;
    }

    public static int blockHasVisibleSide(Block block, IBlockAccess iBlockAccess, int x, int y, int z) {
        int blockID = iBlockAccess.getBlockId(x, y + 1, z);
        if (blockID != block.blockID && !Block.opaqueCubeLookup[blockID]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z);
        }
        int blockID2 = iBlockAccess.getBlockId(x + 1, y, z);
        if (blockID2 != block.blockID && !Block.opaqueCubeLookup[blockID2]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x + 1, y, z);
        }
        int blockID3 = iBlockAccess.getBlockId(x - 1, y, z);
        if (blockID3 != block.blockID && !Block.opaqueCubeLookup[blockID3]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x - 1, y, z);
        }
        int blockID4 = iBlockAccess.getBlockId(x, y, z + 1);
        if (blockID4 != block.blockID && !Block.opaqueCubeLookup[blockID4]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x, y, z + 1);
        }
        int blockID5 = iBlockAccess.getBlockId(x, y, z - 1);
        if (blockID5 != block.blockID && !Block.opaqueCubeLookup[blockID5]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x, y, z - 1);
        }
        int blockID6 = iBlockAccess.getBlockId(x, y - 1, z);
        if (blockID6 != block.blockID && !Block.opaqueCubeLookup[blockID6]) {
            return block.getMixedBrightnessForBlock(iBlockAccess, x, y - 1, z);
        }
        return -1;
    }

    @Override
    public Icon getIconBetterLeaves(int metadata, float randomIndex) {
        return null;
    }

    @Override
    public Icon getIconBetterLeavesSnowed(int metadata, float randomIndex) {
        if (iconBetterLeavesSnowed == null) {
            return null;
        }
        return iconBetterLeavesSnowed[(int) ((randomIndex * (iconBetterLeavesSnowed.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconFallingLeaves(int metadata) {
        return null;
    }

    @Override
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.008f;
    }
}
