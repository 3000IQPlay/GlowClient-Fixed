//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.EnumHand
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.extra.wurst.WConnection;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public final class WPlayer {
    public static void swingArmClient() {
        WMinecraft.getPlayer().swingArm(EnumHand.MAIN_HAND);
    }

    public static void swingArmPacket() {
        WConnection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    }

    public static void prepareAttack() {
    }

    public static void attackEntity(Entity entity) {
        Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)WMinecraft.getPlayer(), entity);
        WPlayer.swingArmClient();
    }

    public static void sendAttackPacket(Entity entity) {
        WConnection.sendPacket((Packet)new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
    }

    public static float getCooldown() {
        return WMinecraft.getPlayer().getCooledAttackStrength(0.0f);
    }

    public static void addPotionEffect(Potion potion) {
        WMinecraft.getPlayer().addPotionEffect(new PotionEffect(potion, 10801220));
    }

    public static void removePotionEffect(Potion potion) {
        WMinecraft.getPlayer().removePotionEffect(potion);
    }

    public static void copyPlayerModel(EntityPlayer from, EntityPlayer to) {
    }
}

