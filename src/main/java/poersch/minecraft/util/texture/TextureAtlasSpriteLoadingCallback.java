package poersch.minecraft.util.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import net.minecraft.Resource;
import net.minecraft.ResourceLocation;
import net.minecraft.TextureAtlasSprite;

public class TextureAtlasSpriteLoadingCallback extends TextureAtlasSprite {
    protected final ResourceLocation location;
    protected final ITextureLoadingCallback callback;

    public TextureAtlasSpriteLoadingCallback(String targetName, ResourceLocation location, ITextureLoadingCallback callback) {
        super(targetName);
        this.location = location;
        this.callback = callback;
    }

    public ResourceLocation getSourceLocation() {
        return this.location;
    }

    public boolean loadFromResource(Resource resource) throws IOException {
        if (this.callback == null) {
            return false;
        }
        BufferedImage source;
        try (InputStream input = resource.getInputStream()) {
            source = ImageIO.read(input);
        }
        if (source == null) {
            return false;
        }
        BufferedImage processed = this.callback.onTextureLoading(this, source);
        if (processed == null) {
            return false;
        }
        int processedWidth = processed.getWidth();
        int processedHeight = processed.getHeight();
        if (processedWidth <= 0 || processedHeight <= 0 || processedHeight % processedWidth != 0) {
            return false;
        }
        int processedFrameCount = processedHeight / processedWidth;
        List<int[]> frames = new ArrayList<>(processedFrameCount);
        for (int frameIndex = 0; frameIndex < processedFrameCount; frameIndex++) {
            int[] frame = new int[processedWidth * processedWidth];
            processed.getRGB(0, frameIndex * processedWidth, processedWidth, processedWidth, frame, 0, processedWidth);
            frames.add(frame);
        }
        setIconWidth(processedWidth);
        setIconHeight(processedWidth);
        setFramesTextureData(frames);
        return true;
    }
}
