//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.INetHandler
 *  net.minecraft.network.Packet
 *  net.minecraft.network.PacketThreadUtil
 *  net.minecraft.network.play.INetHandlerPlayClient
 *  net.minecraft.network.play.server.SPacketChunkData
 *  net.minecraft.network.play.server.SPacketUseBed
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IThreadListener
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.client.glowclient.utils.mc.console.Console;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient
implements INetHandlerPlayClient {
    private Minecraft mc;
    @Shadow
    private Minecraft client;
    @Shadow
    private WorldClient world;

    @Overwrite
    public void handleChunkData(SPacketChunkData packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)((INetHandler)NetHandlerPlayClient.class.cast(this)), (IThreadListener)this.client);
        if (packetIn.isFullChunk()) {
            this.world.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
        }
        this.world.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
        Chunk chunk = this.world.getChunk(packetIn.getChunkX(), packetIn.getChunkZ());
        chunk.read(packetIn.getReadBuffer(), packetIn.getExtractedSize(), packetIn.isFullChunk());
        this.world.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
        if (!packetIn.isFullChunk() || this.world.provider.shouldClientCheckLighting()) {
            chunk.resetRelightChecks();
        }
        if (!packetIn.isFullChunk()) {
            SpongeHooks.onChunkGenerated(chunk);
        }
        for (NBTTagCompound nbttagcompound : packetIn.getTileEntityTags()) {
            BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
            TileEntity tileentity = this.world.getTileEntity(blockpos);
            if (tileentity == null) continue;
            tileentity.handleUpdateTag(nbttagcompound);
        }
    }

    @Overwrite
    public void handleUseBed(SPacketUseBed packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.client);
        packetIn.getPlayer((World)this.world).trySleep(packetIn.getBedPosition());
        Console.write(packetIn.getPlayer((World)this.world).getName() + packetIn.getBedPosition().toString());
    }
}

