//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Flight
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("Flight", "Speed", "Flight Speed", 2.5, 0.5, 1.0, 10.0);
    public static SettingMode mode = SettingUtils.settingMode("Flight", "Mode", "Mode of Flight", "Normal", "Normal");
    private int flightSet = 0;

    public Flight() {
        super(Category.MOVEMENT, "Flight", false, -1, "Fly");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.2f", speed.getDouble());
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Normal")) {
            double speed2 = speed.getDouble() / 10.0;
            MovementInput movementInput = Globals.MC.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = Globals.MC.player.rotationYaw;
            double move = speed2;
            if (!Globals.MC.gameSettings.keyBindJump.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
                Globals.MC.player.motionY = 0.0;
            }
            if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                Globals.MC.player.motionY = 0.5 + speed2;
            }
            if (Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
                Globals.MC.player.motionY = -(0.5 + speed2);
            }
            if (Globals.MC.gameSettings.keyBindJump.isKeyDown() && Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
                Globals.MC.player.motionY = 0.0;
            }
            if (forward == 0.0 && strafe == 0.0) {
                Globals.MC.player.motionX = 0.0;
                Globals.MC.player.motionZ = 0.0;
            } else if (!Globals.MC.player.onGround) {
                Globals.MC.player.motionX = forward * move * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * move * Math.sin(Math.toRadians(yaw + 90.0f));
                Globals.MC.player.motionZ = forward * move * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * move * Math.cos(Math.toRadians(yaw + 90.0f));
            }
            Globals.MC.player.onGround = false;
            Globals.MC.player.fallDistance = 0.0f;
        }
        if (mode.getMode().equals("Flat")) {
            Globals.MC.player.motionY = 0.0;
        }
    }
}

