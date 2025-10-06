//package poersch.minecraft.util.asm;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//import net.minecraft.launchwrapper.IClassTransformer;
//
//public class ClassPatcher implements IClassTransformer {
//    private static HashMap<String, File> toOverwrite = new HashMap<>();
//    private static HashMap<String, ArrayList<IClassPatcher>> toPatch = new HashMap<>();
//    public static Logger logger = Logger.getLogger("PoerschCore");
//
//    public byte[] transform(String name, String arg1, byte[] byteCode) {
//        File zipLocation = null;
//        try {
//            zipLocation = toOverwrite.remove(name);
//        } catch (ClassCircularityError e) {
//            logger.log(Level.WARNING, "ClassCircularityError");
//        }
//        if (zipLocation != null) {
//            byteCode = overwriteClass(name, byteCode, zipLocation);
//        }
//        ArrayList<IClassPatcher> classPatchers = null;
//        try {
//            classPatchers = toPatch.remove(name);
//        } catch (ClassCircularityError e2) {
//            logger.log(Level.WARNING, "ClassCircularityError");
//        }
//        if (classPatchers != null) {
//            Iterator i$ = classPatchers.iterator();
//            while (i$.hasNext()) {
//                IClassPatcher classPatcher = i$.next();
//                byteCode = classPatcher.patchClass(name, byteCode);
//            }
//        }
//        return byteCode;
//    }
//
//    private byte[] overwriteClass(String name, byte[] byteCode, File zipLocation) {
//        try {
//            ZipFile zip = new ZipFile(zipLocation);
//            ZipEntry entry = zip.getEntry("patches/" + name.replace('.', '/') + ".class");
//            if (entry == null) {
//                logger.log(Level.WARNING, "Class \"" + name + "\" not found in " + zipLocation.getName());
//            } else {
//                InputStream zipInputStream = zip.getInputStream(entry);
//                int maxBytes = (int) entry.getSize();
//                byteCode = new byte[maxBytes];
//                int bytes = 0;
//                while (bytes < maxBytes) {
//                    int inputLength = zipInputStream.read(byteCode, bytes, maxBytes - bytes);
//                    if (inputLength <= 0) {
//                        throw new IOException();
//                    }
//                    bytes += inputLength;
//                }
//                zipInputStream.close();
//                logger.log(Level.INFO, "Patched class: " + name);
//            }
//            zip.close();
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Error overwriting class \"" + name + "\" from " + zipLocation.getName(), (Throwable) e);
//        }
//        return byteCode;
//    }
//
//    public static void addPatcherFor(String className, IClassPatcher classPatcher) {
//        ArrayList<IClassPatcher> classPatchers = toPatch.get(className);
//        if (classPatchers == null) {
//            classPatchers = new ArrayList<>();
//        }
//        classPatchers.add(classPatcher);
//        toPatch.put(className, classPatchers);
//    }
//
//    public static void addPatchesFrom(File zipLocation) {
//        int patchCount = 0;
//        try {
//            ZipFile zip = new ZipFile(zipLocation);
//            Enumeration<? extends ZipEntry> entries = zip.entries();
//            while (entries.hasMoreElements()) {
//                String filename = entries.nextElement().getName();
//                if (filename.startsWith("patches/") && filename.endsWith(".class")) {
//                    toOverwrite.put(filename.substring(8, filename.length() - 6), zipLocation);
//                    patchCount++;
//                }
//            }
//            zip.close();
//        } catch (Exception e) {
//        }
//        logger.log(Level.INFO, "Found " + patchCount + " patches in " + zipLocation.getName());
//    }
//}
