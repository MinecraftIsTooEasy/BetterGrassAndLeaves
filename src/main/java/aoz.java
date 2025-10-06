//package defpackage;
//
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import net.minecraftforge.common.IShearable;
//import poersch.minecraft.bettergrassandleaves.BetterGrassAndLeavesMod;
//import poersch.minecraft.bettergrassandleaves.renderer.BetterLeavesRenderer;
//
///* compiled from: BlockLeaves.java */
///* loaded from: BetterGrassAndLeavesMod[v1.6.4.D].jar:patches/aoz.class */
//public class aoz extends arh implements IShearable {
//    public static final String[] a = {"oak", "spruce", "birch", "jungle"};
//    public static final String[][] b = {new String[]{"leaves_oak", "leaves_spruce", "leaves_birch", "leaves_jungle"}, new String[]{"leaves_oak_opaque", "leaves_spruce_opaque", "leaves_birch_opaque", "leaves_jungle_opaque"}};
//
//    @SideOnly(Side.CLIENT)
//    private int e;
//    private ms[][] cX;
//    int[] c;
//
//    /* JADX WARN: Type inference failed for: r1v2, types: [ms[], ms[][]] */
//    protected aoz(int par1) {
//        super(par1, akc.j, false);
//        this.cX = new ms[2];
//        b(true);
//        a(ww.c);
//    }
//
//    @SideOnly(Side.CLIENT)
//    public int o() {
//        return abs.a(0.5d, 1.0d);
//    }
//
//    @SideOnly(Side.CLIENT)
//    public int b(int par1) {
//        return (par1 & 3) == 1 ? abs.a() : (par1 & 3) == 2 ? abs.b() : abs.c();
//    }
//
//    @SideOnly(Side.CLIENT)
//    public int c(acf par1IBlockAccess, int par2, int par3, int par4) {
//        int l = par1IBlockAccess.h(par2, par3, par4);
//        if ((l & 3) == 1) {
//            return abs.a();
//        }
//        if ((l & 3) == 2) {
//            return abs.b();
//        }
//        int i1 = 0;
//        int j1 = 0;
//        int k1 = 0;
//        for (int l1 = -1; l1 <= 1; l1++) {
//            for (int i2 = -1; i2 <= 1; i2++) {
//                int j2 = par1IBlockAccess.a(par2 + i2, par4 + l1).l();
//                i1 += (j2 & 16711680) >> 16;
//                j1 += (j2 & 65280) >> 8;
//                k1 += j2 & 255;
//            }
//        }
//        return (((i1 / 9) & 255) << 16) | (((j1 / 9) & 255) << 8) | ((k1 / 9) & 255);
//    }
//
//    public void a(abw par1World, int par2, int par3, int par4, int par5, int par6) {
//        int j1 = 1 + 1;
//        if (par1World.e(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1)) {
//            for (int k1 = -1; k1 <= 1; k1++) {
//                for (int l1 = -1; l1 <= 1; l1++) {
//                    for (int i2 = -1; i2 <= 1; i2++) {
//                        int j2 = par1World.a(par2 + k1, par3 + l1, par4 + i2);
//                        if (aqz.s[j2] != null) {
//                            aqz.s[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public void a(abw par1World, int par2, int par3, int par4, Random par5Random) {
//        if (!par1World.I) {
//            int l = par1World.h(par2, par3, par4);
//            if ((l & 8) != 0 && (l & 4) == 0) {
//                int i1 = 4 + 1;
//                int j1 = 32 * 32;
//                int k1 = 32 / 2;
//                if (this.c == null) {
//                    this.c = new int[32 * 32 * 32];
//                }
//                if (par1World.e(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1)) {
//                    for (int l1 = -4; l1 <= 4; l1++) {
//                        for (int i2 = -4; i2 <= 4; i2++) {
//                            for (int j2 = -4; j2 <= 4; j2++) {
//                                int k2 = par1World.a(par2 + l1, par3 + i2, par4 + j2);
//                                aqz block = aqz.s[k2];
//                                if (block != null && block.canSustainLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
//                                    this.c[((l1 + k1) * j1) + ((i2 + k1) * 32) + j2 + k1] = 0;
//                                } else if (block == null || !block.isLeaves(par1World, par2 + l1, par3 + i2, par4 + j2)) {
//                                    this.c[((l1 + k1) * j1) + ((i2 + k1) * 32) + j2 + k1] = -1;
//                                } else {
//                                    this.c[((l1 + k1) * j1) + ((i2 + k1) * 32) + j2 + k1] = -2;
//                                }
//                            }
//                        }
//                    }
//                    for (int l12 = 1; l12 <= 4; l12++) {
//                        for (int i22 = -4; i22 <= 4; i22++) {
//                            for (int j22 = -4; j22 <= 4; j22++) {
//                                for (int k22 = -4; k22 <= 4; k22++) {
//                                    if (this.c[((i22 + k1) * j1) + ((j22 + k1) * 32) + k22 + k1] == l12 - 1) {
//                                        if (this.c[(((i22 + k1) - 1) * j1) + ((j22 + k1) * 32) + k22 + k1] == -2) {
//                                            this.c[(((i22 + k1) - 1) * j1) + ((j22 + k1) * 32) + k22 + k1] = l12;
//                                        }
//                                        if (this.c[((i22 + k1 + 1) * j1) + ((j22 + k1) * 32) + k22 + k1] == -2) {
//                                            this.c[((i22 + k1 + 1) * j1) + ((j22 + k1) * 32) + k22 + k1] = l12;
//                                        }
//                                        if (this.c[((i22 + k1) * j1) + (((j22 + k1) - 1) * 32) + k22 + k1] == -2) {
//                                            this.c[((i22 + k1) * j1) + (((j22 + k1) - 1) * 32) + k22 + k1] = l12;
//                                        }
//                                        if (this.c[((i22 + k1) * j1) + ((j22 + k1 + 1) * 32) + k22 + k1] == -2) {
//                                            this.c[((i22 + k1) * j1) + ((j22 + k1 + 1) * 32) + k22 + k1] = l12;
//                                        }
//                                        if (this.c[((i22 + k1) * j1) + ((j22 + k1) * 32) + ((k22 + k1) - 1)] == -2) {
//                                            this.c[((i22 + k1) * j1) + ((j22 + k1) * 32) + ((k22 + k1) - 1)] = l12;
//                                        }
//                                        if (this.c[((i22 + k1) * j1) + ((j22 + k1) * 32) + k22 + k1 + 1] == -2) {
//                                            this.c[((i22 + k1) * j1) + ((j22 + k1) * 32) + k22 + k1 + 1] = l12;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                int l13 = this.c[(k1 * j1) + (k1 * 32) + k1];
//                if (l13 >= 0) {
//                    par1World.b(par2, par3, par4, l & (-9), 4);
//                } else {
//                    k(par1World, par2, par3, par4);
//                }
//            }
//        }
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void b(abw par1World, int par2, int par3, int par4, Random par5Random) {
//        if (par1World.F(par2, par3 + 1, par4) && !par1World.w(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1) {
//            double d0 = par2 + par5Random.nextFloat();
//            double d1 = par3 - 0.05d;
//            double d2 = par4 + par5Random.nextFloat();
//            par1World.a("dripWater", d0, d1, d2, 0.0d, 0.0d, 0.0d);
//        }
//        super.b(par1World, par2, par3, par4, par5Random);
//    }
//
//    private void k(abw par1World, int par2, int par3, int par4) {
//        c(par1World, par2, par3, par4, par1World.h(par2, par3, par4), 0);
//        par1World.i(par2, par3, par4);
//    }
//
//    public int a(Random par1Random) {
//        return par1Random.nextInt(20) == 0 ? 1 : 0;
//    }
//
//    public int a(int par1, Random par2Random, int par3) {
//        return aqz.D.cF;
//    }
//
//    public void a(abw par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
//        if (!par1World.I) {
//            int j1 = 20;
//            if ((par5 & 3) == 3) {
//                j1 = 40;
//            }
//            if (par7 > 0) {
//                j1 -= 2 << par7;
//                if (j1 < 10) {
//                    j1 = 10;
//                }
//            }
//            if (par1World.s.nextInt(j1) == 0) {
//                int k1 = a(par5, par1World.s, par7);
//                b(par1World, par2, par3, par4, new ye(k1, 1, a(par5)));
//            }
//            int j12 = 200;
//            if (par7 > 0) {
//                j12 = 200 - (10 << par7);
//                if (j12 < 40) {
//                    j12 = 40;
//                }
//            }
//            if ((par5 & 3) == 0 && par1World.s.nextInt(j12) == 0) {
//                b(par1World, par2, par3, par4, new ye(yc.l, 1, 0));
//            }
//        }
//    }
//
//    public void a(abw par1World, uf par2EntityPlayer, int par3, int par4, int par5, int par6) {
//        super.a(par1World, par2EntityPlayer, par3, par4, par5, par6);
//    }
//
//    public int a(int par1) {
//        return par1 & 3;
//    }
//
//    @Override // defpackage.arh
//    public boolean c() {
//        return !this.d;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void a(boolean par1) {
//        this.d = par1;
//        this.e = par1 ? 0 : 1;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void a(int par1, ww par2CreativeTabs, List par3List) {
//        par3List.add(new ye(par1, 1, 0));
//        par3List.add(new ye(par1, 1, 1));
//        par3List.add(new ye(par1, 1, 2));
//        par3List.add(new ye(par1, 1, 3));
//    }
//
//    protected ye d_(int par1) {
//        return new ye(this.cF, 1, par1 & 3);
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void a(mt par1IconRegister) {
//        for (int i = 0; i < b.length; i++) {
//            this.cX[i] = new ms[b[i].length];
//            for (int j = 0; j < b[i].length; j++) {
//                this.cX[i][j] = par1IconRegister.a(b[i][j]);
//            }
//        }
//    }
//
//    public boolean isShearable(ye item, abw world, int x, int y, int z) {
//        return true;
//    }
//
//    public ArrayList<ye> onSheared(ye item, abw world, int x, int y, int z, int fortune) {
//        ArrayList<ye> ret = new ArrayList<>();
//        ret.add(new ye(this, 1, world.h(x, y, z) & 3));
//        return ret;
//    }
//
//    public void beginLeavesDecay(abw world, int x, int y, int z) {
//        world.b(x, y, z, world.h(x, y, z) | 8, 4);
//    }
//
//    public boolean isLeaves(abw world, int x, int y, int z) {
//        return true;
//    }
//
//    /* JADX WARN: Multi-variable type inference failed */
//    @SideOnly(Side.CLIENT)
//    public ms a(int side, int metadata) {
//        if (this.d && BetterGrassAndLeavesMod.modActive && ((Boolean) BetterGrassAndLeavesMod.useRoundedVanillaLeaves.value).booleanValue() && this == aqz.P && BetterLeavesRenderer.iconRoundedLeaves[metadata & 3] != null) {
//            return BetterLeavesRenderer.iconRoundedLeaves[metadata & 3];
//        }
//        return this.cX[this.e][metadata & 3];
//    }
//
//    @Override // defpackage.arh
//    public ms getIconBetterLeaves(int metadata, float randomIndex) {
//        if (BetterLeavesRenderer.iconBetterLeaves == null || this != aqz.P) {
//            return super.getIconBetterLeaves(metadata, randomIndex);
//        }
//        int leafIndex = metadata & 3;
//        return BetterLeavesRenderer.iconBetterLeaves[leafIndex][(int) ((randomIndex * (BetterLeavesRenderer.iconBetterLeaves[leafIndex].length - 1)) + 0.5f)];
//    }
//
//    @Override // defpackage.arh
//    public ms getIconFallingLeaves(int metadata) {
//        if (BetterLeavesRenderer.iconFallingLeaves == null || this != aqz.P) {
//            return super.getIconFallingLeaves(metadata);
//        }
//        return BetterLeavesRenderer.iconFallingLeaves[metadata & 3];
//    }
//}
