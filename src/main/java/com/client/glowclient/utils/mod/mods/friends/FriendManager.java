//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.StringUtils
 */
package com.client.glowclient.utils.mod.mods.friends;

import com.client.glowclient.utils.mod.mods.friends.Friend;
import java.util.ArrayList;
import net.minecraft.util.StringUtils;

public class FriendManager {
    public static FriendManager friendManager;
    public static ArrayList<Friend> friendsList;

    public static FriendManager getFriends() {
        if (friendManager == null) {
            friendManager = new FriendManager();
        }
        return friendManager;
    }

    public void addFriend(String name) {
        friendsList.add(new Friend(name));
    }

    public void removeFriend(String name) {
        for (Friend friend : friendsList) {
            if (!friend.getName().equalsIgnoreCase(name)) continue;
            friendsList.remove(friend);
            break;
        }
    }

    public boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : friendsList) {
            if (!friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes((String)name))) continue;
            isFriend = true;
            break;
        }
        return isFriend;
    }

    static {
        friendsList = new ArrayList();
    }
}

