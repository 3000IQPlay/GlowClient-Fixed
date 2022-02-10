//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package com.client.glowclient.utils.world.entity;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeRegistry;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityUtils {
    public static boolean isBatsDisabled = false;

    public static MobTypeEnum getRelationship(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            return MobTypeEnum.PLAYER;
        }
        for (MobType type : MobTypeRegistry.getSortedSpecialMobTypes()) {
            if (!type.isMobType(entity)) continue;
            return type.getMobType(entity);
        }
        if (MobTypeRegistry.HOSTILE.isMobType(entity)) {
            return MobTypeEnum.HOSTILE;
        }
        if (MobTypeRegistry.FRIENDLY.isMobType(entity)) {
            return MobTypeEnum.FRIENDLY;
        }
        return MobTypeEnum.HOSTILE;
    }

    public static boolean isMobAggressive(Entity entity) {
        try {
            if (entity instanceof EntityPigZombie) {
                if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry()) {
                    if (!((EntityPigZombie)entity).isAngry()) {
                        ((EntityPigZombie)entity).angerLevel = 400;
                    }
                    return true;
                }
            } else {
                if (entity instanceof EntityWolf) {
                    return ((EntityWolf)entity).isAngry() && !Globals.MC.player.equals((Object)((EntityWolf)entity).getOwner());
                }
                if (entity instanceof EntityEnderman) {
                    return ((EntityEnderman)entity).isScreaming();
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static boolean isPlayer(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public static boolean isLocalPlayer(Entity entity) {
        return Objects.equals((Object)Globals.MC.player, (Object)entity);
    }

    public static boolean isFakeLocalPlayer(Entity entity) {
        return entity != null && entity.getEntityId() == -100;
    }

    public static boolean isValidEntity(Entity entity) {
        Entity riding = Globals.MC.player.getRidingEntity();
        return entity.ticksExisted > 1 && !EntityUtils.isFakeLocalPlayer(entity) && (riding == null || !riding.equals((Object)entity));
    }

    public static boolean isAlive(Entity entity) {
        return EntityUtils.isLiving(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public static boolean isNeutralMob(Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }

    public static boolean isFriendlyMob(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.CREATURE, false) && !EntityUtils.isNeutralMob(entity) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) && !isBatsDisabled || entity instanceof EntityVillager || entity instanceof EntityIronGolem || EntityUtils.isNeutralMob(entity) && !EntityUtils.isMobAggressive(entity);
    }

    public static boolean isHostileMob(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtils.isNeutralMob(entity) || EntityUtils.isMobAggressive(entity);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
        return EntityUtils.getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return EntityUtils.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(EntityUtils.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedEyePos(Entity entity, float ticks) {
        return EntityUtils.getInterpolatedPos(entity, ticks).add(0.0, (double)entity.getEyeHeight(), 0.0);
    }

    public static Vec3d getEyePos(Entity entity) {
        return new Vec3d(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ);
    }

    public static Vec3d getOBBCenter(Entity entity) {
        AxisAlignedBB obb = entity.getEntityBoundingBox();
        return new Vec3d((obb.maxX + obb.minX) / 2.0, (obb.maxY + obb.minY) / 2.0, (obb.maxZ + obb.minZ) / 2.0);
    }

    public static RayTraceResult traceEntity(World world, Vec3d start, Vec3d end, List<Entity> filter) {
        RayTraceResult result = null;
        double hitDistance = -1.0;
        for (Entity ent : world.loadedEntityList) {
            if (filter.contains((Object)ent)) continue;
            double distance = start.distanceTo(ent.getPositionVector());
            RayTraceResult trace = ent.getEntityBoundingBox().calculateIntercept(start, end);
            if (trace == null || hitDistance != -1.0 && !(distance < hitDistance)) continue;
            hitDistance = distance;
            result = trace;
            result.entityHit = ent;
        }
        return result;
    }

    public static boolean isDrivenByPlayer(Entity entityIn) {
        return Globals.MC.player != null && entityIn != null && entityIn == Utils.getRidingEntity();
    }

    public static boolean isAboveWater(Entity entity) {
        return EntityUtils.isAboveWater(entity, false);
    }

    public static boolean isAboveWater(Entity entity, boolean packet) {
        if (entity == null) {
            return false;
        }
        double y = entity.posY - (packet ? 0.03 : (EntityUtils.isPlayer(entity) ? 0.2 : 0.5));
        for (int x = MathHelper.floor((double)entity.posX); x < MathHelper.ceil((double)entity.posX); ++x) {
            for (int z = MathHelper.floor((double)entity.posZ); z < MathHelper.ceil((double)entity.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor((double)y), z);
                if (!(Globals.MC.world.getBlockState(pos).getBlock() instanceof BlockLiquid)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isInWater(Entity entity) {
        if (entity == null) {
            return false;
        }
        double y = entity.posY + 0.01;
        for (int x = MathHelper.floor((double)entity.posX); x < MathHelper.ceil((double)entity.posX); ++x) {
            for (int z = MathHelper.floor((double)entity.posZ); z < MathHelper.ceil((double)entity.posZ); ++z) {
                BlockPos pos = new BlockPos(x, (int)y, z);
                if (!(Globals.MC.world.getBlockState(pos).getBlock() instanceof BlockLiquid)) continue;
                return true;
            }
        }
        return false;
    }
}

