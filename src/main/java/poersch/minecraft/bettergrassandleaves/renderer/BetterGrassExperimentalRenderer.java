package poersch.minecraft.bettergrassandleaves.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.BitSet;
import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.ResourceHelper;
import poersch.minecraft.util.texture.ITextureLoadingCallback;

public class BetterGrassExperimentalRenderer extends BetterBlockRenderer implements ITextureLoadingCallback {
    private Icon[] iconBetterGrassTop;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        this.iconBetterGrassTop = registerBlockIcons("better_grass_top");
        if (this.iconBetterGrassTop == null) {
            this.iconBetterGrassTop = ResourceHelper.registerIconsCallback(iconRegister, "better_grass_top", "textures/blocks/", "grass_top", this);
        }
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        int aboveBlockID;
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterGrass.value || (aboveBlockID = iBlockAccess.getBlockId(x, y + 1, z)) == Block.snow.blockID || !((BitSet) BetterGrassAndLeavesMod.allowBetterGrass.value).get(aboveBlockID)) {
            return false;
        }
        long offset = getRandomOffsetForPosition(x, y, z);
        int color = block.colorMultiplier(iBlockAccess, x, y, z);
        float r = ((color >> 16) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        float g = ((color >> 8) & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        float b = (color & 255) * 0.00392f * BetterGrassAndLeavesMod.betterGrassBrightness.value;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(r, g, b);
        Icon icon = this.iconBetterGrassTop != null ? this.iconBetterGrassTop[MathHelper.floor_float(((((offset >> 12) & 15) / 15.0f) * (this.iconBetterGrassTop.length - 1)) + 0.5f)] : null;
        if (icon == null) {
            return false;
        }
        double minGrassHeight = 0.24d * BetterGrassAndLeavesMod.averageGrassHeight.value;
        double yS = y + 1.0d;
        double yA = minGrassHeight + ((((offset >> 12) & 15) / 15.0f) * 0.14d);
        double yB = minGrassHeight + ((((offset >> 16) & 15) / 15.0f) * 0.14d);
        double yC = minGrassHeight + ((((offset >> 20) & 15) / 15.0f) * 0.14d);
        double yD = minGrassHeight + ((((offset >> 24) & 15) / 15.0f) * 0.14d);
        if (((offset >> 8) & 15) / 15.0f < 0.5f) {
            minU = icon.getMinU();
            maxU = icon.getMaxU();
        } else {
            minU = icon.getMaxU();
            maxU = icon.getMinU();
        }
        if (((offset >> 4) & 15) / 15.0f < 0.5f) {
            minV = icon.getMinV();
            maxV = icon.getMaxV();
        } else {
            minV = icon.getMaxV();
            maxV = icon.getMinV();
        }
        int grassPolygons = (int) (10.0f + (12.0f * BetterGrassAndLeavesMod.averageGrassHeight.value));
        for (int n = 0; n < grassPolygons; n++) {
            float factor = 1.0f - ((float) n / grassPolygons);
            float brightness = 0.5f + (0.5f * factor);
            tessellator.setColorOpaque_F(r * brightness, g * brightness, b * brightness);
            tessellator.addVertexWithUV(x, yS + (yA * factor), z, minU, minV);
            tessellator.addVertexWithUV(x, yS + (yB * factor), z + 1.0d, minU, maxV);
            tessellator.addVertexWithUV(x + 1.0d, yS + (yC * factor), z + 1.0d, maxU, maxV);
            tessellator.addVertexWithUV(x + 1.0d, yS + (yD * factor), z, maxU, minV);
        }
        return false;
    }

    @Override
    public BufferedImage onTextureLoading(TextureAtlasSprite icon, BufferedImage texture) {
        int diameter = texture.getWidth();
        BufferedImage buffer = new BufferedImage(diameter, diameter, 2);
        Graphics2D canvas = buffer.createGraphics();
        canvas.drawImage(texture, (BufferedImageOp) null, 0, 0);
        canvas.dispose();
        int noiseOffset = icon.getIconName().isEmpty() ? 0 : icon.getIconName().charAt(icon.getIconName().length() - 1);
        int[] argb = buffer.getRGB(0, 0, diameter, diameter, new int[diameter * diameter], 0, diameter);
        int pixelSize = diameter <= 16 ? 1 : diameter / 16;
        int i = 0;
        while (true) {
            int y = i;
            if (y < diameter) {
                int i2 = 0;
                while (true) {
                    int x = i2;
                    if (x < diameter) {
                        long offset = ((x * 3129871) ^ (noiseOffset * 116129781)) ^ y;
                        if ((((((offset * offset) * 42317861) + (offset * 11)) >> 16) & 15) / 15.0f > 0.4d) {
                            for (int y2 = y; y2 < y + pixelSize; y2++) {
                                for (int x2 = x; x2 < x + pixelSize; x2++) {
                                    int i3 = (y2 * diameter) + x2;
                                    argb[i3] = argb[i3] & 16777215;
                                }
                            }
                        }
                        i2 = x + pixelSize;
                    }
                }
//                i = y + pixelSize;
            } else {
                buffer.setRGB(0, 0, diameter, diameter, argb, 0, diameter);
                return buffer;
            }
        }
    }
}
