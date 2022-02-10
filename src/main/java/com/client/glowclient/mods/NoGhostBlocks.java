//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.event.world.BlockEvent$BreakEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoGhostBlocks
extends ToggleMod {
    public NoGhostBlocks() {
        super(Category.SERVER, "NoGhostBlocks", false, -1, "Makes ghost blocks much more infrequent");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onUpdate(BlockEvent.BreakEvent event) {
        if (!(Globals.MC.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            BlockPos pos = Globals.MC.player.getPosition();
            for (int dx = -4; dx <= 4; ++dx) {
                for (int dy = -4; dy <= 4; ++dy) {
                    for (int dz = -4; dz <= 4; ++dz) {
                        BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                        if (!Globals.MC.world.getBlockState(blockPos).getBlock().equals((Object)Blocks.AIR)) continue;
                        Globals.MC.playerController.processRightClickBlock(Globals.MC.player, Globals.MC.world, blockPos, EnumFacing.DOWN, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }
}

