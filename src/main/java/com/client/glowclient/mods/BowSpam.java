//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.binding.Bindings;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.ProjectileUtils;
import com.client.glowclient.utils.mod.mods.timermod.TimerUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BowSpam
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("BowSpam", "Speed", "Speed of bow spam", 200.0, 50.0, 100.0, 1200.0);
    public static final SettingBoolean tpsSync = SettingUtils.settingBoolean("BowSpam", "TpsSync", "Syncs bow spam to tps", false);
    private boolean doOnce = false;

    public BowSpam() {
        super(Category.COMBAT, "BowSpam", false, -1, "Spams the bow at a select speed");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        double tps;
        double power = speed.getDouble();
        double d = tps = tpsSync.getBoolean() ? 1.0 / TimerUtils.getTpsTimerModifier() : 1.0;
        if (ProjectileUtils.isBow(Globals.MC.player.getHeldItemMainhand())) {
            if (Globals.MC.gameSettings.keyBindAttack.isKeyDown()) {
                this.doOnce = true;
                if (!this.simpleTimer.isStarted()) {
                    this.simpleTimer.start();
                }
                if (this.simpleTimer.hasTimeElapsed(0.0)) {
                    Bindings.keyBindUseItem.setPressed(true);
                }
                if (this.simpleTimer.hasTimeElapsed(power * tps)) {
                    this.simpleTimer.start();
                    Bindings.keyBindUseItem.setPressed(false);
                }
            } else if (this.doOnce) {
                Bindings.keyBindUseItem.setPressed(false);
                this.doOnce = false;
            }
        }
    }
}

