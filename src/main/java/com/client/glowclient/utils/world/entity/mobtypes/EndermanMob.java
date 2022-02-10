//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityEnderman
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.classes.priority.PriorityEnum;
import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;

public class EndermanMob
extends MobType {
    @Override
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOW;
    }

    @Override
    public boolean isMobType(Entity entity) {
        return entity instanceof EntityEnderman;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        EntityEnderman enderman = (EntityEnderman)entity;
        return enderman.isScreaming() ? MobTypeEnum.HOSTILE : MobTypeEnum.NEUTRAL;
    }
}

