/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GenChunkEvent
extends Event {
    private final Chunk chunk;

    public GenChunkEvent(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return this.chunk;
    }
}

