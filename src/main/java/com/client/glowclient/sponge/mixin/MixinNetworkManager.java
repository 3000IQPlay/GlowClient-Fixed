// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

// Decompiled with: Procyon 0.5.36
package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.client.glowclient.sponge.events.PacketEvent;
import net.minecraft.network.INetHandler;
import org.spongepowered.asm.mixin.Shadow;
import javax.annotation.Nullable;
import java.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.Packet;
import io.netty.channel.SimpleChannelInboundHandler;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<Packet<?>>
{
    private boolean sendPackets;

    public MixinNetworkManager() {
        super();
        this.sendPackets = true;
    }

    @Shadow
    protected abstract void dispatchPacket(final Packet<?> p0, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] p1);

    @Redirect(method = "channelRead0", at = @At(value = "INVOKE", target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V"))
    @SuppressWarnings("unchecked")
    private void processPacket(Packet<?> packetIn, INetHandler handler) {
        PacketEvent event = new PacketEvent.Receive(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;

        ((Packet<INetHandler>) event.getPacket()).processPacket(handler);
    }

    @SuppressWarnings("AmbiguousMixinReference")
    @Redirect(method = "sendPacket", at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendPacket(NetworkManager networkManager, Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<?super Void>>[] futureListeners) {
        PacketEvent event = new PacketEvent.Send(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;

        this.dispatchPacket(event.getPacket(), null);
    }

    @Inject(method = { "flushOutboundQueue" }, at = { @At("HEAD") }, cancellable = true)
    private void flushOutboundQueue(final CallbackInfo ci) {
        if (!this.sendPackets) {
            ci.cancel();
        }
    }
}