/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.indev;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Template
extends ToggleMod {
    public static SettingDouble templateDouble = SettingUtils.settingDouble("Template", "TemplateDouble", "Template Description", 0.0, 0.0, 0.0, 0.0);
    public static SettingBoolean templateBoolean = SettingUtils.settingBoolean("Template", "TemplateBoolean", "Template Description", true);
    public static SettingString templateString = SettingUtils.settingString("Template", "TemplateString", "Template Description", "TempValue");
    public static SettingMode templateMode = SettingUtils.settingMode("Template", "TemplateString", "Template Description", "Value1", "Value1", "Value2");

    public Template() {
        super(Category.WIP, "Template", false, -1, "Template Description");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onDisabled() {
    }

    @Override
    public boolean isDrawn() {
        return true;
    }

    @Override
    public String getHUDTag() {
        return "";
    }
}

