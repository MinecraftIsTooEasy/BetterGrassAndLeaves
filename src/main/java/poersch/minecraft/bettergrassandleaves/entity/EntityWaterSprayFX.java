package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.BlockFluid;
import net.minecraft.Material;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRenderer;

public class EntityWaterSprayFX extends EntityFX {
    public EntityWaterSprayFX(World world, double x, double y, double z, float scale, Icon icon) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.motionX *= 0.0d;
        this.motionY *= 0.0d;
        this.motionZ *= 0.0d;
        this.particleGravity = 0.02f;
        this.particleScale = 0.3f;
        this.particleMaxAge = 40;
        this.noClip = true;
        this.particleIcon = icon;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        this.particleAge++;
        if (this.particleAge < 10) {
            this.particleAlpha = this.particleAge / 16.7f;
        } else if (this.particleAge > this.particleMaxAge - 10) {
            this.particleAlpha = (this.particleMaxAge - this.particleAge) / 16.7f;
            if (this.particleAge > this.particleMaxAge) {
                setDead();
            }
        } else {
            this.particleAlpha = 0.6f;
        }
        if (this.worldObj.getBlockMaterial((int) this.posX, (int) this.posY, (int) this.posZ) == Material.water) {
            if (this.posY < (((int) this.posY) + 1) - BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata((int) this.posX, (int) this.posY, (int) this.posZ))) {
                this.motionY = 0.0d;
            } else {
                this.motionY = (this.motionY * 0.98d) - (this.particleGravity * 0.5d);
            }
            float motionAngle = (float) BlockFluid.getFlowDirection(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, Material.water);
            this.motionX *= 0.86d;
            this.motionZ *= 0.86d;
            if (motionAngle != -1000.0d) {
                this.motionX -= MathHelper.sin(motionAngle) * 0.005d;
                this.motionZ += MathHelper.cos(motionAngle) * 0.005d;
            }
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        moveEntity(this.motionX, this.motionY, this.motionZ);
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
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, this.particleAlpha);
        this.particleScale = 0.3f + (((this.particleAge + partialTickTime) / this.particleMaxAge) * 0.3f);
        BetterBlockRenderer.renderBlock(this.particleIcon, 0.0d, 0.0d, 0.0d, this.particleScale, 1.0f, 1.0f, 1.0f, this.particleAlpha, false, false);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) + (rotationXZ * this.particleScale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * this.particleScale) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), currentZ + (rotationYZ * this.particleScale) + (rotationXZ * this.particleScale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ + (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), maxU, maxV);
    }
}
