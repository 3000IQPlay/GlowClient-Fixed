//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.RenderBoatEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Yaw
extends ToggleMod {
    public static SettingMode mode = SettingUtils.settingMode("Yaw", "Mode", "Mode of Yaw", "Diagonal", "Cardinal", "Diagonal", "CoordLock", "Custom");
    public static final SettingDouble custom_angle = SettingUtils.settingDouble("Yaw", "Angle", "Custom angle to snap to", 0.0, 1.0, -180.0, 180.0);
    float f = 0.0f;
    public static double x = 0.0;
    public static double z = 0.0;

    public Yaw() {
        super(Category.PLAYER, "Yaw", false, -1, "Locks YAW to a specific rotation");
    }

    @Override
    public String getHUDTag() {
        if (mode.getMode().equals("Custom")) {
            return String.format("%.1f", custom_angle.getDouble());
        }
        return "";
    }

    @SubscribeEvent
    public void onRenderBoat(RenderBoatEvent event) {
        if (EntityUtils.isDrivenByPlayer((Entity)event.getBoat())) {
            float yaw;
            event.getBoat().rotationYaw = yaw = Globals.MC.player.rotationYaw;
            event.setYaw(yaw);
        }
    }

    @Override
    public void onDisabled() {
        HookUtils.isBoatStrafingActivated = false;
    }

    @SubscribeEvent
    public void onUpdate(TickEvent event) {
        HookUtils.isBoatStrafingActivated = true;
        if (Globals.MC.player != null) {
            if (mode.getMode().equals("Cardinal")) {
                this.f = 90.0f;
            }
            if (mode.getMode().equals("Diagonal")) {
                this.f = 45.0f;
            }
            Vec3d vec = new Vec3d(x, Globals.MC.player.posY, z);
            double yaw = (float)Math.round((Globals.MC.player.rotationYaw + 1.0f) / this.f) * this.f;
            if (mode.getMode().equals("Custom")) {
                yaw = custom_angle.getDouble();
            }
            if (!mode.getMode().equals("CoordLock")) {
                Globals.MC.player.rotationYaw = (float)yaw;
                Globals.MC.player.rotationYawHead = (float)yaw;
            } else {
                RotationSpoofing.faceVectorClientYaw(vec);
            }
        }
    }
}

