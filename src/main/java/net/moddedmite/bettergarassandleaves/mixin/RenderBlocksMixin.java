package net.moddedmite.bettergarassandleaves.mixin;

import net.minecraft.Block;
import net.minecraft.IBlockAccess;
import net.minecraft.RenderBlocks;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;

@Mixin(RenderBlocks.class)
public class RenderBlocksMixin {
    @Shadow public IBlockAccess blockAccess;

    @Inject(method = "renderBlockByRenderType", at = @At("HEAD"), cancellable = true)
    private void onRenderBlockByRenderType(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (BetterBlockRendererList.onRenderBlockHook(block, this.blockAccess, x, y, z, ReflectHelper.dyCast(this))) {
            cir.setReturnValue(true);
        }
    }
}
