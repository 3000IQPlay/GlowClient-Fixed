//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BoatAura
extends ToggleMod {
    public static final SettingDouble range = SettingUtils.settingDouble("BoatAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);
    public static SettingBoolean vis_check = SettingUtils.settingBoolean("BoatAura", "ThroughWalls", "Hit through walls", false);
    public static SettingBoolean ridden = SettingUtils.settingBoolean("BoatAura", "TargetRidden", "Targets the ridden boat", false);
    private long currentMS = 0L;
    private long lastMS = -1L;

    public BoatAura() {
        super(Category.COMBAT, "BoatAura", false, -1, "Automatically attacks nearby boats");
    }

    public boolean isVisible(Entity target) {
        return vis_check.getBoolean() || Globals.MC.player.canEntityBeSeen(target);
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("%.1f", range.getDouble());
        return mode;
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        try {
            double speed = 4.0;
            EntityPlayerSP player = Globals.MC.player;
            ItemStack stack = Globals.MC.player.getHeldItemMainhand();
            ItemStack stack2 = Globals.MC.player.getHeldItemOffhand();
            if (!(stack != null && (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown() || stack2 != null && (stack2.getItem() instanceof ItemFood || stack2.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown())) {
                this.currentMS = System.nanoTime() / 1000000L;
                if (this.hasDelayRun((long)(1000.0 / speed))) {
                    for (Entity e : Globals.MC.world.loadedEntityList) {
                        if (e == null || !((double)player.getDistance(e) < range.getDouble()) || !this.isVisible(e)) continue;
                        if (e instanceof EntityBoat) {
                            if (!ridden.getBoolean()) {
                                if (e == Globals.MC.player.getRidingEntity()) continue;
                                RotationSpoofing.faceEntityServer(e, this);
                                Globals.MC.playerController.attackEntity((EntityPlayer)player, e);
                                player.swingArm(EnumHand.MAIN_HAND);
                                this.lastMS = System.nanoTime() / 1000000L;
                                continue;
                            }
                            RotationSpoofing.faceEntityServer(e, this);
                            Globals.MC.playerController.attackEntity((EntityPlayer)player, e);
                            player.swingArm(EnumHand.MAIN_HAND);
                            this.lastMS = System.nanoTime() / 1000000L;
                            continue;
                        }
                        RotationSpoofing.resetSpoofedRotation(this);
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean hasDelayRun(long time) {
        return this.currentMS - this.lastMS >= time;
    }
}

