//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElytraControl
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("ElytraControl", "ConSpeed", "Control Speed", 0.05, 0.05, 0.0, 0.5);
    public static final SettingDouble cspeed = SettingUtils.settingDouble("ElytraControl", "CreSpeed", "Creative Speed", 0.15, 0.05, 0.0, 0.5);
    public static final SettingMode mode = SettingUtils.settingMode("ElytraControl", "Mode", "Mode of the mod", "Control", "Creative", "Control");

    public ElytraControl() {
        super(Category.MOVEMENT, "ElytraControl", false, -1, "Control elytra with WASD");
    }

    @Override
    public String getHUDTag() {
        return mode.getMode();
    }

    @Override
    protected void onEnabled() {
        if (mode.getMode().equals("Creative")) {
            Globals.MC.addScheduledTask(() -> {
                if (Globals.MC.player != null && !Globals.MC.player.isElytraFlying()) {
                    Utils.getNetworkManager().sendPacket((Packet)new CPacketEntityAction((Entity)Globals.MC.player, CPacketEntityAction.Action.START_FALL_FLYING));
                }
            });
        }
    }

    @Override
    public void onDisabled() {
        if (mode.getMode().equals("Creative") && Globals.MC.player != null) {
            Globals.MC.player.capabilities.isFlying = false;
            Utils.getNetworkManager().sendPacket((Packet)new CPacketEntityAction((Entity)Globals.MC.player, CPacketEntityAction.Action.START_FALL_FLYING));
            Globals.MC.player.capabilities.setFlySpeed(0.05f);
        }
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onOopdoote(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Creative")) {
            if (Globals.MC.player.isElytraFlying()) {
                Globals.MC.player.capabilities.isFlying = true;
            }
            Globals.MC.player.capabilities.setFlySpeed((float)cspeed.getDouble());
        }
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Control") && Globals.MC.player.isElytraFlying()) {
            if (WMinecraft.WMC.gameSettings.keyBindJump.isKeyDown()) {
                WMinecraft.getPlayer().motionY += 0.08;
            } else if (WMinecraft.WMC.gameSettings.keyBindSneak.isKeyDown()) {
                WMinecraft.getPlayer().motionY -= 0.04;
            }
            if (WMinecraft.WMC.gameSettings.keyBindForward.isKeyDown()) {
                float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
                WMinecraft.getPlayer().motionX -= (double)WMath.sin(yaw) * speed.getDouble();
                WMinecraft.getPlayer().motionZ += (double)WMath.cos(yaw) * speed.getDouble();
            } else if (WMinecraft.WMC.gameSettings.keyBindBack.isKeyDown()) {
                float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
                WMinecraft.getPlayer().motionX += (double)(WMath.sin(yaw) * 0.05f);
                WMinecraft.getPlayer().motionZ -= (double)(WMath.cos(yaw) * 0.05f);
            } else if (WMinecraft.WMC.gameSettings.keyBindRight.isKeyDown()) {
                float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
                WMinecraft.getPlayer().motionX -= (double)(WMath.sin(yaw - 80.1107f) * 0.08f);
                WMinecraft.getPlayer().motionZ += (double)(WMath.cos(yaw - 80.1107f) * 0.08f);
            } else if (WMinecraft.WMC.gameSettings.keyBindLeft.isKeyDown()) {
                float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
                WMinecraft.getPlayer().motionX -= (double)(WMath.sin(yaw + 80.1107f) * 0.08f);
                WMinecraft.getPlayer().motionZ += (double)(WMath.cos(yaw + 80.1107f) * 0.08f);
            }
        }
    }
}

