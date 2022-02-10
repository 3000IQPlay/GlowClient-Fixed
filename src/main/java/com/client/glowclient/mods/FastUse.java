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

public class FastUse
extends ToggleMod {
    public FastUse() {
        super(Category.PLAYER, "FastUse", false, -1, "Fast use blocks and items");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        try {
            Globals.MC.rightClickDelayTimer = 0;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

