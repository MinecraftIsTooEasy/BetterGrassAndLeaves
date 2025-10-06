package poersch.minecraft.bettergrassandleaves.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.minecraft.Block;
import net.minecraft.Material;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterSeaweed;
import poersch.minecraft.util.texture.ITextureLoadingCallback;

public class BetterSeaweedRenderer extends BetterBlockRenderer implements ITextureLoadingCallback, IBetterSeaweed {
    public static Icon[] iconBetterAlgae;
    public static Icon[][] iconBetterReed;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBetterAlgae = registerBlockIcons("better_algae");
        iconBetterReed = new Icon[2][];
        iconBetterReed[0] = registerBlockIconsCallback("better_reed_bottom", "better_reed", this);
        iconBetterReed[1] = registerBlockIconsCallback("better_reed_top", "better_reed", this);
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        Icon icon;
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterSeaweed.value || iBlockAccess.getBlockMaterial(x, y + 1, z) != Material.water) {
            return false;
        }
        IBetterSeaweed betterSeaweed = block instanceof IBetterSeaweed ? (IBetterSeaweed) block : this;
        long offset = getRandomOffsetForPosition(x, y, z);
        if (iBlockAccess.getBlockMaterial(x, y + 2, z) == Material.water) {
            if (!BetterGrassAndLeavesMod.algaeHostingBiomes.value.get(iBlockAccess.getBiomeGenForCoords(x, z).biomeID) || ((offset >> 4) & 15) / 15.0f >= BetterGrassAndLeavesMod.algaePopulation.value || (icon = betterSeaweed.getIconBetterAlgae(0, ((offset >> 12) & 15) / 15.0f)) == null) {
                return false;
            }
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
            tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterAlgaeBrightness.value, BetterGrassAndLeavesMod.betterAlgaeBrightness.value, BetterGrassAndLeavesMod.betterAlgaeBrightness.value);
            double xS = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
            double yS = (y + 1.707d) - ((((offset >> 20) & 15) / 15.0f) * 0.16d);
            double zS = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
            boolean flipU = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
            setRotationalOffsetMap(offsetMap24px, x, y, z);
            renderCrossedQuadsY(icon, xS, yS, zS, flipU, false);
            return false;
        }
        if (BetterGrassAndLeavesMod.reedHostingBiomes.value.get(iBlockAccess.getBiomeGenForCoords(x, z).biomeID) && iBlockAccess.isAirBlock(x, y + 2, z)) {
            float reedChance = ((offset >> 4) & 15) / 15.0f;
            if (nearShore(iBlockAccess, x, y + 1, z)) {
                if (reedChance >= BetterGrassAndLeavesMod.reedPopulation.value) {
                    return false;
                }
            } else if (reedChance >= BetterGrassAndLeavesMod.reedPopulation.value * BetterGrassAndLeavesMod.reedOffshorePopulation.value) {
                return false;
            }
            float reedIndex = ((offset >> 12) & 15) / 15.0f;
            Icon icon2 = betterSeaweed.getIconBetterReed(0, reedIndex);
            if (icon2 == null) {
                return false;
            }
            tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 2, z));
            tessellator.setColorOpaque_F(BetterGrassAndLeavesMod.betterReedBrightness.value, BetterGrassAndLeavesMod.betterReedBrightness.value, BetterGrassAndLeavesMod.betterReedBrightness.value);
            double xS2 = x + 0.5d + (((((offset >> 16) & 15) / 15.0f) - 0.5d) * 0.3d);
            double yS2 = (y + 1.707d) - ((((offset >> 20) & 15) / 15.0f) * 0.16d);
            double zS2 = z + 0.5d + (((((offset >> 24) & 15) / 15.0f) - 0.5d) * 0.3d);
            boolean flipU2 = ((float) ((offset >> 8) & 15)) / 15.0f < 0.5f;
            setRotationalOffsetMap(offsetMap24px, x, y, z);
            renderCrossedQuadsY(icon2, xS2, yS2, zS2, flipU2, false);
            Icon icon3 = betterSeaweed.getIconBetterReed(1, reedIndex);
            if (icon3 == null) {
                return false;
            }
            renderCrossedQuadsY(icon3, xS2, yS2 + 1.414d, zS2, flipU2, false);
            return false;
        }
        return false;
    }

    protected boolean nearShore(IBlockAccess iBlockAccess, int x, int y, int z) {
        return iBlockAccess.getBlockMaterial(x - 2, y, z) != Material.water || iBlockAccess.getBlockMaterial(x + 2, y, z) != Material.water || iBlockAccess.getBlockMaterial(x, y, z - 2) != Material.water || iBlockAccess.getBlockMaterial(x, y, z + 2) != Material.water;
    }

    @Override
    public BufferedImage onTextureLoading(TextureAtlasSprite icon, BufferedImage texture) {
        int diameter = texture.getWidth();
        BufferedImage buffer = new BufferedImage(diameter, diameter, 2);
        Graphics2D canvas = buffer.createGraphics();
        if (icon.getIconName().startsWith("better_reed_top")) {
            canvas.drawImage(texture, null, 0, (diameter * 2) - texture.getHeight());
        } else {
            canvas.drawImage(texture, null, 0, (-texture.getHeight()) + diameter);
        }
        canvas.dispose();
        return buffer;
    }

    @Override
    public Icon getIconBetterAlgae(int metadata, float randomIndex) {
        if (iconBetterAlgae == null) {
            return null;
        }
        return iconBetterAlgae[(int) ((randomIndex * (iconBetterAlgae.length - 1)) + 0.5f)];
    }

    @Override
    public Icon getIconBetterReed(int metadata, float randomIndex) {
        if (iconBetterReed == null || iconBetterReed[metadata] == null) {
            return null;
        }
        return iconBetterReed[metadata][(int) ((randomIndex * (iconBetterReed[metadata].length - 1)) + 0.5f)];
    }
}
