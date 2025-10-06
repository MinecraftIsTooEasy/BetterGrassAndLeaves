package poersch.minecraft.util;

import net.minecraft.Minecraft;
import net.minecraft.Resource;
import net.minecraft.ResourceLocation;
import net.minecraft.ResourceManager;
import net.minecraft.ResourcePackRepository;
import net.minecraft.IconRegister;
import net.minecraft.TextureAtlasSprite;
import net.minecraft.Icon;
import net.moddedmite.bettergarassandleaves.interfaces.ITextureMap;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.texture.ITextureLoadingCallback;
import poersch.minecraft.util.texture.TextureAtlasSpriteLoadingCallback;

public class ResourceHelper {
    private static ResourceManager resourceManager;
    private static ResourcePackRepository resourcepackRepository;

    public static String getCurrentResourcepack() {
        if (resourcepackRepository == null) {
            resourcepackRepository = Minecraft.getMinecraft().getResourcePackRepository();
        }
        return resourcepackRepository.getResourcePackName();
    }

    public static Resource getResource(ResourceLocation location) {
        if (resourceManager == null) {
            resourceManager = Minecraft.getMinecraft().getResourceManager();
        }
        try {
            return resourceManager.getResource(location);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean resourceExists(ResourceLocation location) {
        return getResource(location) != null;
    }

    public static Icon registerIcon(IconRegister iconRegister, String path, String name) {
        if (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, path + name + ".png", false))) {
            return iconRegister.registerIcon(BetterGrassAndLeavesMod.resourceID + name);
        }
        return null;
    }

    public static Icon[] registerIcons(IconRegister iconRegister, String path, String name) {
        if (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, path + name + ".png", false))) {
            return new Icon[]{iconRegister.registerIcon(BetterGrassAndLeavesMod.resourceID + name)};
        }
        int iconCount = 0;
        while (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, path + name + "_" + iconCount + ".png", false))) {
            iconCount++;
        }
        if (iconCount == 0) {
            return null;
        }
        Icon[] icon = new Icon[iconCount];
        for (int n = 0; n < iconCount; n++) {
            icon[n] = iconRegister.registerIcon(BetterGrassAndLeavesMod.resourceID + name + "_" + n);
        }
        return icon;
    }

    public static Icon registerIconOrCallback(IconRegister iconRegister, String targetPath, String targetName, String sourcePath, String sourceName, ITextureLoadingCallback callback) {
        Icon icon = registerIcon(iconRegister, targetPath, targetName);
        if (icon != null) {
            return icon;
        }
        Icon icon2 = registerIconCallback(iconRegister, targetName, sourcePath, sourceName, callback);
        if (icon2 != null) {
            return icon2;
        }
        return null;
    }

    public static Icon[] registerIconsOrCallback(IconRegister iconRegister, String targetPath, String targetName, String sourcePath, String sourceName, ITextureLoadingCallback callback) {
        Icon[] icon = registerIcons(iconRegister, targetPath, targetName);
        if (icon != null) {
            return icon;
        }
        Icon[] icon2 = registerIconsCallback(iconRegister, targetName, sourcePath, sourceName, callback);
        if (icon2 != null) {
            return icon2;
        }
        return null;
    }

    private static Icon registerIconCallbackInternal(IconRegister iconRegister, String name, String sourcePath, String sourceName, ITextureLoadingCallback callback) {
        ResourceLocation location = new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, sourcePath + sourceName + ".png");
        TextureAtlasSprite textureAtlasSprite = new TextureAtlasSpriteLoadingCallback(name, location, callback);
        if (((ITextureMap) iconRegister).setTextureEntry(name, textureAtlasSprite)) {
            return textureAtlasSprite;
        }
        return null;
    }

    public static Icon registerIconCallback(IconRegister iconRegister, String targetName, String sourcePath, String sourceName, ITextureLoadingCallback callback) {
        if (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, sourcePath + sourceName + ".png", false))) {
            return registerIconCallbackInternal(iconRegister, targetName, sourcePath, sourceName, callback);
        }
        return null;
    }

    public static Icon[] registerIconsCallback(IconRegister iconRegister, String targetName, String sourcePath, String sourceName, ITextureLoadingCallback callback) {
        if (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, sourcePath + sourceName + ".png", false))) {
            return new Icon[]{registerIconCallbackInternal(iconRegister, targetName, sourcePath, sourceName, callback)};
        }
        int iconCount = 0;
        while (resourceExists(new ResourceLocation(BetterGrassAndLeavesMod.resourceDomain, sourcePath + sourceName + "_" + iconCount + ".png", false))) {
            iconCount++;
        }
        if (iconCount == 0) {
            return null;
        }
        Icon[] icon = new Icon[iconCount];
        for (int n = 0; n < iconCount; n++) {
            icon[n] = registerIconCallbackInternal(iconRegister, targetName + "_" + n, sourcePath, sourceName + "_" + n, callback);
        }
        return icon;
    }
}
