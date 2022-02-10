//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.GuiConnecting
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.event.world.WorldEvent$Unload
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class AutoReconnect
extends ToggleMod {
    private static ServerData lastConnectedServer;
    public static boolean hasAutoLogged;
    public static final SettingDouble delay;

    public void updateLastConnectedServer() {
        ServerData data = Globals.MC.getCurrentServerData();
        if (data != null) {
            lastConnectedServer = data;
        }
    }

    public AutoReconnect() {
        super(Category.SERVER, "AutoReconnect", false, -1, "Automatically reconnects to the server");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event) {
        try {
            if (!hasAutoLogged && event.getGui() instanceof GuiDisconnected && !(event.getGui() instanceof GuiDisconnectedOverride)) {
                this.updateLastConnectedServer();
                GuiDisconnected disconnected = (GuiDisconnected)event.getGui();
                event.setGui((GuiScreen)new GuiDisconnectedOverride(disconnected.parentScreen, "connect.failed", disconnected.message, disconnected.reason, delay.getDouble()));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        hasAutoLogged = false;
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        this.updateLastConnectedServer();
    }

    static {
        hasAutoLogged = false;
        delay = SettingUtils.settingDouble("AutoReconnect", "Delay", "Reconnect glideSpeed", 5.0, 0.5, 0.0, 30.0);
    }

    public static class GuiDisconnectedOverride
    extends GuiDisconnected {
        private GuiScreen parent;
        private ITextComponent message;
        private long reconnectTime;
        private GuiButton reconnectButton = null;

        public GuiDisconnectedOverride(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp, String reason, double delay) {
            super(screen, reasonLocalizationKey, chatComp);
            this.parent = screen;
            this.message = chatComp;
            this.reconnectTime = System.currentTimeMillis() + (long)(delay * 1000.0);
            try {
                ReflectionHelper.setPrivateValue(GuiDisconnected.class, this, reason, "reason", "reason", "a");
            }
            catch (Exception e) {
                Utils.printStackTrace(e);
            }
        }

        public long getTimeUntilReconnect() {
            return this.reconnectTime - System.currentTimeMillis();
        }

        public double getTimeUntilReconnectInSeconds() {
            return (double)this.getTimeUntilReconnect() / 1000.0;
        }

        public String getFormattedReconnectText() {
            return String.format("Reconnecting (%.1f)...", this.getTimeUntilReconnectInSeconds());
        }

        public ServerData getLastConnectedServerData() {
            return lastConnectedServer != null ? lastConnectedServer : Globals.MC.getCurrentServerData();
        }

        private void reconnect() {
            ServerData data = this.getLastConnectedServerData();
            if (data != null) {
                FMLClientHandler.instance().showGuiScreen((Object)new GuiConnecting(this.parent, Globals.MC, data));
            }
        }

        public void initGui() {
            super.initGui();
            List multilineMessage = this.fontRenderer.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
            int textHeight = multilineMessage.size() * this.fontRenderer.FONT_HEIGHT;
            if (this.getLastConnectedServerData() != null) {
                this.reconnectButton = new GuiButton(this.buttonList.size(), this.width / 2 - 100, this.height / 2 + textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 23, this.getFormattedReconnectText());
                this.buttonList.add(this.reconnectButton);
            }
        }

        protected void actionPerformed(GuiButton button) throws IOException {
            super.actionPerformed(button);
            if (button.equals((Object)this.reconnectButton)) {
                // empty if block
            }
        }

        public void updateScreen() {
            super.updateScreen();
            if (this.reconnectButton != null) {
                this.reconnectButton.displayString = this.getFormattedReconnectText();
            }
            if (System.currentTimeMillis() >= this.reconnectTime) {
                this.reconnect();
            }
        }
    }
}

