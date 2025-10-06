package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.EnumParticle;
import net.minecraft.World;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(method = "spawnParticle", at = @At("HEAD"), cancellable = true)
    private void onSpawnParticle(EnumParticle particleName, double x, double y, double z, double motionX, double motionY, double motionZ, CallbackInfo ci) {
        if (BetterBlockRendererList.onSpawnParticleHook(particleName, ReflectHelper.dyCast(this), x, y, z, motionX, motionY, motionZ, null)) {
            ci.cancel();
        }
    }
}
