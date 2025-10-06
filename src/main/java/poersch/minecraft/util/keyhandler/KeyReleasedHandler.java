//package poersch.minecraft.util.keyhandler;
//
//import cpw.mods.fml.client.registry.KeyBindingRegistry;
//import cpw.mods.fml.common.TickType;
//import java.util.EnumSet;
//import net.minecraft.client.settings.KeyBinding;
//
//public class KeyReleasedHandler extends KeyBindingRegistry.KeyHandler {
//    public final int id;
//    public final String keyName;
//    public final IKeyReleasedCallback callback;
//
//    public static void create(int id, String keyName, int keyCode, IKeyReleasedCallback callback) {
//        KeyBindingRegistry.registerKeyBinding(new KeyReleasedHandler(id, keyName, keyCode, callback));
//    }
//
//    private KeyReleasedHandler(int id, String keyName, int keyCode, IKeyReleasedCallback callback) {
//        super(new KeyBinding[]{new KeyBinding(keyName, keyCode)}, new boolean[]{false});
//        this.id = id;
//        this.keyName = keyName;
//        this.callback = callback;
//    }
//
//    public String getLabel() {
//        return this.keyName;
//    }
//
//    public void keyDown(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd, boolean isRepeat) {
//    }
//
//    public void keyUp(EnumSet<TickType> types, KeyBinding keyBinding, boolean tickEnd) {
//        if (tickEnd) {
//            this.callback.onKeyReleased(this);
//        }
//    }
//
//    public EnumSet<TickType> ticks() {
//        return EnumSet.of(TickType.CLIENT);
//    }
//}
