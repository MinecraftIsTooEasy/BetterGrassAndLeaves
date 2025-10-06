package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityBloodStainsFX extends EntityFX {
    private boolean flipU;

    public EntityBloodStainsFX(World world, double x, double y, double z, float scale, float angle, float red, float green, float blue, Icon icon, boolean flipU) {
        super(world, x, y + 0.03d, z, 0.0d, 0.0d, 0.0d);
        this.particleScale = scale;
        scale = (float)((double) scale * 0.707106781);
        angle = (float)((double) angle - 0.7853981633974483);
        this.prevPosX = MathHelper.sin(angle) * scale;
        this.prevPosY = MathHelper.cos(angle) * scale;
        angle = (float)((double) angle + 1.5707963267948966);
        this.prevPosZ = MathHelper.sin(angle) * scale;
        this.particleGravity = MathHelper.cos(angle) * scale;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleAlpha = 1.0f;
        this.particleMaxAge = 450;
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
        this.particleAlpha = 1.0f - (this.particleAge / this.particleMaxAge);
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i > this.particleMaxAge) {
            setDead();
        }
        if ((this.particleAge & 8) == 0 && this.worldObj.getBlockId((int) this.posX, (int) (this.posY - 0.035d), (int) this.posZ) == 0) {
            setDead();
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
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
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        if (this.particleAge < 4) {
            this.particleScale = (this.particleAge + partialTickTime) / 4.0f;
            tessellator.addVertexWithUV(x + (this.prevPosX * this.particleScale), y, z + (this.prevPosY * this.particleScale), maxU, minV);
            tessellator.addVertexWithUV(x + (this.prevPosZ * this.particleScale), y, z + (this.particleGravity * this.particleScale), minU, minV);
            tessellator.addVertexWithUV(x - (this.prevPosX * this.particleScale), y, z - (this.prevPosY * this.particleScale), minU, maxV);
            tessellator.addVertexWithUV(x - (this.prevPosZ * this.particleScale), y, z - (this.particleGravity * this.particleScale), maxU, maxV);
            return;
        }
        tessellator.addVertexWithUV(x + this.prevPosX, y, z + this.prevPosY, maxU, minV);
        tessellator.addVertexWithUV(x + this.prevPosZ, y, z + this.particleGravity, minU, minV);
        tessellator.addVertexWithUV(x - this.prevPosX, y, z - this.prevPosY, minU, maxV);
        tessellator.addVertexWithUV(x - this.prevPosZ, y, z - this.particleGravity, maxU, maxV);
    }
}
