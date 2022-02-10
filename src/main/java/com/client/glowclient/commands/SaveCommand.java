/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;

public class SaveCommand
extends Command {
    public SaveCommand() {
        super("save");
    }

    @Override
    public void execute(String cmd, String[] args) {
        Utils.getFileManager().saveAll();
        Console.write("\u00a7bConfig Saved");
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "save";
    }
}

