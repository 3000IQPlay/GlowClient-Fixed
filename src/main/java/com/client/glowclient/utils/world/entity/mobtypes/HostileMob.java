/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;

public class HostileMob
extends MobType {
    @Override
    public boolean isMobType(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        return MobTypeEnum.HOSTILE;
    }
}

