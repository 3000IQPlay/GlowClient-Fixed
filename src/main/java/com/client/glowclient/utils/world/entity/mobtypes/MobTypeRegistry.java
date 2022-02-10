/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package com.client.glowclient.utils.world.entity.mobtypes;

import com.client.glowclient.utils.world.entity.mobtypes.EndermanMob;
import com.client.glowclient.utils.world.entity.mobtypes.FriendlyMob;
import com.client.glowclient.utils.world.entity.mobtypes.HostileMob;
import com.client.glowclient.utils.world.entity.mobtypes.MobType;
import com.client.glowclient.utils.world.entity.mobtypes.PigZombieMob;
import com.client.glowclient.utils.world.entity.mobtypes.WolfMob;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

public class MobTypeRegistry {
    public static final MobType HOSTILE = new HostileMob();
    public static final MobType FRIENDLY = new FriendlyMob();
    private static final List<MobType> MOB_TYPES_SPECIAL = Lists.newCopyOnWriteArrayList();

    public static void register(MobType type) {
        MOB_TYPES_SPECIAL.add(type);
        Collections.sort(MOB_TYPES_SPECIAL);
    }

    public static void unregister(MobType type) {
        MOB_TYPES_SPECIAL.remove(type);
        Collections.sort(MOB_TYPES_SPECIAL);
    }

    public static List<MobType> getSortedSpecialMobTypes() {
        return MOB_TYPES_SPECIAL;
    }

    static {
        MobTypeRegistry.register(new EndermanMob());
        MobTypeRegistry.register(new PigZombieMob());
        MobTypeRegistry.register(new WolfMob());
    }
}

