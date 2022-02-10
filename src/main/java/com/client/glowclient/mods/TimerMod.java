//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mod.mods.timermod.TimerUtils;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TimerMod
extends ToggleMod {
    public static final SettingDouble factor = SettingUtils.settingDouble("Timer", "Speed", "Game Speed", 1.0, 0.1, 0.1, 25.0);
    public static SettingBoolean tps = SettingUtils.settingBoolean("Timer", "TPSSync", "Makes game go speed of the server", false);

    public TimerMod() {
        super(Category.SERVER, "Timer", false, -1, "Speeds up game time");
    }

    @Override
    public String getHUDTag() {
        return String.format("%.1f", TimerUtils.getTimerSpeed());
    }

    @Override
    public void onDisabled() {
        TimerUtils.setTimerSpeed(1.0f);
    }

    @Override
    public void onEnabled() {
        if (!tps.getBoolean()) {
            TimerUtils.setTimerSpeed((float)factor.getDouble());
        }
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        try {
            Timer timer = Globals.MC.timer;
            if (tps.getBoolean()) {
                TimerUtils.setTimerSpeed((float)TimerUtils.getTpsTimerModifier());
            }
            if (TimerUtils.getDefaultSpeed() / timer.tickLength == 0.0f) {
                TimerUtils.setTimerSpeed(0.1f);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

