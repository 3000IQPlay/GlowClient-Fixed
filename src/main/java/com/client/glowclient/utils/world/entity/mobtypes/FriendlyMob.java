/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.passive.EntityVillager
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;

public class FriendlyMob
extends MobType {
    @Override
    public boolean isMobType(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.CREATURE, false) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false) || entity instanceof EntityVillager || entity instanceof EntityGolem;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        return MobTypeEnum.FRIENDLY;
    }
}

