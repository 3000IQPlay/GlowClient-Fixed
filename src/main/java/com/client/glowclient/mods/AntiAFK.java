//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class AntiAFK
extends ToggleMod {
    public static final SettingDouble delay = SettingUtils.settingDouble("AntiAFK", "Hitdelay", "Delay between swings in ms", 30000.0, 1.0, 0.0, 60000.0);
    public final boolean silent = false;
    private SimpleTimer timer = new SimpleTimer();
    boolean gay;

    public AntiAFK() {
        super(Category.PLAYER, "AntiAFK", false, -1, "Swing arm to prevent being afk kicked");
    }

    @SubscribeEvent
    public void onKeyboardinput(InputEvent.KeyInputEvent event) {
        if (this.timer.isStarted()) {
            this.timer.start();
        }
    }

    public void togglegay() {
        this.gay = !this.gay;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        SimpleTimer timer2 = new SimpleTimer();
        if (!this.timer.isStarted()) {
            this.timer.start();
        } else {
            if (this.timer.hasTimeElapsed(delay.getDouble() - 500.0) && this.gay) {
                Globals.MC.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (this.timer.hasTimeElapsed(delay.getDouble())) {
                this.timer.start();
                this.togglegay();
                Globals.MC.player.swingArm(EnumHand.MAIN_HAND);
                timer2.start();
            }
        }
    }
}

