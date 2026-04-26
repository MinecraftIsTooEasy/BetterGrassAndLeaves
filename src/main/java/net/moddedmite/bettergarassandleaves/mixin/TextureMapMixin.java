package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.ResourceLocation;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.TextureMap;
import net.moddedmite.bettergarassandleaves.interfaces.ITextureMap;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;
import poersch.minecraft.util.texture.TextureAtlasSpriteLoadingCallback;

import java.util.Map;

@Mixin(value = TextureMap.class, priority = 3000)
public abstract class TextureMapMixin implements ITextureMap {
    @Shadow @Final private Map mapRegisteredSprites;
    @Shadow @Final private String basePath;
    @Shadow public abstract int getTextureType();

    @Override
    public boolean setTextureEntry(String name, TextureAtlasSprite entry) {
        if (!this.mapRegisteredSprites.containsKey(name)) {
            this.mapRegisteredSprites.put(name, entry);
            return true;
        }
        return false;
    }

    @ModifyArg(method = "loadTextureAtlas", at = @At(value = "INVOKE", target = "Lnet/minecraft/ResourceManager;getResource(Lnet/minecraft/ResourceLocation;)Lnet/minecraft/Resource;"), index = 0)
    private ResourceLocation redirectResourceForCallbackSprites(ResourceLocation requestedLocation) {
        String resourcePath = requestedLocation.getResourcePath();
        if (resourcePath == null || !resourcePath.endsWith(".png")) {
            return requestedLocation;
        }

        String basePrefix = this.basePath.endsWith("/") ? this.basePath : this.basePath + "/";
        if (!resourcePath.startsWith(basePrefix)) {
            return requestedLocation;
        }

        String spriteKey = resourcePath.substring(basePrefix.length(), resourcePath.length() - 4);
        Object sprite = this.mapRegisteredSprites.get(spriteKey);
        if (sprite instanceof TextureAtlasSpriteLoadingCallback callbackSprite) {
            return callbackSprite.getSourceLocation();
        }
        return requestedLocation;
    }

    @Inject(method = "loadTextureAtlas", at = @At("TAIL"))
    private void onRegisterIconsHook(CallbackInfo ci) {
        if (BetterGrassAndLeavesMod.useRegisterIconsHook.value && this.getTextureType() == 0) {
            BetterGrassAndLeavesMod.workingRegisterIconsHook = true;
            BetterBlockRendererList.onRegisterIconsHook(ReflectHelper.dyCast(this));
        }
    }
}
