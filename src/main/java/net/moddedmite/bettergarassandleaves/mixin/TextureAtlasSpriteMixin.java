package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.AnimationMetadataSection;
import net.minecraft.Resource;
import net.minecraft.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.util.texture.TextureAtlasSpriteLoadingCallback;

@Mixin(TextureAtlasSprite.class)
public class TextureAtlasSpriteMixin {

    @Shadow private AnimationMetadataSection animationMetadata;

    @Inject(method = "loadSprite", at = @At("HEAD"), cancellable = true)
    private void loadSpriteWithCallback(Resource resource, CallbackInfo ci) throws java.io.IOException {
        if ((Object) this instanceof TextureAtlasSpriteLoadingCallback callbackSprite && callbackSprite.loadFromResource(resource)) {
            this.animationMetadata = null;
            ci.cancel();
        }
    }
}
