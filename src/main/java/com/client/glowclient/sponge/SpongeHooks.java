/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.network.Packet
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge;

import com.client.glowclient.sponge.events.AddCollisionBoxToListEvent;
import com.client.glowclient.sponge.events.GenChunkEvent;
import com.client.glowclient.sponge.events.LocalPlayerUpdateMovementEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.sponge.events.RenderBlockEvent;
import com.client.glowclient.sponge.events.RenderBoatEvent;
import com.client.glowclient.sponge.events.RenderChunkEvent;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SpongeHooks {
    public static void onChunkGenerated(Chunk chunk) {
        MinecraftForge.EVENT_BUS.post((Event)new GenChunkEvent(chunk));
    }

    public static void onRenderChunk(RenderChunk renderChunk, BlockRenderLayer layer) {
        MinecraftForge.EVENT_BUS.post((Event)new RenderChunkEvent(renderChunk, layer));
    }

    public static boolean onRenderBlock(BlockPos pos, IBlockState state, IBlockAccess access, BufferBuilder buffer) {
        return MinecraftForge.EVENT_BUS.post((Event)new RenderBlockEvent(pos, state, access, buffer));
    }

    public static void onUpdateWalkingPlayerPre() {
        MinecraftForge.EVENT_BUS.register((Object)new LocalPlayerUpdateMovementEvent.Pre());
    }

    public static void onUpdateWalkingPlayerPost() {
        MinecraftForge.EVENT_BUS.register((Object)new LocalPlayerUpdateMovementEvent.Post());
    }

    public static boolean onAddCollisionBoxToList(Block block, IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean bool) {
        return MinecraftForge.EVENT_BUS.post((Event)new AddCollisionBoxToListEvent(block, state, worldIn, pos, entityBox, collidingBoxes, entityIn, bool));
    }

    public static float onRenderBoat(EntityBoat boat, float entityYaw) {
        RenderBoatEvent event = new RenderBoatEvent(boat, entityYaw);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.getYaw();
    }

    public static void onPacketSent(Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post((Event)new PacketEvent.Send(packet));
    }

    public static void onPacketRecieved(Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post((Event)new PacketEvent.Receive(packet));
    }
}

