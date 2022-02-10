/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;

public class FakeMessageCommand
extends Command {
    public FakeMessageCommand() {
        super("fakemessage");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        }
        String argument = "";
        for (String arg : args) {
            if (arg.equals(args[0])) continue;
            argument = argument + arg + " ";
        }
        Console.writeNoTag(String.format(argument, new Object[0]).replace("&", "\u00a7"));
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "fakemessage";
    }
}

