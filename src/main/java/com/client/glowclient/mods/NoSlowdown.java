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

public class NoSlowdown
extends ToggleMod {
    public NoSlowdown() {
        super(Category.MOVEMENT, "NoSlow", false, -1, "Stops slowing from items");
    }

    @Override
    public void onEnabled() {
        HookUtils.isNoSlowActivated = true;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isNoSlowActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isNoSlowActivated = false;
    }
}

