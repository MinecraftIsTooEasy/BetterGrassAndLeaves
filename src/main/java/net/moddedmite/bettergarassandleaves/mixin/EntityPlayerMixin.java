package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.Entity;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.moddedmite.bettergarassandleaves.util.EnumExtra;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/Entity;canAttackWithItem()Z"))
    private void addBlood(Entity target, CallbackInfo ci) {
        if (target instanceof EntityLivingBase && !target.worldObj.isRemote) {
            target.entityFX(EnumExtra.bloodFX);
        }
    }
}
