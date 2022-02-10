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

public class NoPush
extends ToggleMod {
    public static SettingBoolean entities = SettingUtils.settingBoolean("NoPush", "Entities", "Stops pushing of entities", true);
    public static SettingBoolean water = SettingUtils.settingBoolean("NoPush", "Water", "Stops pushing from water", true);
    public static SettingBoolean blocks = SettingUtils.settingBoolean("NoPush", "Blocks", "Stops pushing from blocks", true);

    public NoPush() {
        super(Category.PLAYER, "NoPush", false, -1, "Stops player pushing");
    }

    @Override
    public void onEnabled() {
        HookUtils.isNoPushWaterActivated = water.getBoolean();
        HookUtils.isNoPushEntitiesActivated = entities.getBoolean();
        HookUtils.isNoPushBlocksActivated = blocks.getBoolean();
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isNoPushWaterActivated = water.getBoolean();
        HookUtils.isNoPushEntitiesActivated = entities.getBoolean();
        HookUtils.isNoPushBlocksActivated = blocks.getBoolean();
    }

    @Override
    public void onDisabled() {
        HookUtils.isNoPushWaterActivated = false;
        HookUtils.isNoPushEntitiesActivated = false;
        HookUtils.isNoPushBlocksActivated = false;
    }
}

