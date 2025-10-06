package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.BlockGrass;
import net.minecraft.IconRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

@Mixin(BlockGrass.class)
public class BlockGrassMixin {
    @Inject(method = "registerIcons", at = @At("HEAD"))
    private void onRegisterIcons(IconRegister iconRegister, CallbackInfo ci) {
        if (BetterGrassAndLeavesMod.workingRegisterIconsHook) {
            BetterBlockRendererList.onRegisterIconsHook(iconRegister);
        }
    }
}
