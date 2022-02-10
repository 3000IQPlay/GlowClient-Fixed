//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.RenderBoatEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BoatFly
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("BoatFly", "Speed", "Flight Speed", 1.2, 0.1, 0.0, 5.0);
    public static final SettingDouble glideSpeed = SettingUtils.settingDouble("BoatFly", "GlideSpeed", "Speed of glide", 0.033, 0.001, 0.0, 1.0);
    public static final SettingDouble upSpeed = SettingUtils.settingDouble("BoatFly", "UpSpeed", "how fast to go up", 0.2, 0.1, 0.0, 1.0);
    public static SettingBoolean Yaw = SettingUtils.settingBoolean("BoatFly", "Yaw", "Sets boat yaw to your's", true);
    public final boolean noGravity = true;
    public final double maintainY = 105.0;

    public BoatFly() {
        super(Category.MOVEMENT, "BoatFly", false, -1, "Fly with boats");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.1f", speed.getDouble());
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        HookUtils.isNoBoatGravityActivated = Utils.getRidingEntity() instanceof EntityBoat;
    }

    @Override
    public void onDisabled() {
        HookUtils.isBoatStrafingActivated = false;
        HookUtils.isNoBoatGravityActivated = false;
    }

    @SubscribeEvent
    public void onRenderBoat(RenderBoatEvent event) {
        if (EntityUtils.isDrivenByPlayer((Entity)event.getBoat()) && Yaw.getBoolean()) {
            float yaw;
            event.getBoat().rotationYaw = yaw = Globals.MC.player.rotationYaw;
            event.setYaw(yaw);
        }
    }

    @SubscribeEvent
    public void onClientTick(PlayerUpdateEvent event) {
        if (Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            HookUtils.isNoBoatGravityActivated = true;
            if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                Globals.MC.player.getRidingEntity().onGround = false;
                Globals.MC.player.getRidingEntity().motionY = upSpeed.getDouble();
            } else {
                Globals.MC.player.getRidingEntity().motionY = -glideSpeed.getDouble();
            }
            if (Globals.MC.player.posY <= 100.0 && Globals.MC.player.posY > 95.0) {
                BoatFly.setMoveSpeedEntity(speed.getDouble());
            }
        }
    }

    @SubscribeEvent
    public void onClientTick2(PlayerUpdateEvent event) {
        HookUtils.isBoatStrafingActivated = true;
        if (Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            BoatFly.setMoveSpeedEntity(speed.getDouble());
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
}

