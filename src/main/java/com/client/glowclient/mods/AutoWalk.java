//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.binding.Bindings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoWalk
extends ToggleMod {
    public static SettingMode mode = SettingUtils.settingMode("AutoWalk", "Mode", "Mode of AutoWalk", "Constant", "Constant", "Pulse");
    private boolean isBound = false;
    SimpleTimer timer = new SimpleTimer();
    private static boolean fag = true;

    public AutoWalk() {
        super(Category.MOVEMENT, "AutoWalk", false, -1, "Automatically walks forward");
    }

    @Override
    public void onEnabled() {
        fag = true;
    }

    @Override
    public void onDisabled() {
        if (this.isBound) {
            Bindings.keyBindForward.setPressed(false);
            Bindings.keyBindForward.unbind();
            this.isBound = false;
        }
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Constant")) {
            if (!this.isBound) {
                Bindings.keyBindForward.bind();
                this.isBound = true;
            }
            if (!Bindings.keyBindForward.getBinding().isKeyDown()) {
                Bindings.keyBindForward.setPressed(true);
            }
        }
        if (mode.getMode().equals("Pulse")) {
            if (!this.timer.isStarted()) {
                this.timer.start();
            }
            if (!this.timer.isStarted()) {
                this.timer.start();
            }
            if (this.timer.hasTimeElapsed(500.0)) {
                Bindings.keyBindForward.setPressed(true);
            }
            if (this.timer.hasTimeElapsed(2000.0)) {
                Bindings.keyBindForward.setPressed(false);
                this.timer.start();
            }
        }
    }
}

