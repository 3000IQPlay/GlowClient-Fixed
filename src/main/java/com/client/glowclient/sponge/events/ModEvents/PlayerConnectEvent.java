/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events.ModEvents;

import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.mojang.authlib.GameProfile;
import java.util.Objects;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerConnectEvent
extends Event {
    private final PlayerInfo playerInfo;
    private final GameProfile profile;

    public PlayerConnectEvent(PlayerInfo playerInfo, GameProfile profile) {
        Objects.requireNonNull(profile);
        this.playerInfo = playerInfo;
        this.profile = profile;
    }

    public PlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public static class Leave
    extends PlayerConnectEvent {
        public Leave(PlayerInfo playerInfo, GameProfile profile) {
            super(playerInfo, profile);
        }
    }

    public static class Join
    extends PlayerConnectEvent {
        public Join(PlayerInfo playerInfo, GameProfile profile) {
            super(playerInfo, profile);
        }
    }
}

