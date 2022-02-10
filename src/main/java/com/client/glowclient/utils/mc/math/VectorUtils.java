//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.util.vector.Matrix4f
 *  org.lwjgl.util.vector.Vector4f
 */
package com.client.glowclient.utils.mc.math;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.Angle;
import com.client.glowclient.utils.mc.math.Plane;
import java.nio.FloatBuffer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class VectorUtils {
    static Matrix4f modelMatrix = new Matrix4f();
    static Matrix4f projectionMatrix = new Matrix4f();

    private static void VecTransformCoordinate(Vector4f vec, Matrix4f matrix) {
        float x = vec.x;
        float y = vec.y;
        float z = vec.z;
        vec.x = x * matrix.m00 + y * matrix.m10 + z * matrix.m20 + matrix.m30;
        vec.y = x * matrix.m01 + y * matrix.m11 + z * matrix.m21 + matrix.m31;
        vec.z = x * matrix.m02 + y * matrix.m12 + z * matrix.m22 + matrix.m32;
        vec.w = x * matrix.m03 + y * matrix.m13 + z * matrix.m23 + matrix.m33;
    }

    public static Plane toScreen(double x, double y, double z) {
        try {
            Entity view = Globals.MC.getRenderViewEntity();
            if (view == null) {
                return new Plane(0.0, 0.0, false);
            }
            Vec3d camPos = ActiveRenderInfo.position;
            Vec3d eyePos = ActiveRenderInfo.projectViewFromEntity((Entity)view, (double)Globals.MC.getRenderPartialTicks());
            float vecX = (float)(camPos.x + eyePos.x - (double)((float)x));
            float vecY = (float)(camPos.y + eyePos.y - (double)((float)y));
            float vecZ = (float)(camPos.z + eyePos.z - (double)((float)z));
            Vector4f pos = new Vector4f(vecX, vecY, vecZ, 1.0f);
            FloatBuffer boof1 = ActiveRenderInfo.MODELVIEW;
            FloatBuffer boof2 = ActiveRenderInfo.PROJECTION;
            modelMatrix.load(boof1.asReadOnlyBuffer());
            projectionMatrix.load(boof2.asReadOnlyBuffer());
            VectorUtils.VecTransformCoordinate(pos, modelMatrix);
            VectorUtils.VecTransformCoordinate(pos, projectionMatrix);
            if (pos.w > 0.0f) {
                pos.x *= -100000.0f;
                pos.y *= -100000.0f;
            } else {
                float invert = 1.0f / pos.w;
                pos.x *= invert;
                pos.y *= invert;
            }
            ScaledResolution res = new ScaledResolution(Globals.MC);
            float halfWidth = (float)res.getScaledWidth() / 2.0f;
            float halfHeight = (float)res.getScaledHeight() / 2.0f;
            pos.x = halfWidth + (0.5f * pos.x * (float)res.getScaledWidth() + 0.5f);
            pos.y = halfHeight - (0.5f * pos.y * (float)res.getScaledHeight() + 0.5f);
            boolean bVisible = true;
            if (pos.x < 0.0f || pos.y < 0.0f || pos.x > (float)res.getScaledWidth() || pos.y > (float)res.getScaledHeight()) {
                bVisible = false;
            }
            return new Plane(pos.x, pos.y, bVisible);
        }
        catch (Exception exception) {
            return new Plane(1.0, 1.0, false);
        }
    }

    public static Plane toScreen(Vec3d vec) {
        return VectorUtils.toScreen(vec.x, vec.y, vec.z);
    }

    @Deprecated
    public static ScreenPos _toScreen(double x, double y, double z) {
        Plane plane = VectorUtils.toScreen(x, y, z);
        return new ScreenPos(plane.getX(), plane.getY(), plane.isVisible());
    }

    @Deprecated
    public static ScreenPos _toScreen(Vec3d vec3d) {
        return VectorUtils._toScreen(vec3d.x, vec3d.y, vec3d.z);
    }

    public static Angle vectorAngle(Vec3d vec3d) {
        double pitch;
        double yaw;
        if (vec3d.x == 0.0 && vec3d.z == 0.0) {
            yaw = 0.0;
            pitch = 90.0;
        } else {
            yaw = Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - 90.0;
            double mag = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
            pitch = Math.toDegrees(-1.0 * Math.atan2(vec3d.y, mag));
        }
        return new Angle(pitch, yaw);
    }

    public static Vec3d multiplyBy(Vec3d vec1, Vec3d vec2) {
        return new Vec3d(vec1.x * vec2.x, vec1.y * vec2.y, vec1.z * vec2.z);
    }

    public static Vec3d copy(Vec3d toCopy) {
        return new Vec3d(toCopy.x, toCopy.y, toCopy.z);
    }

    @Deprecated
    public static class ScreenPos {
        public final int x;
        public final int y;
        public final boolean isVisible;
        public final double xD;
        public final double yD;

        public ScreenPos(double x, double y, boolean isVisible) {
            this.x = (int)x;
            this.y = (int)y;
            this.xD = x;
            this.yD = y;
            this.isVisible = isVisible;
        }
    }
}

