package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityFallingLeavesFancyFX extends EntityFallingLeavesFastFX {
    public EntityFallingLeavesFancyFX(World world, double x, double y, double z, float scale, float brightnessMultiplier, int color, Icon icon) {
        super(world, x, y, z, scale, brightnessMultiplier, color, icon);
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
        float angle = (float) (this.prevPosY + ((this.posY - this.prevPosY) * partialTickTime) + this.windAngleOffset);
        double radius = 0.176776695d * this.particleScale;
        double minY = currentY - (0.125d * this.particleScale);
        double maxY = currentY + (0.125d * this.particleScale);
        double xA = currentX + (MathHelper.sin(angle) * radius);
        double xB = currentX + (MathHelper.sin((float) (angle + 1.5707963267948966d)) * radius);
        double xC = currentX + (MathHelper.sin((float) (angle + 3.141592653589793d)) * radius);
        double xD = currentX + (MathHelper.sin((float) (angle + 4.71238898038469d)) * radius);
        double zA = currentZ + (MathHelper.cos(angle) * radius);
        double zB = currentZ + (MathHelper.cos((float) (angle + 1.5707963267948966d)) * radius);
        double zC = currentZ + (MathHelper.cos((float) (angle + 3.141592653589793d)) * radius);
        double zD = currentZ + (MathHelper.cos((float) (angle + 4.71238898038469d)) * radius);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV(xA, maxY, zA, minU, minV);
        tessellator.addVertexWithUV(xB, maxY, zB, minU, maxV);
        tessellator.addVertexWithUV(xC, maxY, zC, maxU, maxV);
        tessellator.addVertexWithUV(xD, maxY, zD, maxU, minV);
        tessellator.setColorRGBA_F(this.particleRed * 0.6f, this.particleGreen * 0.6f, this.particleBlue * 0.6f, this.particleAlpha);
        tessellator.addVertexWithUV(xA, minY, zA, maxU, minV);
        tessellator.addVertexWithUV(xD, minY, zD, maxU, maxV);
        tessellator.addVertexWithUV(xC, minY, zC, minU, maxV);
        tessellator.addVertexWithUV(xB, minY, zB, minU, minV);
        tessellator.setColorRGBA_F(this.particleRed * 0.8f, this.particleGreen * 0.8f, this.particleBlue * 0.8f, this.particleAlpha);
        tessellator.addVertexWithUV(xA, maxY, zA, minU, minV);
        tessellator.addVertexWithUV(xA, minY, zA, minU, maxV);
        tessellator.addVertexWithUV(xB, minY, zB, maxU, maxV);
        tessellator.addVertexWithUV(xB, maxY, zB, maxU, minV);
        tessellator.addVertexWithUV(xC, maxY, zC, minU, minV);
        tessellator.addVertexWithUV(xC, minY, zC, minU, maxV);
        tessellator.addVertexWithUV(xD, minY, zD, maxU, maxV);
        tessellator.addVertexWithUV(xD, maxY, zD, maxU, minV);
        tessellator.addVertexWithUV(xB, maxY, zB, minU, minV);
        tessellator.addVertexWithUV(xB, minY, zB, minU, maxV);
        tessellator.addVertexWithUV(xC, minY, zC, maxU, maxV);
        tessellator.addVertexWithUV(xC, maxY, zC, maxU, minV);
        tessellator.addVertexWithUV(xD, maxY, zD, minU, minV);
        tessellator.addVertexWithUV(xD, minY, zD, minU, maxV);
        tessellator.addVertexWithUV(xA, minY, zA, maxU, maxV);
        tessellator.addVertexWithUV(xA, maxY, zA, maxU, minV);
    }
}
