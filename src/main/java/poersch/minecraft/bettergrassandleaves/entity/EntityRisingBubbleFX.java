package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.BlockFluid;
import net.minecraft.Material;
import net.minecraft.Minecraft;
import net.minecraft.EntityBubbleFX;
import net.minecraft.EntityRainFX;
import net.minecraft.MathHelper;
import net.minecraft.World;

public class EntityRisingBubbleFX extends EntityBubbleFX {
    protected float streamAngleOffset;
    protected double surfaceY;

    public EntityRisingBubbleFX(World world, double x, double y, double z, float streamAngleOffset) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.surfaceY = 1000000.0d;
//        ?? r3 = 0;
        this.motionZ = 0.0d;
        this.motionY = 0.0d;
//        r3.motionX = this;
        this.streamAngleOffset = streamAngleOffset;
        this.particleGravity = this.particleScale;
        this.particleScale = 0.0f;
        this.particleMaxAge = 0;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.surfaceY == 1000000.0d && this.worldObj.getBlockMaterial((int) this.posX, (int) (this.posY + 0.5d), (int) this.posZ) != Material.water) {
            this.surfaceY = (((int) this.posY) + 1.01d) - BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata((int) this.posX, (int) this.posY, (int) this.posZ));
        }
        if (this.posY < this.surfaceY) {
            int i = this.particleAge;
            this.particleAge = i + 1;
            if (i < 12) {
                this.particleScale = this.particleGravity * (this.particleAge / 12.0f);
            }
            float motionAngle = (((float) this.posY) * 0.8f) + this.streamAngleOffset;
            this.motionX = MathHelper.sin(motionAngle) * 0.22d * this.motionY;
            this.motionY = ((this.motionY * 0.98d) + 0.008d) - (0.0022d * this.particleGravity);
            this.motionZ = MathHelper.sin(motionAngle) * 0.22d * this.motionY;
            moveEntity(this.motionX, this.posY + this.motionY < this.surfaceY ? this.motionY : this.surfaceY - this.posY, this.motionZ);
            if (this.surfaceY < 1000000.0d && this.worldObj.getBlockMaterial((int) this.posX, (int) this.posY, (int) this.posZ) != Material.water) {
                setDead();
                return;
            }
            return;
        }
        this.particleScale = this.particleGravity * 1.5f;
        int i2 = this.particleMaxAge;
        this.particleMaxAge = i2 + 1;
        if (i2 > 0) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityRainFX(this.worldObj, this.posX, this.posY, this.posZ));
            setDead();
        }
    }
}
