//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package com.client.glowclient.utils.world.block;

import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.RotationUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockUtils {
    public static boolean breakBlockViewServer(BlockPos pos, Module mod) {
        Vec3d eyesPos = RotationUtils.getEyesPosBlock();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 36.0) || !(distanceSqHitVec < distanceSqPosVec)) continue;
            RotationSpoofing.faceVectorServer(hitVec, mod);
            if (!Globals.MC.playerController.onPlayerDamageBlock(pos, side)) {
                return false;
            }
            Globals.MC.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        RotationSpoofing.resetSpoofedRotation(mod);
        return false;
    }

    public static boolean breakBlockViewClient(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 36.0) || !(distanceSqHitVec < distanceSqPosVec)) continue;
            RotationSpoofing.faceVectorClient(hitVec);
            if (!Globals.MC.playerController.onPlayerDamageBlock(pos, side)) {
                return false;
            }
            Globals.MC.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    public static boolean isBlockAboveEntitySolid(Entity entity) {
        if (entity != null) {
            BlockPos pos = new BlockPos(entity.posX, entity.posY + 2.0, entity.posZ);
            return Globals.MC.world.getBlockState(pos).getBlock() != Blocks.AIR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.END_PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LAVA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_LAVA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SAPLING && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RED_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.YELLOW_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.BROWN_MUSHROOM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RED_MUSHROOM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WHEAT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CARROTS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POTATOES && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.BEETROOTS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REEDS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.PUMPKIN_STEM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.MELON_STEM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WATERLILY && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.NETHER_WART && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.COCOA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CHORUS_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CHORUS_PLANT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TALLGRASS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.DEADBUSH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.VINE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.ACTIVATOR_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.DETECTOR_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.GOLDEN_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_WIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNLIT_REDSTONE_TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POWERED_REPEATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNPOWERED_REPEATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POWERED_COMPARATOR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNPOWERED_COMPARATOR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LADDER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LEVER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.STONE_BUTTON && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WOODEN_BUTTON && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TRIPWIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TRIPWIRE_HOOK && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWER_POT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SKULL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.END_ROD && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CARPET && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SNOW && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWER_POT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_LAVA;
        }
        return false;
    }

    public static boolean isBlockBelowEntitySolid(Entity entity) {
        if (entity != null) {
            BlockPos pos = new BlockPos(entity.posX, entity.posY - 1.0, entity.posZ);
            return Globals.MC.world.getBlockState(pos).getBlock() != Blocks.AIR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.END_PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LAVA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_LAVA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SAPLING && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RED_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.YELLOW_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.BROWN_MUSHROOM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RED_MUSHROOM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WHEAT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CARROTS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POTATOES && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.BEETROOTS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REEDS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.PUMPKIN_STEM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.MELON_STEM && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WATERLILY && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.NETHER_WART && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.COCOA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CHORUS_FLOWER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CHORUS_PLANT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TALLGRASS && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.DEADBUSH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.VINE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.ACTIVATOR_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.DETECTOR_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.GOLDEN_RAIL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_WIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNLIT_REDSTONE_TORCH && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POWERED_REPEATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNPOWERED_REPEATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.POWERED_COMPARATOR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.UNPOWERED_COMPARATOR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LADDER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LEVER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.STONE_BUTTON && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WOODEN_BUTTON && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TRIPWIRE && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.TRIPWIRE_HOOK && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWER_POT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SKULL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.END_ROD && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.CARPET && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.SNOW && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWER_POT && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_LAVA;
        }
        return false;
    }
}

