/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.clickgui.utils;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;

public class GuiUtils {
    public static void drawButtonMenu(String name, int x, int y, int w, int h, int mouseX, int mouseY) {
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        SurfaceBuilder builder = new SurfaceBuilder();
        int Color2 = mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h ? Colors.toRGBA(150, 150, 150, 50) : Colors.toRGBA(0, 0, 0, 0);
        SurfaceHelper.drawRect(x, y, w, h, Colors.toRGBA(0, 0, 0, 150));
        SurfaceHelper.drawRect(x, y, w, h, Color2);
        SurfaceHelper.drawRect(x, y, w, 1, Colors.toRGBA(red, green, blue, 255));
        SurfaceHelper.drawRect(x, y + h, w, 1, Colors.toRGBA(red, green, blue, 255));
        if (HUD.FONT != null) {
            builder.reset().fontRenderer(HUD.FONT).color(Colors.SHADOW).textcentered(name, x + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0 + 1.0, false).color(Colors.WHITE).textcentered(name, x + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0, false);
        } else {
            SurfaceHelper.drawTextShadowCentered(name, x + w / 2, y + h / 2, Colors.WHITE);
        }
    }

    public static void drawButtonMenuDesc(String name, int x, int y, int w, int h, int mouseX, int mouseY, String desc) {
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        SurfaceBuilder builder = new SurfaceBuilder();
        int Color2 = mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h ? Colors.toRGBA(150, 150, 150, 50) : Colors.toRGBA(0, 0, 0, 0);
        SurfaceHelper.drawRect(x, y, w, h, Colors.toRGBA(0, 0, 0, 150));
        SurfaceHelper.drawRect(x, y, w, h, Color2);
        SurfaceHelper.drawRect(x, y, w, 1, Colors.toRGBA(red, green, blue, 255));
        SurfaceHelper.drawRect(x, y + h, w, 1, Colors.toRGBA(red, green, blue, 255));
        if (HUD.FONT != null) {
            builder.reset().fontRenderer(HUD.FONT).color(Colors.SHADOW).textcentered(name, x + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0 + 1.0, false).color(Colors.WHITE).textcentered(name, x + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0, false);
        } else {
            SurfaceHelper.drawTextShadowCentered(name, x + w / 2, y + h / 2, Colors.WHITE);
        }
        if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h && desc != null && HUD.descriptions.getBoolean()) {
            SurfaceHelper.drawOutlinedRectShaded(mouseX, mouseY + 2, (int)SurfaceHelper.getStringWidth(HUD.FONT, desc) + 2, -12, Colors.toRGBA(10, 10, 10, 255), 175, 1.0f);
            builder.reset().fontRenderer(HUD.FONT).color(Colors.WHITE).text(desc, mouseX + 1, mouseY - 7);
        }
    }

    public static void drawButtonAntiPacket(SettingBoolean sname, int x, int y, int w, int h, int mouseX, int mouseY) {
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        SurfaceBuilder builder = new SurfaceBuilder();
        String desc = sname.getDescription();
        int Color2 = mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h ? Colors.toRGBA(150, 150, 150, 50) : Colors.toRGBA(0, 0, 0, 0);
        SurfaceHelper.drawRect(x, y, w, h, Colors.toRGBA(0, 0, 0, 150));
        SurfaceHelper.drawRect(x, y, w, h, Color2);
        SurfaceHelper.drawRect(x, y, w, 1, Colors.toRGBA(red, green, blue, 255));
        SurfaceHelper.drawRect(x, y + h, w, 1, Colors.toRGBA(red, green, blue, 255));
        if (SurfaceHelper.getStringWidth(HUD.FONT, sname.getName()) < 100.0) {
            if (!sname.getBoolean()) {
                if (HUD.FONT != null) {
                    builder.reset().fontRenderer(HUD.FONT).color(Colors.SHADOW).textcentered(sname.getName(), x + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0 + 1.0, false).color(Colors.GRAY).textcentered(sname.getName(), x + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0, false);
                } else {
                    SurfaceHelper.drawTextShadowCentered(sname.getName(), x + w / 2, y + h / 2, Colors.GRAY);
                }
            } else if (HUD.FONT != null) {
                builder.reset().fontRenderer(HUD.FONT).color(Colors.SHADOW).textcentered(sname.getName(), x + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0 + 1.0, false).color(Colors.WHITE).textcentered(sname.getName(), x + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(HUD.FONT) / 2.0, false);
            } else {
                SurfaceHelper.drawTextShadowCentered(sname.getName(), x + w / 2, y + h / 2, Colors.WHITE);
            }
        } else if (!sname.getBoolean()) {
            if (HUD.FONT != null) {
                builder.reset().fontRenderer(Fonts.VERDANA75).color(Colors.SHADOW).textcentered(sname.getName(), x + 15 + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(Fonts.VERDANA75) / 2.0 + 1.0, false).color(Colors.GRAY).textcentered(sname.getName(), x + 15 + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(Fonts.VERDANA75) / 2.0, false);
            } else {
                SurfaceHelper.drawText(sname.getName(), (double)(x - 2 + w / 2) - SurfaceHelper.getStringWidth(Fonts.VERDANA75, sname.getName()) / 2.0, y - 2 + h / 2, Colors.GRAY, 0.75);
            }
        } else if (HUD.FONT != null) {
            builder.reset().fontRenderer(Fonts.VERDANA75).color(Colors.SHADOW).textcentered(sname.getName(), x + 15 + w / 2 + 1, (double)(y + h / 2) - SurfaceHelper.getStringHeight(Fonts.VERDANA75) / 2.0 + 1.0, false).color(Colors.WHITE).textcentered(sname.getName(), x + 15 + w / 2, (double)(y + h / 2) - SurfaceHelper.getStringHeight(Fonts.VERDANA75) / 2.0, false);
        } else {
            SurfaceHelper.drawText(sname.getName(), (double)(x - 2 + w / 2) - SurfaceHelper.getStringWidth(Fonts.VERDANA75, sname.getName()) / 2.0, y - 2 + h / 2, Colors.WHITE, 0.75);
        }
    }

    public static void toggleMod(Module module) {
        if (!module.isEnabled()) {
            module.enable();
        } else {
            module.disable();
        }
    }

    public static void toggleSetting(SettingBoolean setting) {
        if (setting.getBoolean()) {
            setting.setValue(false);
        } else {
            setting.setValue(true);
        }
    }

    public static double roundSliderForConfig(double val) {
        return Double.parseDouble(String.format("%.2f", val));
    }

    public static String roundSlider(float f) {
        return String.format("%.2f", Float.valueOf(f));
    }

    public static float roundSliderStep(float input, float step) {
        return (float)Math.round(input / step) * step;
    }

    public static float reCheckSliderRange(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }
}

