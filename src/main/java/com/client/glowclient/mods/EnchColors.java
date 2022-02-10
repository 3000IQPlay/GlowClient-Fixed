/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;

public class EnchColors
extends ToggleMod {
    public static SettingDouble red = SettingUtils.settingDouble("EnchColors", "Red", "Red Color", 100.0, 1.0, 0.0, 255.0);
    public static SettingDouble green = SettingUtils.settingDouble("EnchColors", "Green", "Green Color", 255.0, 1.0, 0.0, 255.0);
    public static SettingDouble blue = SettingUtils.settingDouble("EnchColors", "Blue", "Blue Color", 175.0, 1.0, 0.0, 255.0);
    public static SettingBoolean rainbow = SettingUtils.settingBoolean("EnchColors", "Rainbow", "Rainbow Enchants", false);

    public EnchColors() {
        super(Category.RENDER, "EnchColors", false, -1, "Change colors of enchanted items");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }
}

