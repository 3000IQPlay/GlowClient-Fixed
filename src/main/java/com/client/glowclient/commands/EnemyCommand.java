/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.client.FileManager;
import com.client.glowclient.utils.mc.console.Console;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;

public class EnemyCommand
extends Command {
    public EnemyCommand() {
        super("enemy");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        }
        if (!EnemyManager.getEnemies().isEnemy(args[1])) {
            if (FriendManager.getFriends().isFriend(args[1])) {
                Console.write(String.format("\u00a7c%s is already a friend", args[1]));
            } else {
                EnemyManager.getEnemies().addEnemy(args[1]);
                Console.write(String.format("\u00a7bAdded %s to enemy list", args[1]));
                FileManager.getInstance().saveEnemies();
            }
        } else {
            EnemyManager.getEnemies().removeEnemy(args[1]);
            Console.write(String.format("\u00a7cRemoved %s from enemy list", args[1]));
            FileManager.getInstance().saveEnemies();
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "enemy";
    }
}

