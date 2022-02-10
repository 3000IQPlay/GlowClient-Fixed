//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.indev.EmpsShit;

import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.extra.pepsimod.PUtils;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;
import com.client.glowclient.utils.render.fonts.MinecraftFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUDImpact
extends ToggleMod {
    public static MinecraftFontRenderer FONT = Fonts.ImpactFont;

    public HUDImpact() {
        super(Category.JEWISHTRICKS, "HUDImpact", false, -1, "Shows all HUD features");
    }

    private String generateTickRateText() {
        StringBuilder builder = new StringBuilder("");
        TickrateRecorder.TickRateData data = TickrateRecorder.getTickData();
        int factor = 100;
        int sections = data.getSampleSize() / factor;
        if (sections * factor < data.getSampleSize()) {
            TickrateRecorder.TickRateData.CalculationData point = data.getPoint();
            builder.append(String.format("%.2f", point.getAverage()));
            if (sections > 0) {
                builder.append(", ");
            }
        }
        if (sections > 0) {
            for (int i = sections; i > 0; --i) {
                int at = i * factor;
                TickrateRecorder.TickRateData.CalculationData point = data.getPoint(at);
                builder.append(String.format("%.2f", point.getAverage()));
                if (i - 1 == 0) continue;
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    @Override
    public void onEnabled() {
        ModuleManager.getModuleFromName("HUD").disable();
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        int posX = 1;
        int posY = 1;
        String pitch = String.format("%.2f", Float.valueOf(MC.player.rotationPitch));
        String yaw = String.format("%.2f", Float.valueOf(MC.player.rotationYaw));
        String y = String.format("%.1f", MC.player.posY);
        String x = String.format("%.1f", MC.player.posX);
        String z = String.format("%.1f", MC.player.posZ);
        String nx = String.format("%.3f", MC.player.posX / 8.0);
        String nz = String.format("%.3f", MC.player.posZ / 8.0);
        String ox = String.format("%.3f", MC.player.posX * 8.0);
        String oz = String.format("%.3f", MC.player.posZ * 8.0);
        double x2 = posX;
        double y2 = posY;
        String watermark = "lmpact \u00a7f4.2";
        int Color2 = Colors.toRGBA(70, 160, 175, 255);
        this.drawHUDText(watermark, 4.0, 5.0, Color2, builder, scaledresolution);
        this.drawHUDText("FPS \u00a7f" + Minecraft.getDebugFPS(), 4.0, 16.0, Color2, builder, scaledresolution);
        if (MC.getConnection().getPlayerInfo(MC.player.getUniqueID()) != null) {
            this.drawHUDText("Ping \u00a7f" + MC.getConnection().getPlayerInfo(MC.player.getUniqueID()).getResponseTime(), 4.0, 27.0, Color2, builder, scaledresolution);
        }
        this.drawHUDText("TPS \u00a7f" + this.generateTickRateText(), 4.0, 38.0, Color2, builder, scaledresolution);
        this.drawHUDTextInfo("X: \u00a7f", x, posX - 2, posY - 14, Color2, builder, scaledresolution);
        this.drawHUDTextInfo("Y: \u00a7f", y, posX - 2, posY - 3, Color2, builder, scaledresolution);
        this.drawHUDTextInfo("Z: \u00a7f", z, posX - 2, posY + 8, Color2, builder, scaledresolution);
        int xPos = scaledresolution.getScaledWidth() / 2 + 129;
        int yPos = scaledresolution.getScaledHeight() - 55;
        int i = 0;
        xPos -= 103;
        for (int j = 0; j < 4; ++j) {
            ItemStack stack = PUtils.getWearingArmor(j);
            builder.reset().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(stack, xPos - 16 + 11 * i++, yPos).itemOverlay(stack, xPos - 27 + 11 * i++, yPos).task(SurfaceBuilder::disableItemRendering);
        }
        int pusY = 5;
        int n = 10;
        String modname = "Middle Click Friend";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY, Colors.toRGBA(255, 150, 150, 200), builder, scaledresolution);
        modname = "Levitation Control";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(175, 150, 175, 200), builder, scaledresolution);
        modname = "Auto Reconnect";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(225, 150, 150, 200), builder, scaledresolution);
        modname = "Velocity \u00a770% 0%";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(225, 175, 150, 200), builder, scaledresolution);
        modname = "CameraClip";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(150, 225, 225, 200), builder, scaledresolution);
        modname = "Auto Totem";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(150, 225, 175, 200), builder, scaledresolution);
        modname = "Nametags";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(175, 210, 235, 200), builder, scaledresolution);
        modname = "Anti Blind";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(200, 200, 150, 200), builder, scaledresolution);
        modname = "Auto Fish";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(175, 150, 175, 200), builder, scaledresolution);
        modname = "Respawn";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(225, 175, 150, 200), builder, scaledresolution);
        modname = "Tracers";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(200, 200, 150, 200), builder, scaledresolution);
        modname = "Riding";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(225, 175, 150, 200), builder, scaledresolution);
        modname = "Light";
        this.drawHUDText(modname, (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, modname) - 4.0, pusY += n, Colors.toRGBA(255, 150, 150, 200), builder, scaledresolution);
        pusY += n;
    }

    public void drawHUDText(String name, double posX, double posY, int Color2, SurfaceBuilder builder, ScaledResolution scaledresolution) {
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + 1.0, posY + 1.0, true).color(Color2).text(name, posX, posY);
        } else {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX, posY, true);
        }
    }

    public void drawHUDTextInfo(String name, String setting, double posX, double posY, int Color2, SurfaceBuilder builder, ScaledResolution scaledresolution) {
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0 + 1.0, true).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0 + 1.0, true).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0);
        } else {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0, true).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0, true);
        }
    }
}

