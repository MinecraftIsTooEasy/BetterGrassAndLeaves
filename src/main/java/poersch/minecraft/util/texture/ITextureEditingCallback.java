package poersch.minecraft.util.texture;

import net.minecraft.TextureAtlasSprite;

public interface ITextureEditingCallback {
    int[] onTextureEditing(TextureAtlasSprite textureAtlasSprite, int[] iArr, int i, int i2);
}
