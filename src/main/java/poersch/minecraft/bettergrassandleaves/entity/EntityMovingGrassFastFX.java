package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.Tessellator;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRenderer;

public class EntityMovingGrassFastFX extends EntityFX {
    protected int offsetIndex;
    protected float lightScale;
    protected boolean flipU;

    public EntityMovingGrassFastFX(World world, double x, double y, double z, float overScale, int offsetIndex, int brightness, float brightnessMultiplier, int color, Icon icon, boolean flipU) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        this.lightScale = 0.8f;
        this.motionX = overScale;
        this.particleScale = overScale;
        this.motionY = 1.0d;
        this.motionZ = brightness;
        this.offsetIndex = offsetIndex + 1;
        this.particleRed = ((color >> 16) & 255) * 0.00392f * brightnessMultiplier;
        this.particleGreen = ((color >> 8) & 255) * 0.00392f * brightnessMultiplier;
        this.particleBlue = (color & 255) * 0.00392f * brightnessMultiplier;
        this.particleMaxAge = 22;
        this.noClip = true;
        this.particleIcon = icon;
        this.flipU = flipU;
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
        double minU;
        double maxU;
        if (this.flipU) {
            minU = this.particleIcon.getMaxU();
            maxU = this.particleIcon.getMinU();
        } else {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
        }
        double minV = this.particleIcon.getMinV();
        double maxV = this.particleIcon.getMaxV();
        double x = this.posX - interpPosX;
        double y = this.posY - interpPosY;
        double z = this.posZ - interpPosZ;
        double minY = y - BetterBlockRenderer.offsetMap24px[0][0];
        double maxY = y + BetterBlockRenderer.offsetMap24px[0][0] + this.particleScale;
        double minX = x - BetterBlockRenderer.offsetMap24px[this.offsetIndex][0];
        double maxX = x + BetterBlockRenderer.offsetMap24px[this.offsetIndex][0];
        double minZ = z - BetterBlockRenderer.offsetMap24px[this.offsetIndex][1];
        double maxZ = z + BetterBlockRenderer.offsetMap24px[this.offsetIndex][1];
        double panX = BetterBlockRenderer.offsetMap24px[this.offsetIndex][0] * this.motionY;
        double panZ = BetterBlockRenderer.offsetMap24px[this.offsetIndex][1] * this.motionY;
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
        tessellator.addVertexWithUV(minX - panX, maxY, minZ + panZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX - panX, maxY, maxZ + panZ, minU, minV);
        tessellator.addVertexWithUV(minX + panX, maxY, minZ - panZ, maxU, minV);
        tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX + panX, maxY, maxZ - panZ, minU, minV);
        double minX2 = x - BetterBlockRenderer.offsetMap24px[this.offsetIndex][2];
        double maxX2 = x + BetterBlockRenderer.offsetMap24px[this.offsetIndex][2];
        double minZ2 = z - BetterBlockRenderer.offsetMap24px[this.offsetIndex][3];
        double maxZ2 = z + BetterBlockRenderer.offsetMap24px[this.offsetIndex][3];
        double panX2 = BetterBlockRenderer.offsetMap24px[this.offsetIndex][0] * this.motionY;
        double panZ2 = BetterBlockRenderer.offsetMap24px[this.offsetIndex][1] * this.motionY;
        tessellator.addVertexWithUV(maxX2 - panX2, maxY, maxZ2 - panZ2, minU, minV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX2 - panX2, maxY, minZ2 - panZ2, maxU, minV);
        tessellator.addVertexWithUV(maxX2 + panX2, maxY, maxZ2 + panZ2, minU, minV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(minX2 + panX2, maxY, minZ2 + panZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2 + panX2, maxY, minZ2 + panZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX2 + panX2, maxY, maxZ2 + panZ2, minU, minV);
        tessellator.addVertexWithUV(minX2 - panX2, maxY, minZ2 - panZ2, maxU, minV);
        tessellator.addVertexWithUV(minX2, minY, minZ2, maxU, maxV);
        tessellator.addVertexWithUV(maxX2, minY, maxZ2, minU, maxV);
        tessellator.addVertexWithUV(maxX2 - panX2, maxY, maxZ2 - panZ2, minU, minV);
    }
}
