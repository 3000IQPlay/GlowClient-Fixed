/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.base.command.builder;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final CommandManager INSTANCE = new CommandManager();
    private static HashMap<String, Command> commandNames = new HashMap();

    public static CommandManager getInstance() {
        return INSTANCE;
    }

    public static String getSuggestionFor(String input) {
        if (input.length() == 1) {
            return Command.prefix.getString() + CommandManager.commandNames.values().iterator().next().name;
        }
        String[] split = input.split(" ");
        try {
            String commandName = split[0].substring(1);
            Command command = commandNames.get(commandName);
            if (command != null) {
                return command.getSuggestion(input, split);
            }
            for (Map.Entry<String, Command> entry : commandNames.entrySet()) {
                if (!entry.getKey().startsWith(commandName)) continue;
                return Command.prefix.getString() + entry.getKey();
            }
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            // empty catch block
        }
        return "";
    }

    public static void registerCommand(Command command) {
        if (!commandNames.values().contains(command)) {
            for (String s : command.aliases()) {
                commandNames.put(s, command);
            }
        }
    }

    public static void runCommand(String command2) {
        try {
            String command = command2;
            String[] split = command.split(" ");
            String commandName = split[0].substring(1);
            for (Map.Entry<String, Command> entry : commandNames.entrySet()) {
                if (!entry.getKey().equalsIgnoreCase(commandName)) continue;
                entry.getValue().execute(command, split);
                return;
            }
            Console.write("\u00a7cUnknown command \"" + command.replace(Command.prefix.getString(), "") + "\" Use " + Command.prefix.getString() + "help for a list of commands!");
        }
        catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }
}

