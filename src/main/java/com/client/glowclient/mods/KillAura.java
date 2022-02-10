//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.world.entity.CombatUtils;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KillAura
extends ToggleMod {
    public static SettingBoolean players = SettingUtils.settingBoolean("KillAura", "Players", "Target players", true);
    public static SettingBoolean mobs = SettingUtils.settingBoolean("KillAura", "Mobs", "Target hostile mobs", true);
    public static SettingBoolean passive = SettingUtils.settingBoolean("KillAura", "Passive", "Target passive mobs", true);
    public static SettingMode mode = SettingUtils.settingMode("KillAura", "Mode", "KillAura mode", "Single", "Single", "Switch");
    public static SettingMode priority = SettingUtils.settingMode("KillAura", "Priority", "KillAura attack priority", "Distance", "Distance");
    public static SettingBoolean throughWalls = SettingUtils.settingBoolean("KillAura", "ThroughWalls", "Hit through walls", false);
    public static SettingBoolean silent = SettingUtils.settingBoolean("KillAura", "Silent", "Doesn't look at target", true);
    public static SettingBoolean tpsSync = SettingUtils.settingBoolean("KillAura", "TPSsync", "KillAura TPS sync", true);
    public static SettingBoolean manual = SettingUtils.settingBoolean("KillAura", "ManualClick", "Click to attack an entity around you. Not automatic", false);
    public static SettingBoolean friendDetect = SettingUtils.settingBoolean("KillAura", "FriendDetect", "Does not attack friended entities", true);
    public static SettingBoolean attackBox = SettingUtils.settingBoolean("KillAura", "AttackBox", "Shows a outline around targeted entity", true);
    public static final SettingDouble range = SettingUtils.settingDouble("KillAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);

    public KillAura() {
        super(Category.COMBAT, "KillAura", false, -1, "Attacks given entities around you");
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("%.1f", range.getDouble());
        return mode;
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
        CombatUtils.setKillAuraTarget(null);
    }

    private double getLagComp() {
        if (tpsSync.getBoolean()) {
            return -(20.0 - TickrateRecorder.getTickData().getPoint().getAverage());
        }
        return 0.0;
    }

    public boolean isValidTarget(Entity entity, Vec3d entPos, Vec3d selfPos) {
        boolean value;
        boolean bl = value = EntityUtils.isLiving(entity) && EntityUtils.isAlive(entity) && !entity.equals((Object)Globals.MC.player) && EntityUtils.isValidEntity(entity) && (EntityUtils.isPlayer(entity) && players.getBoolean() || EntityUtils.isHostileMob(entity) && mobs.getBoolean() || EntityUtils.isFriendlyMob(entity) && passive.getBoolean()) && (range.getDouble() <= 0.0 || entPos.distanceTo(selfPos) <= range.getDouble()) && (throughWalls.getBoolean() || Globals.MC.player.canEntityBeSeen(entity));
        if (friendDetect.getBoolean()) {
            if (!FriendManager.getFriends().isFriend(entity.getName())) {
                return value;
            }
        } else {
            return value;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Entity findTargetEntity(final Vec3d selfPos, final Vec3d selfLookVec) {
        final World world = Minecraft.getMinecraft().world;
        final Vec3d selfLookVecNormal = selfLookVec.normalize();
        Entity target = null;
        double shortestDistance = -1.0;
        synchronized (world.loadedEntityList) {
            for (final Entity entity : Collections.synchronizedList((List<Entity>)Lists.newArrayList((Iterable)world.loadedEntityList))) {
                if (entity != null) {
                    final Vec3d pos = EntityUtils.getOBBCenter(entity);
                    if (!this.isValidTarget(entity, pos, selfPos) || !KillAura.priority.getMode().equals("Distance")) {
                        continue;
                    }
                    final double distance = pos.subtract(selfPos).normalize().subtract(selfLookVecNormal).length();
                    if (shortestDistance != -1.0 && distance >= shortestDistance) {
                        continue;
                    }
                    target = entity;
                    shortestDistance = distance;
                }
            }
        }
        CombatUtils.setKillAuraTarget(target);
        return target;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        EntityPlayerSP localPlayer = Globals.MC.player;
        Entity target = CombatUtils.getKillAuraTarget();
        Vec3d selfPos = EntityUtils.getEyePos((Entity)localPlayer);
        Vec3d selfLookVec = localPlayer.getLookVec();
        if (mode.getMode().equals("Single")) {
            if (target == null || !this.isValidTarget(target, EntityUtils.getOBBCenter(target), selfPos)) {
                target = this.findTargetEntity(selfPos, selfLookVec);
            }
        } else if (mode.getMode().equals("Switch")) {
            target = this.findTargetEntity(selfPos, selfLookVec);
        }
        if (target != null) {
            if (!manual.getBoolean()) {
                if (silent.getBoolean()) {
                    this.isSpoofingRotation = true;
                    RotationSpoofing.faceEntityServer(target, this);
                } else {
                    this.isSpoofingRotation = false;
                    RotationSpoofing.faceEntityClient(target);
                }
                if (localPlayer.getCooledAttackStrength((float)this.getLagComp()) >= 1.0f) {
                    ItemStack stack = Globals.MC.player.getHeldItemMainhand();
                    ItemStack stack2 = Globals.MC.player.getHeldItemOffhand();
                    if (stack == null || !(stack.getItem() instanceof ItemFood) && !(stack.getItem() instanceof ItemAppleGold) || !Globals.MC.gameSettings.keyBindUseItem.isKeyDown()) {
                        if (!silent.getBoolean()) {
                            if (stack2 == null || !(stack2.getItem() instanceof ItemFood) && !(stack2.getItem() instanceof ItemAppleGold) || !Globals.MC.gameSettings.keyBindUseItem.isKeyDown()) {
                                localPlayer.swingArm(EnumHand.MAIN_HAND);
                                Globals.MC.playerController.attackEntity((EntityPlayer)Globals.MC.player, target);
                            }
                        } else {
                            Globals.MC.playerController.attackEntity((EntityPlayer)Globals.MC.player, target);
                            Utils.getNetworkManager().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                    }
                }
            } else if (Globals.MC.gameSettings.keyBindAttack.isKeyDown()) {
                ItemStack stack = Globals.MC.player.getHeldItemMainhand();
                ItemStack stack2 = Globals.MC.player.getHeldItemOffhand();
                if (!(stack != null && (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown() || stack2 != null && (stack2.getItem() instanceof ItemFood || stack2.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown())) {
                    if (silent.getBoolean()) {
                        RotationSpoofing.faceEntityServer(target, this);
                    } else {
                        RotationSpoofing.faceEntityClient(target);
                        RotationSpoofing.resetSpoofedRotation(this);
                    }
                    Globals.MC.playerController.attackEntity((EntityPlayer)Globals.MC.player, target);
                    localPlayer.swingArm(EnumHand.MAIN_HAND);
                }
            }
        } else {
            RotationSpoofing.resetSpoofedRotation(this);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRender(RenderEvent event) {
        Entity target = CombatUtils.getKillAuraTarget();
        if (target != null && attackBox.getBoolean()) {
            RenderUtils.drawTargetESP(target, 255, 0, 0);
        }
    }
}

