package net.moddedmite.bettergarassandleaves.util;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.EnumEntityFX;
import net.minecraft.EnumParticle;

public class EnumExtra {
    public static final EnumParticle blood = ClassTinkerers.getEnum(EnumParticle.class, "blood");
    public static final EnumEntityFX bloodFX = ClassTinkerers.getEnum(EnumEntityFX.class, "blood");
}
