/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.extra.wurst.ChatUtils;
import java.util.ArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PortalGodMode
extends ToggleMod {
    private final ArrayList<Packet> packets = new ArrayList();

    public PortalGodMode() {
        super(Category.SERVER, "PortalGodMode", false, -1, "Disables CPacketConfirmTeleport");
    }

    @SubscribeEvent
    public void onPacketSending(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (!(packet instanceof CPacketConfirmTeleport)) {
            return;
        }
        event.setCanceled(true);
        this.packets.add(packet);
    }

    public void cancel() {
        this.packets.clear();
        ChatUtils.setEnabled(false);
    }
}

