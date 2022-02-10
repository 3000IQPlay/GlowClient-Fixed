//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.EnumConnectionState
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.handshake.client.C00Handshake
 */
package com.client.glowclient.sponge.mixin;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={C00Handshake.class})
public abstract class MixinC00Handshake {
    @Shadow
    int protocolVersion;
    @Shadow
    String ip;
    @Shadow
    int port;
    @Shadow
    EnumConnectionState requestedState;

    @Inject(method={"writePacketData"}, at={@At(value="HEAD")}, cancellable=true)
    public void writePacketData(PacketBuffer buf, CallbackInfo info) {
        info.cancel();
        buf.writeVarInt(this.protocolVersion);
        buf.writeString(this.ip);
        buf.writeShort(this.port);
        buf.writeVarInt(this.requestedState.getId());
    }
}

