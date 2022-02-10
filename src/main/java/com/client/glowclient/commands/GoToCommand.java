/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;

public class GoToCommand
extends Command {
    public GoToCommand() {
        super("goto");
    }

    @Override
    public void execute(String cmd, String[] args) {
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "gcprefix";
    }
}

