/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.base.mod.branches;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.setting.SettingUtils;

public class ToggleMod
extends Module {
    public ToggleMod(Category category, String modName, boolean defaultValue, int defaultKeybind, String description) {
        super(category, modName, description);
        this.toggled = SettingUtils.settingToggled(modName, "Toggled", "Toggles the mod", defaultValue);
        this.key = SettingUtils.settingKeybind(modName, "Keybind", "Keybind of the mod", defaultKeybind);
    }

    public final void toggle() {
        if (this.isEnabled()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    @Override
    public void enable() {
        this.toggled.setValue(true);
    }

    @Override
    public void disable() {
        this.toggled.setValue(false);
    }

    @Override
    public boolean isDrawn() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.toggled.getBoolean();
    }
}

