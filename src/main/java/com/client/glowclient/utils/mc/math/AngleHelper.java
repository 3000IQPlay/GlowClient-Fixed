//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3d
 */
package com.client.glowclient.utils.mc.math;

import com.client.glowclient.utils.mc.math.AngleN;
import net.minecraft.util.math.Vec3d;

public class AngleHelper {
    public static final long DEFAULT_N = 1000000000L;
    public static final double DEFAULT_EPSILON = 1.0E-9;
    public static final double TWO_PI = Math.PI * 2;
    public static final double HALF_PI = 1.5707963267948966;
    public static final double QUARTER_PI = 0.7853981633974483;

    public static double roundAngle(double a, long n) {
        return Math.round(a * (double)n) / n;
    }

    public static double roundAngle(double a) {
        return AngleHelper.roundAngle(a, 1000000000L);
    }

    public static boolean isAngleEqual(double a1, double a2, double epsilon) {
        return Double.compare(a1, a2) == 0 || Math.abs(a1 - a2) < epsilon;
    }

    public static boolean isAngleEqual(double a1, double a2) {
        return AngleHelper.isAngleEqual(a1, a2, 1.0E-9);
    }

    public static double normalizeInRadians(double ang) {
        while (ang > Math.PI) {
            ang -= Math.PI * 2;
        }
        while (ang < -Math.PI) {
            ang += Math.PI * 2;
        }
        return ang;
    }

    public static double normalizeInDegrees(double ang) {
        while (ang <= -180.0) {
            ang += 360.0;
        }
        while (ang > 180.0) {
            ang -= 360.0;
        }
        return ang;
    }

    public static AngleN getAngleFacingInRadians(Vec3d vector) {
        double pitch;
        double yaw;
        if (vector.x == 0.0 && vector.z == 0.0) {
            yaw = 0.0;
            pitch = 1.5707963267948966;
        } else {
            yaw = Math.atan2(vector.z, vector.x) - 1.5707963267948966;
            double mag = Math.sqrt(vector.x * vector.x + vector.z * vector.z);
            pitch = -Math.atan2(vector.y, mag);
        }
        return AngleN.radians(pitch, yaw);
    }

    public static AngleN getAngleFacingInDegrees(Vec3d vector) {
        return AngleHelper.getAngleFacingInRadians(vector).toDegrees();
    }
}

