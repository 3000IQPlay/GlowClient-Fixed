//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.PacketHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoFall
extends ToggleMod {
    private float lastFallDistance = 0.0f;

    public NoFall() {
        super(Category.PLAYER, "NoFall", false, -1, "Removes fall damage");
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        try {
            if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Rotation) && !PacketHelper.isIgnored(event.getPacket())) {
                CPacketPlayer packetPlayer = (CPacketPlayer)event.getPacket();
                if (packetPlayer.onGround && this.lastFallDistance >= 4.0f) {
                    CPacketPlayer.PositionRotation packet = new CPacketPlayer.PositionRotation(((CPacketPlayer)event.getPacket()).getX(0.0), 1337.0 + ((CPacketPlayer)event.getPacket()).getY(0.0), ((CPacketPlayer)event.getPacket()).getZ(0.0), ((CPacketPlayer)event.getPacket()).getYaw(0.0f), ((CPacketPlayer)event.getPacket()).getPitch(0.0f), true);
                    CPacketPlayer.PositionRotation reposition = new CPacketPlayer.PositionRotation(((CPacketPlayer)event.getPacket()).getX(0.0), ((CPacketPlayer)event.getPacket()).getY(0.0), ((CPacketPlayer)event.getPacket()).getZ(0.0), ((CPacketPlayer)event.getPacket()).getYaw(0.0f), ((CPacketPlayer)event.getPacket()).getPitch(0.0f), true);
                    PacketHelper.ignore((Packet)packet);
                    PacketHelper.ignore((Packet)reposition);
                    Utils.getNetworkManager().sendPacket((Packet)packet);
                    Utils.getNetworkManager().sendPacket((Packet)reposition);
                    this.lastFallDistance = 0.0f;
                } else {
                    this.lastFallDistance = Globals.MC.player.fallDistance;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

