package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.Entity;
import net.minecraft.Icon;
import net.minecraft.World;

public class EntityMovingTallGrassFancyFX extends EntityMovingTallGrassFastFX {
    protected Entity entity;
    protected float distanceWalked;
    protected int allowedBlockID;
    protected int color;
    protected int brightness;

    public EntityMovingTallGrassFancyFX(World world, double x, double y, double z, float overScale, int brightness, int color, Icon icon, Entity entity, int allowedBlockID) {
        super(world, x, y, z, overScale, brightness, color, icon);
        this.particleMaxAge = 46;
        this.entity = entity;
        if (entity != null) {
            this.distanceWalked = entity.distanceWalkedOnStepModified + 0.5f;
            this.allowedBlockID = allowedBlockID;
        }
        this.color = color;
        this.brightness = brightness;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i > this.particleMaxAge - 10) {
            this.particleAlpha = (this.particleMaxAge - this.particleAge) * 0.1f;
        }
        if (this.entity != null && this.entity.distanceWalkedOnStepModified > this.distanceWalked) {
            if (this.worldObj.getBlockId((int) this.entity.posX, ((int) this.posY) - 1, (int) this.entity.posZ) == this.allowedBlockID) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityMovingTallGrassFancyFX(this.worldObj, this.entity.posX, this.posY, this.entity.posZ, this.particleGravity, this.brightness, this.color, this.particleIcon, null, 0));
            }
            this.entity = null;
        }
    }
}
