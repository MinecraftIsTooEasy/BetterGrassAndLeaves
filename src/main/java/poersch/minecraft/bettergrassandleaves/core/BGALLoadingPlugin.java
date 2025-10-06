//package poersch.minecraft.bettergrassandleaves.core;
//
//import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
//import java.io.File;
//import java.util.Map;
//import org.objectweb.asm.Label;
//import org.objectweb.asm.tree.FieldNode;
//import org.objectweb.asm.tree.MethodInsnNode;
//import org.objectweb.asm.tree.MethodNode;
//import poersch.minecraft.util.asm.ClassPatcher;
//import poersch.minecraft.util.asm.InstructionInjector;
//import poersch.minecraft.util.asm.InterfaceInjector;
//import poersch.minecraft.util.asm.MethodInjector;
//
//@IFMLLoadingPlugin.TransformerExclusions({"poersch.minecraft.bettergrassandleaves"})
//@IFMLLoadingPlugin.MCVersion("1.6.4")
///* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:poersch/minecraft/bettergrassandleaves/core/BGALLoadingPlugin.class */
//public class BGALLoadingPlugin implements IFMLLoadingPlugin {
//    public String[] getLibraryRequestClass() {
//        return null;
//    }
//
//    public String[] getASMTransformerClass() {
//        return new String[]{ClassPatcher.class.getName()};
//    }
//
//    public String getModContainerClass() {
//        return null;
//    }
//
//    public String getSetupClass() {
//        return null;
//    }
//
//    public void injectData(Map<String, Object> data) {
//        boolean deobfuscated = !((Boolean) data.get("runtimeDeobfuscationEnabled")).booleanValue();
//        ClassPatcher.addPatchesFrom((File) data.get("coremodLocation"));
//        if (deobfuscated) {
//            MethodNode methodNode = new MethodNode(1, "registerIcons", "(Lnet/minecraft/client/renderer/texture/IconRegister;)V", (String) null, (String[]) null);
//            methodNode.visitFieldInsn(178, "poersch/minecraft/bettergrassandleaves/BetterGrassAndLeavesMod", "workingRegisterIconsHook", "Z");
//            Label label = new Label();
//            methodNode.visitJumpInsn(154, label);
//            methodNode.visitVarInsn(25, 1);
//            methodNode.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRegisterIconsHook", "(Lnet/minecraft/client/renderer/texture/IconRegister;)V");
//            methodNode.visitLabel(label);
//            methodNode.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            methodNode.visitInsn(177);
//            ClassPatcher.addPatcherFor("net.minecraft.block.BlockGrass", new InstructionInjector(methodNode, 1));
//        } else {
//            MethodNode methodNode2 = new MethodNode(1, "a", "(Lmt;)V", (String) null, (String[]) null);
//            methodNode2.visitFieldInsn(178, "poersch/minecraft/bettergrassandleaves/BetterGrassAndLeavesMod", "workingRegisterIconsHook", "Z");
//            Label label2 = new Label();
//            methodNode2.visitJumpInsn(154, label2);
//            methodNode2.visitVarInsn(25, 1);
//            methodNode2.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRegisterIconsHook", "(Lnet/minecraft/client/renderer/texture/IconRegister;)V");
//            methodNode2.visitLabel(label2);
//            methodNode2.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            methodNode2.visitInsn(177);
//            ClassPatcher.addPatcherFor("aon", new InstructionInjector(methodNode2, 1));
//        }
//        if (deobfuscated) {
//            MethodNode methodNode3 = new MethodNode(1, "renderBlockByRenderType", "(Lnet/minecraft/block/Block;III)Z", (String) null, (String[]) null);
//            methodNode3.visitVarInsn(25, 1);
//            methodNode3.visitVarInsn(25, 0);
//            methodNode3.visitFieldInsn(180, "net/minecraft/client/renderer/RenderBlocks", "blockAccess", "Lnet/minecraft/world/IBlockAccess;");
//            methodNode3.visitVarInsn(21, 2);
//            methodNode3.visitVarInsn(21, 3);
//            methodNode3.visitVarInsn(21, 4);
//            methodNode3.visitVarInsn(25, 0);
//            methodNode3.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRenderBlockHook", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;IIILnet/minecraft/client/renderer/RenderBlocks;)Z");
//            Label label3 = new Label();
//            methodNode3.visitJumpInsn(153, label3);
//            methodNode3.visitInsn(4);
//            methodNode3.visitInsn(172);
//            methodNode3.visitLabel(label3);
//            methodNode3.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            ClassPatcher.addPatcherFor("net.minecraft.client.renderer.RenderBlocks", new InstructionInjector(methodNode3));
//        } else {
//            MethodNode methodNode4 = new MethodNode(1, "b", "(Laqz;III)Z", (String) null, (String[]) null);
//            methodNode4.visitVarInsn(25, 1);
//            methodNode4.visitVarInsn(25, 0);
//            methodNode4.visitFieldInsn(180, "bfr", "a", "Lacf;");
//            methodNode4.visitVarInsn(21, 2);
//            methodNode4.visitVarInsn(21, 3);
//            methodNode4.visitVarInsn(21, 4);
//            methodNode4.visitVarInsn(25, 0);
//            methodNode4.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRenderBlockHook", "(Laqz;Lacf;IIILbfr;)Z");
//            Label label4 = new Label();
//            methodNode4.visitJumpInsn(153, label4);
//            methodNode4.visitInsn(4);
//            methodNode4.visitInsn(172);
//            methodNode4.visitLabel(label4);
//            methodNode4.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            ClassPatcher.addPatcherFor("bfr", new InstructionInjector(methodNode4));
//        }
//        if (deobfuscated) {
//            MethodNode methodNode5 = new MethodNode(1, "moveEntity", "(DDD)V", (String) null, (String[]) null);
//            methodNode5.visitFieldInsn(178, "net/minecraft/block/Block", "blocksList", "[Lnet/minecraft/block/Block;");
//            methodNode5.visitVarInsn(21, 33);
//            methodNode5.visitInsn(50);
//            methodNode5.visitVarInsn(25, 0);
//            methodNode5.visitFieldInsn(180, "net/minecraft/entity/Entity", "worldObj", "Lnet/minecraft/world/World;");
//            methodNode5.visitVarInsn(21, 31);
//            methodNode5.visitVarInsn(21, 28);
//            methodNode5.visitVarInsn(21, 32);
//            methodNode5.visitVarInsn(25, 0);
//            methodNode5.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onEntityWalkingHook", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)Z");
//            methodNode5.visitInsn(87);
//            MethodInsnNode targetNode = new MethodInsnNode(182, "net/minecraft/block/Block", "onEntityWalking", "(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V");
//            ClassPatcher.addPatcherFor("net.minecraft.entity.Entity", new InstructionInjector(methodNode5, targetNode, -9));
//        } else {
//            MethodNode methodNode6 = new MethodNode(1, "d", "(DDD)V", (String) null, (String[]) null);
//            methodNode6.visitFieldInsn(178, "aqz", "s", "[Laqz;");
//            methodNode6.visitVarInsn(21, 33);
//            methodNode6.visitInsn(50);
//            methodNode6.visitVarInsn(25, 0);
//            methodNode6.visitFieldInsn(180, "nn", "q", "Labw;");
//            methodNode6.visitVarInsn(21, 31);
//            methodNode6.visitVarInsn(21, 28);
//            methodNode6.visitVarInsn(21, 32);
//            methodNode6.visitVarInsn(25, 0);
//            methodNode6.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onEntityWalkingHook", "(Laqz;Labw;IIILnn;)Z");
//            methodNode6.visitInsn(87);
//            MethodInsnNode targetNode2 = new MethodInsnNode(182, "aqz", "b", "(Labw;IIILnn;)V");
//            ClassPatcher.addPatcherFor("nn", new InstructionInjector(methodNode6, targetNode2, -9));
//        }
//        if (deobfuscated) {
//            MethodNode methodNode7 = new MethodNode(1, "doVoidFogParticles", "(III)V", (String) null, (String[]) null);
//            methodNode7.visitFieldInsn(178, "net/minecraft/block/Block", "blocksList", "[Lnet/minecraft/block/Block;");
//            methodNode7.visitVarInsn(21, 10);
//            methodNode7.visitInsn(50);
//            methodNode7.visitVarInsn(25, 0);
//            methodNode7.visitVarInsn(21, 7);
//            methodNode7.visitVarInsn(21, 8);
//            methodNode7.visitVarInsn(21, 9);
//            methodNode7.visitVarInsn(25, 5);
//            methodNode7.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRandomDisplayTickHook", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILjava/util/Random;)Z");
//            methodNode7.visitInsn(87);
//            MethodInsnNode targetNode3 = new MethodInsnNode(182, "net/minecraft/block/Block", "randomDisplayTick", "(Lnet/minecraft/world/World;IIILjava/util/Random;)V");
//            ClassPatcher.addPatcherFor("net.minecraft.client.multiplayer.WorldClient", new InstructionInjector(methodNode7, targetNode3, -8));
//        } else {
//            MethodNode methodNode8 = new MethodNode(1, "J", "(III)V", (String) null, (String[]) null);
//            methodNode8.visitFieldInsn(178, "aqz", "s", "[Laqz;");
//            methodNode8.visitVarInsn(21, 10);
//            methodNode8.visitInsn(50);
//            methodNode8.visitVarInsn(25, 0);
//            methodNode8.visitVarInsn(21, 7);
//            methodNode8.visitVarInsn(21, 8);
//            methodNode8.visitVarInsn(21, 9);
//            methodNode8.visitVarInsn(25, 5);
//            methodNode8.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onRandomDisplayTickHook", "(Laqz;Labw;IIILjava/util/Random;)Z");
//            methodNode8.visitInsn(87);
//            MethodInsnNode targetNode4 = new MethodInsnNode(182, "aqz", "b", "(Labw;IIILjava/util/Random;)V");
//            ClassPatcher.addPatcherFor("bdd", new InstructionInjector(methodNode8, targetNode4, -8));
//        }
//        if (deobfuscated) {
//            MethodNode methodNode9 = new MethodNode(1, "hitByEntity", "(Lnet/minecraft/entity/Entity;)Z", (String) null, (String[]) null);
//            methodNode9.visitCode();
//            methodNode9.visitLdcInsn("blood");
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitFieldInsn(180, "net/minecraft/entity/Entity", "worldObj", "Lnet/minecraft/world/World;");
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitFieldInsn(180, "net/minecraft/entity/Entity", "posX", "D");
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitFieldInsn(180, "net/minecraft/entity/Entity", "posY", "D");
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitFieldInsn(180, "net/minecraft/entity/Entity", "posZ", "D");
//            methodNode9.visitInsn(14);
//            methodNode9.visitInsn(14);
//            methodNode9.visitInsn(14);
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onSpawnParticleHook", "(Ljava/lang/String;Lnet/minecraft/world/World;DDDDDDLnet/minecraft/entity/Entity;)Z");
//            methodNode9.visitInsn(87);
//            methodNode9.visitVarInsn(25, 0);
//            methodNode9.visitVarInsn(25, 1);
//            methodNode9.visitMethodInsn(183, "net/minecraft/entity/Entity", "hitByEntity", "(Lnet/minecraft/entity/Entity;)Z");
//            methodNode9.visitInsn(172);
//            methodNode9.visitMaxs(0, 0);
//            methodNode9.visitEnd();
//            ClassPatcher.addPatcherFor("net.minecraft.entity.EntityLivingBase", new MethodInjector(methodNode9));
//        } else {
//            MethodNode methodNode10 = new MethodNode(1, "i", "(Lnn;)Z", (String) null, (String[]) null);
//            methodNode10.visitCode();
//            methodNode10.visitLdcInsn("blood");
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitFieldInsn(180, "nn", "q", "Labw;");
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitFieldInsn(180, "nn", "u", "D");
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitFieldInsn(180, "nn", "v", "D");
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitFieldInsn(180, "nn", "w", "D");
//            methodNode10.visitInsn(14);
//            methodNode10.visitInsn(14);
//            methodNode10.visitInsn(14);
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onSpawnParticleHook", "(Ljava/lang/String;Labw;DDDDDDLnn;)Z");
//            methodNode10.visitInsn(87);
//            methodNode10.visitVarInsn(25, 0);
//            methodNode10.visitVarInsn(25, 1);
//            methodNode10.visitMethodInsn(183, "nn", "i", "(Lnn;)Z");
//            methodNode10.visitInsn(172);
//            methodNode10.visitMaxs(0, 0);
//            methodNode10.visitEnd();
//            ClassPatcher.addPatcherFor("of", new MethodInjector(methodNode10));
//        }
//        if (deobfuscated) {
//            MethodNode methodNode11 = new MethodNode(1, "spawnParticle", "(Ljava/lang/String;DDDDDD)V", (String) null, (String[]) null);
//            methodNode11.visitVarInsn(25, 1);
//            methodNode11.visitVarInsn(25, 0);
//            methodNode11.visitVarInsn(24, 2);
//            methodNode11.visitVarInsn(24, 4);
//            methodNode11.visitVarInsn(24, 6);
//            methodNode11.visitVarInsn(24, 8);
//            methodNode11.visitVarInsn(24, 10);
//            methodNode11.visitVarInsn(24, 12);
//            methodNode11.visitInsn(1);
//            methodNode11.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onSpawnParticleHook", "(Ljava/lang/String;Lnet/minecraft/world/World;DDDDDDLnet/minecraft/entity/Entity;)Z");
//            Label label5 = new Label();
//            methodNode11.visitJumpInsn(153, label5);
//            methodNode11.visitInsn(177);
//            methodNode11.visitLabel(label5);
//            methodNode11.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            ClassPatcher.addPatcherFor("net.minecraft.world.World", new InstructionInjector(methodNode11));
//        } else {
//            MethodNode methodNode12 = new MethodNode(1, "a", "(Ljava/lang/String;DDDDDD)V", (String) null, (String[]) null);
//            methodNode12.visitVarInsn(25, 1);
//            methodNode12.visitVarInsn(25, 0);
//            methodNode12.visitVarInsn(24, 2);
//            methodNode12.visitVarInsn(24, 4);
//            methodNode12.visitVarInsn(24, 6);
//            methodNode12.visitVarInsn(24, 8);
//            methodNode12.visitVarInsn(24, 10);
//            methodNode12.visitVarInsn(24, 12);
//            methodNode12.visitInsn(1);
//            methodNode12.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BlockRendererList", "onSpawnParticleHook", "(Ljava/lang/String;Labw;DDDDDDLnn;)Z");
//            Label label6 = new Label();
//            methodNode12.visitJumpInsn(153, label6);
//            methodNode12.visitInsn(177);
//            methodNode12.visitLabel(label6);
//            methodNode12.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            ClassPatcher.addPatcherFor("abw", new InstructionInjector(methodNode12));
//        }
//        if (deobfuscated) {
//            FieldNode fieldNode = new FieldNode(1, "colorBetterBlood", "I", (String) null, (Object) null);
//            MethodNode methodNode13 = new MethodNode(1, "getColorBetterBlood", "()I", (String) null, (String[]) null);
//            methodNode13.visitCode();
//            methodNode13.visitVarInsn(25, 0);
//            methodNode13.visitFieldInsn(180, "net/minecraft/entity/Entity", "colorBetterBlood", "I");
//            Label l0 = new Label();
//            methodNode13.visitJumpInsn(154, l0);
//            methodNode13.visitVarInsn(25, 0);
//            methodNode13.visitVarInsn(25, 0);
//            methodNode13.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
//            methodNode13.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BetterBloodRenderer", "getColorBetterBlood", "(Ljava/lang/Class;)I");
//            methodNode13.visitFieldInsn(181, "net/minecraft/entity/Entity", "colorBetterBlood", "I");
//            methodNode13.visitLabel(l0);
//            methodNode13.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//            methodNode13.visitVarInsn(25, 0);
//            methodNode13.visitFieldInsn(180, "net/minecraft/entity/Entity", "colorBetterBlood", "I");
//            methodNode13.visitInsn(172);
//            methodNode13.visitMaxs(0, 0);
//            methodNode13.visitEnd();
//            ClassPatcher.addPatcherFor("net.minecraft.entity.Entity", new InterfaceInjector("poersch/minecraft/bettergrassandleaves/interfaces/IBetterBlood", fieldNode, methodNode13));
//            return;
//        }
//        FieldNode fieldNode2 = new FieldNode(1, "colorBetterBlood", "I", (String) null, (Object) null);
//        MethodNode methodNode14 = new MethodNode(1, "getColorBetterBlood", "()I", (String) null, (String[]) null);
//        methodNode14.visitCode();
//        methodNode14.visitVarInsn(25, 0);
//        methodNode14.visitFieldInsn(180, "nn", "colorBetterBlood", "I");
//        Label l02 = new Label();
//        methodNode14.visitJumpInsn(154, l02);
//        methodNode14.visitVarInsn(25, 0);
//        methodNode14.visitVarInsn(25, 0);
//        methodNode14.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
//        methodNode14.visitMethodInsn(184, "poersch/minecraft/bettergrassandleaves/renderer/BetterBloodRenderer", "getColorBetterBlood", "(Ljava/lang/Class;)I");
//        methodNode14.visitFieldInsn(181, "nn", "colorBetterBlood", "I");
//        methodNode14.visitLabel(l02);
//        methodNode14.visitFrame(3, 0, (Object[]) null, 0, (Object[]) null);
//        methodNode14.visitVarInsn(25, 0);
//        methodNode14.visitFieldInsn(180, "nn", "colorBetterBlood", "I");
//        methodNode14.visitInsn(172);
//        methodNode14.visitMaxs(0, 0);
//        methodNode14.visitEnd();
//        ClassPatcher.addPatcherFor("nn", new InterfaceInjector("poersch/minecraft/bettergrassandleaves/interfaces/IBetterBlood", fieldNode2, methodNode14));
//    }
//}
