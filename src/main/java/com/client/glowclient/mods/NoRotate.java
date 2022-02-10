//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate
extends ToggleMod {
    public NoRotate() {
        super(Category.PLAYER, "NoRotate", false, -1, "Don't take SPacketRotation");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onPacketRecieved(PacketEvent.Receive event) {
        try {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
                if (Globals.MC.player != null && Globals.MC.player.rotationYaw != -180.0f && Globals.MC.player.rotationPitch != 0.0f) {
                    packet.yaw = Globals.MC.player.rotationYaw;
                    packet.pitch = Globals.MC.player.rotationPitch;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

