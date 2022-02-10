/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.base.mod.branches;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.setting.SettingUtils;

public class ServiceMod
extends Module {
    public ServiceMod(String name, String desc) {
        super(Category.SERVICE, name, desc);
    }

    public ServiceMod(String name, String desc, int defaultKeybind) {
        super(Category.SERVICE, name, desc);
        this.registerKeybind(name, defaultKeybind);
        this.key = SettingUtils.settingKeybind(name, "Keybind", "Keybind of the mod", defaultKeybind);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

