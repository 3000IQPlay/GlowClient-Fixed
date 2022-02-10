//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.network.Packet;

public final class WConnection {
    public static void sendPacket(Packet packet) {
        WMinecraft.getConnection().sendPacket(packet);
    }

    public static void sendPacketBypass(Packet packet) {
        WMinecraft.getConnection().sendPacket(packet);
    }
}

