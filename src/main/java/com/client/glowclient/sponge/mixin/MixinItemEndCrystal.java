//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProviderEnd
 *  net.minecraft.world.end.DragonFightManager
 */
package com.client.glowclient.sponge.mixin;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value={ItemEndCrystal.class})
public abstract class MixinItemEndCrystal
extends Item {

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
        ItemStack itemstack;
        IBlockState iblockstate = playerIn.getBlockState(worldIn);
        if (iblockstate.getBlock() != Blocks.OBSIDIAN && iblockstate.getBlock() != Blocks.BEDROCK) {
            return EnumActionResult.FAIL;
        }
        BlockPos blockpos = worldIn.up();
        if (!stack.canPlayerEdit(blockpos, hand, itemstack = stack.getHeldItem(pos))) {
            return EnumActionResult.FAIL;
        }
        BlockPos blockpos1 = blockpos.up();
        boolean flag = !playerIn.isAirBlock(blockpos) && !playerIn.getBlockState(blockpos).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos);
        if (flag |= !playerIn.isAirBlock(blockpos1) && !playerIn.getBlockState(blockpos1).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos1)) {
            return EnumActionResult.SUCCESS;
        }
        double d0 = blockpos.getX();
        double d1 = blockpos.getY();
        double d2 = blockpos.getZ();
        List list = playerIn.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0, d1 + 2.0, d2 + 1.0));
        EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(playerIn, (double)((float)worldIn.getX() + 0.5f), (double)(worldIn.getY() + 1), (double)((float)worldIn.getZ() + 0.5f));
        entityendercrystal.setShowBottom(false);
        if (!playerIn.isRemote) {
            playerIn.spawnEntity((Entity)entityendercrystal);
        }
        if (!list.isEmpty()) {
            return EnumActionResult.SUCCESS;
        }
        if (!playerIn.isRemote && playerIn.provider instanceof WorldProviderEnd) {
            DragonFightManager dragonfightmanager = ((WorldProviderEnd)playerIn.provider).getDragonFightManager();
            dragonfightmanager.respawnDragon();
        }
        itemstack.shrink(1);
        return EnumActionResult.SUCCESS;
    }
}

