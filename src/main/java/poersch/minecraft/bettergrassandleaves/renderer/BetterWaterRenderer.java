package poersch.minecraft.bettergrassandleaves.renderer;

import net.minecraft.Material;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.EnumParticle;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityWaterSuspendFX;

public class BetterWaterRenderer extends BetterBlockRenderer {
    public static Icon[] iconWaterSpray;
    public static Icon[] iconWaterSuspended;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconWaterSpray = registerBlockIcons("water_spray");
        iconWaterSuspended = registerBlockIcons("water_suspended");
    }

    @Override
    public boolean onSpawnParticle(EnumParticle name, World world, double x, double y, double z, double motionX, double motionY, double motionZ, Entity entity) {
        Icon icon;
        if (BetterGrassAndLeavesMod.waterSuspendedFX.value != 2) {
            return BetterGrassAndLeavesMod.waterSuspendedFX.value == 0;
        }
        if ((world.getBlockMaterial((int) x, ((int) y) + 1, (int) z) != Material.water) || (icon = getIconWaterSuspended(0, (float) Math.random())) == null) {
            return true;
        }
        this.minecraft.effectRenderer.addEffect(new EntityWaterSuspendFX(world, x + Math.random(), y + Math.random(), z + Math.random(), icon));
        return true;
    }

    protected boolean nearShore(IBlockAccess iBlockAccess, int x, int y, int z) {
        return (iBlockAccess.getBlockMaterial(x - 1, y, z) == Material.water && iBlockAccess.getBlockMaterial(x + 1, y, z) == Material.water && iBlockAccess.getBlockMaterial(x, y, z - 1) == Material.water && iBlockAccess.getBlockMaterial(x, y, z + 1) == Material.water) ? false : true;
    }

    public Icon getIconWaterSpray(int metadata, float randomIndex) {
        if (iconWaterSpray == null) {
            return null;
        }
        return iconWaterSpray[(int) ((randomIndex * (iconWaterSpray.length - 1)) + 0.5f)];
    }

    public Icon getIconWaterSuspended(int metadata, float randomIndex) {
        if (iconWaterSuspended == null) {
            return null;
        }
        return iconWaterSuspended[(int) ((randomIndex * (iconWaterSuspended.length - 1)) + 0.5f)];
    }
}
