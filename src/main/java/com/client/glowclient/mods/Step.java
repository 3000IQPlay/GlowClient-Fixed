//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.TimerMod;
import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.PacketHelper;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Step
extends ToggleMod {
    public static final float DEFAULT_STEP_HEIGHT = 0.6f;
    public static SettingBoolean usetimer = SettingUtils.settingBoolean("Step", "UseTimer", "Uses Timer", true);
    public static SettingBoolean entity = SettingUtils.settingBoolean("Step", "EntityStep", "Steps with rideable entities", true);
    public static SettingBoolean infinite = SettingUtils.settingBoolean("Step", "Infinite", "Unlimited step height", false);
    public static final SettingDouble step = SettingUtils.settingDouble("Step", "Height", "step height", 1.0, 1.0, 0.0, 5.0);
    private final float DEF_SPEED = 50.0f;
    private CPacketPlayer previousPositionPacket = null;

    public Step() {
        super(Category.MOVEMENT, "Step", false, -1, "Step up blocks without jumping");
    }

    @Override
    public String getHUDTag() {
        String mode = !infinite.getBoolean() ? String.format("%.0f", step.getDouble()) : "Infinite";
        return mode;
    }

    @Override
    public void onDisabled() {
        if (Globals.MC.player != null) {
            Globals.MC.player.stepHeight = 0.6f;
            this.setSpeed(50.0f);
            if (Globals.MC.player.getRidingEntity() != null && entity.getBoolean()) {
                Globals.MC.player.getRidingEntity().stepHeight = 1.0f;
            }
        }
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (!Globals.MC.player.isElytraFlying()) {
            EntityPlayer localPlayer = (EntityPlayer)event.getEntityLiving();
            TickrateRecorder.TickRateData data = TickrateRecorder.getTickData();
            TickrateRecorder.TickRateData.CalculationData point = data.getPoint();
            double p = point.getAverage();
            double poo = 20.0 / p;
            if (localPlayer.onGround) {
                localPlayer.stepHeight = !infinite.getBoolean() ? (float)step.getDouble() : 256.0f;
                if (this.isEnabled() && !ModuleManager.getModuleFromName("Timer").isEnabled() && ModuleManager.getModuleFromName("Timer") != null) {
                    this.setSpeed(50.0f);
                } else {
                    this.setSpeed(50.0f / (float)TimerMod.factor.getDouble());
                }
                if (TimerMod.tps.getBoolean() && ModuleManager.getModuleFromName("Timer").isEnabled()) {
                    this.setSpeed(50.0f * (float)poo);
                }
            } else {
                localPlayer.stepHeight = 0.6f;
            }
            if (Globals.MC.player.getRidingEntity() != null && entity.getBoolean()) {
                Globals.MC.player.getRidingEntity().stepHeight = !infinite.getBoolean() ? (Globals.MC.player.getRidingEntity().onGround ? (float)step.getDouble() : 1.0f) : (Globals.MC.player.getRidingEntity().onGround ? 256.0f : 1.0f);
            }
        }
    }

    @SubscribeEvent
    public void onPacketSending(PacketEvent.Send event) {
        if ((event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation) && !Globals.MC.player.isElytraFlying()) {
            double diffY;
            CPacketPlayer packetPlayer = (CPacketPlayer)event.getPacket();
            if (this.previousPositionPacket != null && !PacketHelper.isIgnored(event.getPacket()) && (diffY = packetPlayer.getY(0.0) - this.previousPositionPacket.getY(0.0)) > (double)0.6f && diffY <= 1.2491870787) {
                List<Packet> sendList = Lists.newArrayList();
                if (Globals.MC.player.onGround && !Globals.MC.player.isElytraFlying()) {
                    if (usetimer.getBoolean()) {
                        this.setSpeed(166.66666f);
                    } else {
                        this.setSpeed(50.0f);
                    }
                }
                double x = this.previousPositionPacket.getX(0.0);
                double y = this.previousPositionPacket.getY(0.0);
                double z = this.previousPositionPacket.getZ(0.0);
                sendList.add(new CPacketPlayer.Position(x, y + 0.4199999869, z, true));
                sendList.add(new CPacketPlayer.Position(x, y + 0.7531999805, z, true));
                sendList.add(new CPacketPlayer.Position(packetPlayer.getX(0.0), packetPlayer.getY(0.0), packetPlayer.getZ(0.0), packetPlayer.isOnGround()));
                for (Packet toSend : sendList) {
                    PacketHelper.ignore(toSend);
                    Utils.getNetworkManager().sendPacket(toSend);
                }
                event.setCanceled(true);
            }
            this.previousPositionPacket = (CPacketPlayer)event.getPacket();
        }
    }

    public void setSpeed(float value) {
        try {
            Timer timer = Globals.MC.timer;
            timer.tickLength = value;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

