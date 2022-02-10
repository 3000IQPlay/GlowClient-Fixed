/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  net.minecraft.network.Packet
 */
package com.client.glowclient.utils.mc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import net.minecraft.network.Packet;

public class PacketHelper {
    private static final LoadingCache<Packet, Boolean> CACHE = CacheBuilder.newBuilder().expireAfterWrite(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader<Packet, Boolean>(){

        public Boolean load(Packet key) throws Exception {
            return false;
        }
    });

    public static void ignore(Packet packet) {
        CACHE.put(packet, true);
    }

    public static boolean isIgnored(Packet packet) {
        try {
            return (Boolean)CACHE.get(packet);
        }
        catch (ExecutionException e) {
            return false;
        }
    }

    public static void remove(Packet packet) {
        CACHE.invalidate(packet);
    }
}

