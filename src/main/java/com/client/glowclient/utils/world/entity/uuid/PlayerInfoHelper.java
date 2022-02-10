/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.util.concurrent.FutureCallback
 *  com.google.common.util.concurrent.Futures
 *  com.google.common.util.concurrent.ListenableFuture
 *  com.google.common.util.concurrent.ListeningExecutorService
 *  com.google.common.util.concurrent.MoreExecutors
 */
package com.client.glowclient.utils.world.entity.uuid;

import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PlayerInfoHelper {
    private static final int THREAD_COUNT = 2;
    public static final int MAX_NAME_LENGTH = 16;
    private static final ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator((ExecutorService)Executors.newFixedThreadPool(Math.max(2, 1)));
    private static final Map<String, PlayerInfo> NAME_TO_INFO = Maps.newConcurrentMap();
    private static final Map<UUID, PlayerInfo> UUID_TO_INFO = Maps.newConcurrentMap();

    private static PlayerInfo register(String name) {
        if (name.length() > 16) {
            return null;
        }
        PlayerInfo info = new PlayerInfo(name);
        NAME_TO_INFO.put(info.getName().toLowerCase(), info);
        UUID_TO_INFO.put(info.getId(), info);
        return info;
    }

    public static PlayerInfo register(UUID uuid) {
        PlayerInfo info = new PlayerInfo(uuid);
        NAME_TO_INFO.put(info.getName().toLowerCase(), info);
        UUID_TO_INFO.put(info.getId(), info);
        return info;
    }

    public static PlayerInfo get(String name) {
        return NAME_TO_INFO.get(name.toLowerCase());
    }

    public static PlayerInfo get(UUID uuid) {
        return UUID_TO_INFO.get(uuid);
    }

    public static PlayerInfo lookup(String name) {
        PlayerInfo info = PlayerInfoHelper.get(name);
        if (info == null) {
            return PlayerInfoHelper.register(name);
        }
        return info;
    }

    public static PlayerInfo lookup(UUID uuid) {
        PlayerInfo info = PlayerInfoHelper.get(uuid);
        if (info == null) {
            return PlayerInfoHelper.register(uuid);
        }
        return info;
    }

    public static boolean invokeEfficiently(String name, FutureCallback<PlayerInfo> callback) {
        boolean threaded;
        ListenableFuture future;
        PlayerInfo info = PlayerInfoHelper.get(name);
        if (info == null) {
            future = EXECUTOR_SERVICE.submit(() -> PlayerInfoHelper.register(name));
            threaded = true;
        } else {
            future = Futures.immediateFuture((Object)info);
            threaded = false;
        }
        Futures.addCallback((ListenableFuture)future, callback);
        return threaded;
    }

    public static boolean invokeEfficiently(UUID uuid, FutureCallback<PlayerInfo> callback) {
        boolean threaded;
        ListenableFuture future;
        PlayerInfo info = PlayerInfoHelper.get(uuid);
        if (info == null) {
            future = EXECUTOR_SERVICE.submit(() -> PlayerInfoHelper.register(uuid));
            threaded = true;
        } else {
            future = Futures.immediateFuture((Object)info);
            threaded = false;
        }
        Futures.addCallback((ListenableFuture)future, callback);
        return threaded;
    }

    public static UUID getIdFromString(String uuid) {
        if (uuid.contains("-")) {
            return UUID.fromString(uuid);
        }
        return UUID.fromString(uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

    public static String getIdNoHyphens(UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EXECUTOR_SERVICE.shutdown();
            while (!EXECUTOR_SERVICE.isShutdown()) {
                try {
                    EXECUTOR_SERVICE.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                }
                catch (InterruptedException interruptedException) {}
            }
        }));
    }
}

