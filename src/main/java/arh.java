//package defpackage;
//
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
//import poersch.minecraft.bettergrassandleaves.interfaces.IBetterLeaves;
//import poersch.minecraft.bettergrassandleaves.renderer.BetterLeavesRenderer;
//
///* compiled from: BlockLeavesBase.java */
///* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:patches/arh.class */
//public class arh extends aqz implements IBetterLeaves {
//    public boolean d;
//
//    protected arh(int par1, akc par2Material, boolean par3) {
//        super(par1, par2Material);
//        this.d = par3;
//        BetterGrassAndLeavesMod.info("Initiated block: " + getClass().getName());
//        BetterLeavesRenderer.leafBlocks.add(this);
//    }
//
//    public boolean c() {
//        return false;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public boolean a(acf par1IBlockAccess, int par2, int par3, int par4, int par5) {
//        int i1 = par1IBlockAccess.a(par2, par3, par4);
//        if (this.d || i1 != this.cF) {
//            return super.a(par1IBlockAccess, par2, par3, par4, par5);
//        }
//        return false;
//    }
//
//    public ms getIconBetterLeaves(int metadata, float randomIndex) {
//        return null;
//    }
//
//    public ms getIconBetterLeavesSnowed(int metadata, float randomIndex) {
//        if (BetterLeavesRenderer.iconBetterLeavesSnowed == null || this != aqz.P) {
//            return null;
//        }
//        return BetterLeavesRenderer.iconBetterLeavesSnowed[(int) ((randomIndex * (BetterLeavesRenderer.iconBetterLeavesSnowed.length - 1)) + 0.5f)];
//    }
//
//    public ms getIconFallingLeaves(int metadata) {
//        return a(0, metadata);
//    }
//
//    @Override // poersch.minecraft.bettergrassandleaves.interfaces.IBetterLeaves
//    public float getSpawnChanceFallingLeaves(int metadata) {
//        return 0.008f;
//    }
//}
