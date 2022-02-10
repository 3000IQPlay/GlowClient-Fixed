//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.base.Throwables
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.NetworkManager
 *  net.minecraftforge.fml.client.FMLClientHandler
 */
package com.client.glowclient.utils;

import com.client.glowclient.utils.client.FileManager;
import com.client.glowclient.utils.client.Globals;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.client.FMLClientHandler;

public class Utils {
    public static FileManager getFileManager() {
        return FileManager.getInstance();
    }

    public static Entity getRidingEntity() {
        if (Globals.MC.player != null) {
            return Globals.MC.player.getRidingEntity();
        }
        return null;
    }

    public static NetworkManager getNetworkManager() {
        return FMLClientHandler.instance().getClientToServerNetworkManager();
    }

    public static void printStackTrace(Throwable t) {
        Globals.LOGGER.error(Throwables.getStackTraceAsString((Throwable)t));
    }

    public static void handleThrowable(Throwable t) {
        Globals.LOGGER.error(String.format("[%s] %s", t.getClass().getSimpleName(), Strings.nullToEmpty((String)t.getMessage())));
        if (t.getCause() != null) {
            Utils.handleThrowable(t.getCause());
        }
        Utils.printStackTrace(t);
    }
}

