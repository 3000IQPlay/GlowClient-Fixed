//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class RotationUtils {
    private static boolean fakeRotation;
    private static float serverYaw;
    private static float serverPitch;
    public static RotationUtils INSTANCE;
    public static float pitch;
    public static float yaw;

    public RotationUtils() {
        INSTANCE = this;
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(WMinecraft.getPlayer().posX, WMinecraft.getPlayer().posY + (double)WMinecraft.getPlayer().getEyeHeight(), WMinecraft.getPlayer().posZ);
    }

    public static Vec3d getEyesPosBlock() {
        BlockPos pos = new BlockPos(WMinecraft.getPlayer().posX, WMinecraft.getPlayer().posY + (double)WMinecraft.getPlayer().getEyeHeight(), WMinecraft.getPlayer().posZ);
        return new Vec3d((Vec3i)pos);
    }

    public static Vec3d getClientLookVec() {
        float f = WMath.cos(-WMinecraft.getPlayer().rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f1 = WMath.sin(-WMinecraft.getPlayer().rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = -WMath.cos(-WMinecraft.getPlayer().rotationPitch * ((float)Math.PI / 180));
        float f3 = WMath.sin(-WMinecraft.getPlayer().rotationPitch * ((float)Math.PI / 180));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    public static Vec3d getServerLookVec() {
        float f = WMath.cos(-serverYaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f1 = WMath.sin(-serverYaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = -WMath.cos(-serverPitch * ((float)Math.PI / 180));
        float f3 = WMath.sin(-serverPitch * ((float)Math.PI / 180));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    private static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{WMath.wrapDegrees(yaw), WMath.wrapDegrees(pitch)};
    }

    public static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{WMinecraft.getPlayer().rotationYaw + WMath.wrapDegrees(yaw - WMinecraft.getPlayer().rotationYaw), WMinecraft.getPlayer().rotationPitch + WMath.wrapDegrees(pitch - WMinecraft.getPlayer().rotationPitch)};
    }

    public static float limitAngleChange(float current, float intended, float maxChange) {
        float change = WMath.wrapDegrees(intended - current);
        change = WMath.clamp(change, -maxChange, maxChange);
        return WMath.wrapDegrees(current + change);
    }

    public static boolean faceVectorPacket(Vec3d vec) {
        fakeRotation = true;
        float[] rotations = RotationUtils.getNeededRotations(vec);
        serverYaw = RotationUtils.limitAngleChange(serverYaw, rotations[0], 30.0f);
        serverPitch = rotations[1];
        return Math.abs(serverYaw - rotations[0]) < 1.0f;
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = RotationUtils.getNeededRotations2(vec);
        yaw = rotations[0];
        pitch = rotations[1];
    }

    public static boolean faceVectorClient(Vec3d vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        float oldYaw = WMinecraft.getPlayer().prevRotationYaw;
        float oldPitch = WMinecraft.getPlayer().prevRotationPitch;
        WMinecraft.getPlayer().rotationYaw = RotationUtils.limitAngleChange(oldYaw, rotations[0], 30.0f);
        WMinecraft.getPlayer().rotationPitch = rotations[1];
        return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1.0f;
    }

    public static boolean faceEntityClient(Entity entity) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d lookVec = RotationUtils.getServerLookVec();
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        if (RotationUtils.faceVectorClient(bb.getCenter())) {
            return true;
        }
        return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }

    public static boolean faceEntityPacket(Entity entity) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        Vec3d lookVec = RotationUtils.getServerLookVec();
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        if (RotationUtils.faceVectorPacket(bb.getCenter())) {
            return true;
        }
        return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }

    public static boolean faceVectorForWalking(Vec3d vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        float oldYaw = WMinecraft.getPlayer().prevRotationYaw;
        WMinecraft.getPlayer().rotationYaw = RotationUtils.limitAngleChange(oldYaw, rotations[0], 30.0f);
        WMinecraft.getPlayer().rotationPitch = 0.0f;
        return Math.abs(oldYaw - rotations[0]) < 1.0f;
    }

    public static float getAngleToClientRotation(Vec3d vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float diffYaw = WMath.wrapDegrees(WMinecraft.getPlayer().rotationYaw) - needed[0];
        float diffPitch = WMath.wrapDegrees(WMinecraft.getPlayer().rotationPitch) - needed[1];
        float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    public static float getHorizontalAngleToClientRotation(Vec3d vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float angle = WMath.wrapDegrees(WMinecraft.getPlayer().rotationYaw) - needed[0];
        return angle;
    }

    public static float getAngleToServerRotation(Vec3d vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float diffYaw = serverYaw - needed[0];
        float diffPitch = serverPitch - needed[1];
        float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    public static void updateServerRotation() {
        if (fakeRotation) {
            fakeRotation = false;
            return;
        }
        serverYaw = RotationUtils.limitAngleChange(serverYaw, WMinecraft.getPlayer().rotationYaw, 30.0f);
        serverPitch = WMinecraft.getPlayer().rotationPitch;
    }

    public static float getServerYaw() {
        return serverYaw;
    }

    public static float getServerPitch() {
        return serverPitch;
    }

    static {
        pitch = Globals.MC.player.rotationPitch;
        yaw = Globals.MC.player.rotationYaw;
    }
}

