package net.moddedmite.bettergarassandleaves.event;

import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.events.PlayerLoggedInEvent;
import moddedmite.rustedironcore.api.event.listener.IInitializationListener;
import moddedmite.rustedironcore.api.event.listener.IKeybindingListener;
import moddedmite.rustedironcore.api.event.listener.IPlayerEventListener;
import moddedmite.rustedironcore.api.event.listener.IWorldLoadListener;
import net.minecraft.*;
import net.minecraftforge.common.Configuration;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
import poersch.minecraft.util.gui.GuiModSettings;
import poersch.minecraft.util.options.Option;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BGALEvent extends Handlers {

    public static void register() {
        Initialization.register(new IInitializationListener() {
            @Override
            public void onClientStarted(Minecraft client) {
                BetterGrassAndLeavesMod.modConfig = new Configuration(new File(Minecraft.getMinecraft().mcDataDir, "config/BetterGrassAndLeavesMod.cfg"));
                BetterGrassAndLeavesMod.modConfig.load();
                for (Option<?> option : BetterGrassAndLeavesMod.modOptions) {
                    option.read(BetterGrassAndLeavesMod.modConfig, Configuration.CATEGORY_GENERAL);
                }
                BetterGrassAndLeavesMod.modConfig.save();

                for (Block block : Block.blocksList) {
                    if (block != null) {
                        BetterGrassAndLeavesMod.blockMap.put(block.getClass().getName(), block.blockID);
                    }
                }
                if (BetterGrassAndLeavesMod.debugMode.value) {
                    StringBuilder debugInfo = new StringBuilder("[BGAL Debug Info] Known blocks: ");
                    Set<Map.Entry<String, Integer>> blockEntries = BetterGrassAndLeavesMod.blockMap.entrySet();
                    for (Map.Entry<String, Integer> entry : blockEntries) {
                        debugInfo.append(entry.getKey()).append("; ");
                    }
                    System.out.println(debugInfo);
                    StringBuilder debugInfo2 = new StringBuilder("[BGAL Debug Info] Known entities: ");
                    Set<Map.Entry<String, Class<? extends Entity>>> entityEntries = EntityList.stringToClassMapping.entrySet();
                    for (Map.Entry<String, Class<? extends Entity>> entry2 : entityEntries) {
                        debugInfo2.append(entry2.getKey()).append("; ");
                    }
                    System.out.println(debugInfo2);
                }
            }
        });

        Keybinding.register(new IKeybindingListener() {
            @Override
            public void onKeybindingRegister(Consumer<KeyBinding> registry) {
                registry.accept(BetterGrassAndLeavesMod.keyBetterGrassAndLeavesMod);
            }
        });

    }

}
