package net.moddedmite.bettergarassandleaves.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Entity;
import net.minecraft.EnumEntityFX;
import net.minecraft.NetClientHandler;
import net.minecraft.Packet85SimpleSignal;
import net.moddedmite.bettergarassandleaves.util.EnumExtra;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {

    @Inject(method = "handleEntityFX", at = @At(value = "FIELD", target = "Lnet/minecraft/Minecraft;theWorld:Lnet/minecraft/WorldClient;"))
    private void addBlood(Packet85SimpleSignal packet, CallbackInfo ci, @Local(name = "entity") Entity entity, @Local(name = "kind") EnumEntityFX kind) {
        if (kind == EnumExtra.bloodFX)
            BetterBlockRendererList.onSpawnParticleHook(EnumExtra.blood, entity.worldObj, entity.posX, entity.posY, entity.posZ, 0.0d, 0.0d, 0.0d, entity);
    }
}
