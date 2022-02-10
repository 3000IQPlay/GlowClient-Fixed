//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.RenderBoatEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpeed
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("EntitySpeed", "Speed", "Speed of Entity", 3.7, 0.1, 0.0, 3.8);

    public EntitySpeed() {
        super(Category.MOVEMENT, "EntitySpeed", false, -1, "Go faster on rideable entities.");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.1f", speed.getDouble());
    }

    @SubscribeEvent
    public void onClientTick(PlayerUpdateEvent event) {
        HookUtils.isBoatStrafingActivated = true;
        if (Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            EntitySpeed.setMoveSpeedEntity(speed.getDouble());
        }
    }

    public static void setMoveSpeedEntity(double speed) {
        if (Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            MovementInput movementInput = Globals.MC.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = Globals.MC.player.rotationYaw;
            if (forward == 0.0 && strafe == 0.0) {
                Globals.MC.player.getRidingEntity().motionX = 0.0;
                Globals.MC.player.getRidingEntity().motionZ = 0.0;
            } else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += (float)(forward > 0.0 ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += (float)(forward > 0.0 ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                Globals.MC.player.getRidingEntity().motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
                Globals.MC.player.getRidingEntity().motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
            }
        }
    }

    @Override
    public void onDisabled() {
        HookUtils.isBoatStrafingActivated = false;
    }

    @SubscribeEvent
    public void onRenderBoat(RenderBoatEvent event) {
        if (EntityUtils.isDrivenByPlayer((Entity)event.getBoat())) {
            float yaw;
            event.getBoat().rotationYaw = yaw = Globals.MC.player.rotationYaw;
            event.setYaw(yaw);
        }
    }
}

