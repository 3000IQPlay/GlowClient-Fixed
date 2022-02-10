//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.mods.AutoTool;
import com.client.glowclient.utils.extra.wurst.RotationUtils;
import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.extra.wurst.WConnection;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import com.client.glowclient.utils.extra.wurst.WPlayer;
import com.client.glowclient.utils.extra.wurst.WPlayerController;
import com.google.common.collect.AbstractIterator;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public final class BlockUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static boolean placeBlockLegit(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {

            BlockPos neighbor = pos.offset(side);
            if (!WBlock.canBeClicked(neighbor)) continue;
            Vec3d dirVec = new Vec3d(side.getDirectionVec());
            Vec3d hitVec = posVec.add(dirVec.scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 18.0625) || !(distanceSqPosVec <= eyesPos.squareDistanceTo(posVec.add(dirVec))) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
            RotationUtils.faceVectorPacketInstant(hitVec);
            WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
            WPlayer.swingArmClient();
            try {
                BlockUtils.MC.rightClickDelayTimer = 4;
            }
            catch (Exception exception) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    public static boolean placeBlockSimple(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d vec3d = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
            BlockPos neighbor = pos.offset(side);
            if (!WBlock.canBeClicked(neighbor)) continue;
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            if (!(eyesPos.squareDistanceTo(vec3d) <= 36.0)) continue;
            WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
            return true;
        }
        return false;
    }

    public static boolean prepareToBreakBlockLegit(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 18.0625) || !(distanceSqHitVec < distanceSqPosVec) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
            AutoTool.setSlot(pos);
            if (!RotationUtils.faceVectorPacket(hitVec)) {
                return true;
            }
            return true;
        }
        return false;
    }

    public static boolean breakBlockLegit(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            RotationUtils.faceVectorPacketInstant(hitVec);
            if (!(distanceSqHitVec <= 18.0625) || !(distanceSqHitVec < distanceSqPosVec) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
            if (!BlockUtils.MC.playerController.onPlayerDamageBlock(pos, side)) {
                return false;
            }
            WPlayer.swingArmPacket();
            return true;
        }
        return false;
    }

    public static void breakBlockPacketSpam(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            if (!(eyesPos.squareDistanceTo(hitVec) < distanceSqPosVec)) continue;
            WConnection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
            WConnection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
            return;
        }
    }

    public static void breakBlocksPacketSpam(Iterable<BlockPos> blocks) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        block0: for (BlockPos pos : blocks) {
            Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
                if (!(eyesPos.squareDistanceTo(hitVec) < distanceSqPosVec)) continue;
                WConnection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                WConnection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                continue block0;
            }
        }
    }

    public static boolean rightClickBlockLegit(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 18.0625) || !(distanceSqHitVec < distanceSqPosVec) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
            if (!RotationUtils.faceVectorPacket(hitVec)) {
                return true;
            }
            WPlayerController.processRightClickBlock(pos, side, hitVec);
            WPlayer.swingArmClient();
            try {
                BlockUtils.MC.rightClickDelayTimer = 4;
            }
            catch (Exception exception) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    public static boolean rightClickBlockSimple(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (!(distanceSqHitVec <= 36.0) || !(distanceSqHitVec < distanceSqPosVec)) continue;
            WPlayerController.processRightClickBlock(pos, side, hitVec);
            return true;
        }
        return false;
    }

    public static Iterable<BlockPos> getValidBlocksByDistance(double range, final boolean ignoreVisibility, final BlockValidator validator) {
        final Vec3d eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        final double rangeSq = Math.pow(range + 0.5, 2.0);
        final BlockPos startPos = new BlockPos(RotationUtils.getEyesPos());
        return () -> new AbstractIterator<BlockPos>(){
            private ArrayDeque queue;
            private HashSet visited;
            {
                this.queue = new ArrayDeque<BlockPos>(Arrays.asList(new BlockPos[]{startPos}));
                this.visited = new HashSet();
            }

            protected BlockPos computeNext() {
                while (!this.queue.isEmpty()) {
                    BlockPos current = (BlockPos)this.queue.pop();
                    if (eyesPos.squareDistanceTo(new Vec3d((Vec3i)current)) > rangeSq) continue;
                    boolean canBeClicked = WBlock.canBeClicked(current);
                    if (ignoreVisibility || !canBeClicked) {
                        for (EnumFacing facing : EnumFacing.values()) {
                            BlockPos next = current.offset(facing);
                            if (this.visited.contains((Object)next)) continue;
                            this.queue.add(next);
                            this.visited.add(next);
                        }
                    }
                    if (!canBeClicked || !validator.isValid(current)) continue;
                    return current;
                }
                return (BlockPos)this.endOfData();
            }
        };
    }

    public static Iterable<BlockPos> getValidBlocksByDistanceReversed(double range, boolean ignoreVisibility, BlockValidator validator) {
        ArrayDeque<BlockPos> validBlocks = new ArrayDeque<BlockPos>();
        BlockUtils.getValidBlocksByDistance(range, ignoreVisibility, validator).forEach(p -> validBlocks.push((BlockPos)p));
        return validBlocks;
    }

    public static Iterable<BlockPos> getValidBlocks(double range, BlockValidator validator) {
        Vec3d eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        double rangeSq = Math.pow(range + 0.5, 2.0);
        return BlockUtils.getValidBlocks((int)Math.ceil(range), (BlockPos pos) -> {
            Vec3d vec3d = new Vec3d((Vec3i)pos);
            if (eyesPos.squareDistanceTo(vec3d) > rangeSq) {
                return false;
            }
            return validator.isValid(pos);
        });
    }

    public static Iterable<BlockPos> getValidBlocks(int blockRange, final BlockValidator validator) {
        BlockPos playerPos = new BlockPos(RotationUtils.getEyesPos());
        final BlockPos min = playerPos.add(-blockRange, -blockRange, -blockRange);
        final BlockPos max = playerPos.add(blockRange, blockRange, blockRange);
        return () -> new AbstractIterator<BlockPos>(){
            private BlockPos last;

            private BlockPos computeNextUnchecked() {
                if (this.last == null) {
                    this.last = min;
                    return this.last;
                }
                int x = this.last.getX();
                int y = this.last.getY();
                int z = this.last.getZ();
                if (z < max.getZ()) {
                    ++z;
                } else if (x < max.getX()) {
                    z = min.getZ();
                    ++x;
                } else if (y < max.getY()) {
                    z = min.getZ();
                    x = min.getX();
                    ++y;
                } else {
                    return null;
                }
                this.last = new BlockPos(x, y, z);
                return this.last;
            }

            protected BlockPos computeNext() {
                BlockPos pos;
                while ((pos = this.computeNextUnchecked()) != null) {
                    if (WBlock.getMaterial(pos) == Material.AIR || !validator.isValid(pos)) continue;
                    return pos;
                }
                return (BlockPos)this.endOfData();
            }
        };
    }

    public static interface BlockValidator {
        public boolean isValid(BlockPos var1);
    }
}

