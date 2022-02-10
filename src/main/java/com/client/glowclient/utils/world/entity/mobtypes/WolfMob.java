//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.EntityWolf
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.classes.priority.PriorityEnum;
import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;

public class WolfMob
extends MobType {
    @Override
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOW;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        EntityWolf wolf = (EntityWolf)entity;
        return wolf.isAngry() ? MobTypeEnum.HOSTILE : MobTypeEnum.NEUTRAL;
    }

    @Override
    public boolean isMobType(Entity entity) {
        return entity instanceof EntityWolf;
    }
}

