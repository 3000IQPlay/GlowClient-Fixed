/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderBlockEvent
extends Event {
    private final BlockPos pos;
    private final IBlockState state;
    private final IBlockAccess access;
    private final BufferBuilder buffer;

    public RenderBlockEvent(BlockPos pos, IBlockState state, IBlockAccess access, BufferBuilder buffer) {
        this.pos = pos;
        this.state = state;
        this.access = access;
        this.buffer = buffer;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public IBlockAccess getAccess() {
        return this.access;
    }

    public IBlockState getState() {
        return this.state;
    }

    public BufferBuilder getBuffer() {
        return this.buffer;
    }
}

