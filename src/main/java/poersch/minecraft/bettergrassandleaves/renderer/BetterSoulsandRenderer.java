package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.Random;
import net.minecraft.Block;
import net.minecraft.IconRegister;
import net.minecraft.Icon;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.bettergrassandleaves.entity.EntityRisingSoulFX;
import poersch.minecraft.bettergrassandleaves.interfaces.IBetterSoulsand;

public class BetterSoulsandRenderer extends BetterBlockRenderer implements IBetterSoulsand {
    public static Icon[] iconRisingSoul;
    public static Icon[] iconSoulTrack;

    @Override
    public void onRegisterIcons(IconRegister iconRegister) {
        iconRisingSoul = registerBlockIcons("rising_soul");
        iconSoulTrack = registerBlockIcons("soul_track");
    }

    @Override
    public boolean onRandomDisplayTick(Block block, World world, int x, int y, int z, Random random) {
        if (world.provider.dimensionId != -1 || !world.isAirBlock(x, y + 1, z)) {
            return false;
        }
        int metadata = world.getBlockMetadata(x, y, z);
        if (random.nextFloat() <= 1.0f - (0.06f * BetterGrassAndLeavesMod.soulsFXSpawnRate.value)) {
            return false;
        }
        IBetterSoulsand betterSoulsand = block instanceof IBetterSoulsand ? (IBetterSoulsand) block : this;
        Icon icon = betterSoulsand.getIconRisingSoul(metadata, random.nextFloat());
        if (icon == null) {
            return false;
        }
        double xS = x + 0.5d;
        double yS = y + 0.5d;
        double zS = z + 0.5d;
        this.minecraft.effectRenderer.addEffect(new EntityRisingSoulFX(world, xS, yS, zS, icon, betterSoulsand.getIconsSoulTrack(metadata)));
        return false;
    }

    @Override
    public Icon getIconRisingSoul(int metadata, float randomIndex) {
        if (iconRisingSoul == null) {
            return null;
        }
        return iconRisingSoul[(int) ((randomIndex * (iconRisingSoul.length - 1)) + 0.5f)];
    }

    @Override
    public Icon[] getIconsSoulTrack(int metadata) {
        return iconSoulTrack;
    }
}
