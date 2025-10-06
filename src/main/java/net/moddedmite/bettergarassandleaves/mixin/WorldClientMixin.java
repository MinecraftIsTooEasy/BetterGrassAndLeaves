package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

import java.util.Random;

@Mixin(WorldClient.class)
public abstract class WorldClientMixin extends World {

    public WorldClientMixin(ISaveHandler par1ISaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler, ILogAgent par6ILogAgent, long world_creation_time, long total_world_time) {
        super(par1ISaveHandler, par2Str, par3WorldProvider, par4WorldSettings, par5Profiler, par6ILogAgent, world_creation_time, total_world_time);
    }

    @Redirect(method = "doVoidFogParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/Block;randomDisplayTick(Lnet/minecraft/World;IIILjava/util/Random;)V"))
    private void redirectRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        BetterBlockRendererList.onRandomDisplayTickHook(block, world, x, y, z, random);
        block.randomDisplayTick(world, x, y, z, random);
    }
}
