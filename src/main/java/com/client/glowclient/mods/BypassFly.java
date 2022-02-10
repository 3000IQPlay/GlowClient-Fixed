//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.binding.Bindings;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.ProjectileUtils;
import com.client.glowclient.utils.mod.mods.timermod.TimerUtils;
import com.client.glowclient.utils.world.entity.MovementUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BypassFly
extends ToggleMod {
    private SimpleTimer glidetimer = new SimpleTimer();
    private SimpleTimer bowt = new SimpleTimer();
    private boolean BTrue = true;
    boolean onground;
    public static final SettingDouble speed = SettingUtils.settingDouble("BypassFly", "Speed", "Motion Speed of X/Z", 0.25, 0.01, 0.0, 1.0);
    public static final SettingDouble glideSpeed = SettingUtils.settingDouble("BypassFly", "Glidespeed", "Speed you glide down.", 800.0, 1.0, 0.0, 2000.0);
    public static final SettingDouble glidePower = SettingUtils.settingDouble("BypassFly", "GlidePower", "Power of glide", 200.0, 1.0, 0.0, 1000.0);
    public static SettingBoolean glide = SettingUtils.settingBoolean("BypassFly", "Glide", "Glides player down", true);
    public static SettingBoolean bowbowmb = SettingUtils.settingBoolean("BypassFly", "BowBomber", "Spams bow downwards", true);
    public static SettingBoolean fast = SettingUtils.settingBoolean("BypassFly", "FastShot", "Shoots bow at a fast rate", false);
    public static SettingBoolean ground = SettingUtils.settingBoolean("BypassFly", "GroundDetect", "Turns off fly if on ground.", true);
    public static SettingBoolean usetimer = SettingUtils.settingBoolean("BypassFly", "UseTimer", "Go faster with timer", false);
    public static SettingBoolean phasespeed = SettingUtils.settingBoolean("BypassFly", "PhaseSpeed", "Go slow sideways to be able to phase through blocks", false);
    SimpleTimer timer = new SimpleTimer();
    private SimpleTimer phaseTimer = new SimpleTimer();
    private boolean doOnce = false;

    public BypassFly() {
        super(Category.MOVEMENT, "BypassFly", false, -1, "Fly using movement packets");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.2f", speed.getDouble());
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
        TimerUtils.resetTimerSpeed();
        Bindings.keyBindUseItem.setPressed(false);
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        ItemStack heldItem;
        int power = fast.getBoolean() ? 600 : 1200;
        TickrateRecorder.TickRateData data = TickrateRecorder.getTickData();
        TickrateRecorder.TickRateData.CalculationData point = data.getPoint();
        double p = point.getAverage();
        double tps = 20.0 / p;
        boolean onground = ground.getBoolean() ? Globals.MC.player.onGround : false;
        if (!onground && ProjectileUtils.isBow(heldItem = Globals.MC.player.getHeldItemMainhand()) && bowbowmb.getBoolean()) {
            if (Globals.MC.gameSettings.keyBindAttack.isKeyDown()) {
                this.doOnce = true;
                RotationSpoofing.setRotationServer(Globals.MC.player.rotationYaw, 90.0f, this);
                if (!this.bowt.isStarted()) {
                    this.bowt.start();
                }
                if (this.bowt.hasTimeElapsed(0.0)) {
                    Bindings.keyBindUseItem.setPressed(true);
                }
                if (this.bowt.hasTimeElapsed((double)power * tps)) {
                    this.bowt.start();
                    Bindings.keyBindUseItem.setPressed(false);
                }
            } else {
                RotationSpoofing.resetSpoofedRotation(this);
                if (this.doOnce) {
                    Bindings.keyBindUseItem.setPressed(false);
                    this.doOnce = false;
                }
            }
        }
    }

    @Override
    public void onEnabled() {
        this.glidetimer.start();
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (usetimer.getBoolean()) {
            if (!this.timer.isStarted()) {
                this.timer.start();
            }
            if (this.timer.hasTimeElapsed(0.0)) {
                TimerUtils.setTimerSpeed(1.0f);
            }
            if (this.timer.hasTimeElapsed(100.0)) {
                TimerUtils.setTimerSpeed(1.2f);
                this.timer.start();
            }
        }
        this.onground = ground.getBoolean() ? Globals.MC.player.onGround : false;
        if (ModuleManager.getModuleFromName("BypassFly").isEnabled() && !this.onground) {
            try {
                double speed = BypassFly.speed.getDouble();
                if (phasespeed.getBoolean()) {
                    double tim = 300.0;
                    if (!this.phaseTimer.isStarted()) {
                        this.phaseTimer.start();
                    }
                    if (this.phaseTimer.hasTimeElapsed(0.0)) {
                        speed = 0.06;
                    }
                    if (this.phaseTimer.hasTimeElapsed(tim)) {
                        speed = 0.25;
                        this.phaseTimer.start();
                    }
                    if (this.phaseTimer.hasTimeElapsed(tim + 100.0)) {
                        this.timer.start();
                    }
                }
                MovementUtils.movePlayerDirectional(speed);
                int time = glideSpeed.getInt();
                if (this.glidetimer.hasTimeElapsed(time + glidePower.getInt())) {
                    this.glidetimer.start();
                }
                double posX = Globals.MC.player.posX + Globals.MC.player.motionX;
                double posY = Globals.MC.player.posY + (Globals.MC.gameSettings.keyBindJump.isKeyDown() && !this.glidetimer.hasTimeElapsed(time) ? (this.BTrue ? 0.0625 : 0.0624) : 1.0E-9) - (Globals.MC.gameSettings.keyBindSneak.isKeyDown() || this.glidetimer.hasTimeElapsed(time) ? (this.BTrue ? 0.0625 : 0.0624) : 2.0E-9);
                if (!glide.getBoolean()) {
                    posY = Globals.MC.player.posY + (Globals.MC.gameSettings.keyBindJump.isKeyDown() ? (this.BTrue ? 0.0625 : 0.0624) : 1.0E-9) - (Globals.MC.gameSettings.keyBindSneak.isKeyDown() ? (this.BTrue ? 0.0625 : 0.0624) : 2.0E-9);
                }
                double posZ = Globals.MC.player.posZ + Globals.MC.player.motionZ;
                if (!this.isSpoofingRotation) {
                    Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, posY, posZ, Globals.MC.player.rotationYaw, Globals.MC.player.rotationPitch, Globals.MC.player.onGround));
                    Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.PositionRotation(Globals.MC.player.posX, 1000.0 + Globals.MC.player.posY, Globals.MC.player.posZ, Globals.MC.player.rotationYaw, Globals.MC.player.rotationPitch, Globals.MC.player.onGround));
                } else {
                    Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, posY, posZ, Globals.MC.player.rotationYaw, 90.0f, Globals.MC.player.onGround));
                    Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.PositionRotation(Globals.MC.player.posX, 1000.0 + Globals.MC.player.posY, Globals.MC.player.posZ, Globals.MC.player.rotationYaw, 90.0f, Globals.MC.player.onGround));
                }
                Utils.getNetworkManager().sendPacket((Packet)new CPacketEntityAction((Entity)Globals.MC.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Globals.MC.player.setPosition(posX, posY, posZ);
                this.BTrue = !this.BTrue;
                Globals.MC.player.motionX = 0.0;
                Globals.MC.player.motionY = 0.0;
                Globals.MC.player.motionZ = 0.0;
            }
            catch (Exception e) {
                Utils.printStackTrace(e);
            }
        }
    }

    @SubscribeEvent
    public void onPacketRecieved(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            if (Globals.MC.player != null && Globals.MC.player.rotationYaw != -180.0f && Globals.MC.player.rotationPitch != 0.0f) {
                try {
                    packet.pitch = Globals.MC.player.rotationPitch;
                    packet.yaw = Globals.MC.player.rotationYaw;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

