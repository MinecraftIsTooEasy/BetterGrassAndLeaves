package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityRisingSoulFX extends EntityFX {
    private final float windAngleOffset;
    private final Icon[] trackIcons;

    public EntityRisingSoulFX(World world, double x, double y, double z, Icon icon, Icon[] trackIcons) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
//        ?? r3 = 0;
        this.motionZ = 0.0d;
        this.motionY = 0.0d;
//        r3.motionX = this;
        this.windAngleOffset = this.particleScale * 10.0f;
        this.particleScale = 0.6f;
        this.particleGravity = -0.3f;
        this.particleMaxAge = 40;
        this.particleAlpha = 0.5f;
        this.noClip = true;
        this.particleIcon = icon;
        this.trackIcons = trackIcons;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        Icon icon;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY = (this.motionY * 0.98d) - (0.04d * this.particleGravity);
        this.particleAge++;
        if (this.particleAge < 18) {
            this.particleAlpha = this.particleAge / 45.0f;
        } else if (this.particleAge > this.particleMaxAge - 12) {
            this.particleAlpha = (this.particleMaxAge - this.particleAge) / 30.0f;
            if (this.particleAge > this.particleMaxAge) {
                setDead();
            }
        } else {
            this.particleAlpha = 0.4f;
        }
        if (this.trackIcons != null && (icon = this.trackIcons[(int) ((Math.random() * (this.trackIcons.length - 1)) + 0.5d)]) != null) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySoulTrackFX(this.worldObj, this.posX, this.posY - 0.3d, this.posZ, this.particleAlpha * 0.8f, icon));
        }
        long offset = (((long) (this.posX * 3129871.0d)) ^ (((long) this.posZ) * 116129781)) ^ ((long) this.posY);
        long offset2 = (offset * offset * 42317861) + (offset * 11);
        float motionAngle = (((float) this.posY) * 0.8f) + this.windAngleOffset;
        this.motionX = (this.motionX * 0.8d) + ((((((offset2 >> 16) & 15) / 15.0f) - 0.5d) + MathHelper.sin(motionAngle)) * 0.025d);
        this.motionZ = (this.motionZ * 0.8d) + ((((((offset2 >> 24) & 15) / 15.0f) - 0.5d) + MathHelper.cos(motionAngle)) * 0.025d);
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
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), minU, maxV);
        tessellator.addVertexWithUV((currentX - (rotationX * this.particleScale)) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), (currentZ - (rotationYZ * this.particleScale)) + (rotationXZ * this.particleScale), minU, minV);
        tessellator.addVertexWithUV(currentX + (rotationX * this.particleScale) + (rotationXY * this.particleScale), currentY + (rotationZ * this.particleScale), currentZ + (rotationYZ * this.particleScale) + (rotationXZ * this.particleScale), maxU, minV);
        tessellator.addVertexWithUV((currentX + (rotationX * this.particleScale)) - (rotationXY * this.particleScale), currentY - (rotationZ * this.particleScale), (currentZ + (rotationYZ * this.particleScale)) - (rotationXZ * this.particleScale), maxU, maxV);
    }
}
