package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.BlockFluid;
import net.minecraft.Material;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityFallingLeavesFastFX extends EntityFX {
    protected float windAngleOffset;
    protected boolean wasInWater;

    public EntityFallingLeavesFastFX(World world, double x, double y, double z, float scale, float brightnessMultiplier, int color, Icon icon) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.wasInWater = false;
//        ?? r3 = 0;
        this.motionZ = 0.0d;
        this.motionY = 0.0d;
//        r3.motionX = this;
        this.particleTextureJitterX = ((int) this.particleTextureJitterX) * 4.0f;
        this.particleTextureJitterY = ((int) this.particleTextureJitterY) * 4.0f;
        this.particleRed = ((color >> 16) & 255) * 0.00392f * brightnessMultiplier;
        this.particleGreen = ((color >> 8) & 255) * 0.00392f * brightnessMultiplier;
        this.particleBlue = (color & 255) * 0.00392f * brightnessMultiplier;
        this.windAngleOffset = this.particleScale * 10.0f;
        this.particleScale = scale;
        this.particleGravity = 0.0032f;
        this.particleMaxAge = 50;
        this.noClip = false;
        this.particleIcon = icon;
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
        if (this.onGround) {
            this.motionY = -0.001d;
            this.motionX *= 0.6d;
            this.motionZ *= 0.6d;
            this.particleAge++;
            if (this.particleAge > this.particleMaxAge - 12) {
                this.particleAlpha = (this.particleMaxAge - this.particleAge) / 12.0f;
                if (this.particleAge > this.particleMaxAge) {
                    setDead();
                }
            }
        } else if (this.worldObj.getBlockMaterial((int) this.posX, (int) this.posY, (int) this.posZ) == Material.water && this.posY < (((int) this.posY) + 1.06d) - BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata((int) this.posX, (int) this.posY, (int) this.posZ))) {
            this.wasInWater = true;
            this.motionY = this.particleGravity * 0.5d;
            float motionAngle = (float) BlockFluid.getFlowDirection(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, Material.water);
            this.motionX *= 0.93d;
            this.motionZ *= 0.93d;
            if (motionAngle != -1000.0d) {
                this.motionX -= MathHelper.sin(motionAngle) * 0.0044d;
                this.motionZ += MathHelper.cos(motionAngle) * 0.0044d;
            }
            this.particleAge++;
            if (this.particleAge > this.particleMaxAge - 12) {
                this.particleAlpha = (this.particleMaxAge - this.particleAge) / 12.0f;
                if (this.particleAge > this.particleMaxAge) {
                    setDead();
                }
            }
        } else {
            this.motionY = (this.motionY * 0.98d) - this.particleGravity;
            if (!this.wasInWater) {
                float motionAngle2 = (((float) this.posY) * 0.8f) + this.windAngleOffset;
                this.motionX = MathHelper.sin(motionAngle2) * 0.4d * (-this.motionY);
                this.motionZ = MathHelper.cos(motionAngle2) * 0.4d * (-this.motionY);
            } else {
                this.motionX *= 0.99d;
                this.motionZ *= 0.99d;
            }
        }
        moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float minU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX);
        float maxU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX + 4.0f);
        float minV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY);
        float maxV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY + 4.0f);
        float currentX = (float) ((this.prevPosX + ((this.posX - this.prevPosX) * partialTickTime)) - interpPosX);
        float currentY = (float) ((this.prevPosY + ((this.posY - this.prevPosY) * partialTickTime)) - interpPosY);
        float currentZ = (float) ((this.prevPosZ + ((this.posZ - this.prevPosZ) * partialTickTime)) - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        double scale = 0.125d * this.particleScale;
        tessellator.addVertexWithUV((currentX - (rotationX * scale)) - (rotationXY * scale), currentY - (rotationZ * scale), (currentZ - (rotationYZ * scale)) - (rotationXZ * scale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * scale)) + (rotationXY * scale), currentY + (rotationZ * scale), (currentZ - (rotationYZ * scale)) + (rotationXZ * scale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * scale) + (rotationXY * scale), currentY + (rotationZ * scale), currentZ + (rotationYZ * scale) + (rotationXZ * scale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * scale)) - (rotationXY * scale), currentY - (rotationZ * scale), (currentZ + (rotationYZ * scale)) - (rotationXZ * scale), maxU, maxV);
    }
}
