package net.moddedmite.bettergarassandleaves;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.xiaoyu233.fml.util.EnumExtends;

public class BGALEarlyRiser implements PreLaunchEntrypoint {

    public static final EnumAdder ENTITYFX = ClassTinkerers.enumBuilder("net.minecraft.EnumEntityFX");

    @Override
    public void onPreLaunch() {
        EnumExtends.PARTICLE.addEnum("blood");
        ENTITYFX.addEnum("blood");
        ENTITYFX.build();
    }
}
