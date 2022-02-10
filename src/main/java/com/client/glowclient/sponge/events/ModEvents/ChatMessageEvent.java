/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  javax.annotation.Nullable
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events.ModEvents;

import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.google.common.base.Strings;
import javax.annotation.Nullable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChatMessageEvent
extends Event {
    private final PlayerInfo sender;
    private final String message;
    private final PlayerInfo receiver;

    public ChatMessageEvent(PlayerInfo sender, String message, PlayerInfo receiver) {
        this.sender = sender;
        this.message = Strings.nullToEmpty((String)message);
        this.receiver = receiver;
    }

    public static ChatMessageEvent newPublicChat(PlayerInfo playerInfo, String message) {
        return new ChatMessageEvent(playerInfo, message, null);
    }

    public static ChatMessageEvent newPrivateChat(PlayerInfo sender, PlayerInfo receiver, String message) {
        return new ChatMessageEvent(sender, message, receiver);
    }

    public PlayerInfo getSender() {
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

    @Nullable
    public PlayerInfo getReceiver() {
        return this.receiver;
    }

    public boolean isWhispering() {
        return this.receiver != null;
    }
}

