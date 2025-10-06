package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityBloodDropsFX extends EntityFX {
    private final Icon iconBloodStain;

    public EntityBloodDropsFX(World world, double x, double y, double z, int color, Icon icon, Icon iconBloodStain) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.particleRed = ((color >> 16) & 255) * 0.00392f;
        this.particleGreen = ((color >> 8) & 255) * 0.00392f;
        this.particleBlue = (color & 255) * 0.00392f;
        this.particleGravity = 0.02f;
        this.particleScale = 0.2f;
        this.particleMaxAge = 50;
        this.noClip = false;
        this.particleIcon = icon;
        this.iconBloodStain = iconBloodStain;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        this.particleScale = 0.2f + ((0.3f * this.particleAge) / this.particleMaxAge);
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i >= this.particleMaxAge) {
            setDead();
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98d;
        this.motionY = (this.motionY * 0.98d) - this.particleGravity;
        this.motionZ *= 0.98d;
        if (this.onGround) {
            double var10005 = (int) (this.posY + 0.5d);
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBloodStainsFX(this.worldObj, this.posX, var10005, this.posZ, 1.0f, (float) (Math.random() * 3.141592653589793d), this.particleRed, this.particleGreen, this.particleBlue, this.iconBloodStain, Math.random() > 0.5d));
            setDead();
        }
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
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) + (rotationXZ * this.particleScale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * this.particleScale) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), currentZ + (rotationYZ * this.particleScale) + (rotationXZ * this.particleScale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ + (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), maxU, maxV);
    }
}
