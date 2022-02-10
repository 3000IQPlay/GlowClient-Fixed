/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.mods.Yaw;
import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;

public class YawCoordinateCommand
extends Command {
    public YawCoordinateCommand() {
        super("yawcoordinate");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 3) {
            Console.write("\u00a7cNot enough data given");
        }
        String x = args[1];
        String z = args[2];
        Yaw.x = Integer.parseInt(x);
        Yaw.z = Integer.parseInt(z);
        Console.write("\u00a7bSet YawCoordinate for Yaw to " + x + ", " + z);
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "yawcoordinate";
    }
}

