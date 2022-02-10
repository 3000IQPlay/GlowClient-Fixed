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

public class BackSpeed
extends ToggleMod {
    public BackSpeed() {
        super(Category.MOVEMENT, "BackSpeed", false, -1, "Go sprint speed backwards");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (!Globals.MC.player.isElytraFlying() && Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
            double maxSpeed;
            double currentSpeed;
            if (Globals.MC.player.moveForward > 0.0f && !Globals.MC.player.collidedHorizontally) {
                Globals.MC.player.setSprinting(true);
            }
            if (Globals.MC.player.onGround) {
                Globals.MC.player.motionX *= 1.192;
                Globals.MC.player.motionZ *= 1.192;
            }
            if ((currentSpeed = Math.sqrt(Math.pow(Globals.MC.player.motionX, 2.0) + Math.pow(Globals.MC.player.motionZ, 2.0))) > (maxSpeed = (double)0.66f)) {
                Globals.MC.player.motionX = Globals.MC.player.motionX / currentSpeed * maxSpeed;
                Globals.MC.player.motionZ = Globals.MC.player.motionZ / currentSpeed * maxSpeed;
            }
        }
    }
}

