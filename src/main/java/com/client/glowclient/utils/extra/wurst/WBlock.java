//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class WBlock {
    public static IBlockState getState(BlockPos pos) {
        return WMinecraft.getWorld().getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return WBlock.getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getIdFromBlock((Block)WBlock.getBlock(pos));
    }

    public static String getName(Block block) {
        return "" + Block.REGISTRY.getNameForObject(block);
    }

    public static Material getMaterial(BlockPos pos) {
        return WBlock.getState(pos).getMaterial();
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        return WBlock.getState(pos).getBoundingBox((IBlockAccess)WMinecraft.getWorld(), pos).offset(pos);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return WBlock.getBlock(pos).canCollideCheck(WBlock.getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return WBlock.getState(pos).getPlayerRelativeBlockHardness((EntityPlayer)WMinecraft.getPlayer(), (World)WMinecraft.getWorld(), pos);
    }
}

