//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiYesNoCallback
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 */
package com.client.glowclient.clickgui.subguis;

import com.client.glowclient.clickgui.ClickGUI;
import com.client.glowclient.clickgui.utils.GuiUtils;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.builder.SettingManager;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

public class GuiAntiPackets
extends GuiScreen
implements GuiYesNoCallback {
    private static GuiAntiPackets INSTANCE;
    private SurfaceBuilder builder;
    private GuiScreen parentScreen;
    protected String screenTitle;
    public ScaledResolution scaledRes;
    private GuiButton buttonDone;

    public GuiAntiPackets(GuiScreen screen) {
        INSTANCE = this;
        this.builder = new SurfaceBuilder();
        this.screenTitle = "";
        this.scaledRes = new ScaledResolution(Globals.MC);
        this.parentScreen = screen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        SurfaceHelper.drawRect(0, 0, this.width, this.height, Colors.toRGBA(0, 0, 0, 150));
        GuiUtils.drawButtonMenu("<- Return to GUI", 2, 2, 100, 20, mouseX, mouseY);
        String title = "Cancellable Client Packets";
        this.builder.reset().fontRenderer(Fonts.TITLEFONT).color(Colors.SHADOW).text(title, (double)(this.scaledRes.getScaledWidth() / 2) - SurfaceHelper.getStringWidth(Fonts.TITLEFONT, title) / 2.0 + 1.0, 2.0, false).color(Colors.WHITE).text(title, (double)(this.scaledRes.getScaledWidth() / 2) - SurfaceHelper.getStringWidth(Fonts.TITLEFONT, title) / 2.0, 2.0, false);
        String title2 = ModuleManager.getModuleFromName("AntiPackets").getModDescription();
        this.builder.reset().fontRenderer(HUD.FONT).color(Colors.SHADOW).text(title2, (double)(this.scaledRes.getScaledWidth() / 2) - SurfaceHelper.getStringWidth(HUD.FONT, title2) / 2.0 + 1.0, 2.0 + SurfaceHelper.getStringHeight(Fonts.TITLEFONT) + 4.0, false).color(Colors.HUDGRAY).text(title2, (double)(this.scaledRes.getScaledWidth() / 2) - SurfaceHelper.getStringWidth(HUD.FONT, title2) / 2.0, 2.0 + SurfaceHelper.getStringHeight(Fonts.TITLEFONT) + 4.0, false);
        int y = 30;
        int x = 2;
        for (SettingBoolean setting : SettingManager.getSettingBooleans()) {
            if (!setting.getModName().equals("AntiPackets") || setting.getName().equals("Toggled")) continue;
            GuiUtils.drawButtonAntiPacket(setting, x, y, 100, 20, mouseX, mouseY);
            if ((x += 102) < this.scaledRes.getScaledWidth() - 102) continue;
            x = 2;
            y += 32;
        }
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 8, 0xFFFFFF);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            Globals.MC.displayGuiScreen(this.parentScreen);
        }
    }

    public void initGui() {
        this.buttonDone = new GuiButton(0, 2, 2, 100, 20, I18n.format((String)"", (Object[])new Object[0]));
        this.buttonList.add(this.buttonDone);
        int y = 30;
        int x = 2;
        for (SettingBoolean setting : SettingManager.getSettingBooleans()) {
            if (!setting.getModName().equals("AntiPackets") || setting.getName().equals("Toggled")) continue;
            this.buttonList.add(new GuiButton(0, x, y, 100, 20, I18n.format((String)setting.getName(), (Object[])new Object[0])));
            if ((x += 102) < this.scaledRes.getScaledWidth() - 102) continue;
            x = 2;
            y += 32;
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.buttonDone.mousePressed(Globals.MC, mouseX, mouseY)) {
            Globals.MC.displayGuiScreen((GuiScreen)ClickGUI.getInstance());
        }
        for (SettingBoolean setting : SettingManager.getSettingBooleans()) {
            for (GuiButton button : this.buttonList) {
                if (!button.mousePressed(Globals.MC, mouseX, mouseY) || !setting.getModName().equals("AntiPackets") || setting.getName().equals("Toggled") || !setting.getName().equals(button.displayString)) continue;
                GuiUtils.toggleSetting(setting);
            }
        }
    }

    public void updateScreen() {
        super.updateScreen();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public static GuiAntiPackets getInstance() {
        return INSTANCE;
    }
}

