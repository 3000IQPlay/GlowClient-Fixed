/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SafeWalk
extends ToggleMod {
    public SafeWalk() {
        super(Category.MOVEMENT, "SafeWalk", false, -1, "Shift without shifting");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        HookUtils.isSafewalkActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isSafewalkActivated = false;
    }
}

