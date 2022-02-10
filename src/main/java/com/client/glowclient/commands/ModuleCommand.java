/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.setting.Setting;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import com.client.glowclient.utils.base.setting.builder.SettingManager;
import com.client.glowclient.utils.classes.conversion.StringConversionUtils;
import com.client.glowclient.utils.mc.console.Console;

public class ModuleCommand
extends Command {
    private Module module;

    public ModuleCommand(Module mod) {
        super(mod.getModName());
        this.module = mod;
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 3) {
            Console.write("\u00a7cNot enough data given");
        }
        for (Setting setting : SettingManager.getSettings()) {
            if (setting instanceof SettingBoolean && setting.getModName().equals(this.module.getModName()) && setting.getName().equalsIgnoreCase(args[1])) {
                setting.setValue(Boolean.parseBoolean(args[2]));
                Console.write("\u00a7bSet setting: " + setting.getName() + " from module: " + this.module.getModName() + " to " + args[2]);
            }
            if (setting instanceof SettingDouble && setting.getModName().equals(this.module.getModName()) && setting.getName().equalsIgnoreCase(args[1]) && StringConversionUtils.isStringDouble(args[2])) {
                setting.setValue(Double.parseDouble(args[2]));
                Console.write("\u00a7bSet setting: " + setting.getName() + " from module: " + this.module.getModName() + " to " + args[2]);
            }
            if (setting instanceof SettingString && setting.getModName().equals(this.module.getModName()) && setting.getName().equalsIgnoreCase(args[1])) {
                setting.setValue(args[2]);
                Console.write("\u00a7bSet setting: " + setting.getName() + " from module: " + this.module.getModName() + " to " + args[2]);
            }
            if (!(setting instanceof SettingMode) || !setting.getModName().equals(this.module.getModName()) || !setting.getName().equalsIgnoreCase(args[1])) continue;
            for (String mode : ((SettingMode)setting).getModes()) {
                if (!mode.equalsIgnoreCase(args[2])) continue;
                setting.setValue(mode);
                Console.write("\u00a7bSet setting: " + setting.getName() + " from module: " + this.module.getModName() + " to " + mode);
            }
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        switch (args.length) {
            case 2: {
                for (Setting setting : SettingManager.getSettings()) {
                    if (!(setting instanceof SettingBoolean) && !(setting instanceof SettingDouble) && !(setting instanceof SettingString) && !(setting instanceof SettingMode) || !setting.getModName().equals(this.module.getModName()) || !setting.getName().startsWith(args[1])) continue;
                    return Command.prefix.getString() + this.module.getModName() + " " + setting.getName();
                }
                return "";
            }
            case 3: {
                for (Setting setting : SettingManager.getSettings()) {
                    if (!(setting instanceof SettingMode)) continue;
                    for (String mode : ((SettingMode)setting).getModes()) {
                        if (!setting.getModName().equals(args[0].replace(Command.prefix.getString(), "")) || !setting.getName().equals(args[1]) || !mode.startsWith(args[2])) continue;
                        return Command.prefix.getString() + this.module.getModName() + " " + setting.getName() + " " + mode;
                    }
                }
                return "";
            }
        }
        return Command.prefix.getString() + this.module.getModName();
    }
}

