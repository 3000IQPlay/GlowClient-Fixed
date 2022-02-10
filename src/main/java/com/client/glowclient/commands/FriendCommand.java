/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.client.FileManager;
import com.client.glowclient.utils.mc.console.Console;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 2) {
            Console.write("\u00a7cNot enough data given");
        }
        if (!FriendManager.getFriends().isFriend(args[1])) {
            if (EnemyManager.getEnemies().isEnemy(args[1])) {
                Console.write(String.format("\u00a7c%s is already an enemy", args[1]));
            } else {
                FriendManager.getFriends().addFriend(args[1]);
                Console.write(String.format("\u00a7bAdded %s to friends list", args[1]));
                FileManager.getInstance().saveFriends();
            }
        } else {
            FriendManager.getFriends().removeFriend(args[1]);
            Console.write(String.format("\u00a7cRemoved %s from friends list", args[1]));
            FileManager.getInstance().saveFriends();
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return "";
    }
}

