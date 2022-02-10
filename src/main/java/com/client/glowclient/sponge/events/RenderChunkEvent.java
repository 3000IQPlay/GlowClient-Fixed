/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderChunkEvent
extends Event {
    private final RenderChunk renderChunk;
    private final BlockRenderLayer blockRenderLayer;

    public RenderChunkEvent(RenderChunk renderChunk, BlockRenderLayer blockRenderLayer) {
        this.renderChunk = renderChunk;
        this.blockRenderLayer = blockRenderLayer;
    }

    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }

    public BlockRenderLayer getBlockRenderLayer() {
        return this.blockRenderLayer;
    }
}

