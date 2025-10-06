package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityMovingTallGrassFastFX extends EntityFX {
    protected float lightScale;

    public EntityMovingTallGrassFastFX(World world, double x, double y, double z, float overScale, int brightness, int color, Icon icon) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.lightScale = 0.8f;
        this.motionX = overScale;
        this.particleScale = overScale;
        this.motionY = 1.0d;
        this.motionZ = brightness;
        this.particleMaxAge = 22;
        this.particleRed = ((color >> 16) & 255) * 0.00392f;
        this.particleGreen = ((color >> 8) & 255) * 0.00392f;
        this.particleBlue = (color & 255) * 0.00392f;
        this.noClip = true;
        this.particleIcon = icon;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i >= this.particleMaxAge) {
            setDead();
        }
        this.motionY = 1.0d - ((double) this.particleAge / this.particleMaxAge);
        this.particleScale = (float) (this.motionX * this.motionY);
        this.lightScale = 1.0f - (((float) this.motionY) * 0.2f);
        this.motionY *= 1.8d;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        double minU = this.particleIcon.getMinU();
        double maxU = this.particleIcon.getMaxU();
        double minV = this.particleIcon.getMinV();
        double maxV = this.particleIcon.getMaxV();
        double x = this.posX - interpPosX;
        double y = this.posY - interpPosY;
        double z = this.posZ - interpPosZ;
        double minY = y - 0.5d;
        double maxY = y + 0.5d + this.particleScale;
        double minX = x - 0.45d;
        double maxX = x + 0.45d;
        double minZ = z - 0.45d;
        double maxZ = z + 0.45d;
        double panX = 0.45d * this.motionY;
        double panZ = 0.45d * this.motionY;
        tessellator.setBrightness((int) this.motionZ);
        tessellator.setColorRGBA_F(this.particleRed * this.lightScale, this.particleGreen * this.lightScale, this.particleBlue * this.lightScale, this.particleAlpha);
        tessellator.addVertexWithUV(maxX + panX, maxY, maxZ - panZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX + panX, maxY, minZ - panZ, maxU, minV);
        tessellator.addVertexWithUV(maxX - panX, maxY, maxZ + panZ, minU, minV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX - panX, maxY, minZ + panZ, maxU, minV);
        tessellator.addVertexWithUV(minX - panX, maxY, minZ + panZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX - panX, maxY, maxZ + panZ, maxU, minV);
        tessellator.addVertexWithUV(minX + panX, maxY, minZ - panZ, minU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX + panX, maxY, maxZ - panZ, maxU, minV);
        double minX2 = x - (-0.45d);
        double maxX2 = x - 0.45d;
        double minZ2 = z - 0.45d;
        double maxZ2 = z + 0.45d;
        double panX2 = 0.45d * this.motionY;
        double panZ2 = 0.45d * this.motionY;
        tessellator.addVertexWithUV(maxX2 - panX2, maxY, maxZ2 - panZ2, minU, minV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX2 - panX2, maxY, minZ2 - panZ2, maxU, minV);
        tessellator.addVertexWithUV(maxX2 + panX2, maxY, maxZ2 + panZ2, maxU, minV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2 + panX2, maxY, minZ2 + panZ2, minU, minV);
        tessellator.addVertexWithUV(minX2 + panX2, maxY, minZ2 + panZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX2 + panX2, maxY, maxZ2 + panZ2, minU, minV);
        tessellator.addVertexWithUV(minX2 - panX2, maxY, minZ2 - panZ2, minU, minV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2 - panX2, maxY, maxZ2 - panZ2, maxU, minV);
    }
}
