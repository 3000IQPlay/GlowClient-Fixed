//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 */
package com.client.glowclient.utils.mod.mods.trajectories;

import java.util.List;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class SimulationResult {
    private final List<Vec3d> points;
    private final double distanceTraveledSq;
    private final Entity hitEntity;

    public SimulationResult(List<Vec3d> points, double distanceTraveledSq, Entity hitEntity) {
        this.points = points;
        this.distanceTraveledSq = distanceTraveledSq;
        this.hitEntity = hitEntity;
    }

    public Vec3d getShootPos() {
        try {
            return this.points.get(0);
        }
        catch (Throwable t) {
            return null;
        }
    }

    public Vec3d getHitPos() {
        try {
            return this.points.get(this.points.size() - 1);
        }
        catch (Throwable t) {
            return null;
        }
    }

    public Entity getHitEntity() {
        return this.hitEntity;
    }

    public boolean hasTraveled() {
        return !Objects.equals((Object)this.getShootPos(), (Object)this.getHitPos());
    }

    public double getDistanceTraveledSq() {
        return this.distanceTraveledSq;
    }

    public double getDistanceApartSq() {
        Vec3d start = this.getShootPos();
        Vec3d hit = this.getHitPos();
        if (start != null && hit != null) {
            return start.squareDistanceTo(hit);
        }
        return 0.0;
    }

    public List<Vec3d> getPathTraveled() {
        return this.points;
    }
}

