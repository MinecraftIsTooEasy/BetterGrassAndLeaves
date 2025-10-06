package poersch.minecraft.bettergrassandleaves.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.minecraft.RenderBlocks;
import net.minecraft.IconRegister;
import net.minecraft.Entity;
import net.minecraft.ServerPlayer;
import net.minecraft.EnumParticle;
import net.minecraft.MITEConstant;
import net.minecraft.Tessellator;
import net.minecraft.IBlockAccess;
import net.minecraft.World;
import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;

public class BetterBlockRendererList extends ArrayList<BetterBlockRenderer> {
    public static final int RENDER_BLOCK_EVENT = 1;
    public static final int RANDOM_DISPLAY_TICK_EVENT = 2;
    public static final int ENTITY_WALKING_EVENT = 4;
    private static final int standardIndex = 0;
    private int optionIndex = 0;
    private final String name;
    private final int events;
    public static ArrayList<BetterBlockRendererList> rendererList = new ArrayList<>();
    public static Map<EnumParticle, BetterBlockRendererList> particleSpawnerList = new EnumMap(EnumParticle.class);
    private static final ArrayList<LinkedList<BetterBlockRendererList>> renderBlockEventMap = new ArrayList<>(MITEConstant.SQUARE_OF_64);
    private static final ArrayList<LinkedList<BetterBlockRendererList>> entityWalkingEventMap = new ArrayList<>(MITEConstant.SQUARE_OF_64);
    private static final ArrayList<LinkedList<BetterBlockRendererList>> randomDisplayEventMap = new ArrayList<>(MITEConstant.SQUARE_OF_64);
    private static final boolean[] blackList = new boolean[MITEConstant.SQUARE_OF_64];
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    static {
        for (int n = 0; n < 4096; n++) {
            renderBlockEventMap.add(null);
        }
        for (int n2 = 0; n2 < 4096; n2++) {
            entityWalkingEventMap.add(null);
        }
        for (int n3 = 0; n3 < 4096; n3++) {
            randomDisplayEventMap.add(null);
        }
    }

    public BetterBlockRendererList(String name, int events) {
        this.name = name;
        this.events = events;
        rendererList.add(this);
    }

    public static void onRegisterIconsHook(IconRegister iconRegister) {
        BetterBlockRenderer.initiateIconRegistration(iconRegister);
        BetterGrassAndLeavesMod.updatePlugins(iconRegister);
        for (BetterBlockRendererList renderer : rendererList) {
            for (BetterBlockRenderer blockRenderer : renderer) {
                blockRenderer.onRegisterIcons(iconRegister);
            }
        }
    }

    public static boolean onSpawnParticleHook(EnumParticle name, World world, double x, double y, double z, double motionX, double motionY, double motionZ, Entity entity) {
        BetterBlockRendererList assignedRenderer;
        if (BetterGrassAndLeavesMod.modActive.value && (assignedRenderer = particleSpawnerList.get(name)) != null) {
            return assignedRenderer.getCurrentRenderer().onSpawnParticle(name, world, x, y, z, motionX, motionY, motionZ, entity);
        }
        return false;
    }

    public static boolean onRenderBlockHook(Block block, IBlockAccess iBlockAccess, int x, int y, int z, RenderBlocks renderBlocks) {
        LinkedList<BetterBlockRendererList> assignedRenderer;
        if (BetterGrassAndLeavesMod.modActive.value && (assignedRenderer = renderBlockEventMap.get(block.blockID)) != null) {
            BetterBlockRenderer.tessellator = Tessellator.instance;
            boolean returnValue = true;
            for (BetterBlockRendererList blockRenderers : assignedRenderer) {
                returnValue &= blockRenderers.getCurrentRenderer().renderWorldBlock(renderBlocks, iBlockAccess, block, x, y, z);
            }
            return returnValue;
        }
        return false;
    }

    public static boolean onRandomDisplayTickHook(Block block, World world, int x, int y, int z, Random random) {
        LinkedList<BetterBlockRendererList> assignedRenderer;
        if (BetterGrassAndLeavesMod.modActive.value && (assignedRenderer = randomDisplayEventMap.get(block.blockID)) != null) {
            boolean returnValue = true;
            for (BetterBlockRendererList blockRenderers : assignedRenderer) {
                returnValue &= blockRenderers.getCurrentRenderer().onRandomDisplayTick(block, world, x, y, z, random);
            }
            return returnValue;
        }
        return false;
    }

    public static boolean onEntityWalkingHook(Block block, World world, int x, int y, int z, Entity entity) {
        LinkedList<BetterBlockRendererList> assignedRenderer;
        if (!(Boolean) BetterGrassAndLeavesMod.modActive.value) {
            return false;
        }
        if (Minecraft.isSingleplayer() && (entity instanceof ServerPlayer)) {
            return false;
        }
        if ((minecraft.renderViewEntity != null && !entity.isInRangeToRenderDist(entity.getDistanceSqToEntity(minecraft.renderViewEntity))) || (assignedRenderer = entityWalkingEventMap.get(block.blockID)) == null) {
            return false;
        }
        boolean returnValue = true;
        for (BetterBlockRendererList blockRenderers : assignedRenderer) {
            returnValue &= blockRenderers.getCurrentRenderer().onEntityWalking(block, world, x, y, z, entity);
        }
        return returnValue;
    }

    public BetterBlockRenderer getCurrentRenderer() {
        return get(this.optionIndex);
    }

    public BetterBlockRendererList setRendererChoice(int rendererChoice) {
        this.optionIndex = rendererChoice;
        return this;
    }

    public String getRendererName() {
        return this.name;
    }

    public BetterBlockRendererList addRenderer(BetterBlockRenderer... renderer) {
        Collections.addAll(this, renderer);
        return this;
    }

    public BetterBlockRendererList assignToParticleSpawner(EnumParticle... name) {
        for (EnumParticle s : name) {
            particleSpawnerList.put(s, this);
        }
        return this;
    }

    public static void resetBlackList() {
        for (int n = 0; n < 4096; n++) {
            blackList[n] = false;
        }
    }

    public static void addToBlackList(int blockID) {
        if (isBlockIDValid(blockID)) {
            blackList[blockID] = true;
        }
    }

    public static void removeFromBlackList(int blockID) {
        if (isBlockIDValid(blockID)) {
            blackList[blockID] = false;
        }
    }

    public static void resetRendererAssignments() {
        for (int n = 0; n < 4096; n++) {
            renderBlockEventMap.set(n, null);
        }
        for (int n2 = 0; n2 < 4096; n2++) {
            entityWalkingEventMap.set(n2, null);
        }
        for (int n3 = 0; n3 < 4096; n3++) {
            randomDisplayEventMap.set(n3, null);
        }
    }

    public void assignToBlockID(int blockID) {
        if (isBlockIDValid(blockID) && !blackList[blockID]) {
            if ((this.events & 1) == 1) {
                addEventFor(renderBlockEventMap, blockID);
            }
            if ((this.events & 2) == 2) {
                addEventFor(randomDisplayEventMap, blockID);
            }
            if ((this.events & 4) == 4) {
                addEventFor(entityWalkingEventMap, blockID);
            }
        }
    }

    public void removeFromBlockID(int blockID) {
        if (isBlockIDValid(blockID)) {
            if ((this.events & 1) == 1) {
                removeEventFrom(renderBlockEventMap, blockID);
            }
            if ((this.events & 2) == 2) {
                removeEventFrom(randomDisplayEventMap, blockID);
            }
            if ((this.events & 4) == 4) {
                removeEventFrom(entityWalkingEventMap, blockID);
            }
        }
    }

    private void addEventFor(ArrayList<LinkedList<BetterBlockRendererList>> eventMap, int blockID) {
        LinkedList<BetterBlockRendererList> assignedRenderer = eventMap.get(blockID);
        if (assignedRenderer == null) {
            LinkedList<BetterBlockRendererList> assignedRenderer2 = new LinkedList<>();
            eventMap.set(blockID, assignedRenderer2);
            assignedRenderer2.add(this);
        } else if (!assignedRenderer.contains(this)) {
            assignedRenderer.add(this);
        }
    }

    private void removeEventFrom(ArrayList<LinkedList<BetterBlockRendererList>> eventMap, int blockID) {
        LinkedList<BetterBlockRendererList> assignedRenderer = eventMap.get(blockID);
        if (assignedRenderer != null) {
            assignedRenderer.remove(this);
            if (assignedRenderer.isEmpty()) {
                eventMap.set(blockID, null);
            }
        }
    }

    private static boolean isBlockIDValid(int blockID) {
        return blockID >= 0 && blockID < 4096;
    }
}
