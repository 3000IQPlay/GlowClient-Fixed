//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Phase
extends ToggleMod {
    SimpleTimer timer = new SimpleTimer();

    public Phase() {
        super(Category.MOVEMENT, "Phase", false, -1, "Very Shit PhaseMod");
    }

    @Override
    public void onDisabled() {
        Globals.MC.player.noClip = false;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (!this.timer.isStarted()) {
            this.timer.start();
        }
        MovementInput movementInput = Globals.MC.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = Globals.MC.player.rotationYaw;
        double move = 1.0;
        if (this.timer.hasTimeElapsed(1.0)) {
            Globals.MC.player.posY += 0.05;
            move = 0.5;
        }
        if (this.timer.hasTimeElapsed(150.0)) {
            move = -0.1;
        }
        if (this.timer.hasTimeElapsed(200.0)) {
            Globals.MC.player.posY -= 0.05;
            this.timer.start();
        }
        if (Globals.MC.gameSettings.keyBindSneak.isKeyDown() && Globals.MC.player.collidedHorizontally) {
            Globals.MC.player.motionY = 0.0;
            Globals.MC.player.noClip = true;
            if (forward == 0.0 && strafe == 0.0) {
                Globals.MC.player.motionX = 0.0;
                Globals.MC.player.motionZ = 0.0;
            } else {
                Globals.MC.player.motionX = forward * move * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * move * Math.sin(Math.toRadians(yaw + 90.0f));
                Globals.MC.player.motionZ = forward * move * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * move * Math.cos(Math.toRadians(yaw + 90.0f));
            }
        } else {
            Globals.MC.player.noClip = false;
        }
    }
}

