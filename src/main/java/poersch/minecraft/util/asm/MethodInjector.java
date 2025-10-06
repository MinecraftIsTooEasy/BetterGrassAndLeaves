package poersch.minecraft.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.MethodNode;

/* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:poersch/minecraft/util/asm/MethodInjector.class */
public class MethodInjector implements IClassPatcher {
    private final MethodNode methodNode;

    public MethodInjector(MethodNode methodNode) {
        this.methodNode = methodNode;
    }

    @Override // poersch.minecraft.util.asm.IClassPatcher
    public byte[] patchClass(String className, byte[] byteCode) {
        ClassReader classReader = new ClassReader(byteCode);
        ClassWriter classWriter = new ClassWriter(classReader, 3);
        classReader.accept(classWriter, 0);
        this.methodNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
