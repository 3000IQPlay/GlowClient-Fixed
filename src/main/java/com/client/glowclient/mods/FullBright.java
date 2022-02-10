//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright
extends ToggleMod {
    public FullBright() {
        super(Category.RENDER, "FullBright", false, -1, "Sets gamma to max");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @Override
    public void onEnabled() {
        Globals.MC.gameSettings.gammaSetting = 16.0f;
    }

    @Override
    public void onDisabled() {
        Globals.MC.gameSettings.gammaSetting = 1.0f;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Globals.MC.gameSettings.gammaSetting = 16.0f;
    }
}

