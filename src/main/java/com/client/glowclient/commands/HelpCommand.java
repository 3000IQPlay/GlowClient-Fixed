/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.GlowClient;
import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;

public class HelpCommand
extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String cmd, String[] args) {
        String boob = GlowClient.privateVersion ? "\u00a7r\u00a77NameHistory Save GCPrefix YawCoordinate Peek Friend Enemy TP FakeMessage" : "\u00a7r\u00a77NameHistory Save GCPrefix YawCoordinate Peek Friend Enemy TP";
        Console.write("\u00a7lCommands: " + boob + "\n\u00a7r\u00a77Open GUI with RSHIFT");
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "help";
    }
}

