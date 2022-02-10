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

public class ColorSigns
extends ToggleMod {
    public static final SettingDouble doubleS = SettingUtils.settingDouble("ColorSigns", "Length", "Amount of room to type on a sign while writing", 90.0, 5.0, 90.0, 180.0);

    public ColorSigns() {
        super(Category.SERVER, "ColorSigns", false, -1, "Use color codes on signs.");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isColorSignsActivated = true;
    }

    @Override
    public void onEnabled() {
        HookUtils.isColorSignsActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isColorSignsActivated = false;
    }
}

