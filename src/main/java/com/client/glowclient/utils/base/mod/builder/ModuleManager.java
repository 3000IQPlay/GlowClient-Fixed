/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 */
package com.client.glowclient.utils.base.mod.builder;

import com.client.glowclient.utils.base.mod.Module;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private static final Map<String, Module> mods = Maps.newTreeMap((Comparator)String.CASE_INSENSITIVE_ORDER);
    private static final Set<Class<? extends Module>> foundClasses = Sets.newHashSet();

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    public static void registerModule(@Nonnull Module mod) {
        if (!foundClasses.contains(mod.getClass())) {
            foundClasses.add(mod.getClass());
            mods.put(mod.getModName(), mod);
        }
    }

    public static void unregisterMod(@Nonnull Module mod) {
        if (mods.remove(mod.getModName()) != null) {
            mod.unload();
        }
    }

    public static void forEach(Consumer<Module> consumer) {
        mods.forEach((k, v) -> consumer.accept((Module)v));
    }

    @Nullable
    public static Module getModule(Module mod) {
        return mods.get(mod.getModName());
    }

    @Nullable
    public static Module getModuleFromName(String mod) {
        return mods.get(mod);
    }

    public static Collection<Module> getMods() {
        return Collections.unmodifiableCollection(mods.values());
    }
}

