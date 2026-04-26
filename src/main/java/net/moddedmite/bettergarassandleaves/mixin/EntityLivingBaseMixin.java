package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.Entity;
import net.minecraft.EntityLivingBase;
import net.minecraft.EnumEntityState;
import net.minecraft.Minecraft;
import net.moddedmite.bettergarassandleaves.util.EnumExtra;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(method = "handleHealthUpdate", at = @At("TAIL"))
    private void addBloodOnHurt(EnumEntityState state, CallbackInfo ci) {
        if (Minecraft.isSingleplayer()) {
            return;
        }
        if (state == EnumEntityState.hurt_with_red_tint_refreshed || state == EnumEntityState.hurt_without_red_tint_refreshed) {
            Entity entity = ReflectHelper.dyCast(this);
            BetterBlockRendererList.onSpawnParticleHook(EnumExtra.blood, entity.worldObj, entity.posX, entity.posY, entity.posZ, 0.0d, 0.0d, 0.0d, entity);
        }
    }
}
