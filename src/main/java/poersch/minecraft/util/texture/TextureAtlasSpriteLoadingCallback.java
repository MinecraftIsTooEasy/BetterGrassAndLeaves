package poersch.minecraft.util.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.Resource;
import net.minecraft.ResourceLocation;
import net.minecraft.ResourceManager;
import net.minecraft.TextureAtlasSprite;

public class TextureAtlasSpriteLoadingCallback extends TextureAtlasSprite {
    protected final ResourceLocation location;
    protected final ITextureLoadingCallback callback;

    public TextureAtlasSpriteLoadingCallback(String targetName, ResourceLocation location, ITextureLoadingCallback callback) {
        super(targetName);
        this.location = location;
        this.callback = callback;
    }

//    @Override
//    public boolean load(ResourceManager resourceManager, ResourceLocation location) throws IOException {
//        loadSprite(resourceManager.getResource(this.location));
//        return true;
//    }

//    public void loadSprite(Resource resource) throws IOException {
//        InputStream inputstream = resource.getInputStream();
//        BufferedImage bufferedimage = ImageIO.read(inputstream);
//        if (this.callback != null) {
//            bufferedimage = this.callback.onTextureLoading(this, bufferedimage);
//        }
//        this.height = bufferedimage.getHeight();
//        this.width = bufferedimage.getWidth();
//        int[] aint = new int[this.height * this.width];
//        bufferedimage.getRGB(0, 0, this.width, this.height, aint, 0, this.width);
//        if (this.height != this.width) {
//            throw new RuntimeException("broken aspect ratio and not an animation");
//        }
//        this.framesTextureData.add(aint);
//    }
}
