//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 */
package com.client.glowclient.utils.world.entity;

import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationSpoofing {
    public static float pitch = Globals.MC.player.rotationPitch;
    public static float yaw = Globals.MC.player.rotationYaw;
    private static boolean togglePitch = false;

    private static float[] getNeededRotationsClient(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(Globals.MC.player.posX, Globals.MC.player.posY + (double)Globals.MC.player.getEyeHeight(), Globals.MC.player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapDegrees((float)yaw), MathHelper.wrapDegrees((float)pitch)};
    }

    private static float[] getNeededRotationsServer(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(Globals.MC.player.posX, Globals.MC.player.posY + (double)Globals.MC.player.getEyeHeight(), Globals.MC.player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{Globals.MC.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - Globals.MC.player.rotationYaw)), Globals.MC.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - Globals.MC.player.rotationPitch))};
    }

    public static void togglePitch() {
        if (togglePitch) {
            Globals.MC.player.rotationPitch = (float)((double)Globals.MC.player.rotationPitch + 4.0E-4);
            togglePitch = false;
        } else {
            Globals.MC.player.rotationPitch = (float)((double)Globals.MC.player.rotationPitch - 4.0E-4);
            togglePitch = true;
        }
    }

    public static void faceVectorServer(Vec3d vec, Module mod) {
        float[] rotations = RotationSpoofing.getNeededRotationsServer(vec);
        yaw = rotations[0];
        pitch = rotations[1];
        mod.isSpoofingRotation = true;
    }

    public static void faceEntityServer(Entity entity, Module mod) {
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        RotationSpoofing.faceVectorServer(bb.getCenter(), mod);
    }

    public static void setRotationServer(float yaw, float pitch, Module mod) {
        RotationSpoofing.yaw = yaw;
        RotationSpoofing.pitch = pitch;
        mod.isSpoofingRotation = true;
    }

    public static void resetSpoofedRotation(Module mod) {
        if (mod.isSpoofingRotation) {
            yaw = Globals.MC.player.rotationYaw;
            pitch = Globals.MC.player.rotationPitch;
            mod.isSpoofingRotation = false;
        }
    }

    public static void faceVectorClient(Vec3d vec) {
        float[] rotations = RotationSpoofing.getNeededRotationsClient(vec);
        Globals.MC.player.rotationYaw = rotations[0];
        Globals.MC.player.rotationPitch = rotations[1];
    }

    public static void faceEntityClient(Entity entity) {
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        RotationSpoofing.faceVectorClient(bb.getCenter());
    }

    public static void setRotationClient(float yaw, float pitch) {
        Globals.MC.player.rotationYaw = yaw;
        Globals.MC.player.rotationPitch = pitch;
    }

    public static void faceVectorClientYaw(Vec3d vec) {
        float[] rotations = RotationSpoofing.getNeededRotationsClient(vec);
        Globals.MC.player.rotationYaw = rotations[0];
    }
}

