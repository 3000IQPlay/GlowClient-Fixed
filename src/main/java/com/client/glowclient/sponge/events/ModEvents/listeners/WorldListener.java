//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IWorldEventListener
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events.ModEvents.listeners;

import com.client.glowclient.sponge.events.ModEvents.EntityAddedEvent;
import com.client.glowclient.sponge.events.ModEvents.EntityRemovedEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WorldListener
implements IWorldEventListener {
    public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
    }

    public void notifyLightSet(BlockPos pos) {
    }

    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
    }

    public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {
    }

    public void playRecord(SoundEvent soundIn, BlockPos pos) {
    }

    public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int ... parameters) {
    }

    public void spawnParticle(int id, boolean ignoreRange, boolean minimiseParticleLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int ... parameters) {
    }

    public void onEntityAdded(Entity entityIn) {
        MinecraftForge.EVENT_BUS.post((Event)new EntityAddedEvent(entityIn));
    }

    public void onEntityRemoved(Entity entityIn) {
        MinecraftForge.EVENT_BUS.post((Event)new EntityRemovedEvent(entityIn));
    }

    public void broadcastSound(int soundID, BlockPos pos, int data) {
    }

    public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {
    }

    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
    }
}

