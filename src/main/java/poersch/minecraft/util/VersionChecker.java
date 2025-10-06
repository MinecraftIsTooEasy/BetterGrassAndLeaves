//package poersch.minecraft.util;
//
//import cpw.mods.fml.common.ITickHandler;
//import cpw.mods.fml.common.Loader;
//import cpw.mods.fml.common.TickType;
//import cpw.mods.fml.common.registry.TickRegistry;
//import cpw.mods.fml.relauncher.Side;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.EnumSet;
//import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.EnumChatFormatting;
//
//public class VersionChecker implements Runnable, ITickHandler {
//    private URL versionList;
//    private String name;
//    private String currentVersion;
//    private String currentDescription;
//    private String latestVersion;
//    private String latestDescription;
//    private boolean checked = false;
//    private boolean messagePrompted = false;
//
//    public static VersionChecker check(String modName, String modVersion, String modVersionList) {
//        VersionChecker versionChecker = new VersionChecker(modName, modVersion, modVersionList);
//        new Thread(versionChecker, "[PoerschCore][VersionChecker] " + modName).start();
//        TickRegistry.registerTickHandler(versionChecker, Side.CLIENT);
//        return versionChecker;
//    }
//
//    private VersionChecker(String modName, String modVersion, String modVersionList) {
//        this.name = modName;
//        this.currentVersion = modVersion;
//        this.latestVersion = modVersion;
//        try {
//            this.versionList = new URL(modVersionList);
//        } catch (MalformedURLException e) {
//            Logger.getLogger("PoerschCore").log(Level.SEVERE, "Malformed mod version list URL: " + modVersionList, (Throwable) e);
//        }
//    }
//
//    public String getCurrentVersion() {
//        return this.currentVersion;
//    }
//
//    public String getCurrentDescription() {
//        if (this.checked) {
//            return this.latestDescription;
//        }
//        return null;
//    }
//
//    public String getLatestVersion() {
//        if (this.checked) {
//            return this.latestVersion;
//        }
//        return null;
//    }
//
//    public String getLatestDescription() {
//        if (this.checked) {
//            return this.latestDescription;
//        }
//        return null;
//    }
//
//    private boolean isNewer(String version) {
//        return this.latestVersion.toUpperCase().endsWith(".BETA") ? version.toUpperCase().endsWith(".BETA") ? version.compareTo(this.latestVersion) > 0 : new StringBuilder().append(version).append(".BETA").toString().compareTo(this.latestVersion) >= 0 : version.toUpperCase().endsWith(".BETA") ? version.compareTo(new StringBuilder().append(this.latestVersion).append(".BETA").toString()) > 0 : version.compareTo(this.latestVersion) > 0;
//    }
//
//    @Override
//    public void run() {
//        Scanner scanner = null;
//        try {
//            scanner = new Scanner(this.versionList.openStream());
//        } catch (Exception e) {
//        }
//        if (scanner != null) {
//            String minecraftVersion = Loader.instance().getMinecraftModContainer().getVersion();
//            while (scanner.hasNext()) {
//                String nextLine = scanner.nextLine();
//                if (nextLine.startsWith(minecraftVersion)) {
//                    String[] tokens = nextLine.split(";");
//                    if (tokens.length > 1 && tokens[0].equals(minecraftVersion)) {
//                        if (isNewer(tokens[1])) {
//                            this.latestVersion = tokens[1];
//                            if (tokens.length > 2 && tokens[2].length() > 0) {
//                                this.latestDescription = tokens[2];
//                            } else {
//                                this.latestDescription = null;
//                            }
//                        } else if (tokens[1].equals(this.currentVersion)) {
//                            if (tokens.length > 2 && tokens[2].length() > 0) {
//                                this.currentDescription = tokens[2];
//                            } else {
//                                this.currentDescription = null;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        this.checked = true;
//    }
//
//    public void tickStart(EnumSet<TickType> type, Object... tickData) {
//    }
//
//    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
//        if (!this.messagePrompted && this.checked) {
//            if (!this.latestVersion.equals(this.currentVersion)) {
//                ((EntityPlayer) tickData[0]).func_71035_c(EnumChatFormatting.BLUE + this.name + EnumChatFormatting.RESET + " version " + EnumChatFormatting.BLUE + this.latestVersion + EnumChatFormatting.RESET + " is available for download" + (this.latestDescription == null ? "!" : ": " + this.latestDescription));
//            }
//            this.messagePrompted = true;
//        }
//    }
//
//    public EnumSet<TickType> ticks() {
//        return EnumSet.of(TickType.PLAYER);
//    }
//
//    public String getLabel() {
//        return null;
//    }
//}
