package net.moddedmite.bettergarassandleaves.interfaces;

import net.minecraft.TextureAtlasSprite;

public interface ITextureMap {

    default boolean setTextureEntry(String name, TextureAtlasSprite entry) {
        return false;
    }
}
