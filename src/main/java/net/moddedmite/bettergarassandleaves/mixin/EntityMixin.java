package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.Block;
import net.minecraft.Entity;
import net.minecraft.World;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterBlood;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBloodRenderer;

@Mixin(Entity.class)
public abstract class EntityMixin implements IBetterBlood {
    @Shadow public World worldObj;

    @Unique public int colorBetterBlood;

    @Redirect(method = "moveEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/Block;onEntityWalking(Lnet/minecraft/World;IIILnet/minecraft/Entity;)V"))
    private void redirectOnEntityWalking(Block block, World world, int x, int y, int z, Entity entity) {
        BetterBlockRendererList.onEntityWalkingHook(block, world, x, y, z, entity);
        block.onEntityWalking(world, x, y, z, entity);
    }

    @Override
    public int getColorBetterBlood() {
        if (this.colorBetterBlood == 0) {
            this.colorBetterBlood = BetterBloodRenderer.getColorBetterBlood(ReflectHelper.dyCast(getClass()));
        }
        return this.colorBetterBlood;
    }
}
