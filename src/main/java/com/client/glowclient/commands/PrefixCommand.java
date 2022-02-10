/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;

public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super("gcprefix");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        } else {
            Command.prefix.setValue(args[1]);
            Console.write("\u00a7bSet command prefix to: " + Command.prefix.getString());
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "gcprefix";
    }
}

