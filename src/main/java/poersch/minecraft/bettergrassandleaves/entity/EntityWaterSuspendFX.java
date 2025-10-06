package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Material;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityWaterSuspendFX extends EntityFX {
    public EntityWaterSuspendFX(World world, double x, double y, double z, Icon icon) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleAlpha = 0.0f;
        this.particleScale = 0.5f;
        this.particleMaxAge = 40;
        this.noClip = true;
        this.particleIcon = icon;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float motionAngle = (((float) this.posY) * 0.3f) + (this.worldObj.getTotalWorldTime() * 0.02f);
        moveEntity(MathHelper.sin(motionAngle) * 0.01d, 0.0d, MathHelper.cos(motionAngle) * 0.01d);
        this.particleAge++;
        if (this.particleAge < 20) {
            this.particleAlpha = this.particleAge / 500.0f;
        } else if (this.particleAge > this.particleMaxAge - 20) {
            this.particleAlpha = (this.particleMaxAge - this.particleAge) / 500.0f;
            if (this.particleAge > this.particleMaxAge) {
                setDead();
            }
        } else {
            this.particleAlpha = 0.04f;
        }
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) != Material.water) {
            setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float minU = this.particleIcon.getMinU();
        float maxU = this.particleIcon.getMaxU();
        float minV = this.particleIcon.getMinV();
        float maxV = this.particleIcon.getMaxV();
        float currentX = (float) ((this.prevPosX + ((this.posX - this.prevPosX) * partialTickTime)) - interpPosX);
        float currentY = (float) ((this.prevPosY + ((this.posY - this.prevPosY) * partialTickTime)) - interpPosY);
        float currentZ = (float) ((this.prevPosZ + ((this.posZ - this.prevPosZ) * partialTickTime)) - interpPosZ);
        tessellator.setBrightness(15728640);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) + (rotationXZ * this.particleScale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * this.particleScale) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), currentZ + (rotationYZ * this.particleScale) + (rotationXZ * this.particleScale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ + (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), maxU, maxV);
    }
}
