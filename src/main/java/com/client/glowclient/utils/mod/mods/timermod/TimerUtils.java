//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 */
package com.client.glowclient.utils.mod.mods.timermod;

import com.client.glowclient.mods.TimerMod;
import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.util.Timer;

public class TimerUtils {
    public static void resetTimerSpeed() {
        if (!ModuleManager.getModuleFromName("Timer").isEnabled() && ModuleManager.getModuleFromName("Timer") != null) {
            TimerUtils.setTimerSpeed(1.0f);
        } else {
            TimerUtils.setTimerSpeed((float)TimerMod.factor.getDouble());
        }
        if (TimerMod.tps.getBoolean() && ModuleManager.getModuleFromName("Timer").isEnabled()) {
            TimerUtils.setTimerSpeed((float)TimerUtils.getTpsTimerModifier());
        }
    }

    public static void setTimerSpeed(float value) {
        TimerUtils.setSpeed(TimerUtils.getDefaultSpeed() / value);
    }

    private static void setSpeed(float value) {
        try {
            Timer timer = Globals.MC.timer;
            timer.tickLength = value;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static float getDefaultSpeed() {
        return 50.0f;
    }

    public static double getTimerSpeed() {
        try {
            return Double.parseDouble(String.format("%.5f", Float.valueOf(TimerUtils.getDefaultSpeed() / Globals.MC.timer.tickLength)));
        }
        catch (Exception exception) {
            return 0.0;
        }
    }

    public static double getTpsTimerModifier() {
        TickrateRecorder.TickRateData data = TickrateRecorder.getTickData();
        TickrateRecorder.TickRateData.CalculationData point = data.getPoint();
        double p = point.getAverage();
        return p / 20.0;
    }
}

