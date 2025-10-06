package poersch.minecraft.bettergrassandleaves.renderer;

import java.awt.image.BufferedImage;
import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.ResourceHelper;
import poersch.minecraft.util.texture.ITextureLoadingCallback;

public class BetterLadderRenderer extends BetterBlockRenderer implements ITextureLoadingCallback {
    public static boolean[][] segments;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        segments = null;
        ResourceHelper.registerIconCallback(iconRegister, "ladder", "textures/blocks/", "ladder", this);
    }

    @Override
    public boolean renderWorldBlock(RenderBlocks renderBlocks, IBlockAccess iBlockAccess, Block block, int x, int y, int z) {
        if (!(Boolean) BetterGrassAndLeavesMod.renderBetterLadders.value || segments == null) {
            return false;
        }
        Icon icon = block.getBlockTextureFromSide(0);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iBlockAccess, x, y, z));
        switch (iBlockAccess.getBlockMetadata(x, y, z)) {
            case 2:
                render3DFaceZ(icon, x + 0.5d, y + 0.5d, z + 0.96875d, segments, 0.0625d, 1.0f, 1.0f, 1.0f, true, false);
                break;
            case 3:
                render3DFaceZ(icon, x + 0.5d, y + 0.5d, z + 0.03125d, segments, 0.0625d, 1.0f, 1.0f, 1.0f, false, false);
                break;
            case 4:
                render3DFaceX(icon, x + 0.96875d, y + 0.5d, z + 0.5d, segments, 0.0625d, 1.0f, 1.0f, 1.0f, true, false);
                break;
            case 5:
                render3DFaceX(icon, x + 0.03125d, y + 0.5d, z + 0.5d, segments, 0.0625d, 1.0f, 1.0f, 1.0f, false, false);
                break;
        }
        return true;
    }

    @Override
    public BufferedImage onTextureLoading(TextureAtlasSprite icon, BufferedImage texture) {
        int[] imageData = new int[texture.getHeight() * texture.getWidth()];
        texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), imageData, 0, texture.getWidth());
        int diameter = texture.getWidth();
        if (segments == null) {
            segments = new boolean[diameter][4];
        }
        int max = diameter - 1;
        for (int y = 0; y < diameter; y++) {
            for (int i = 0; i < diameter; i++) {
                int alpha = (imageData[(y * diameter) + i] >> 24) & 255;
                if (alpha > 0 && (y == 0 || ((imageData[((y - 1) * diameter) + i] >> 24) & 255) != alpha)) {
                    segments[y][0] = true;
                    break;
                }
            }
        }
        for (int y2 = 0; y2 < diameter; y2++) {
            for (int i2 = 0; i2 < diameter; i2++) {
                int alpha2 = (imageData[(y2 * diameter) + i2] >> 24) & 255;
                if (alpha2 > 0 && (y2 == max || ((imageData[((y2 + 1) * diameter) + i2] >> 24) & 255) != alpha2)) {
                    segments[max - y2][1] = true;
                    break;
                }
            }
        }
        for (int x = 0; x < diameter; x++) {
            for (int i3 = 0; i3 < diameter; i3++) {
                int alpha3 = (imageData[(i3 * diameter) + x] >> 24) & 255;
                if (alpha3 > 0 && (x == 0 || ((imageData[((i3 * diameter) + x) - 1] >> 24) & 255) != alpha3)) {
                    segments[x][2] = true;
                    break;
                }
            }
        }
        for (int x2 = 0; x2 < diameter; x2++) {
            for (int i4 = 0; i4 < diameter; i4++) {
                int alpha4 = (imageData[(i4 * diameter) + x2] >> 24) & 255;
                if (alpha4 > 0 && (x2 == max || ((imageData[((i4 * diameter) + x2) + 1] >> 24) & 255) != alpha4)) {
                    segments[max - x2][3] = true;
                    break;
                }
            }
        }
        return texture;
    }
}
