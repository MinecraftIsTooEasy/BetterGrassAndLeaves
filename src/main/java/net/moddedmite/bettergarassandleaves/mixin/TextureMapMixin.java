package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.TextureAtlasSprite;
import net.minecraft.TextureMap;
import net.moddedmite.bettergarassandleaves.interfaces.ITextureMap;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

import java.util.Map;

@Mixin(TextureMap.class)
public abstract class TextureMapMixin implements ITextureMap {
    @Shadow @Final private Map mapRegisteredSprites;
    @Shadow public abstract int getTextureType();

    @Override
    public boolean setTextureEntry(String name, TextureAtlasSprite entry) {
        if (!this.mapRegisteredSprites.containsKey(name)) {
            this.mapRegisteredSprites.put(name, entry);
            return true;
        }
        return false;
    }

    @Inject(method = "loadTextureAtlas", at = @At(value = "FIELD", target = "Lnet/minecraft/TextureMap;listAnimatedSprites:Ljava/util/List;", shift = At.Shift.AFTER))
    private void onRegisterIconsHook(CallbackInfo ci) {
        if (BetterGrassAndLeavesMod.useRegisterIconsHook.value && this.getTextureType() == 0) {
            BetterGrassAndLeavesMod.workingRegisterIconsHook = true;
            BetterBlockRendererList.onRegisterIconsHook(ReflectHelper.dyCast(this));
        }
    }
}
