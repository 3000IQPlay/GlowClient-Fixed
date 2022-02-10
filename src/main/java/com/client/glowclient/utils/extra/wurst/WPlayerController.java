//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class WPlayerController {
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

    public static ItemStack windowClick_PICKUP(int slot) {
        return WPlayerController.getPlayerController().windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_QUICK_MOVE(int slot) {
        return WPlayerController.getPlayerController().windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_THROW(int slot) {
        return WPlayerController.getPlayerController().windowClick(0, slot, 1, ClickType.THROW, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static void processRightClick() {
        WPlayerController.getPlayerController().processRightClick((EntityPlayer)WMinecraft.getPlayer(), (World)WMinecraft.getWorld(), EnumHand.MAIN_HAND);
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
        WPlayerController.getPlayerController().processRightClickBlock(WMinecraft.getPlayer(), WMinecraft.getWorld(), pos, side, hitVec, EnumHand.MAIN_HAND);
    }
}

