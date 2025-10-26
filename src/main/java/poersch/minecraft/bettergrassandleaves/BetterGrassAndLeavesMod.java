package poersch.minecraft.bettergrassandleaves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.*;
import net.minecraftforge.common.Configuration;
import net.moddedmite.bettergarassandleaves.event.BGALEvent;
import net.xiaoyu233.fml.ModResourceManager;
import org.lwjgl.input.Keyboard;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRendererList;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBloodRenderer;
import poersch.minecraft.bettergrassandleaves.renderer.BetterLeavesRenderer;
import poersch.minecraft.bettergrassandleaves.renderer.BetterBlockRenderer;
import poersch.minecraft.util.BGALStringHelper;
import poersch.minecraft.util.options.*;

public class BetterGrassAndLeavesMod implements ClientModInitializer {
    public static boolean workingRegisterIconsHook = true;
    public static Configuration modConfig;
    public static final Map<String, Integer> blockMap = new HashMap();
    public static List<Option<?>> modOptions = new ArrayList();
    public static OptionOffOn modActive = new OptionOffOn(modOptions, "modActive", "开关全部", "是否启用更好的草和树叶？", "On");
    public static OptionOffOn renderBetterGrass = new OptionOffOn(modOptions, "renderBetterGrass", "更好的草", "是否开启更好的草？", "On");
    public static OptionChoice currentGrassRenderer = new OptionChoice(modOptions, "currentGrassRenderer", "草的渲染", "选择草的渲染效果", "标准", new String[]{"标准", "无"});
    public static OptionOffFastFancy renderGrassSides = new OptionOffFastFancy(modOptions, "renderGrassSides", "草方块四周", "草方块四周是否有植物？", "高品质");
    public static OptionOffOn renderSnowedGrass = new OptionOffOn(modOptions, "renderSnowedGrass", "雪地的草", "被雪覆盖的草的效果", "On");
    public static OptionOffFastFancy renderGrassFX = new OptionOffFastFancy(modOptions, "renderGrassFX", "触碰草的特效", "选择触碰草时的特效", "On");
    public static OptionInterval averageGrassHeight = new OptionInterval(modOptions, "averageGrassHeight", "草的平均高度", "调整草的高度", "0.5");
    public static OptionInterval betterGrassBrightness = new OptionInterval(modOptions, "betterGrassBrightness", "草的亮度", "调整草的亮度", "1.0");
    public static OptionBitList allowBetterGrass = new OptionBitList(modOptions, "allowBetterGrass", "Allow Better Grass", "The listed block IDs will allow Better Grass to render under them. (e.g. allowBetterGrass=0, 10, 20)", "0, 50, 63, 65, 68, 75, 76, 78, 85, 106, 107, 132", false, MITEConstant.SQUARE_OF_64);
    public static OptionOffOn renderBetterCacti = new OptionOffOn(modOptions, "renderBetterCacti", "更好的仙人掌", "是否开启更好的仙人掌？", "On");
    public static OptionInterval betterCactiBrightness = new OptionInterval(modOptions, "betterCactiBrightness", "仙人掌亮度", "调整仙人掌的亮度", "1.0");
    public static OptionOffOn renderBetterSeaweed = new OptionOffOn(modOptions, "renderBetterSeaweed", "更好的海藻", "是否开启更好的海藻？", "On");
    public static OptionInterval algaePopulation = new OptionInterval(modOptions, "algaePopulation", "海藻数量", "调整海藻产生的几率", "0.3");
    public static OptionInterval betterAlgaeBrightness = new OptionInterval(modOptions, "betterAlgaeBrightness", "海藻亮度", "调整海藻的亮度", "1.0");
    public static OptionBitList algaeHostingBiomes = new OptionBitList(modOptions, "algaeHostingBiomes", "Algae Hosting Biomes", "The listed biome IDs will allow Better Algae to render.", "0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22", false, MITEConstant.SQUARE_OF_16);
    public static OptionInterval reedPopulation = new OptionInterval(modOptions, "reedPopulation", "芦苇数量", "调整芦苇产生的几率", "0.9");
    public static OptionInterval reedOffshorePopulation = new OptionInterval(modOptions, "reedOffshorePopulation", "芦苇离岸距离", "调整芦苇离岸距离", "0.0");
    public static OptionInterval betterReedBrightness = new OptionInterval(modOptions, "betterReedBrightness", "芦苇亮度", "调整芦苇亮度", "1.0");
    public static OptionBitList reedHostingBiomes = new OptionBitList(modOptions, "reedHostingBiomes", "Reed Hosting Biomes", "The listed biome IDs will allow Better Reed to render.", "1, 4, 6, 14, 18, 21, 22", false, MITEConstant.SQUARE_OF_16);
    public static OptionOffOn renderBetterCorals = new OptionOffOn(modOptions, "renderBetterCorals", "更好的珊瑚", "是否开启更好的珊瑚？", "On");
    public static OptionInterval coralPopulation = new OptionInterval(modOptions, "coralPopulation", "珊瑚数量", "调整珊瑚产生的几率", "0.8");
    public static OptionInterval maximumCoralDepth = new OptionInterval(modOptions, "maximumCoralDepth", "珊瑚最深的深度", "调整珊瑚生成的最大深度", "50", 0.0f, 255.0f);
    public static OptionInterval minimumCoralDepth = new OptionInterval(modOptions, "minimumCoralDepth", "珊瑚最浅的深度", "调整珊瑚生成的最小深度", "63", 0.0f, 255.0f);
    public static OptionInterval betterCoralsBrightness = new OptionInterval(modOptions, "betterCoralsBrightness", "珊瑚亮度", "调整珊瑚的亮度", "1.0");
    public static OptionBitList coralHostingBiomes = new OptionBitList(modOptions, "coralHostingBiomes", "Coral Hosting Biomes", "The listed biome IDs will allow Better Corals to render.", "0, 16", false, MITEConstant.SQUARE_OF_16);
    public static OptionInterval bubblesFXSpawnRate = new OptionInterval(modOptions, "bubblesFXSpawnRate", "上升气泡特效的产生速率", "调整上升气泡特效产生的速率", "0.3");
    public static OptionOffOn renderBetterLeaves = new OptionOffOn(modOptions, "renderBetterLeaves", "更好的树叶", "是否开启更好的树叶？", "On");
    public static OptionChoice currentLeavesRenderer = new OptionChoice(modOptions, "currentLeavesRenderer", "树叶的渲染", "选择树叶的渲染效果", "高级", new String[]{"标准", "高级", "无"});
    public static OptionOffOn renderSnowedLeaves = new OptionOffOn(modOptions, "renderSnowedLeaves", "覆盖雪的树叶", "被雪覆盖的树叶的效果", "On");
    public static OptionOffOn renderLeavesFX = new OptionOffOn(modOptions, "renderLeavesFX", "落叶渲染", "是否产生落叶", "On");
    public static OptionInterval leavesFXSpawnRate = new OptionInterval(modOptions, "leavesFXSpawnRate", "落叶速率", "调整树叶掉落的速度", "0.5");
    public static OptionOffOn useRoundedVanillaLeaves = new OptionOffOn(modOptions, "useRoundedVanillaLeaves", "圆形树叶贴图", "使用圆形树叶贴图替换原始方形树叶贴图", "On");
    public static OptionOffOn renderOnlyOuterLeaves = new OptionOffOn(modOptions, "renderOnlyOuterLeaves", "仅渲染外围树叶", "是否仅渲染树叶外围？(开启=更好的性能)", "On");
    public static OptionInterval betterLeavesBrightness = new OptionInterval(modOptions, "betterLeavesBrightness", "树叶亮度", "调整树叶的亮度", "1.0");
    public static OptionOffFastFancy renderFootprintsFX = new OptionOffFastFancy(modOptions, "renderFootprintsFX", "足迹特效", "是否渲染脚印特效？", "Fancy");
    public static OptionInterval soulsFXSpawnRate = new OptionInterval(modOptions, "soulsFXSpawnRate", "灵魂沙特效的产生速度", "调整灵魂沙特效的产生速度", "0.2");
    public static OptionOffOn renderBetterLilyPads = new OptionOffOn(modOptions, "renderBetterLilyPads", "更好的睡莲", "是否开启更好的睡莲？", "On");
    public static OptionInterval lilyPadFlowerPopulation = new OptionInterval(modOptions, "lilyPadFlowerPopulation", "开花的几率", "调整睡莲开花的几率", "0.2");
    public static OptionInterval betterLilyPadsBrightness = new OptionInterval(modOptions, "betterLilyPadsBrightness", "睡莲亮度", "调整睡莲的亮度", "1.0");
    public static OptionOffOn renderStoneHair = new OptionOffOn(modOptions, "renderStoneHair", "石毛", "是否渲染地下世界的石毛？", "Off");
    public static OptionOffOn renderBetterNetherrack = new OptionOffOn(modOptions, "renderBetterNetherrack", "更好的地狱岩", "是否开启更好的地狱岩？", "Off");
    public static OptionInterval betterNetherrackBrightness = new OptionInterval(modOptions, "betterNetherrackBrightness", "地狱岩的亮度", "调整地狱岩亮度", "1.0");
    public static OptionOffOn renderBetterLadders = new OptionOffOn(modOptions, "renderBetterLadders", "更好的梯子", "是否开启更好的梯子？", "On");
    public static OptionOffFastFancy waterSuspendedFX = new OptionOffFastFancy(modOptions, "waterSuspendedFX", "水中悬浮特效", "调整水中悬浮特效(快速=Minecraft默认效果) ", "Fancy");
    public static OptionOffOn bloodFX = new OptionOffOn(modOptions, "bloodFX", "溅血效果", "是否开启溅血效果？", "On");
    public static OptionOffOn useRegisterIconsHook = new OptionOffOn(modOptions, "useRegisterIconsHook", "Use Register Icons Hook", "Use Forge based register icons hook (won't work with MCPatcher)?", "Off");
    public static OptionChoice textureSource = new OptionChoice(modOptions, "textureSource", "材质来源", "定义了Mod的材质来源", "Pack > AutoGen > Mod", new String[]{"Pack", "Pack > AutoGen", "Pack > AutoGen > Mod", "Pack > Mod", "Mod"});
    public static OptionStringList blackList = new OptionStringList(modOptions, "blackList", "Blacklist", "The listed block IDs or block classes won't be altered by the mod. (e.g. blackList=1, net.minecraft.block.BlockGrass, 3)", "");
    public static OptionStringList addBloodTo = new OptionStringList(modOptions, "addBloodTo", "Add Blood To", "Add the given blood color to the specified entity. (e.g. addBloodTo=Blaze:ff910f, Skeleton:-1)", "Blaze:ff910f, CaveSpider:2362f1, Creeper:4fc82a, EnderDragon:9f00b7, Enderman:9f00b7, Ghast:-1, LavaSlime:450100, PigZombie:711300, Skeleton:-1, Slime:4fc82a, SnowMan:-1, Spider:2362f1, Squid:09153d, VillagerGolem:-1, WitherBoss:282828, Zombie:711300");
    public static OptionStringList addRendererTo = new OptionStringList(modOptions, "addRendererTo", "Add Renderer To", "Add the given Better Renderer to the specified block IDs. (e.g. addRendererTo=better-grass:1;net.minecraft.BlockGrass;2, better-leaves:net.minecraft.BlockLeaves)", "better-grass:2, better-mycelium:110, better-leaves:18, better-cacti:81, better-seaweed:3, better-corals:1;12, better-soulsand:88, better-footprints:12;13;78;88, better-lily-pad:111, better-netherrack:87, better-water:9, better-ladder:65");
    public static OptionStringList whiteList = new OptionStringList(modOptions, "whiteList", "Whitelist", "The listed texture names will be linked to an auto generated Better Leaves icon, which will then be used as texture fallback. (e.g. whiteList=leaves_oak, leaves_spruce, leaves_birch)", "");
    public static OptionOffOn debugMode = new OptionOffOn(modOptions, "debugMode", "Debug Mode", "Prints debug information to Minecraft's console? (search for the [BGAL-Debug-Info] to find it)", "Off");
    public static Option<?>[] modIngameOptions = {modActive, currentGrassRenderer, renderBetterGrass, renderGrassSides, renderSnowedGrass, renderGrassFX, averageGrassHeight, betterGrassBrightness, currentLeavesRenderer, renderBetterLeaves, useRoundedVanillaLeaves, renderSnowedLeaves, renderLeavesFX, leavesFXSpawnRate, renderOnlyOuterLeaves, betterLeavesBrightness, renderBetterSeaweed, reedPopulation, algaePopulation, reedOffshorePopulation, betterAlgaeBrightness, betterReedBrightness, renderBetterCorals, coralPopulation, maximumCoralDepth, minimumCoralDepth, betterCoralsBrightness, bubblesFXSpawnRate, renderBetterLilyPads, lilyPadFlowerPopulation, betterLilyPadsBrightness, renderBetterCacti, betterCactiBrightness, renderStoneHair, renderBetterNetherrack, betterNetherrackBrightness, renderBetterLadders, renderFootprintsFX, soulsFXSpawnRate, waterSuspendedFX, bloodFX};
    public static Logger logger = Logger.getLogger("BetterGrassAndLeavesMod");
    public static KeyBinding keyBetterGrassAndLeavesMod = new KeyBinding("bgal.keybinding.name", Keyboard.KEY_F10);
    public static String resourceDomain = "bettergrassandleaves";
    public static String resourceID = "bettergrassandleaves:";

    @Override
    public void onInitializeClient() {
        BGALEvent.register();
        ModResourceManager.addResourcePackDomain(resourceDomain);
    }

    public static void updatePlugins(IconRegister iconRegister) {
        Class<? extends Entity> entityClass;
        BetterBlockRendererList.resetBlackList();
        for (String value : blackList.value) {
            if (value.charAt(0) != '-') {
                try {
                    BetterBlockRendererList.addToBlackList(Integer.parseInt(value));
                } catch (Exception e) {
                    Integer blockID = blockMap.get(value);
                    if (blockID != null) {
                        BetterBlockRendererList.addToBlackList(blockID);
                    }
                }
            } else if (value.length() > 1) {
                String value2 = value.substring(1);
                try {
                    BetterBlockRendererList.removeFromBlackList(Integer.parseInt(value2));
                } catch (Exception e2) {
                    Integer blockID2 = blockMap.get(value2);
                    if (blockID2 != null) {
                        BetterBlockRendererList.removeFromBlackList(blockID2);
                    }
                }
            }
        }
        BetterBloodRenderer.resetBloodColors();
        for (String s : addBloodTo.value) {
            List<String> tokens = BGALStringHelper.splitTrimToList(s, ':');
            if (tokens.size() > 1 && (entityClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(tokens.get(0))) != null) {
                try {
                    BetterBloodRenderer.setColorBetterBlood(entityClass, Integer.parseInt(tokens.get(1), 16));
                } catch (Exception ignore) {
                }
            }
        }
        BetterBlockRendererList.resetRendererAssignments();
        for (String s2 : addRendererTo.value) {
            List<String> tokens2 = BGALStringHelper.splitTrimToList(s2, ':');
            if (tokens2.size() > 1) {
                String renderName = tokens2.get(0);
                List<String> values = BGALStringHelper.splitTrimToList(tokens2.get(1), ';');
                for (BetterBlockRendererList renderer : BetterBlockRendererList.rendererList) {
                    if (renderName.equals(renderer.getRendererName())) {
                        for (String value3 : values) {
                            if (value3.charAt(0) != '-') {
                                try {
                                    renderer.assignToBlockID(Integer.parseInt(value3));
                                } catch (Exception e4) {
                                    Integer blockID3 = blockMap.get(value3);
                                    if (blockID3 != null) {
                                        renderer.assignToBlockID(blockID3);
                                    }
                                }
                            } else if (value3.length() > 1) {
                                String value4 = value3.substring(1);
                                try {
                                    renderer.removeFromBlockID(Integer.parseInt(value4));
                                } catch (Exception e5) {
                                    Integer blockID4 = blockMap.get(value4);
                                    if (blockID4 != null) {
                                        renderer.removeFromBlockID(blockID4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        BetterLeavesRenderer.resetBetterLeavesLinkage();
        for (String textureName : whiteList.value) {
            BetterLeavesRenderer.linkBetterLeavesTo(iconRegister, textureName);
        }
    }

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void onSettingsUpdated() {
        Minecraft mc = Minecraft.getMinecraft();
        BetterBlockRenderer.grassRenderer.setRendererChoice(currentGrassRenderer.value);
        BetterBlockRenderer.leavesRenderer.setRendererChoice(currentLeavesRenderer.value);
        mc.renderGlobal.loadRenderers();
    }
}
