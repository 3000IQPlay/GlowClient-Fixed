//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 */
package com.client.glowclient.utils.mc.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Angle {
    private double p = 0.0;
    private double y = 0.0;
    private double r = 0.0;

    public Angle() {
        this.r = 0.0;
        this.y = 0.0;
        this.p = 0.0;
    }

    public Angle(double p, double y, double r) {
        this.p = p;
        this.y = y;
        this.r = r;
    }

    public Angle(double p, double y) {
        this.p = p;
        this.y = y;
    }

    public Angle(Angle copy) {
        this.p = copy.p;
        this.y = copy.y;
        this.r = copy.r;
    }

    public boolean equals(Angle other) {
        if (this.p != other.p) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.r != other.r) {
            return false;
        }
        return false;
    }

    public Angle add(Angle other) {
        this.p += other.p;
        this.y += other.y;
        this.r += other.r;
        return this;
    }

    public Angle sub(Angle other) {
        this.p -= other.p;
        this.y -= other.y;
        this.r -= other.r;
        return this;
    }

    public Angle mul(Angle other) {
        this.p *= other.p;
        this.y *= other.y;
        this.r *= other.r;
        return this;
    }

    public Angle mul(double other) {
        this.p *= other;
        this.y *= other;
        this.r *= other;
        return this;
    }

    public Angle div(Angle other) {
        this.p /= other.p;
        this.y /= other.y;
        this.r /= other.r;
        return this;
    }

    public Angle div(double other) {
        this.p /= other;
        this.y /= other;
        this.r /= other;
        return this;
    }

    public Vec3d forward() {
        float pitch = (float)Math.toRadians(this.p);
        float yaw = (float)Math.toRadians(this.y);
        float roll = (float)Math.toRadians(this.r);
        double x = MathHelper.sin((float)roll) * MathHelper.sin((float)pitch) + MathHelper.cos((float)roll) * MathHelper.sin((float)yaw) * MathHelper.cos((float)pitch);
        double y = -MathHelper.cos((float)roll) * MathHelper.sin((float)pitch) + MathHelper.sin((float)roll) * MathHelper.sin((float)yaw) * MathHelper.cos((float)pitch);
        double z = MathHelper.cos((float)yaw) * MathHelper.cos((float)pitch);
        return new Vec3d(x, y, z);
    }

    public Vec3d right() {
        float yaw = (float)Math.toRadians(this.y);
        float roll = (float)Math.toRadians(this.r);
        double x = MathHelper.cos((float)roll) * MathHelper.cos((float)yaw);
        double y = MathHelper.sin((float)roll) * MathHelper.cos((float)yaw);
        double z = -MathHelper.sin((float)yaw);
        return new Vec3d(x, y, z);
    }

    public Vec3d up() {
        float pitch = (float)Math.toRadians(this.p);
        float yaw = (float)Math.toRadians(this.y);
        float roll = (float)Math.toRadians(this.r);
        double x = -MathHelper.sin((float)roll) * MathHelper.cos((float)pitch) + MathHelper.cos((float)roll) * MathHelper.sin((float)yaw) * MathHelper.sin((float)pitch);
        double y = MathHelper.cos((float)roll) * MathHelper.cos((float)pitch) + MathHelper.sin((float)roll) * MathHelper.sin((float)yaw) * MathHelper.sin((float)pitch);
        double z = MathHelper.cos((float)yaw) * MathHelper.sin((float)pitch);
        return new Vec3d(x, y, z);
    }

    public static double normalizeAngle(double angle) {
        while (angle <= -180.0) {
            angle += 360.0;
        }
        while (angle > 180.0) {
            angle -= 360.0;
        }
        return angle;
    }

    public Angle normalize() {
        return new Angle(Angle.normalizeAngle(this.p), Angle.normalizeAngle(this.y), Angle.normalizeAngle(this.r));
    }

    public Vec3d getCartesianCoords() {
        double c = Math.cos(this.getPitch(true));
        return new Vec3d(Math.cos(this.getYaw(true)) * c, Math.sin(this.getPitch(true)), Math.sin(this.getYaw(true)) * c);
    }

    public double getPitch(boolean inRadians) {
        return inRadians ? Math.toRadians(this.p) : this.p;
    }

    public double getPitch() {
        return this.getPitch(false);
    }

    public double getYaw(boolean inRadians) {
        return inRadians ? Math.toRadians(this.y) : this.y;
    }

    public double getYaw() {
        return this.getYaw(false);
    }

    public double getRoll(boolean inRadians) {
        return inRadians ? Math.toRadians(this.r) : this.r;
    }

    public double getRoll() {
        return this.getRoll(false);
    }

    public void setPitch(double p) {
        this.p = p;
    }

    public void setYaw(double y) {
        this.y = y;
    }

    public void setRoll(double r) {
        this.r = r;
    }

    public String toString() {
        return String.format("(p, y, r) = %.2f, %.2f, %.2f", this.p, this.y, this.r);
    }
}

