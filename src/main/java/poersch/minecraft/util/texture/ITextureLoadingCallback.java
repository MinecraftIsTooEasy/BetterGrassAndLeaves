package poersch.minecraft.util.texture;

import java.awt.image.BufferedImage;
import net.minecraft.TextureAtlasSprite;

public interface ITextureLoadingCallback {
    BufferedImage onTextureLoading(TextureAtlasSprite textureAtlasSprite, BufferedImage bufferedImage);
}
