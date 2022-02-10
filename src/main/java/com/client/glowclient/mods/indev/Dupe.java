//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.indev;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Dupe
extends ToggleMod {
    public static SettingMode mode = SettingUtils.settingMode("Dupe", "Mode", "Mode of Dupe", "Donkey", "Donkey");

    public Dupe() {
        super(Category.SERVER, "Dupe", false, -1, "Assistive module for duplication bugs");
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (mode.getMode().equals("Donkey") && event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCanceled(true);
        }
    }

    @Override
    public void onEnabled() {
        if (mode.getMode().equals("Donkey")) {
            Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.PositionRotation(Globals.MC.player.posX, 1000.0 + Globals.MC.player.posY, Globals.MC.player.posZ, Globals.MC.player.rotationYaw, Globals.MC.player.rotationPitch, true));
        }
    }

    @Override
    public boolean isDrawn() {
        return true;
    }

    @Override
    public String getHUDTag() {
        return mode.getMode();
    }
}

