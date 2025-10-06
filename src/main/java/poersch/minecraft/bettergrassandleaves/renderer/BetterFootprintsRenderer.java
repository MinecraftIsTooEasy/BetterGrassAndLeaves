package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.Block;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.EntityList;
import net.minecraft.EntityLiving;
import net.minecraft.Icon;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityFootprintsFX;

public class BetterFootprintsRenderer extends BetterBlockRenderer {
    public static Icon[] iconFootprint;
    protected static Map<Class<? extends Entity>, Icon[]> iconMap = new HashMap();

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconFootprint = registerBlockIcons("footprint");
        iconMap.clear();
        Set<Map.Entry<String, Class<? extends Entity>>> entries = EntityList.stringToClassMapping.entrySet();
        for (Map.Entry<String, Class<? extends Entity>> entry : entries) {
            Icon[] icon = registerBlockIcons("footprint_" + entry.getKey().toLowerCase());
            if (icon != null) {
                iconMap.put(entry.getValue(), icon);
            }
        }
    }

    protected static Icon getIconFromMap(Class<? extends Entity> entityClass, float randomIndex) {
        Icon[] icons = iconMap.get(entityClass);
        if (icons != null) {
            return icons[(int) ((randomIndex * (icons.length - 1)) + 0.5f)];
        }
        return null;
    }

    @Override
    public boolean onEntityWalking(Block block, World world, int x, int y, int z, Entity entity) {
        if (BetterGrassAndLeavesMod.renderFootprintsFX.value == 0 || !world.isAirBlock(x, y + 1, z)) {
            return false;
        }
        float randomIndex = (float) Math.random();
        Icon icon = getIconFromMap(entity.getClass(), randomIndex);
        if (icon == null) {
            if (iconFootprint == null) {
                return false;
            }
            icon = iconFootprint[(int) ((randomIndex * (iconFootprint.length - 1)) + 0.5f)];
            if (icon == null) {
                return false;
            }
        }
        float particleScale = (float) icon.getIconWidth() / Block.stone.getIcon(0, 0).getIconWidth();
        if ((entity instanceof EntityLiving) && ((EntityLiving) entity).isChild()) {
            particleScale *= 0.5f;
        }
        if (BetterGrassAndLeavesMod.renderFootprintsFX.value == 1) {
            this.minecraft.effectRenderer.addEffect(new EntityFootprintsFX(world, entity.posX, y + (block == Block.snow ? ((1 + world.getBlockMetadata(x, y, z)) & 7) / 8.0d : 1.0d), entity.posZ, particleScale, (-(entity.rotationYaw * 3.141593f)) / 180.0f, icon, (((int) entity.distanceWalkedOnStepModified) & 1) == 0, null, block.blockID));
            return false;
        }
        this.minecraft.effectRenderer.addEffect(new EntityFootprintsFX(world, entity.posX, y + (block == Block.snow ? ((1 + world.getBlockMetadata(x, y, z)) & 7) / 8.0d : 1.0d), entity.posZ, particleScale, (-(entity.rotationYaw * 3.141593f)) / 180.0f, icon, true, entity, block.blockID));
        return false;
    }
}
