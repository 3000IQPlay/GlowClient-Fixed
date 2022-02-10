/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderBlockLayerEvent
extends Event {
    private final BlockRenderLayer renderLayer;
    private final double partialTicks;

    public RenderBlockLayerEvent(BlockRenderLayer renderLayer, double partialTicks) {
        this.renderLayer = renderLayer;
        this.partialTicks = partialTicks;
    }

    public BlockRenderLayer getRenderLayer() {
        return this.renderLayer;
    }

    public double getPartialTicks() {
        return this.partialTicks;
    }

    public static class Post
    extends RenderBlockLayerEvent {
        public Post(BlockRenderLayer renderLayer, double partialTicks) {
            super(renderLayer, partialTicks);
        }
    }

    @Cancelable
    public static class Pre
    extends RenderBlockLayerEvent {
        public Pre(BlockRenderLayer renderLayer, double partialTicks) {
            super(renderLayer, partialTicks);
        }
    }
}

