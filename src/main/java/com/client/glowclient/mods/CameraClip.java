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

public class CameraClip
extends ToggleMod {
    public static final SettingDouble doubleS = SettingUtils.settingDouble("CameraClip", "Zoom", "Zoom Distance", 4.0, 0.5, 3.0, 20.0);

    public CameraClip() {
        super(Category.RENDER, "CameraClip", false, -1, "Blocks no longer block camera");
    }

    @Override
    public String getHUDTag() {
        return "";
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isCameraClipActivated = true;
    }

    @Override
    public void onEnabled() {
        HookUtils.isCameraClipActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isCameraClipActivated = false;
    }
}

