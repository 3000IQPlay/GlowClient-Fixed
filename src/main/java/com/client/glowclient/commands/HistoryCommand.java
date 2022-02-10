/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.FutureCallback
 *  javax.annotation.Nullable
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfoHelper;
import com.google.common.util.concurrent.FutureCallback;
import javax.annotation.Nullable;

public class HistoryCommand
extends Command {
    public HistoryCommand() {
        super("namehistory");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        }
        final int indents = Console.getIndents();
        PlayerInfoHelper.invokeEfficiently(args[1], new FutureCallback<PlayerInfo>(){

            public void onSuccess(@Nullable PlayerInfo result) {
                if (result == null) {
                    return;
                }
                int previousIndents = Console.getIndents();
                Console.setIndents(indents);
                if (result.isOfflinePlayer()) {
                    Console.write(String.format("\u00a7c\"%s\" is not a valid username", result.getName()));
                } else if (result.getNameHistory().size() > 1) {
                    Console.write(String.format("\u00a7b%s's name history: \u00a7o%s", result.getName(), result.getNameHistoryAsString()));
                } else {
                    Console.write(String.format("%s has no name history", result.getName()));
                }
                Console.setIndents(previousIndents);
            }

            public void onFailure(Throwable t) {
            }
        });
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "namehistory";
    }
}

