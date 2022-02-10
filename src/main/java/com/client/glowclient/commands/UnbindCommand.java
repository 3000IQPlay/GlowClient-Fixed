/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.mc.console.Console;

public class UnbindCommand
extends Command {
    public UnbindCommand() {
        super("unbind");
    }

    @Override
    public void execute(String cmd, String[] args) {
        Module module;
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        }
        if ((module = ModuleManager.getModuleFromName(args[1])) == null) {
            Console.write("\u00a7cCould not find module: " + args[1]);
        } else if (!(ModuleManager.getModuleFromName(args[1]) instanceof ServiceMod)) {
            module.setBind(-1);
            Console.write("\u00a7bUnbound " + module.getModName());
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        switch (args.length) {
            case 2: {
                for (Module module : ModuleManager.getMods()) {
                    if (module instanceof ServiceMod || !module.getModName().startsWith(args[1])) continue;
                    return Command.prefix.getString() + "unbind " + module.getModName();
                }
                return "";
            }
        }
        return Command.prefix.getString() + "unbind";
    }
}

