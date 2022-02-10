//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  net.minecraft.client.renderer.chunk.SetVisibility
 *  net.minecraft.client.renderer.chunk.VisGraph
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.IntegerCache
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntegerCache;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={VisGraph.class})
public abstract class MixinVisGraph {
    @Shadow
    private static final int DX = (int)Math.pow(16.0, 0.0);
    @Shadow
    private static final int DZ = (int)Math.pow(16.0, 1.0);
    @Shadow
    private static final int DY = (int)Math.pow(16.0, 2.0);
    @Shadow
    private static final int[] INDEX_OF_EDGES = new int[1352];
    @Shadow
    private int empty = 4096;
    @Shadow
    private final BitSet bitSet = new BitSet(4096);

    @Shadow
    private Set<EnumFacing> floodFill(int pos) {
        EnumSet<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
        ArrayDeque queue = Queues.newArrayDeque();
        queue.add(IntegerCache.getInteger((int)pos));
        this.bitSet.set(pos, true);
        while (!queue.isEmpty()) {
            int i = (Integer)queue.poll();
            this.addEdges(i, set);
            for (EnumFacing enumfacing : EnumFacing.values()) {
                int j = this.getNeighborIndexAtFace(i, enumfacing);
                if (j < 0 || this.bitSet.get(j)) continue;
                this.bitSet.set(j, true);
                queue.add(IntegerCache.getInteger((int)j));
            }
        }
        return set;
    }

    @Shadow
    private void addEdges(int pos, Set<EnumFacing> p_178610_2_) {
        int i = pos >> 0 & 0xF;
        if (i == 0) {
            p_178610_2_.add(EnumFacing.WEST);
        } else if (i == 15) {
            p_178610_2_.add(EnumFacing.EAST);
        }
        int j = pos >> 8 & 0xF;
        if (j == 0) {
            p_178610_2_.add(EnumFacing.DOWN);
        } else if (j == 15) {
            p_178610_2_.add(EnumFacing.UP);
        }
        int k = pos >> 4 & 0xF;
        if (k == 0) {
            p_178610_2_.add(EnumFacing.NORTH);
        } else if (k == 15) {
            p_178610_2_.add(EnumFacing.SOUTH);
        }
    }

    @Shadow
    private static int getIndex(int x, int y, int z) {
        return x << 0 | y << 8 | z << 4;
    }

    @Shadow
    private static int getIndex(BlockPos pos) {
        return MixinVisGraph.getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
    }

    @Shadow
    private int getNeighborIndexAtFace(int pos, EnumFacing facing) {
        switch (facing) {
            case DOWN: {
                if ((pos >> 8 & 0xF) == 0) {
                    return -1;
                }
                return pos - DY;
            }
            case UP: {
                if ((pos >> 8 & 0xF) == 15) {
                    return -1;
                }
                return pos + DY;
            }
            case NORTH: {
                if ((pos >> 4 & 0xF) == 0) {
                    return -1;
                }
                return pos - DZ;
            }
            case SOUTH: {
                if ((pos >> 4 & 0xF) == 15) {
                    return -1;
                }
                return pos + DZ;
            }
            case WEST: {
                if ((pos >> 0 & 0xF) == 0) {
                    return -1;
                }
                return pos - DX;
            }
            case EAST: {
                if ((pos >> 0 & 0xF) == 15) {
                    return -1;
                }
                return pos + DX;
            }
        }
        return -1;
    }

    @Overwrite
    public void setOpaqueCube(BlockPos pos) {
    }

    @Overwrite
    public SetVisibility computeVisibility() {
        SetVisibility setvisibility = new SetVisibility();
        setvisibility.setAllVisible(true);
        return setvisibility;
    }
}

