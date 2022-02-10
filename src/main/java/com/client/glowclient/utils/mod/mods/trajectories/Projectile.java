//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 */
package com.client.glowclient.utils.mod.mods.trajectories;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.AngleHelper;
import com.client.glowclient.utils.mc.math.AngleN;
import com.client.glowclient.utils.mod.mods.trajectories.IProjectile;
import com.client.glowclient.utils.mod.mods.trajectories.SimulationResult;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public enum Projectile implements IProjectile
{
    NULL{

        @Override
        public Item getItem() {
            return null;
        }

        @Override
        public double getForce(int charge) {
            return 0.0;
        }

        @Override
        public double getMaxForce() {
            return 0.0;
        }

        @Override
        public double getMinForce() {
            return 0.0;
        }

        @Override
        public double getGravity() {
            return 0.0;
        }

        @Override
        public double getDrag() {
            return 0.0;
        }

        @Override
        public double getWaterDrag() {
            return 0.0;
        }

        @Override
        public double getProjectileSize() {
            return 0.0;
        }
    }
    ,
    BOW{

        @Override
        public Item getItem() {
            return Items.BOW;
        }

        @Override
        public double getForce(int charge) {
            double force = (double)charge / 20.0;
            if (force > 1.0) {
                force = 1.0;
            }
            return force *= 3.0;
        }

        @Override
        public double getMaxForce() {
            return 3.0;
        }

        @Override
        public double getMinForce() {
            return 0.15;
        }

        @Override
        public double getGravity() {
            return 0.05;
        }

        @Override
        public double getWaterDrag() {
            return 0.6;
        }

        @Override
        public double getProjectileSize() {
            return 0.5;
        }
    }
    ,
    SNOWBALL{

        @Override
        public Item getItem() {
            return Items.SNOWBALL;
        }
    }
    ,
    EGG{

        @Override
        public Item getItem() {
            return Items.EGG;
        }
    }
    ,
    FISHING_ROD{

        @Override
        public Item getItem() {
            return Items.FISHING_ROD;
        }

        @Override
        public double getGravity() {
            return 0.04f;
        }

        @Override
        public double getDrag() {
            return 0.92;
        }
    }
    ,
    ENDER_PEARL{

        @Override
        public Item getItem() {
            return Items.ENDER_PEARL;
        }
    };

    private static final int MAX_ITERATIONS = 1000;
    private static final double SHOOT_POS_OFFSET = (double)0.1f;

    public boolean isNull() {
        return this.getItem() == null;
    }

    @Nullable
    public SimulationResult getSimulatedTrajectory(Vec3d shootPos, AngleN angle, double force, int factor) throws IllegalArgumentException {
        Vec3d next;
        if (this.isNull()) {
            return null;
        }
        Entity hitEntity = null;
        final double[] forward = angle.forward();
        final Vec3d v = new Vec3d(forward[0], forward[1], forward[2]).normalize().scale(force);
        double velocityX = v.x;
        double velocityY = v.y;
        double velocityZ = v.z;
        double distanceTraveledSq = 0.0;
        List<Vec3d> points = (factor < 0) ? Collections.emptyList() : Lists.newArrayList();
        points.add(shootPos);
        Vec3d previous = next = new Vec3d(shootPos.x, shootPos.y, shootPos.z);
        int n = 0;
        for (int index = points.size(); index < 1000; ++index) {
            AxisAlignedBB bb;
            RayTraceResult trace = Projectile.rayTraceCheckEntityCollisions(previous, next = next.add(velocityX, velocityY, velocityZ), bb = this.getBoundBox(next), velocityX, velocityY, velocityZ);
            if (trace != null) {
                hitEntity = trace.entityHit;
                distanceTraveledSq += previous.squareDistanceTo(trace.hitVec);
                points.add(trace.hitVec);
                break;
            }
            if (n == factor) {
                points.add(next);
                n = 0;
            } else {
                ++n;
            }
            distanceTraveledSq += previous.squareDistanceTo(next);
            if (next.y <= 0.0) break;
            double d = Globals.MC.world.isMaterialInBB(bb, Material.WATER) ? this.getWaterDrag() : this.getDrag();
            velocityX *= d;
            velocityY = velocityY * d - this.getGravity();
            velocityZ *= d;
            previous = next;
        }
        return new SimulationResult(points, distanceTraveledSq, hitEntity);
    }

    @Nullable
    public SimulationResult getSimulatedTrajectoryFromEntity(Entity shooter, AngleN angle, double force, int factor) {
        angle = Projectile.getAngleFacing(angle);
        return this.getSimulatedTrajectory(Projectile.getShootPosFacing(shooter, angle), angle, force, factor);
    }

    @Nullable
    public AngleN getEstimatedImpactAngleInRadians(Vec3d shooterPos, Vec3d targetPos, double force) {
        if (this.isNull()) {
            return null;
        }
        Vec3d start = shooterPos.subtract(targetPos);
        double yaw = AngleHelper.getAngleFacingInRadians(targetPos.subtract(shooterPos)).yaw();
        force *= this.getDrag();
        double x = Math.sqrt(start.x * start.x + start.z * start.z);
        double g = this.getGravity();
        double root = Math.pow(force, 4.0) - g * (g * Math.pow(x, 2.0) + 2.0 * start.y * Math.pow(force, 2.0));
        if (root < 0.0) {
            return null;
        }
        double A = (Math.pow(force, 2.0) + Math.sqrt(root)) / (g * x);
        double B = (Math.pow(force, 2.0) - Math.sqrt(root)) / (g * x);
        double pitch = Math.atan(Math.max(A, B));
        return AngleN.radians(pitch, yaw).normalize();
    }

    @Nullable
    public AngleN getEstimatedImpactAngleInRadiansFromEntity(Entity entity, Vec3d targetPos, double force) {
        return this.getEstimatedImpactAngleInRadians(Projectile.getEntityShootPos(entity), targetPos, force);
    }

    public boolean canHitEntity(Vec3d shooterPos, Entity targetEntity) {
        double max;
        if (this.isNull()) {
            return false;
        }
        Vec3d targetPos = EntityUtils.getOBBCenter(targetEntity);
        double min = this.getMinForce();
        for (double force = max = this.getMaxForce(); force >= min; force -= min) {
            AngleN shootAngle = this.getEstimatedImpactAngleInRadians(shooterPos, targetPos, force);
            if (shootAngle == null) continue;
            SimulationResult result = this.getSimulatedTrajectory(shooterPos, shootAngle, force, -1);
            if (result == null) {
                return false;
            }
            if (!Objects.equals((Object)targetEntity, (Object)result.getHitEntity())) continue;
            return true;
        }
        return false;
    }

    private AxisAlignedBB getBoundBox(Vec3d pos) {
        double mp = this.getProjectileSize() / 2.0;
        return new AxisAlignedBB(pos.x - mp, pos.y - mp, pos.z - mp, pos.x + mp, pos.y + mp, pos.z + mp);
    }

    private static RayTraceResult rayTraceCheckEntityCollisions(Vec3d start, Vec3d end, AxisAlignedBB bb, double motionX, double motionY, double motionZ) {
        RayTraceResult trace = Globals.MC.world.rayTraceBlocks(start, end, false, true, false);
        if (trace != null) {
            end = trace.hitVec;
        }
        List<Entity> entities = Globals.MC.world.getEntitiesWithinAABBExcludingEntity(Globals.MC.player, bb.expand(motionX, motionY, motionZ).grow(1.0));
        double best = 0.0;
        Vec3d hitPos = Vec3d.ZERO;
        Entity hitEntity = null;
        for (Entity entity : entities) {
            double distance;
            if (!entity.canBeCollidedWith()) continue;
            float size = entity.getCollisionBorderSize();
            AxisAlignedBB bbe = entity.getEntityBoundingBox().grow((double)size);
            RayTraceResult tr = bbe.calculateIntercept(start, end);
            if (tr == null || !((distance = start.squareDistanceTo(tr.hitVec)) < best) && hitEntity != null) continue;
            best = distance;
            hitPos = tr.hitVec;
            hitEntity = entity;
        }
        if (hitEntity != null) {
            trace = new RayTraceResult(hitEntity, hitPos);
        }
        return trace;
    }

    private static Vec3d getEntityShootPos(Entity entity) {
        return EntityUtils.getEyePos(entity).subtract(0.0, (double)0.1f, 0.0);
    }

    private static Vec3d getShootPosFacing(Entity entity, AngleN angleFacing) {
        return Projectile.getEntityShootPos(entity).subtract(Math.cos(angleFacing.toRadians().yaw() - 1.5707963267948966) * 0.16, 0.0, Math.sin(angleFacing.toRadians().yaw() - 1.5707963267948966) * 0.16);
    }

    private static AngleN getAngleFacing(AngleN angle) {
        return AngleN.radians(-angle.toRadians().pitch(), angle.toRadians().yaw() + 1.5707963267948966);
    }

    public static Projectile getProjectileByItem(Item item) {
        if (item != null) {
            for (Projectile p : Projectile.values()) {
                if (p.getItem() == null || !p.getItem().equals((Object)item)) continue;
                return p;
            }
        }
        return NULL;
    }

    public static Projectile getProjectileByItemStack(ItemStack item) {
        return item == null ? NULL : Projectile.getProjectileByItem(item.getItem());
    }
}

