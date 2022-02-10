/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent
extends Event {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packetIn) {
        this.packet = packetIn;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    @Cancelable
    public static class Receive
    extends PacketEvent {
        public Receive(Packet<?> packetIn) {
            super(packetIn);
        }
    }

    @Cancelable
    public static class Send
    extends PacketEvent {
        public Send(Packet<?> packetIn) {
            super(packetIn);
        }
    }
}

