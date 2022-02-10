//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 */
package com.client.glowclient.utils.mc.math;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.Angle;
import com.client.glowclient.utils.mc.math.VectorUtils;
import com.client.glowclient.utils.world.entity.EntityUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

public class ProjectileUtils {
    private static final int SIMULATION_ITERATIONS = 150;
    private static final int BESTPOS_ITERATIONS = 10;
    public static final double PROJECTILE_SHOOTPOS_OFFSET = (double)0.1f;

    public static Vec3d getFiringPos(Entity entity) {
        return entity.getPositionVector();
    }

    public static boolean isThrowable(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: 
            case 332: 
            case 344: 
            case 346: 
            case 368: {
                return true;
            }
        }
        return false;
    }

    public static boolean isBow(ItemStack itemStack) {
        return itemStack != null && Item.getIdFromItem((Item)itemStack.getItem()) == 261;
    }

    public static boolean isAttackableThrowable(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: 
            case 332: 
            case 344: 
            case 346: {
                return true;
            }
        }
        return false;
    }

    public static double getForce(ItemStack itemStack) {
        double force = 0.0;
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                int duration = itemStack.getMaxItemUseDuration() - Globals.MC.player.getItemInUseCount();
                force = (double)duration / 20.0;
                if (force > 1.0) {
                    force = 1.0;
                }
                force *= 3.0;
                break;
            }
            case 332: 
            case 344: 
            case 346: 
            case 368: {
                force = 1.5;
            }
        }
        return force;
    }

    public static double getMinForce(ItemStack itemStack) {
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                return 0.15;
            }
            case 332: 
            case 344: 
            case 346: 
            case 368: {
                return 1.5;
            }
        }
        return 0.0;
    }

    public static double getMaxForce(ItemStack itemStack) {
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                return 3.0;
            }
            case 332: 
            case 344: 
            case 346: 
            case 368: {
                return 1.5;
            }
        }
        return 0.0;
    }

    public static Vec3d getGravity(ItemStack itemStack) {
        Vec3d gravity = new Vec3d(0.0, 0.0, 0.0);
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                gravity = new Vec3d(0.0, -0.05, 0.0);
                break;
            }
            case 332: 
            case 344: 
            case 368: {
                gravity = new Vec3d(0.0, -0.03, 0.0);
                break;
            }
            case 346: {
                gravity = new Vec3d(0.0, (double)-0.04f, 0.0);
            }
        }
        return gravity;
    }

    public static Vec3d getAirResistance(ItemStack itemStack) {
        Vec3d ar = new Vec3d(0.0, 0.0, 0.0);
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: 
            case 332: 
            case 344: 
            case 368: {
                ar = new Vec3d(0.99, 0.99, 0.99);
                break;
            }
            case 346: {
                ar = new Vec3d(0.92, 0.92, 0.92);
            }
        }
        return ar;
    }

    public static Vec3d getImpactPos(ItemStack itemStack, Vec3d initPos, Vec3d hitPos, Angle angle) {
        RayTraceResult trace;
        double force = ProjectileUtils.getForce(itemStack);
        Angle initAngle = new Angle(-angle.getPitch(), angle.getYaw() + 90.0, 0.0);
        double fixX = Math.cos(initAngle.getYaw(true) - 1.5707963267948966) * 0.16;
        double fixY = 0.1f;
        double fixZ = Math.sin(initAngle.getYaw(true) - 1.5707963267948966) * 0.16;
        initPos = initPos.subtract(fixX, fixY, fixZ);
        Vec3d velocity = initAngle.getCartesianCoords().normalize().scale(force);
        Vec3d acceleration = ProjectileUtils.getGravity(itemStack);
        Vec3d airResistance = ProjectileUtils.getAirResistance(itemStack);
        double bestDistance = -1.0;
        Vec3d startPos = VectorUtils.copy(initPos);
        Vec3d endPos = VectorUtils.copy(startPos);
        for (int i = 1; i < 150; ++i) {
            startPos = startPos.add(velocity);
            velocity = VectorUtils.multiplyBy(velocity, airResistance);
            velocity = velocity.add(acceleration);
            double x = startPos.x - hitPos.x;
            double z = startPos.z - hitPos.z;
            double distance = x * x + z * z;
            if (distance != -1.0 && !(distance < bestDistance)) break;
            bestDistance = distance;
            endPos = VectorUtils.copy(startPos);
        }
        if ((trace = Globals.MC.world.rayTraceBlocks(startPos, endPos)) != null && trace.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
            return trace.hitVec;
        }
        return initPos;
    }

    private static Angle getLookAtAngles(Vec3d startPos, Vec3d endPos) {
        return VectorUtils.vectorAngle(endPos.subtract(startPos)).normalize();
    }

    private static Angle getLookAtAngles(Vec3d endPos) {
        return ProjectileUtils.getLookAtAngles(EntityUtils.getEyePos((Entity)Globals.MC.player), endPos);
    }

    public static Angle getShootAngle(ItemStack itemStack, Vec3d startPos, Vec3d targetPos, double force) {
        Angle angle = new Angle();
        Vec3d initPos = startPos.subtract(0.0, (double)0.1f, 0.0);
        initPos = initPos.subtract(targetPos);
        angle.setYaw(ProjectileUtils.getLookAtAngles(targetPos).getYaw(false));
        Vec3d acceleration = ProjectileUtils.getGravity(itemStack);
        Vec3d airResistance = ProjectileUtils.getAirResistance(itemStack);
        double x = Math.sqrt(initPos.x * initPos.x + initPos.z * initPos.z);
        double g = acceleration.y;
        double root = Math.pow(force *= airResistance.y, 4.0) - g * (g * Math.pow(x, 2.0) + 2.0 * initPos.y * Math.pow(force, 2.0));
        if (root < 0.0) {
            return null;
        }
        double A = (Math.pow(force, 2.0) + Math.sqrt(root)) / (g * x);
        double B = (Math.pow(force, 2.0) - Math.sqrt(root)) / (g * x);
        angle.setPitch(Math.toDegrees(Math.atan(Math.max(A, B))));
        return angle.normalize();
    }

    public static Angle getShootAngle(ItemStack itemStack, Vec3d startPos, Vec3d targetPos) {
        return ProjectileUtils.getShootAngle(itemStack, startPos, targetPos, ProjectileUtils.getForce(itemStack));
    }

    public static double scale(double x, double from_min, double from_max, double to_min, double to_max) {
        return to_min + (to_max - to_min) * ((x - from_min) / (from_max - from_min));
    }

    public static double getBestPitch(ItemStack itemStack, Vec3d hitPos) {
        EntityPlayerSP localPlayer = Globals.MC.player;
        Vec3d initPos = EntityUtils.getEyePos((Entity)localPlayer);
        Angle angle = new Angle();
        angle.setYaw(Globals.MC.player.rotationYaw);
        double minAngle = localPlayer.rotationPitch;
        double maxAngle = minAngle - 45.0;
        double bestOffset = -1.0;
        double bestDistance = -1.0;
        for (int i = 0; i < 10; ++i) {
            double offset = ProjectileUtils.scale(0.5, 0.0, 1.0, minAngle, maxAngle);
            angle.setPitch(offset);
            Vec3d pos = ProjectileUtils.getImpactPos(itemStack, initPos, hitPos, angle);
            double distance = pos.distanceTo(hitPos);
            if (bestDistance == -1.0 || distance < bestDistance) {
                bestDistance = distance;
                bestOffset = offset;
            }
            if (pos.y - hitPos.y < 0.0) {
                minAngle = offset;
                continue;
            }
            maxAngle = offset;
        }
        return bestOffset;
    }

    public static boolean projectileTrajectoryHitsEntity(Entity target, Vec3d shootPos, Vec3d targetPos, ProjectileTraceResult result) {
        double max;
        EntityPlayerSP localPlayer = Globals.MC.player;
        Vec3d selfPos = localPlayer.getPositionVector();
        ItemStack heldItem = localPlayer.getHeldItemMainhand();
        double min = ProjectileUtils.getMinForce(heldItem);
        block0: for (double force = max = ProjectileUtils.getMaxForce(heldItem); force >= min; force -= min) {
            Vec3d startPos;
            Angle angle = ProjectileUtils.getShootAngle(heldItem, shootPos, targetPos, force);
            if (angle == null) continue;
            Angle initAngle = new Angle(-angle.getPitch(), angle.getYaw() + 90.0, 0.0);
            double fixX = Math.cos(initAngle.getYaw(true) - 1.5707963267948966) * 0.16;
            double fixY = 0.1f;
            double fixZ = Math.sin(initAngle.getYaw(true) - 1.5707963267948966) * 0.16;
            Vec3d initPos = new Vec3d(-fixX, (double)localPlayer.getEyeHeight() - fixY, -fixZ);
            Vec3d acceleration = ProjectileUtils.getGravity(heldItem);
            Vec3d airResistance = ProjectileUtils.getAirResistance(heldItem);
            Vec3d velocity = initAngle.getCartesianCoords().normalize().scale(force);
            Vec3d endPos = startPos = initPos;
            for (int i = 0; i < 100; ++i) {
                Vec3d wrlEnd;
                startPos = startPos.add(velocity);
                velocity = VectorUtils.multiplyBy(velocity, airResistance);
                velocity = velocity.add(acceleration);
                Vec3d wrlStart = selfPos.add(startPos);
                RayTraceResult tr = Globals.MC.world.rayTraceBlocks(wrlStart, wrlEnd = selfPos.add(endPos));
                if (tr != null && !Globals.MC.world.getBlockState(tr.getBlockPos()).getBlock().isPassable((IBlockAccess)Globals.MC.world, tr.getBlockPos())) continue block0;
                tr = target.getEntityBoundingBox().calculateIntercept(wrlStart, wrlEnd);
                if (tr != null) {
                    if (result != null) {
                        result.maxForce = force;
                        result.shootAngle = angle;
                    }
                    return true;
                }
                endPos = startPos;
            }
        }
        return false;
    }

    public static class ProjectileTraceResult {
        public double maxForce = 0.0;
        public Angle shootAngle = new Angle();
    }
}

