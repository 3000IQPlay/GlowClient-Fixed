//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.network.NetworkManager
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals
extends ToggleMod {
    public boolean packet = true;

    public Criticals() {
        super(Category.COMBAT, "Criticals", false, -1, "Causes a critical hit every time");
    }

    @SubscribeEvent
    public void onPacketSending(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && !(((CPacketUseEntity)event.getPacket()).getEntityFromWorld((World)Globals.MC.world) instanceof EntityEnderCrystal)) {
            this.doCrit();
        }
    }

    public void doCrit() {
        EntityPlayerSP player = Globals.MC.player;
        if (!player.onGround) {
            return;
        }
        if (player.isInWater() || player.isInLava()) {
            return;
        }
        if (this.packet) {
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;
            NetworkManager manager = Globals.MC.getConnection().getNetworkManager();
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.0625, z, true));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y + 1.1E-5, z, false));
            manager.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
        } else {
            player.motionY = 0.1f;
            player.fallDistance = 0.1f;
            player.onGround = false;
        }
    }
}

