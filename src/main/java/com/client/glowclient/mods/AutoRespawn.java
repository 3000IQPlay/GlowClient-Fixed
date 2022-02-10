//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRespawn
extends ToggleMod {
    public AutoRespawn() {
        super(Category.PLAYER, "AutoRespawn", false, -1, "Auto respawn on death");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (Globals.MC.player.getHealth() <= 0.0f) {
            Globals.MC.player.respawnPlayer();
        }
    }
}

