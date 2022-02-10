/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package com.client.glowclient;

import com.client.glowclient.clickgui.ClickGUI;
import com.client.glowclient.clickgui.Windows;
import com.client.glowclient.commands.BindCommand;
import com.client.glowclient.commands.EnemyCommand;
import com.client.glowclient.commands.FakeMessageCommand;
import com.client.glowclient.commands.FriendCommand;
import com.client.glowclient.commands.GoToCommand;
import com.client.glowclient.commands.HelpCommand;
import com.client.glowclient.commands.HistoryCommand;
import com.client.glowclient.commands.ModuleCommand;
import com.client.glowclient.commands.PeekCommand;
import com.client.glowclient.commands.PrefixCommand;
import com.client.glowclient.commands.SaveCommand;
import com.client.glowclient.commands.TPCommand;
import com.client.glowclient.commands.ToggleCommand;
import com.client.glowclient.commands.UnbindCommand;
import com.client.glowclient.commands.YawCoordinateCommand;
import com.client.glowclient.mods.AntiAFK;
import com.client.glowclient.mods.AntiPackets;
import com.client.glowclient.mods.AntiPotion;
import com.client.glowclient.mods.AutoArmor;
import com.client.glowclient.mods.AutoEat;
import com.client.glowclient.mods.AutoFish;
import com.client.glowclient.mods.AutoGapple;
import com.client.glowclient.mods.AutoLog;
import com.client.glowclient.mods.AutoMine;
import com.client.glowclient.mods.AutoReconnect;
import com.client.glowclient.mods.AutoRespawn;
import com.client.glowclient.mods.AutoTool;
import com.client.glowclient.mods.AutoTotem;
import com.client.glowclient.mods.AutoWalk;
import com.client.glowclient.mods.BackSpeed;
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
import com.client.glowclient.mods.ChamsMod;
import com.client.glowclient.mods.ChunkFinder;
import com.client.glowclient.mods.ColorSigns;
import com.client.glowclient.mods.Criticals;
import com.client.glowclient.mods.CrystalAura;
import com.client.glowclient.mods.ElytraControl;
import com.client.glowclient.mods.EnchColors;
import com.client.glowclient.mods.EntityControl;
import com.client.glowclient.mods.EntityESP;
import com.client.glowclient.mods.EntityHunger;
import com.client.glowclient.mods.EntitySpeed;
import com.client.glowclient.mods.FastBreak;
import com.client.glowclient.mods.FastUse;
import com.client.glowclient.mods.Flight;
import com.client.glowclient.mods.FontChat;
import com.client.glowclient.mods.Freecam;
import com.client.glowclient.mods.FullBright;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.mods.HighJump;
import com.client.glowclient.mods.HorseMod;
import com.client.glowclient.mods.IceSpeed;
import com.client.glowclient.mods.InvMove;
import com.client.glowclient.mods.Jesus;
import com.client.glowclient.mods.KillAura;
import com.client.glowclient.mods.Knockback;
import com.client.glowclient.mods.LiquidSpeed;
import com.client.glowclient.mods.LogoutESP;
import com.client.glowclient.mods.MCF;
import com.client.glowclient.mods.Nametags;
import com.client.glowclient.mods.NoFall;
import com.client.glowclient.mods.NoGhostBlocks;
import com.client.glowclient.mods.NoPush;
import com.client.glowclient.mods.NoRender;
import com.client.glowclient.mods.NoRotate;
import com.client.glowclient.mods.NoSlowdown;
import com.client.glowclient.mods.NoVoid;
import com.client.glowclient.mods.NoWeather;
import com.client.glowclient.mods.Nuker;
import com.client.glowclient.mods.PeekMod;
import com.client.glowclient.mods.Phase;
import com.client.glowclient.mods.PlayerList;
import com.client.glowclient.mods.PortalGodMode;
import com.client.glowclient.mods.Reach;
import com.client.glowclient.mods.SafeWalk;
import com.client.glowclient.mods.Scaffold;
import com.client.glowclient.mods.SkinBlinker;
import com.client.glowclient.mods.Speed;
import com.client.glowclient.mods.Sprint;
import com.client.glowclient.mods.Step;
import com.client.glowclient.mods.StorageESP;
import com.client.glowclient.mods.TabMod;
import com.client.glowclient.mods.TimerMod;
import com.client.glowclient.mods.Tracers;
import com.client.glowclient.mods.Trajectories;
import com.client.glowclient.mods.TunnelSpeed;
import com.client.glowclient.mods.Xray;
import com.client.glowclient.mods.Yaw;
import com.client.glowclient.mods.background.BackgroundFixMod;
import com.client.glowclient.mods.background.EventMod;
import com.client.glowclient.mods.background.GuiMod;
import com.client.glowclient.mods.background.TextureDownloader;
import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.mods.indev.BlockFinder;
import com.client.glowclient.mods.indev.Dupe;
import com.client.glowclient.mods.indev.EmpsShit.HUDImpact;
import com.client.glowclient.mods.indev.EmpsShit.NameChanger;
import com.client.glowclient.mods.indev.EmpsShit.NametagsImpact;
import com.client.glowclient.mods.indev.EmpsShit.TracersImpact;
import com.client.glowclient.mods.indev.ExploitDev;
import com.client.glowclient.mods.indev.GlowBot;
import com.client.glowclient.mods.indev.invis.RemoveEntities;
import com.client.glowclient.mods.indev.invis.SpawnPlayer;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.command.builder.CommandManager;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="glowclient", name="GlowClient", useMetadata=true, clientSideOnly=true)
public class GlowClient {
    public static boolean privateVersion = true;

    public static String getVersion() {
        return "beta-1.8pre";
    }

    private static void registerMods() {
        ModuleManager.registerModule(new GuiMod());
        ModuleManager.registerModule(new TextureDownloader());
        ModuleManager.registerModule(new TickrateRecorder());
        ModuleManager.registerModule(new EventMod());
        ModuleManager.registerModule(new BackgroundFixMod());
        ModuleManager.registerModule(new PlayerList());
        ModuleManager.registerModule(new BowSpam());
        ModuleManager.registerModule(new EnchColors());
        ModuleManager.registerModule(new PeekMod());
        ModuleManager.registerModule(new AntiAFK());
        ModuleManager.registerModule(new AntiPackets());
        ModuleManager.registerModule(new AntiPotion());
        ModuleManager.registerModule(new AutoArmor());
        ModuleManager.registerModule(new AutoEat());
        ModuleManager.registerModule(new AutoFish());
        ModuleManager.registerModule(new AutoGapple());
        ModuleManager.registerModule(new AutoLog());
        ModuleManager.registerModule(new AutoMine());
        ModuleManager.registerModule(new AutoReconnect());
        ModuleManager.registerModule(new AutoRespawn());
        ModuleManager.registerModule(new AutoTool());
        ModuleManager.registerModule(new AutoTotem());
        ModuleManager.registerModule(new AutoWalk());
        ModuleManager.registerModule(new BackSpeed());
        ModuleManager.registerModule(new BedAura());
        ModuleManager.registerModule(new Blink());
        ModuleManager.registerModule(new BoatAura());
        ModuleManager.registerModule(new BoatFly());
        ModuleManager.registerModule(new BoatJump());
        ModuleManager.registerModule(new BreadCrumbs());
        ModuleManager.registerModule(new BypassFly());
        ModuleManager.registerModule(new CameraClip());
        ModuleManager.registerModule(new ChamsMod());
        ModuleManager.registerModule(new ChunkFinder());
        ModuleManager.registerModule(new ColorSigns());
        ModuleManager.registerModule(new Criticals());
        ModuleManager.registerModule(new CrystalAura());
        ModuleManager.registerModule(new ElytraControl());
        ModuleManager.registerModule(new EntityControl());
        ModuleManager.registerModule(new EntityESP());
        ModuleManager.registerModule(new EntityHunger());
        ModuleManager.registerModule(new EntitySpeed());
        ModuleManager.registerModule(new FastBreak());
        ModuleManager.registerModule(new FastUse());
        ModuleManager.registerModule(new Flight());
        ModuleManager.registerModule(new FontChat());
        ModuleManager.registerModule(new Freecam());
        ModuleManager.registerModule(new InvMove());
        ModuleManager.registerModule(new HighJump());
        ModuleManager.registerModule(new HorseMod());
        ModuleManager.registerModule(new HUD());
        ModuleManager.registerModule(new IceSpeed());
        ModuleManager.registerModule(new Jesus());
        ModuleManager.registerModule(new KillAura());
        ModuleManager.registerModule(new Knockback());
        ModuleManager.registerModule(new LiquidSpeed());
        ModuleManager.registerModule(new LogoutESP());
        ModuleManager.registerModule(new FullBright());
        ModuleManager.registerModule(new MCF());
        ModuleManager.registerModule(new Nametags());
        ModuleManager.registerModule(new NoFall());
        ModuleManager.registerModule(new NoPush());
        ModuleManager.registerModule(new NoRender());
        ModuleManager.registerModule(new NoRotate());
        ModuleManager.registerModule(new NoSlowdown());
        ModuleManager.registerModule(new NoVoid());
        ModuleManager.registerModule(new NoWeather());
        ModuleManager.registerModule(new Nuker());
        ModuleManager.registerModule(new Phase());
        ModuleManager.registerModule(new PortalGodMode());
        ModuleManager.registerModule(new Reach());
        ModuleManager.registerModule(new SafeWalk());
        ModuleManager.registerModule(new Scaffold());
        ModuleManager.registerModule(new SkinBlinker());
        ModuleManager.registerModule(new Sprint());
        ModuleManager.registerModule(new Step());
        ModuleManager.registerModule(new StorageESP());
        ModuleManager.registerModule(new Speed());
        ModuleManager.registerModule(new TabMod());
        ModuleManager.registerModule(new TimerMod());
        ModuleManager.registerModule(new Tracers());
        ModuleManager.registerModule(new Trajectories());
        ModuleManager.registerModule(new TunnelSpeed());
        ModuleManager.registerModule(new Xray());
        ModuleManager.registerModule(new Yaw());
        ModuleManager.registerModule(new BlockFinder());
        ModuleManager.registerModule(new NoGhostBlocks());
        ModuleManager.registerModule(new GlowBot());
        ModuleManager.registerModule(new SpawnPlayer());
        ModuleManager.registerModule(new RemoveEntities());
        ModuleManager.registerModule(new Dupe());
        if (privateVersion) {
            ModuleManager.registerModule(new ExploitDev());
            ModuleManager.registerModule(new BoatTravel());
            ModuleManager.registerModule(new HUDImpact());
            ModuleManager.registerModule(new NametagsImpact());
            ModuleManager.registerModule(new TracersImpact());
            ModuleManager.registerModule(new NameChanger());
        }
    }

    private static void registerCommands() {
        CommandManager.registerCommand(new ToggleCommand());
        CommandManager.registerCommand(new HelpCommand());
        CommandManager.registerCommand(new FriendCommand());
        CommandManager.registerCommand(new EnemyCommand());
        CommandManager.registerCommand(new HistoryCommand());
        CommandManager.registerCommand(new PrefixCommand());
        CommandManager.registerCommand(new YawCoordinateCommand());
        CommandManager.registerCommand(new TPCommand());
        CommandManager.registerCommand(new SaveCommand());
        CommandManager.registerCommand(new BindCommand());
        CommandManager.registerCommand(new UnbindCommand());
        CommandManager.registerCommand(new PeekCommand());
        CommandManager.registerCommand(new GoToCommand());
        for (Module mod : ModuleManager.getMods()) {
            if (!(mod instanceof ToggleMod)) continue;
            CommandManager.registerCommand(new ModuleCommand(mod));
        }
        if (privateVersion) {
            CommandManager.registerCommand(new FakeMessageCommand());
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GlowClient.registerMods();
        GlowClient.registerCommands();
        Utils.getFileManager().loadAll();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new ClickGUI();
        if (privateVersion) {
            ClickGUI.getInstance().setWindows(Windows.windowJewishTricks, Windows.windowOther, Windows.windowServer, Windows.windowRender, Windows.windowMovement, Windows.windowPlayer, Windows.windowCombat);
        } else {
            ClickGUI.getInstance().setWindows(Windows.windowOther, Windows.windowServer, Windows.windowRender, Windows.windowMovement, Windows.windowPlayer, Windows.windowCombat);
        }
        ClickGUI.getInstance().initWindows();
        ModuleManager.forEach(Module::load);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}

