package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.Minecraft;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.EnumParticle;
import net.minecraft.Icon;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityBloodDropsFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterBlood;

public class BetterBloodRenderer extends BetterBlockRenderer {
    public static Icon[] iconBloodDrops;
    public static Icon[] iconBloodStains;
    protected static Map<Class<? extends Entity>, Integer> colorMap = new HashMap();

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconBloodDrops = registerBlockIcons("blood_drops");
        iconBloodStains = registerBlockIcons("blood_stains");
    }

    @Override
    public boolean onSpawnParticle(EnumParticle name, World world, double x, double y, double z, double motionX, double motionY, double motionZ, Entity entity) {
        if (!BetterGrassAndLeavesMod.bloodFX.value || !(entity instanceof IBetterBlood)) {
            return false;
        }
        int bloodColor = ((IBetterBlood) entity).getColorBetterBlood();
        if (bloodColor == -1) {
            return true;
        }
        Icon iconDrops = iconBloodDrops[(int) ((Math.random() * (iconBloodDrops.length - 1)) + 0.5d)];
        Icon iconStain = iconBloodStains[(int) ((Math.random() * (iconBloodStains.length - 1)) + 0.5d)];
        if (iconDrops == null || iconStain == null) {
            return true;
        }
        this.minecraft.effectRenderer.addEffect(new EntityBloodDropsFX(Minecraft.getMinecraft().theWorld, x, y + 0.5d, z, bloodColor, iconDrops, iconStain));
        return true;
    }

    public static void resetBloodColors() {
        colorMap.clear();
    }

    public static void setColorBetterBlood(Class<? extends Entity> entityClass, int color) {
        colorMap.put(entityClass, color);
    }

    public static int getColorBetterBlood(Class<? extends Entity> entityClass) {
        Integer color = colorMap.get(entityClass);
        if (color != null) {
            return color;
        }
        return 16711680;
    }
}
