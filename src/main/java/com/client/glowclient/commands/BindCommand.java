/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.classes.conversion.StringConversionUtils;
import com.client.glowclient.utils.mc.console.Console;

public class BindCommand
extends Command {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void execute(String cmd, String[] args) {
        Module module;
        if (args.length < 3) {
            Console.write("\u00a7cNot enough data given");
        }
        if ((module = ModuleManager.getModuleFromName(args[1])) == null) {
            Console.write("\u00a7cCould not find module: " + args[1]);
        } else if (!(ModuleManager.getModuleFromName(args[1]) instanceof ServiceMod) && StringConversionUtils.isStringInteger(args[2])) {
            module.key.setValue(Integer.parseInt(args[2]));
            Console.write("\u00a7bBound " + module.getModName() + " to key: " + args[2]);
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        switch (args.length) {
            case 2: {
                for (Module module : ModuleManager.getMods()) {
                    if (!(module instanceof ToggleMod) || !module.getModName().startsWith(args[1])) continue;
                    return Command.prefix.getString() + "bind " + module.getModName();
                }
                return Command.prefix.getString() + "bind";
            }
        }
        return Command.prefix.getString() + "bind";
    }
}

