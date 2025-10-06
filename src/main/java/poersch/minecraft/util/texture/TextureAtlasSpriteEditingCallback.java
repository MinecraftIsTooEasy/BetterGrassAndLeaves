package poersch.minecraft.util.texture;

import java.io.IOException;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.Resource;

@Environment(EnvType.CLIENT)
public class TextureAtlasSpriteEditingCallback extends TextureAtlasSprite {
    protected final ITextureEditingCallback callback;

    public TextureAtlasSpriteEditingCallback(String iconName, ITextureEditingCallback callback) {
        super(iconName);
        this.callback = callback;
    }

//    public void loadSprite(Resource resource) throws IOException {
//        super.loadSprite(resource);
//        if (this.callback != null) {
//            for (int n = 0; n < this.field_110976_a.size(); n++) {
//                this.field_110976_a.set(n, this.callback.onTextureEditing(this, (int[]) this.field_110976_a.get(n), this.field_130223_c, this.field_130224_d));
//            }
//        }
//    }
}
