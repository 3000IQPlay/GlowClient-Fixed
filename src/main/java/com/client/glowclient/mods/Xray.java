/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.world.WorldUtils;

public class Xray
extends ToggleMod {
    public static final SettingDouble opacity = SettingUtils.settingDouble("Xray", "Opacity", "Block transparency", 75.0, 1.0, 0.0, 255.0);
    private boolean previousForgeLightPipelineEnabled = false;

    public Xray() {
        super(Category.RENDER, "Xray", false, -1, "Adds transparency to blocks");
    }

    @Override
    public void onEnabled() {
        HookUtils.isXrayActivated = true;
        WorldUtils.reloadChunks();
    }

    @Override
    public void onDisabled() {
        HookUtils.isXrayActivated = false;
        WorldUtils.reloadChunks();
    }
}

