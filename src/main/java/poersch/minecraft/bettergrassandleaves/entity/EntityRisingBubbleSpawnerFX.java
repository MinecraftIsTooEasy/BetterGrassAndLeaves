package poersch.minecraft.bettergrassandleaves.entity;

import net.minecraft.Minecraft;
import net.minecraft.EntityBubbleFX;
import net.minecraft.Tessellator;
import net.minecraft.World;

public class EntityRisingBubbleSpawnerFX extends EntityBubbleFX {
    protected float streamAngleOffset;

    public EntityRisingBubbleSpawnerFX(World world, double x, double y, double z, float streamAngleOffset, int maxAge) {
        super(world, x, y, z, 0.0d, 0.0d, 0.0d);
        float f = this.particleScale * 10.0f;
        this.particleMaxAge = maxAge;
    }

    @Override
    public void onUpdate() {
        int i = this.particleAge;
        this.particleAge = i + 1;
        if (i > this.particleMaxAge) {
            setDead();
        }
        if (this.particleAge % 3 == 0) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityRisingBubbleFX(this.worldObj, this.posX + ((this.particleAge & 1) * 0.08d), this.posY, this.posZ + ((this.particleAge & 2) * 0.04d), this.streamAngleOffset));
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
    }
}
