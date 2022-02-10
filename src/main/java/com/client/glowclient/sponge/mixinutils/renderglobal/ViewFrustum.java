//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.chunk.IRenderChunkFactory
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixinutils.renderglobal;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class ViewFrustum {
    public final RenderGlobal renderGlobal;
    public final World world;
    public int countChunksY;
    public int countChunksX;
    public int countChunksZ;
    public RenderChunk[] renderChunks;

    public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal renderGlobalIn, IRenderChunkFactory renderChunkFactory) {
        this.renderGlobal = renderGlobalIn;
        this.world = worldIn;
        this.setCountChunksXYZ(renderDistanceChunks);
        this.createRenderChunks(renderChunkFactory);
    }

    public void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
        int i = this.countChunksX * this.countChunksY * this.countChunksZ;
        this.renderChunks = new RenderChunk[i];
        int j = 0;
        for (int k = 0; k < this.countChunksX; ++k) {
            for (int l = 0; l < this.countChunksY; ++l) {
                for (int i1 = 0; i1 < this.countChunksZ; ++i1) {
                    int j1 = (i1 * this.countChunksY + l) * this.countChunksX + k;
                    this.renderChunks[j1] = renderChunkFactory.create(this.world, this.renderGlobal, j++);
                    this.renderChunks[j1].setPosition(k * 16, l * 16, i1 * 16);
                }
            }
        }
    }

    public void deleteGlResources() {
        for (RenderChunk renderchunk : this.renderChunks) {
            renderchunk.deleteGlResources();
        }
    }

    public void setCountChunksXYZ(int renderDistanceChunks) {
        int i;
        this.countChunksX = i = renderDistanceChunks * 2 + 1;
        this.countChunksY = 16;
        this.countChunksZ = i;
    }

    public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
        int i = MathHelper.floor((double)viewEntityX) - 8;
        int j = MathHelper.floor((double)viewEntityZ) - 8;
        int k = this.countChunksX * 16;
        for (int l = 0; l < this.countChunksX; ++l) {
            int i1 = this.getBaseCoordinate(i, k, l);
            for (int j1 = 0; j1 < this.countChunksZ; ++j1) {
                int k1 = this.getBaseCoordinate(j, k, j1);
                for (int l1 = 0; l1 < this.countChunksY; ++l1) {
                    int i2 = l1 * 16;
                    RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
                    renderchunk.setPosition(i1, i2, k1);
                }
            }
        }
    }

    public int getBaseCoordinate(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
        int i = p_178157_3_ * 16;
        int j = i - p_178157_1_ + p_178157_2_ / 2;
        if (j < 0) {
            j -= p_178157_2_ - 1;
        }
        return i - j / p_178157_2_ * p_178157_2_;
    }

    public void markBlocksForUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean updateImmediately) {
        int i = MathHelper.intFloorDiv((int)minX, (int)16);
        int j = MathHelper.intFloorDiv((int)minY, (int)16);
        int k = MathHelper.intFloorDiv((int)minZ, (int)16);
        int l = MathHelper.intFloorDiv((int)maxX, (int)16);
        int i1 = MathHelper.intFloorDiv((int)maxY, (int)16);
        int j1 = MathHelper.intFloorDiv((int)maxZ, (int)16);
        for (int k1 = i; k1 <= l; ++k1) {
            int l1 = k1 % this.countChunksX;
            if (l1 < 0) {
                l1 += this.countChunksX;
            }
            for (int i2 = j; i2 <= i1; ++i2) {
                int j2 = i2 % this.countChunksY;
                if (j2 < 0) {
                    j2 += this.countChunksY;
                }
                for (int k2 = k; k2 <= j1; ++k2) {
                    int l2 = k2 % this.countChunksZ;
                    if (l2 < 0) {
                        l2 += this.countChunksZ;
                    }
                    int i3 = (l2 * this.countChunksY + j2) * this.countChunksX + l1;
                    RenderChunk renderchunk = this.renderChunks[i3];
                    renderchunk.setNeedsUpdate(updateImmediately);
                }
            }
        }
    }

    @Nullable
    public RenderChunk getRenderChunk(BlockPos pos) {
        int i = MathHelper.intFloorDiv((int)pos.getX(), (int)16);
        int j = MathHelper.intFloorDiv((int)pos.getY(), (int)16);
        int k = MathHelper.intFloorDiv((int)pos.getZ(), (int)16);
        if (j >= 0 && j < this.countChunksY) {
            if ((i %= this.countChunksX) < 0) {
                i += this.countChunksX;
            }
            if ((k %= this.countChunksZ) < 0) {
                k += this.countChunksZ;
            }
            int l = (k * this.countChunksY + j) * this.countChunksX + i;
            return this.renderChunks[l];
        }
        return null;
    }
}

