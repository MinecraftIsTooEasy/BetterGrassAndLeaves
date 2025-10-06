package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntitySoulTrackFX extends EntityFX {
    float maxAlpha;
    boolean flipU;
    boolean flipV;

    public EntitySoulTrackFX(World world, double x, double y, double z, float maxAlpha, Icon icon) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
//        ?? r3 = 0;
        this.motionZ = 0.0d;
        this.motionY = 0.0d;
//        r3.motionX = this;
        this.particleScale = 0.3f;
        this.particleGravity = 0.01f;
        this.particleMaxAge = 24;
        this.maxAlpha = maxAlpha;
        this.particleAlpha = maxAlpha;
        this.noClip = true;
        this.particleIcon = icon;
        this.flipU = Math.random() > 0.5d;
        this.flipV = Math.random() > 0.5d;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY = (this.motionY * 0.98d) - (0.04d * this.particleGravity);
        this.particleAge++;
        if (this.particleAge < 2) {
            this.particleAlpha = (this.maxAlpha * this.particleAge) / 2.0f;
        } else if (this.particleAge > this.particleMaxAge - 20) {
            this.particleAlpha = (this.maxAlpha * (this.particleMaxAge - this.particleAge)) / 20.0f;
            if (this.particleAge > this.particleMaxAge) {
                setDead();
            }
        } else {
            this.particleAlpha = this.maxAlpha;
        }
        moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        double minU;
        double maxU;
        double minV;
        double maxV;
        if (!this.flipU) {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
        } else {
            minU = this.particleIcon.getMaxU();
            maxU = this.particleIcon.getMinU();
        }
        if (!this.flipV) {
            minV = this.particleIcon.getMinV();
            maxV = this.particleIcon.getMaxV();
        } else {
            minV = this.particleIcon.getMaxV();
            maxV = this.particleIcon.getMinV();
        }
        float currentX = (float) ((this.prevPosX + ((this.posX - this.prevPosX) * partialTickTime)) - interpPosX);
        float currentY = (float) ((this.prevPosY + ((this.posY - this.prevPosY) * partialTickTime)) - interpPosY);
        float currentZ = (float) ((this.prevPosZ + ((this.posZ - this.prevPosZ) * partialTickTime)) - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) + (rotationXZ * this.particleScale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * this.particleScale) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), currentZ + (rotationYZ * this.particleScale) + (rotationXZ * this.particleScale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ + (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), maxU, maxV);
    }
}
