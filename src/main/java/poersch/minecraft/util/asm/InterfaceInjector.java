package poersch.minecraft.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:poersch/minecraft/util/asm/InterfaceInjector.class */
public class InterfaceInjector implements IClassPatcher {
    private final String interfaceName;
    private final FieldNode fieldNode;
    private final MethodNode methodNode;

    public InterfaceInjector(String interfaceName, FieldNode fieldNode, MethodNode methodNode) {
        this.interfaceName = interfaceName;
        this.fieldNode = fieldNode;
        this.methodNode = methodNode;
    }

    @Override // poersch.minecraft.util.asm.IClassPatcher
    public byte[] patchClass(String className, byte[] byteCode) {
        ClassReader classReader = new ClassReader(byteCode);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        classNode.interfaces.add(this.interfaceName);
        ClassWriter classWriter = new ClassWriter(1);
        classNode.accept(classWriter);
        this.fieldNode.accept(classWriter);
        this.methodNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
