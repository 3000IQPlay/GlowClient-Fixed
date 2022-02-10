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
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Reach
extends ToggleMod {
    public static final SettingDouble distance = SettingUtils.settingDouble("Reach", "Distance", "How far you can interact with things", 4.5, 0.5, 4.5, 10.0);

    public Reach() {
        super(Category.PLAYER, "Reach", false, -1, "Changes reach distance of player");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.1f", distance.getDouble());
    }

    @Override
    public void onEnabled() {
        HookUtils.isExtendedReachActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isExtendedReachActivated = false;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isExtendedReachActivated = true;
    }
}

