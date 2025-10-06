package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.Entity;
import net.minecraft.EntityFX;
import net.minecraft.Icon;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityFootprintsFX extends EntityFX {
    private Entity entity;
    private float distanceWalked;
    private final boolean flipU;
    private final int allowedBlockID;

    public EntityFootprintsFX(World world, double x, double y, double z, float scale, float angle, Icon icon, boolean flipU, Entity entity, int allowedBlockID) {
        super(world, x, y + 0.02d, z, 0.0d, 0.0d, 0.0d);
        this.particleScale = scale;
        scale = (float)((double) scale * 0.707106781);
        angle = (float)((double) angle - 0.7853981633974483);
        this.prevPosX = MathHelper.sin(angle) * scale;
        this.prevPosY = MathHelper.cos(angle) * scale;
        angle = (float)((double) angle + 1.5707963267948966);
        this.prevPosZ = MathHelper.sin(angle) * scale;
        this.particleGravity = MathHelper.cos(angle) * scale;
        this.particleAlpha = 0.4f;
        this.particleMaxAge = 550;
        this.noClip = true;
        this.particleIcon = icon;
        this.flipU = flipU;
        this.allowedBlockID = allowedBlockID;
        this.entity = entity;
        if (entity != null) {
            this.distanceWalked = entity.distanceWalkedOnStepModified + 0.5f;
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        this.particleAlpha = 0.4f - ((0.4f * this.particleAge) / this.particleMaxAge);
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i > this.particleMaxAge) {
            setDead();
        }
        if ((this.particleAge & 8) == 0 && this.worldObj.getBlockId((int) this.posX, (int) (this.posY - 0.025d), (int) this.posZ) != this.allowedBlockID) {
            setDead();
        }
        if (this.entity != null && this.entity.distanceWalkedOnStepModified > this.distanceWalked) {
            if (this.worldObj.getBlockId((int) this.entity.posX, (int) (this.posY - 0.025d), (int) this.entity.posZ) == this.allowedBlockID) {
                double var10005 = this.posY - 0.02d;
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFootprintsFX(this.worldObj, this.entity.posX, var10005, this.entity.posZ, this.particleScale, (-(this.entity.rotationYaw * 3.141593f)) / 180.0f, this.particleIcon, !this.flipU, null, this.allowedBlockID));
            }
            this.entity = null;
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
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, this.particleAlpha);
        tessellator.addVertexWithUV(x + this.prevPosX, y, z + this.prevPosY, maxU, minV);
        tessellator.addVertexWithUV(x + this.prevPosZ, y, z + this.particleGravity, minU, minV);
        tessellator.addVertexWithUV(x - this.prevPosX, y, z - this.prevPosY, minU, maxV);
        tessellator.addVertexWithUV(x - this.prevPosZ, y, z - this.particleGravity, maxU, maxV);
    }
}
