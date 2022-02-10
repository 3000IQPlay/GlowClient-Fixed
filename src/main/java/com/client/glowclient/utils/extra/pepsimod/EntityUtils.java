//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityFlying
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 */
package com.client.glowclient.utils.extra.pepsimod;

import com.client.glowclient.utils.extra.pepsimod.PUtils;
import com.client.glowclient.utils.extra.pepsimod.TargettingTranslator;
import com.client.glowclient.utils.extra.wurst.RotationUtils;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;

public class EntityUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();
    public static final TargetSettings DEFAULT_SETTINGS = new TargetSettings();
    public static final String[] colors = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static boolean isCorrectEntity(Entity en, TargetSettings settings) {
        if (en == null) {
            return false;
        }
        if (en instanceof EntityLivingBase && (((EntityLivingBase)en).isDead || ((EntityLivingBase)en).getHealth() <= 0.0f)) {
            return false;
        }
        if (EntityUtils.MC.player.getDistance(en) > settings.getRange()) {
            return false;
        }
        if (!settings.targetBehindWalls() && !PUtils.canEntityBeSeen(en, (EntityPlayer)EntityUtils.MC.player, settings.getTargetBone())) {
            return false;
        }
        if (en instanceof EntityPlayer) {
            if (!settings.targetPlayers() ? !((EntityPlayer)en).isPlayerSleeping() && !en.isInvisible() : (!settings.targetSleepingPlayers() ? ((EntityPlayer)en).isPlayerSleeping() : !settings.targetInvisiblePlayers() && en.isInvisible())) {
                return false;
            }
            if (settings.targetTeams() && !EntityUtils.checkName(en.getDisplayName().getFormattedText(), settings.getTeamColors())) {
                return false;
            }
            if (en == EntityUtils.MC.player) {
                return false;
            }
            if (en.getName().equals(EntityUtils.MC.player.getName())) {
                return false;
            }
        } else if (en instanceof EntityLiving) {
            if (en.isInvisible()) {
                if (!settings.targetInvisibleMobs()) {
                    return false;
                }
            } else if (en instanceof EntityAgeable || en instanceof EntityAmbientCreature || en instanceof EntityWaterMob) {
                if (!settings.targetAnimals()) {
                    return false;
                }
            } else if (en instanceof EntityMob || en instanceof EntitySlime || en instanceof EntityFlying) {
                if (!settings.targetMonsters()) {
                    return false;
                }
            } else if (en instanceof EntityGolem) {
                if (!settings.targetGolems()) {
                    return false;
                }
            } else {
                return false;
            }
            if (settings.targetTeams() && en.hasCustomName() && !EntityUtils.checkName(en.getCustomNameTag(), settings.getTeamColors())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private static boolean checkName(String name, boolean[] teamColors) {
        boolean hasKnownColor = false;
        for (int i = 0; i < 16; ++i) {
            if (!name.contains("\u00a7" + colors[i])) continue;
            hasKnownColor = true;
            if (!teamColors[i]) continue;
            return true;
        }
        return !hasKnownColor && teamColors[15];
    }

    public static ArrayList<Entity> getValidEntities(TargetSettings settings) {
        ArrayList<Entity> validEntities = new ArrayList<Entity>();
        for (Entity entity : EntityUtils.MC.world.loadedEntityList) {
            if (EntityUtils.isCorrectEntity(entity, settings)) {
                validEntities.add(entity);
            }
            if (validEntities.size() < 64) continue;
            break;
        }
        return validEntities;
    }

    public static Entity getClosestEntity(TargetSettings settings) {
        Entity closestEntity = null;
        for (Entity entity : EntityUtils.MC.world.loadedEntityList) {
            if (!EntityUtils.isCorrectEntity(entity, settings) || closestEntity != null && !(EntityUtils.MC.player.getDistance(entity) < EntityUtils.MC.player.getDistance(closestEntity))) continue;
            closestEntity = entity;
        }
        return closestEntity;
    }

    public static Entity getClosestEntityOtherThan(Entity otherEntity, TargetSettings settings) {
        Entity closestEnemy = null;
        for (Entity entity : EntityUtils.MC.world.loadedEntityList) {
            if (!EntityUtils.isCorrectEntity(entity, settings) || entity == otherEntity || closestEnemy != null && !(EntityUtils.MC.player.getDistance(entity) < EntityUtils.MC.player.getDistance(closestEnemy))) continue;
            closestEnemy = entity;
        }
        return closestEnemy;
    }

    public static Entity getClosestEntityWithName(String name, TargetSettings settings) {
        Entity closestEntity = null;
        for (Entity entity : EntityUtils.MC.world.loadedEntityList) {
            if (!EntityUtils.isCorrectEntity(entity, settings) || !entity.getName().equalsIgnoreCase(name) || closestEntity != null && !(EntityUtils.MC.player.getDistanceSq(entity) < EntityUtils.MC.player.getDistanceSq(closestEntity))) continue;
            closestEntity = entity;
        }
        return closestEntity;
    }

    public static Entity getBestEntityToAttack(TargetSettings settings) {
        Entity bestEntity = null;
        float bestAngle = Float.POSITIVE_INFINITY;
        for (Entity entity : EntityUtils.MC.world.loadedEntityList) {
            float angle;
            if (!EntityUtils.isCorrectEntity(entity, settings) || !((angle = RotationUtils.getAngleToServerRotation(PUtils.adjustVectorForBone(entity.getEntityBoundingBox().getCenter(), entity, settings.getTargetBone()))) < bestAngle)) continue;
            bestEntity = entity;
            bestAngle = angle;
        }
        return bestEntity;
    }

    public static class TargetSettings {
        public static final boolean[] team_colors = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

        public boolean targetFriends() {
            return TargettingTranslator.INSTANCE.friends;
        }

        public boolean targetBehindWalls() {
            return TargettingTranslator.INSTANCE.through_walls;
        }

        public float getRange() {
            return TargettingTranslator.INSTANCE.reach;
        }

        public float getFOV() {
            return TargettingTranslator.INSTANCE.fov;
        }

        public boolean targetPlayers() {
            return TargettingTranslator.INSTANCE.players;
        }

        public boolean targetAnimals() {
            return TargettingTranslator.INSTANCE.animals;
        }

        public boolean targetMonsters() {
            return TargettingTranslator.INSTANCE.monsters;
        }

        public boolean targetGolems() {
            return TargettingTranslator.INSTANCE.golems;
        }

        public boolean targetSleepingPlayers() {
            return TargettingTranslator.INSTANCE.sleeping;
        }

        public boolean targetInvisiblePlayers() {
            return TargettingTranslator.INSTANCE.players && TargettingTranslator.INSTANCE.invisible;
        }

        public boolean targetInvisibleMobs() {
            return TargettingTranslator.INSTANCE.monsters && TargettingTranslator.INSTANCE.invisible;
        }

        public boolean targetTeams() {
            return TargettingTranslator.INSTANCE.teams;
        }

        public boolean[] getTeamColors() {
            return team_colors;
        }

        public TargettingTranslator.TargetBone getTargetBone() {
            return TargettingTranslator.INSTANCE.targetBone;
        }
    }
}

