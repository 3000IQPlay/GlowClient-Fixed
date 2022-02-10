/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.base.command;

import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingString;

public abstract class Command {
    public static SettingString prefix = SettingUtils.settingPrefix("Prefix", "Set the command prefix", ",");
    public String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void execute(String var1, String[] var2);

    public abstract String getSuggestion(String var1, String[] var2);

    public String[] aliases() {
        return new String[]{this.name};
    }
}

