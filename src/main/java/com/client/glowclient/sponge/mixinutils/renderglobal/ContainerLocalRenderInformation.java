/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.util.EnumFacing
 */
package com.client.glowclient.sponge.mixinutils.renderglobal;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;

public class ContainerLocalRenderInformation {
    public final RenderChunk renderChunk;
    public final EnumFacing facing;
    public byte setFacing;
    public final int counter;

    public ContainerLocalRenderInformation(RenderChunk renderChunkIn, EnumFacing facingIn, @Nullable int counterIn) {
        this.renderChunk = renderChunkIn;
        this.facing = facingIn;
        this.counter = counterIn;
    }

    public void setDirection(byte p_189561_1_, EnumFacing p_189561_2_) {
        this.setFacing = (byte)(this.setFacing | p_189561_1_ | 1 << p_189561_2_.ordinal());
    }

    public boolean hasDirection(EnumFacing p_189560_1_) {
        return (this.setFacing & 1 << p_189560_1_.ordinal()) > 0;
    }
}

