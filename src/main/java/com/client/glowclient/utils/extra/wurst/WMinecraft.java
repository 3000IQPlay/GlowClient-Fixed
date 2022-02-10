//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 */
package com.client.glowclient.utils.extra.wurst;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;

public final class WMinecraft {
    public static final String VERSION = "1.12";
    public static final String DISPLAY_VERSION = "1.12";
    public static final boolean OPTIFINE = true;
    public static final boolean REALMS = false;
    public static final boolean COOLDOWN = true;
    public static final NavigableMap<Integer, String> PROTOCOLS;
    public static final Minecraft WMC;

    public static EntityPlayerSP getPlayer() {
        return WMinecraft.WMC.player;
    }

    public static WorldClient getWorld() {
        return WMinecraft.WMC.world;
    }

    public static NetHandlerPlayClient getConnection() {
        return WMinecraft.getPlayer().connection;
    }

    public static boolean isRunningOnMac() {
        return Minecraft.IS_RUNNING_ON_MAC;
    }

    static {
        TreeMap<Integer, String> protocols = new TreeMap<Integer, String>();
        protocols.put(335, "1.12");
        PROTOCOLS = Collections.unmodifiableNavigableMap(protocols);
        WMC = Minecraft.getMinecraft();
    }
}

