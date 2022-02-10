/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 */
package com.client.glowclient.utils.base.mod;

import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingInt;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class Module {
    private final String modName;
    private final String modDescription;
    private final Category category;
    public SettingBoolean toggled;
    public SettingInt key;
    public KeyBinding keybind;
    private boolean registered = false;
    public boolean isSpoofingRotation = false;
    protected SimpleTimer simpleTimer = new SimpleTimer();
    protected static Minecraft mc = FMLClientHandler.instance().getClient();
    public int randomRed;
    public int randomGreen;
    public int randomBlue;

    public Module(Category category, String name, String desc) {
        this.modName = name;
        this.modDescription = desc;
        this.category = category;
    }

    public Module(Category category, String name) {
        this(category, name, "");
    }

    public final void load() {
        if (this.isEnabled()) {
            this.start();
        }
        this.onLoad();
    }

    public final void unload() {
        this.stop();
        this.onUnload();
    }

    public final void start() {
        if (this.register()) {
            this.onEnabled();
            this.decideRandomHUD();
        }
    }

    public final void stop() {
        if (this.unregister()) {
            this.onDisabled();
        }
    }

    public void enable() {
        this.start();
    }

    public void disable() {
        this.stop();
    }

    public final boolean register() {
        if (!this.registered) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            this.registered = true;
            return true;
        }
        return false;
    }

    public final boolean unregister() {
        if (this.registered) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            this.registered = false;
            return true;
        }
        return false;
    }

    public final String getModName() {
        return this.modName;
    }

    public final String getModDescription() {
        return this.modDescription;
    }

    public Category getModCategory() {
        return this.category;
    }

    public String getHUDTag() {
        return "";
    }

    public String getNameAndTag() {
        if (!this.getHUDTag().equals("") && this.getHUDTag() != null) {
            return this.getModName() + "\u00a7f[\u00a7f" + this.getHUDTag() + "\u00a7f]";
        }
        return this.getModName();
    }

    private void decideRandomHUD() {
        Random random = new Random();
        this.randomRed = random.nextInt(255) + 50;
        this.randomGreen = random.nextInt(255) + 50;
        this.randomBlue = random.nextInt(255) + 50;
    }

    public boolean isDrawn() {
        return false;
    }

    protected void onLoad() {
    }

    protected void onUnload() {
    }

    protected void onEnabled() {
    }

    protected void onDisabled() {
    }

    public abstract boolean isEnabled();

    public int getBind() {
        return this.key.getInt();
    }

    public void setBind(int bind) {
        this.key.setValue(bind);
    }

    protected void registerKeybind(String name, int key) {
        this.keybind = new KeyBinding(name, key == -1 ? 0 : key, "GlowClient");
        ClientRegistry.registerKeyBinding((KeyBinding)this.keybind);
    }
}

