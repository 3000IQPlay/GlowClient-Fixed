//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.RenderBoatEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.client.glowclient.utils.world.entity.MovementUtils;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class BoatTravel
extends ToggleMod {
    public static SettingMode bottom = SettingUtils.settingMode("BoatTravel", "Mode", "Mode of BoatTravel", "Top", "Top", "Bottom", "GlideBounce");
    public static SettingMode speed = SettingUtils.settingMode("BoatTravel", "Speed", "Speed mode of BoatTravel", "400m/s", "400m/s", "1600m/s");
    SimpleTimer timer = new SimpleTimer();

    public BoatTravel() {
        super(Category.MOVEMENT, "BoatTravel", false, -1, "Go faster than chunks in boats! Down Arrow Key to go down");
    }

    @Override
    public String getHUDTag() {
        return bottom.getMode();
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (Globals.MC.player.getRidingEntity() != null) {
            HookUtils.isNoBoatGravityActivated = true;
        }
    }

    @Override
    public void onDisabled() {
        if (Globals.MC.player != null) {
            if (Globals.MC.player.getRidingEntity() != null) {
                Globals.MC.player.getRidingEntity().noClip = false;
            }
            HookUtils.isBoatStrafingActivated = false;
            HookUtils.isNoBoatGravityActivated = false;
        }
    }

    @SubscribeEvent
    public void onRenderBoat(RenderBoatEvent event) {
        if (EntityUtils.isDrivenByPlayer((Entity)event.getBoat())) {
            float yaw;
            event.getBoat().rotationYaw = yaw = Globals.MC.player.rotationYaw;
            event.setYaw(yaw);
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        double sped = 0.0;
        if (bottom.getMode().equals("Top") && Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            Globals.MC.player.getRidingEntity().noClip = true;
            HookUtils.isBoatStrafingActivated = true;
            if (Globals.MC.player.posY < 124.5 && Globals.MC.player.getRidingEntity().posY < 124.5) {
                sped = 1.59;
            } else if (speed.getMode().equals("400m/s")) {
                sped = Globals.MC.world.getChunk(Globals.MC.player.getPosition()).isLoaded() ? 100.0 : (Globals.MC.player.posY < 125.5 && Globals.MC.player.getRidingEntity().posY < 125.5 ? 1.59 : 22.177769);
            } else if (speed.getMode().equals("1600m/s")) {
                sped = 88.844439;
            }
            MovementUtils.moveEntityStrafe(sped, Globals.MC.player.getRidingEntity());
            HookUtils.isNoBoatGravityActivated = true;
            if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                Globals.MC.player.getRidingEntity().onGround = false;
                if (Globals.MC.player.posY < 125.5 && Globals.MC.player.getRidingEntity().posY < 125.5) {
                    if (!this.timer.isStarted()) {
                        this.timer.start();
                    }
                    if (this.timer.hasTimeElapsed(0.0)) {
                        Globals.MC.player.getRidingEntity().motionY = 1.0;
                    }
                    if (this.timer.hasTimeElapsed(50.0)) {
                        Globals.MC.player.getRidingEntity().motionY = 0.0;
                    }
                    if (this.timer.hasTimeElapsed(800.0)) {
                        this.timer.start();
                    }
                } else {
                    Globals.MC.player.getRidingEntity().motionY = 0.0;
                }
            } else {
                Globals.MC.player.getRidingEntity().motionY = Globals.MC.player.posY > 1.0 && Globals.MC.player.getRidingEntity().posY > 1.0 ? (Keyboard.isKeyDown((int)208) ? -1.0 : (Keyboard.isKeyDown((int)200) ? 1.0 : 0.0)) : 0.0;
            }
        }
        if (bottom.getMode().equals("Bottom") && Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            Globals.MC.player.getRidingEntity().noClip = true;
            HookUtils.isBoatStrafingActivated = true;
            if (Globals.MC.player.posY > 1.5 && Globals.MC.player.getRidingEntity().posY > 1.5) {
                sped = 1.59;
            } else if (speed.getMode().equals("400m/s")) {
                sped = Globals.MC.world.getChunk(Globals.MC.player.getPosition()).isLoaded() ? 100.0 : 22.177769;
            } else if (speed.getMode().equals("1600m/s")) {
                sped = 88.844439;
            }
            MovementUtils.moveEntityStrafe(sped, Globals.MC.player.getRidingEntity());
            HookUtils.isNoBoatGravityActivated = true;
            if (Keyboard.isKeyDown((int)208)) {
                Globals.MC.player.getRidingEntity().onGround = false;
                if (Globals.MC.player.posY > 1.5 && Globals.MC.player.getRidingEntity().posY > 1.5) {
                    if (!this.timer.isStarted()) {
                        this.timer.start();
                    }
                    if (this.timer.hasTimeElapsed(0.0)) {
                        Globals.MC.player.getRidingEntity().motionY = -1.0;
                    }
                    if (this.timer.hasTimeElapsed(50.0)) {
                        Globals.MC.player.getRidingEntity().motionY = 0.0;
                    }
                    if (this.timer.hasTimeElapsed(550.0)) {
                        this.timer.start();
                    }
                } else {
                    Globals.MC.player.getRidingEntity().motionY = 0.0;
                }
            } else {
                Globals.MC.player.getRidingEntity().motionY = Globals.MC.player.posY > 0.5 && Globals.MC.player.getRidingEntity().posY > 0.5 ? (Globals.MC.gameSettings.keyBindJump.isKeyDown() ? 0.5 : 0.0) : 0.0;
            }
        }
        if (bottom.getMode().equals("GlideBounce") && Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null) {
            Globals.MC.player.getRidingEntity().noClip = true;
            HookUtils.isBoatStrafingActivated = true;
            if (!Globals.MC.world.getChunk(Globals.MC.player.getPosition()).isLoaded()) {
                double pw = Keyboard.isKeyDown((int)200) ? 20.0 : 0.8;
                if (!Globals.MC.gameSettings.keyBindSprint.isKeyDown()) {
                    if (!Keyboard.isKeyDown((int)208)) {
                        if (!this.timer.isStarted()) {
                            this.timer.start();
                        }
                        if (this.timer.hasTimeElapsed(0.0)) {
                            Globals.MC.player.getRidingEntity().motionY = pw;
                        }
                        if (this.timer.hasTimeElapsed(50.0)) {
                            Globals.MC.player.getRidingEntity().motionY = -0.033;
                        }
                        if (this.timer.hasTimeElapsed(1000.0)) {
                            this.timer.start();
                        }
                    } else {
                        Globals.MC.player.getRidingEntity().motionY = -2.0;
                    }
                }
            }
            if (Globals.MC.world.getChunk(Globals.MC.player.getPosition()).isLoaded()) {
                if (!Globals.MC.gameSettings.keyBindSprint.isKeyDown()) {
                    MovementUtils.moveEntityStrafe(1.59, Globals.MC.player.getRidingEntity());
                } else {
                    MovementUtils.moveEntityStrafe(100.0, Globals.MC.player.getRidingEntity());
                }
            } else {
                if (speed.getMode().equals("400m/s")) {
                    if (Keyboard.isKeyDown((int)203)) {
                        MovementUtils.moveEntityStrafe(2.0, Globals.MC.player.getRidingEntity());
                    } else {
                        MovementUtils.moveEntityStrafe(22.077769, Globals.MC.player.getRidingEntity());
                    }
                }
                if (speed.getMode().equals("1600m/s")) {
                    if (Keyboard.isKeyDown((int)203)) {
                        MovementUtils.moveEntityStrafe(2.0, Globals.MC.player.getRidingEntity());
                    } else {
                        MovementUtils.moveEntityStrafe(88.844439, Globals.MC.player.getRidingEntity());
                    }
                }
            }
        }
    }
}

