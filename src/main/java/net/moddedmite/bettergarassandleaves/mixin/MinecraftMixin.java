package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.minecraft.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.gui.GuiModSettings;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract void displayGuiScreen(GuiScreen par1GuiScreen);

    @Inject(method = "runTick", at = @At("HEAD"))
    private void keyAction(CallbackInfo ci) {
        if (BetterGrassAndLeavesMod.keyBetterGrassAndLeavesMod.isPressed()) {
            this.displayGuiScreen(new GuiModSettings("更好的草和树叶", BetterGrassAndLeavesMod.modIngameOptions, BetterGrassAndLeavesMod.modConfig, BetterGrassAndLeavesMod::onSettingsUpdated));
        }
    }

    @Inject(method = "loadWorld(Lnet/minecraft/WorldClient;)V", at = @At("TAIL"))
    private void load(WorldClient par1WorldClient, CallbackInfo ci) {
        BetterGrassAndLeavesMod.onSettingsUpdated();
    }
}
