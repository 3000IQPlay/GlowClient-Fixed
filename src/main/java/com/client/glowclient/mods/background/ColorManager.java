/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;

public class ColorManager
extends ToggleMod {
    public static SettingDouble saturation = SettingUtils.settingDouble("Colors", "Saturation", "Saturation of rainbow colors", 255.0, 1.0, 0.0, 255.0);
    public static SettingDouble brightness = SettingUtils.settingDouble("Colors", "Brightness", "Brightness of rainbow colors", 255.0, 1.0, 0.0, 255.0);
    public static SettingMode rendering = SettingUtils.settingMode("Colors", "Rendering", "Rendering rainbow up or down", "Down", "Down", "Up");
    public static SettingDouble rainbowSpeed = SettingUtils.settingDouble("Colors", "RainbowSpeed", "Speed of rainbow's change", 1.0, 0.01, 0.01, 1.0);

    public ColorManager() {
        super(Category.OTHER, "Colors", false, -1, "Manage client colors");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

