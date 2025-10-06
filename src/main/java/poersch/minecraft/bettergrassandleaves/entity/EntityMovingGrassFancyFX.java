package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.Entity;
import net.minecraft.Icon;
import net.minecraft.World;

public class EntityMovingGrassFancyFX extends EntityMovingGrassFastFX {
    protected Entity entity;
    protected float distanceWalked;
    protected int allowedBlockID;
    protected int color;
    protected int brightness;
    protected float brightnessMultiplier;

    public EntityMovingGrassFancyFX(World world, double x, double y, double z, float overScale, int offsetIndex, int brightness, float brightnessMultiplier, int color, Icon icon, boolean flipU, Entity entity, int allowedBlockID) {
        super(world, x, y, z, overScale, offsetIndex, brightness, brightnessMultiplier, color, icon, flipU);
        this.particleMaxAge = 46;
        this.entity = entity;
        if (entity != null) {
            this.distanceWalked = entity.distanceWalkedOnStepModified + 0.5f;
            this.allowedBlockID = allowedBlockID;
        }
        this.color = color;
        this.brightness = brightness;
        this.brightnessMultiplier = brightnessMultiplier;
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
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityMovingGrassFancyFX(this.worldObj, this.entity.posX, this.posY, this.entity.posZ, this.particleGravity, this.offsetIndex - 1, this.brightness, this.brightnessMultiplier, this.color, this.particleIcon, this.flipU, null, 0));
            }
            this.entity = null;
        }
    }
}
