package poersch.minecraft.util.asm;

import java.util.Iterator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:poersch/minecraft/util/asm/InstructionInjector.class */
public class InstructionInjector implements IClassPatcher {
    public static final int insertAtStart = 0;
    public static final int insertAtEnd = 1;
    private final MethodNode methodNode;
    private final MethodInsnNode targetMethodNode;
    private final int offset;
    private final int mode;

    public InstructionInjector(MethodNode methodNode, MethodInsnNode targetMethodNode, int offset) {
        this.methodNode = methodNode;
        this.targetMethodNode = targetMethodNode;
        this.offset = offset;
        this.mode = 0;
    }

    public InstructionInjector(MethodNode methodNode, int mode) {
        this.methodNode = methodNode;
        this.targetMethodNode = null;
        this.offset = 0;
        this.mode = mode;
    }

    public InstructionInjector(MethodNode methodNode) {
        this(methodNode, 0);
    }

    @Override // poersch.minecraft.util.asm.IClassPatcher
    public byte[] patchClass(String className, byte[] byteCode) {
        System.out.println("[InstructionInjector] Found class:" + className);
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(byteCode);
        classReader.accept(classNode, 0);
        Iterator i$ = classNode.methods.iterator();
        while (true) {
            if (!i$.hasNext()) {
                break;
            }
            MethodNode method = (MethodNode) i$.next();
            if (method.name.equals(this.methodNode.name) && method.desc.equals(this.methodNode.desc)) {
                System.out.println("[InstructionInjector] Found method: " + this.methodNode.name + this.methodNode.desc);
                if (this.targetMethodNode == null) {
                    if (this.mode == 0) {
                        method.instructions.insert(this.methodNode.instructions);
                        System.out.println("[InstructionInjector] Injected instructions at the beginning of: " + this.methodNode.name + this.methodNode.desc);
                    } else {
                        method.instructions.insertBefore(method.instructions.get(method.instructions.size() - 2), this.methodNode.instructions);
                        System.out.println("[InstructionInjector] Injected instructions at the end of: " + this.methodNode.name + this.methodNode.desc);
                    }
                } else {
                    Iterator<AbstractInsnNode> instructionIterator = method.instructions.iterator();
                    int currentNode = 0;
                    while (true) {
                        if (!instructionIterator.hasNext()) {
                            break;
                        }
                        AbstractInsnNode next = instructionIterator.next();
                        if (next.getType() == this.targetMethodNode.getType() && next.getOpcode() == this.targetMethodNode.getOpcode()) {
                            MethodInsnNode mInstruction = (MethodInsnNode) next;
                            if (mInstruction.owner.equals(this.targetMethodNode.owner) && mInstruction.name.equals(this.targetMethodNode.name) && mInstruction.desc.equals(this.targetMethodNode.desc) && currentNode + this.offset >= 0 && currentNode + this.offset < method.instructions.size()) {
                                method.instructions.insertBefore(method.instructions.get(currentNode + this.offset), this.methodNode.instructions);
                                System.out.println("[InstructionInjector] Injected instructions (" + this.offset + ") " + (this.offset > 0 ? "after" : "before") + ": " + this.methodNode.name + this.methodNode.desc);
                            }
                        }
                        currentNode++;
                    }
                }
            }
        }
        ClassWriter writer = new ClassWriter(1);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
