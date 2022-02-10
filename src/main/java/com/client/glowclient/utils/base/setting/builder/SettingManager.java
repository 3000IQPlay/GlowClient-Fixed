/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.client.glowclient.utils.base.setting.builder;

import com.client.glowclient.mods.AntiAFK;
import com.client.glowclient.mods.AntiPackets;
import com.client.glowclient.mods.AntiPotion;
import com.client.glowclient.mods.AutoGapple;
import com.client.glowclient.mods.AutoLog;
import com.client.glowclient.mods.AutoReconnect;
import com.client.glowclient.mods.AutoWalk;
import com.client.glowclient.mods.BedAura;
import com.client.glowclient.mods.Blink;
import com.client.glowclient.mods.BoatAura;
import com.client.glowclient.mods.BoatFly;
import com.client.glowclient.mods.BoatJump;
import com.client.glowclient.mods.BoatTravel;
import com.client.glowclient.mods.BowSpam;
import com.client.glowclient.mods.BreadCrumbs;
import com.client.glowclient.mods.BypassFly;
import com.client.glowclient.mods.CameraClip;
import com.client.glowclient.mods.ChunkFinder;
import com.client.glowclient.mods.ColorSigns;
import com.client.glowclient.mods.CrystalAura;
import com.client.glowclient.mods.ElytraControl;
import com.client.glowclient.mods.EnchColors;
import com.client.glowclient.mods.EntityESP;
import com.client.glowclient.mods.EntitySpeed;
import com.client.glowclient.mods.FastBreak;
import com.client.glowclient.mods.Flight;
import com.client.glowclient.mods.Freecam;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.mods.HorseMod;
import com.client.glowclient.mods.IceSpeed;
import com.client.glowclient.mods.Jesus;
import com.client.glowclient.mods.KillAura;
import com.client.glowclient.mods.Knockback;
import com.client.glowclient.mods.LiquidSpeed;
import com.client.glowclient.mods.Nametags;
import com.client.glowclient.mods.NoPush;
import com.client.glowclient.mods.NoRender;
import com.client.glowclient.mods.Nuker;
import com.client.glowclient.mods.PeekMod;
import com.client.glowclient.mods.Reach;
import com.client.glowclient.mods.Scaffold;
import com.client.glowclient.mods.Speed;
import com.client.glowclient.mods.Step;
import com.client.glowclient.mods.StorageESP;
import com.client.glowclient.mods.TabMod;
import com.client.glowclient.mods.TimerMod;
import com.client.glowclient.mods.Tracers;
import com.client.glowclient.mods.Xray;
import com.client.glowclient.mods.Yaw;
import com.client.glowclient.mods.background.ColorManager;
import com.client.glowclient.mods.background.GuiMod;
import com.client.glowclient.mods.indev.BlockFinder;
import com.client.glowclient.mods.indev.Dupe;
import com.client.glowclient.mods.indev.EmpsShit.NameChanger;
import com.client.glowclient.mods.indev.EmpsShit.NametagsImpact;
import com.client.glowclient.mods.indev.GlowBot;
import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.Setting;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class SettingManager {
    private static Hashtable<String, Setting> settingsRegistry = new Hashtable();
    private static ArrayList<Setting> settings = new ArrayList();
    private static ArrayList<SettingString> settingStrings = new ArrayList();
    private static ArrayList<SettingBoolean> settingBooleans = new ArrayList();
    private static ArrayList<SettingDouble> settingDoubles = new ArrayList();
    private static ArrayList<SettingMode> settingModes = new ArrayList();

    public static Collection<Setting> getSettings() {
        return Collections.unmodifiableCollection(settings);
    }

    public static Collection<SettingDouble> getSettingDoubles() {
        return Collections.unmodifiableCollection(settingDoubles);
    }

    public static Collection<SettingString> getSettingStrings() {
        return Collections.unmodifiableCollection(settingStrings);
    }

    public static Collection<SettingBoolean> getSettingBooleans() {
        return Collections.unmodifiableCollection(settingBooleans);
    }

    public static Collection<SettingMode> getSettingModes() {
        return Collections.unmodifiableCollection(settingModes);
    }

    private static void registerSetting(Setting setting) {
        settingsRegistry.put(setting.getFullName(), setting);
        settings.add(setting);
        if (setting instanceof SettingBoolean) {
            settingBooleans.add((SettingBoolean)setting);
        }
        if (setting instanceof SettingDouble) {
            settingDoubles.add((SettingDouble)setting);
        }
        if (setting instanceof SettingString) {
            settingStrings.add((SettingString)setting);
        }
        if (setting instanceof SettingMode) {
            settingModes.add((SettingMode)setting);
        }
    }

    public static void decodeConfig(String configJson) {
        JsonObject object;
        try {
            object = new JsonParser().parse(configJson).getAsJsonObject();
        }
        catch (IllegalStateException e) {
            return;
        }
        for (Map.Entry entry : object.entrySet()) {
            settingsRegistry.getOrDefault(entry.getKey(), Setting.getInstance()).decode((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsJsonObject());
        }
    }

    public static String encodeConfig() {
        JsonObject object = new JsonObject();
        for (Setting setting : settingsRegistry.values()) {
            JsonObject elementObj = new JsonObject();
            setting.encode(elementObj);
            object.add(setting.getFullName(), (JsonElement)elementObj);
        }
        return new Gson().toJson((JsonElement)object);
    }

    static {
        for (Module mod : ModuleManager.getMods()) {
            if (!(mod instanceof ToggleMod)) continue;
            SettingManager.registerSetting(mod.toggled);
        }
        for (Module mod : ModuleManager.getMods()) {
            if (!(mod instanceof ToggleMod) && !(mod instanceof GuiMod)) continue;
            SettingManager.registerSetting(mod.key);
        }
        SettingManager.registerSetting(Yaw.mode);
        SettingManager.registerSetting(Yaw.custom_angle);
        SettingManager.registerSetting(Xray.opacity);
        SettingManager.registerSetting(Tracers.width);
        SettingManager.registerSetting(Tracers.players);
        SettingManager.registerSetting(Tracers.passive);
        SettingManager.registerSetting(Tracers.items);
        SettingManager.registerSetting(Tracers.hostile);
        SettingManager.registerSetting(Tracers.distance2);
        SettingManager.registerSetting(TimerMod.tps);
        SettingManager.registerSetting(TimerMod.factor);
        SettingManager.registerSetting(TabMod.noping);
        SettingManager.registerSetting(TabMod.nonames);
        SettingManager.registerSetting(TabMod.noheads);
        SettingManager.registerSetting(TabMod.extended);
        SettingManager.registerSetting(StorageESP.shulker);
        SettingManager.registerSetting(StorageESP.itemframes);
        SettingManager.registerSetting(StorageESP.hopper);
        SettingManager.registerSetting(StorageESP.furnace);
        SettingManager.registerSetting(StorageESP.echest);
        SettingManager.registerSetting(StorageESP.dispenser);
        SettingManager.registerSetting(StorageESP.chest);
        SettingManager.registerSetting(Step.usetimer);
        SettingManager.registerSetting(Step.step);
        SettingManager.registerSetting(Step.infinite);
        SettingManager.registerSetting(Step.entity);
        SettingManager.registerSetting(Speed.useTimer);
        SettingManager.registerSetting(Speed.sprint);
        SettingManager.registerSetting(Speed.spoofCamera);
        SettingManager.registerSetting(Speed.mode);
        SettingManager.registerSetting(Scaffold.usetimer);
        SettingManager.registerSetting(Scaffold.tower);
        SettingManager.registerSetting(Reach.distance);
        SettingManager.registerSetting(PeekMod.tooltips);
        SettingManager.registerSetting(PeekMod.rightClick);
        SettingManager.registerSetting(Nuker.silent);
        SettingManager.registerSetting(Nuker.selective);
        SettingManager.registerSetting(Nuker.range);
        SettingManager.registerSetting(Nuker.flat);
        SettingManager.registerSetting(NoRender.tut);
        SettingManager.registerSetting(NoRender.totem);
        SettingManager.registerSetting(NoRender.skylight);
        SettingManager.registerSetting(NoRender.hurtcam);
        SettingManager.registerSetting(NoRender.fog);
        SettingManager.registerSetting(NoRender.effects);
        SettingManager.registerSetting(NoRender.boss);
        SettingManager.registerSetting(NoRender.block);
        SettingManager.registerSetting(NoPush.water);
        SettingManager.registerSetting(NoPush.entities);
        SettingManager.registerSetting(NoPush.blocks);
        SettingManager.registerSetting(NametagsImpact.players);
        SettingManager.registerSetting(NametagsImpact.passive);
        SettingManager.registerSetting(NametagsImpact.mobs);
        SettingManager.registerSetting(Nametags.visibility);
        SettingManager.registerSetting(Nametags.shielding);
        SettingManager.registerSetting(Nametags.scaling);
        SettingManager.registerSetting(Nametags.scale);
        SettingManager.registerSetting(Nametags.players);
        SettingManager.registerSetting(Nametags.passive);
        SettingManager.registerSetting(Nametags.mobs);
        SettingManager.registerSetting(Nametags.items);
        SettingManager.registerSetting(Nametags.healthb);
        SettingManager.registerSetting(LiquidSpeed.doubleS2);
        SettingManager.registerSetting(LiquidSpeed.doubleS);
        SettingManager.registerSetting(Knockback.multiplier_y);
        SettingManager.registerSetting(Knockback.multiplier_h);
        SettingManager.registerSetting(KillAura.tpsSync);
        SettingManager.registerSetting(KillAura.throughWalls);
        SettingManager.registerSetting(KillAura.silent);
        SettingManager.registerSetting(KillAura.range);
        SettingManager.registerSetting(KillAura.priority);
        SettingManager.registerSetting(KillAura.players);
        SettingManager.registerSetting(KillAura.passive);
        SettingManager.registerSetting(KillAura.mode);
        SettingManager.registerSetting(KillAura.mobs);
        SettingManager.registerSetting(KillAura.manual);
        SettingManager.registerSetting(KillAura.friendDetect);
        SettingManager.registerSetting(KillAura.attackBox);
        SettingManager.registerSetting(Jesus.mode1);
        SettingManager.registerSetting(IceSpeed.speed);
        SettingManager.registerSetting(HUD.watermark);
        SettingManager.registerSetting(HUD.tps);
        SettingManager.registerSetting(HUD.speed);
        SettingManager.registerSetting(HUD.side);
        SettingManager.registerSetting(HUD.separation);
        SettingManager.registerSetting(HUD.rotation);
        SettingManager.registerSetting(HUD.red);
        SettingManager.registerSetting(HUD.order);
        SettingManager.registerSetting(HUD.green);
        SettingManager.registerSetting(HUD.direction);
        SettingManager.registerSetting(HUD.dimensioncoords);
        SettingManager.registerSetting(HUD.descriptions);
        SettingManager.registerSetting(HUD.customfont);
        SettingManager.registerSetting(HUD.coordinates);
        SettingManager.registerSetting(HUD.colorMode);
        SettingManager.registerSetting(HUD.blue);
        SettingManager.registerSetting(HUD.arraylist);
        SettingManager.registerSetting(HUD.armor);
        SettingManager.registerSetting(HorseMod.speed);
        SettingManager.registerSetting(HorseMod.jumppower);
        SettingManager.registerSetting(HorseMod.jumpHeight);
        SettingManager.registerSetting(GlowBot.Private);
        SettingManager.registerSetting(GlowBot.prefix);
        SettingManager.registerSetting(Freecam.tracer);
        SettingManager.registerSetting(Freecam.speed);
        SettingManager.registerSetting(Freecam.name);
        SettingManager.registerSetting(Freecam.esp);
        SettingManager.registerSetting(Flight.speed);
        SettingManager.registerSetting(Flight.mode);
        SettingManager.registerSetting(FastBreak.fast);
        SettingManager.registerSetting(FastBreak.delay);
        SettingManager.registerSetting(EntitySpeed.speed);
        SettingManager.registerSetting(EntityESP.width);
        SettingManager.registerSetting(EntityESP.players);
        SettingManager.registerSetting(EntityESP.passive);
        SettingManager.registerSetting(EntityESP.mode);
        SettingManager.registerSetting(EntityESP.items);
        SettingManager.registerSetting(EntityESP.hostile);
        SettingManager.registerSetting(EntityESP.everything);
        SettingManager.registerSetting(EnchColors.red);
        SettingManager.registerSetting(EnchColors.rainbow);
        SettingManager.registerSetting(EnchColors.green);
        SettingManager.registerSetting(EnchColors.blue);
        SettingManager.registerSetting(ElytraControl.speed);
        SettingManager.registerSetting(ElytraControl.mode);
        SettingManager.registerSetting(ElytraControl.cspeed);
        SettingManager.registerSetting(CrystalAura.throughWalls);
        SettingManager.registerSetting(CrystalAura.range);
        SettingManager.registerSetting(CrystalAura.mode);
        SettingManager.registerSetting(ColorSigns.doubleS);
        SettingManager.registerSetting(ColorManager.saturation);
        SettingManager.registerSetting(ColorManager.rendering);
        SettingManager.registerSetting(ColorManager.rainbowSpeed);
        SettingManager.registerSetting(ColorManager.brightness);
        SettingManager.registerSetting(ChunkFinder.red);
        SettingManager.registerSetting(ChunkFinder.mode);
        SettingManager.registerSetting(ChunkFinder.green);
        SettingManager.registerSetting(ChunkFinder.blue);
        SettingManager.registerSetting(ChunkFinder.alpha);
        SettingManager.registerSetting(CameraClip.doubleS);
        SettingManager.registerSetting(BypassFly.usetimer);
        SettingManager.registerSetting(BypassFly.speed);
        SettingManager.registerSetting(BypassFly.phasespeed);
        SettingManager.registerSetting(BypassFly.ground);
        SettingManager.registerSetting(BypassFly.glideSpeed);
        SettingManager.registerSetting(BypassFly.glidePower);
        SettingManager.registerSetting(BypassFly.glide);
        SettingManager.registerSetting(BypassFly.fast);
        SettingManager.registerSetting(BypassFly.bowbowmb);
        SettingManager.registerSetting(BreadCrumbs.width);
        SettingManager.registerSetting(BowSpam.tpsSync);
        SettingManager.registerSetting(BowSpam.speed);
        SettingManager.registerSetting(BoatTravel.speed);
        SettingManager.registerSetting(BoatTravel.bottom);
        SettingManager.registerSetting(BoatJump.speed);
        SettingManager.registerSetting(BoatFly.Yaw);
        SettingManager.registerSetting(BoatFly.upSpeed);
        SettingManager.registerSetting(BoatFly.speed);
        SettingManager.registerSetting(BoatFly.glideSpeed);
        SettingManager.registerSetting(BoatAura.vis_check);
        SettingManager.registerSetting(BoatAura.ridden);
        SettingManager.registerSetting(BoatAura.range);
        SettingManager.registerSetting(Blink.tracer);
        SettingManager.registerSetting(Blink.name);
        SettingManager.registerSetting(Blink.esp);
        SettingManager.registerSetting(BedAura.range);
        SettingManager.registerSetting(AutoWalk.mode);
        SettingManager.registerSetting(AutoReconnect.delay);
        SettingManager.registerSetting(AutoLog.threshold);
        SettingManager.registerSetting(AutoGapple.threshold);
        SettingManager.registerSetting(AutoGapple.regen);
        SettingManager.registerSetting(AutoGapple.healths);
        SettingManager.registerSetting(AntiPotion.weakness);
        SettingManager.registerSetting(AntiPotion.slowness);
        SettingManager.registerSetting(AntiPotion.nausea);
        SettingManager.registerSetting(AntiPotion.levitation);
        SettingManager.registerSetting(AntiPotion.invisibility);
        SettingManager.registerSetting(AntiPotion.fatigue);
        SettingManager.registerSetting(AntiPotion.blindness);
        SettingManager.registerSetting(AntiAFK.delay);
        SettingManager.registerSetting(AntiPackets.AnimationN);
        SettingManager.registerSetting(AntiPackets.ChatMessage);
        SettingManager.registerSetting(AntiPackets.ClickWindow);
        SettingManager.registerSetting(AntiPackets.ClientStatus);
        SettingManager.registerSetting(AntiPackets.CloseWindow);
        SettingManager.registerSetting(AntiPackets.ConfirmTeleport);
        SettingManager.registerSetting(AntiPackets.ConfirmTransaction);
        SettingManager.registerSetting(AntiPackets.CreativeInventoryAction);
        SettingManager.registerSetting(AntiPackets.CustomPayload);
        SettingManager.registerSetting(AntiPackets.EnchantItem);
        SettingManager.registerSetting(AntiPackets.EntityAction);
        SettingManager.registerSetting(AntiPackets.HeldItemChange);
        SettingManager.registerSetting(AntiPackets.Input);
        SettingManager.registerSetting(AntiPackets.KeepAlive);
        SettingManager.registerSetting(AntiPackets.PlaceRecipe);
        SettingManager.registerSetting(AntiPackets.Player);
        SettingManager.registerSetting(AntiPackets.PlayerAbilities);
        SettingManager.registerSetting(AntiPackets.PlayerDigging);
        SettingManager.registerSetting(AntiPackets.PlayerTryUseItem);
        SettingManager.registerSetting(AntiPackets.PlayerTryUseItemOnBlock);
        SettingManager.registerSetting(AntiPackets.RecipeInfo);
        SettingManager.registerSetting(AntiPackets.ResourcePackStatus);
        SettingManager.registerSetting(AntiPackets.SeenAdvancements);
        SettingManager.registerSetting(AntiPackets.Spectate);
        SettingManager.registerSetting(AntiPackets.SteerBoat);
        SettingManager.registerSetting(AntiPackets.TabComplete);
        SettingManager.registerSetting(AntiPackets.UpdateSign);
        SettingManager.registerSetting(AntiPackets.UseEntity);
        SettingManager.registerSetting(AntiPackets.VehicleMove);
        SettingManager.registerSetting(AntiPackets.AdvancementInfo);
        SettingManager.registerSetting(AntiPackets.Animation);
        SettingManager.registerSetting(AntiPackets.BlockAction);
        SettingManager.registerSetting(AntiPackets.BlockBreakAnim);
        SettingManager.registerSetting(AntiPackets.BlockChange);
        SettingManager.registerSetting(AntiPackets.Camera);
        SettingManager.registerSetting(AntiPackets.ChangeGameState);
        SettingManager.registerSetting(AntiPackets.Chat);
        SettingManager.registerSetting(AntiPackets.ChunkData);
        SettingManager.registerSetting(AntiPackets.CloseWindow2);
        SettingManager.registerSetting(AntiPackets.CollectItem);
        SettingManager.registerSetting(AntiPackets.CombatEvent);
        SettingManager.registerSetting(AntiPackets.ConfirmTransaction2);
        SettingManager.registerSetting(AntiPackets.Cooldown);
        SettingManager.registerSetting(AntiPackets.CustomPayload2);
        SettingManager.registerSetting(AntiPackets.CustomSound);
        SettingManager.registerSetting(AntiPackets.DestroyEntities);
        SettingManager.registerSetting(AntiPackets.Disconnect);
        SettingManager.registerSetting(AntiPackets.DisplayObjective);
        SettingManager.registerSetting(AntiPackets.Effect);
        SettingManager.registerSetting(AntiPackets.Entity);
        SettingManager.registerSetting(AntiPackets.EntityAttach);
        SettingManager.registerSetting(AntiPackets.EntityEffect);
        SettingManager.registerSetting(AntiPackets.EntityEquipment);
        SettingManager.registerSetting(AntiPackets.EntityHeadLook);
        SettingManager.registerSetting(AntiPackets.EntityMetadata);
        SettingManager.registerSetting(AntiPackets.EntityProperties);
        SettingManager.registerSetting(AntiPackets.EntityStatus);
        SettingManager.registerSetting(AntiPackets.EntityTeleport);
        SettingManager.registerSetting(AntiPackets.EntityVelocity);
        SettingManager.registerSetting(AntiPackets.Explosion);
        SettingManager.registerSetting(AntiPackets.HeldItemChange2);
        SettingManager.registerSetting(AntiPackets.JoinGame);
        SettingManager.registerSetting(AntiPackets.KeepAlive2);
        SettingManager.registerSetting(AntiPackets.Maps);
        SettingManager.registerSetting(AntiPackets.MoveVehicle);
        SettingManager.registerSetting(AntiPackets.MultiBlockChange);
        SettingManager.registerSetting(AntiPackets.OpenWindow);
        SettingManager.registerSetting(AntiPackets.Particles);
        SettingManager.registerSetting(AntiPackets.PlaceGhostRecipe);
        SettingManager.registerSetting(AntiPackets.PlayerAbilities2);
        SettingManager.registerSetting(AntiPackets.PlayerListHeaderFooter);
        SettingManager.registerSetting(AntiPackets.PlayerListItem);
        SettingManager.registerSetting(AntiPackets.PlayerPosLook);
        SettingManager.registerSetting(AntiPackets.RecipeBook);
        SettingManager.registerSetting(AntiPackets.RemoveEntityEffect);
        SettingManager.registerSetting(AntiPackets.ResourcePackSend);
        SettingManager.registerSetting(AntiPackets.Respawn);
        SettingManager.registerSetting(AntiPackets.ScoreboardObjective);
        SettingManager.registerSetting(AntiPackets.SelectAdvancementsTab);
        SettingManager.registerSetting(AntiPackets.ServerDifficulty);
        SettingManager.registerSetting(AntiPackets.SetExperience);
        SettingManager.registerSetting(AntiPackets.SetPassengers);
        SettingManager.registerSetting(AntiPackets.SetSlot);
        SettingManager.registerSetting(AntiPackets.SignEditorOpen);
        SettingManager.registerSetting(AntiPackets.SoundEffect);
        SettingManager.registerSetting(AntiPackets.SpawnExperienceOrb);
        SettingManager.registerSetting(AntiPackets.SpawnGlobalEntity);
        SettingManager.registerSetting(AntiPackets.SpawnMob);
        SettingManager.registerSetting(AntiPackets.SpawnObject);
        SettingManager.registerSetting(AntiPackets.SpawnPainting);
        SettingManager.registerSetting(AntiPackets.SpawnPlayer);
        SettingManager.registerSetting(AntiPackets.SpawnPosition);
        SettingManager.registerSetting(AntiPackets.Statistics);
        SettingManager.registerSetting(AntiPackets.TabComplete2);
        SettingManager.registerSetting(AntiPackets.Teams);
        SettingManager.registerSetting(AntiPackets.TimeUpdate);
        SettingManager.registerSetting(AntiPackets.Title);
        SettingManager.registerSetting(AntiPackets.UnloadChunk);
        SettingManager.registerSetting(AntiPackets.UpdateBossInfo);
        SettingManager.registerSetting(AntiPackets.UpdateHealth);
        SettingManager.registerSetting(AntiPackets.UpdateScore);
        SettingManager.registerSetting(AntiPackets.UpdateTileEntity);
        SettingManager.registerSetting(AntiPackets.UseBed);
        SettingManager.registerSetting(AntiPackets.WindowItems);
        SettingManager.registerSetting(AntiPackets.WindowProperty);
        SettingManager.registerSetting(AntiPackets.WorldBorder);
        SettingManager.registerSetting(NameChanger.GlowskiBroski);
        SettingManager.registerSetting(NameChanger.GlowskiBroski2B);
        SettingManager.registerSetting(NameChanger.GlowClient);
        SettingManager.registerSetting(NameChanger.TheDark_Emperor);
        SettingManager.registerSetting(NameChanger.Infernales_);
        SettingManager.registerSetting(NameChanger.Lag_Tyrant);
        SettingManager.registerSetting(NameChanger.furleoxnop);
        SettingManager.registerSetting(NameChanger.FallsGreen);
        SettingManager.registerSetting(AutoLog.totem);
        SettingManager.registerSetting(AutoLog.totemThreshold);
        SettingManager.registerSetting(CrystalAura.friendDetect);
        SettingManager.registerSetting(BlockFinder.range2);
        SettingManager.registerSetting(BlockFinder.tracer);
        SettingManager.registerSetting(BlockFinder.outline);
        SettingManager.registerSetting(Dupe.mode);
        SettingManager.registerSetting(Command.prefix);
    }
}

