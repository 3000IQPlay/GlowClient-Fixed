/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.classes.priority.PriorityEnum;
import com.client.glowclient.utils.world.entity.mobtypes.MobTypeEnum;
import net.minecraft.entity.Entity;

public abstract class MobType
implements Comparable<MobType> {
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOWEST;
    }

    public boolean isNeutralMob(Entity entity) {
        return false;
    }

    public abstract boolean isMobType(Entity var1);

    protected abstract MobTypeEnum getMobTypeUnchecked(Entity var1);

    public MobTypeEnum getMobType(Entity entity) {
        try {
            return this.getMobTypeUnchecked(entity);
        }
        catch (Throwable t) {
            return MobTypeEnum.HOSTILE;
        }
    }

    @Override
    public int compareTo(MobType o) {
        return this.getPriority().compareTo(o.getPriority());
    }
}

