/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TabMod
extends ToggleMod {
    public static SettingBoolean extended = SettingUtils.settingBoolean("TabMod", "Extended", "Shows all players in tab menu", false);
    public static SettingBoolean noheads = SettingUtils.settingBoolean("TabMod", "NoHeads", "Stops player heads from rendering", false);
    public static SettingBoolean nonames = SettingUtils.settingBoolean("TabMod", "NoNames", "Stops playernames from rendering", false);
    public static SettingBoolean noping = SettingUtils.settingBoolean("TabMod", "NoPing", "Stops ping from rendering", false);

    public TabMod() {
        super(Category.RENDER, "TabMod", false, -1, "Allows the tab menu to be customized");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isExtendedTabActivated = extended.getBoolean();
        HookUtils.isNoPlayerHeadsTabActivated = noheads.getBoolean();
        HookUtils.isNoNameTabActivated = nonames.getBoolean();
        HookUtils.isNoPingTabActivated = noping.getBoolean();
    }

    @Override
    public void onDisabled() {
        HookUtils.isExtendedTabActivated = false;
        HookUtils.isNoPlayerHeadsTabActivated = false;
        HookUtils.isNoNameTabActivated = false;
        HookUtils.isNoPingTabActivated = false;
    }
}

