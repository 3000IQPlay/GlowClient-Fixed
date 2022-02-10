//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import java.util.Random;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkinBlinker
extends ToggleMod {
    SimpleTimer timer = new SimpleTimer();

    public SkinBlinker() {
        super(Category.RENDER, "SkinBlinker", false, -1, "Flash skin layers on and off");
    }

    @Override
    public void onDisabled() {
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.HAT, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.JACKET, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE, true);
        Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (!this.timer.isStarted()) {
            this.timer.start();
        }
        if (this.timer.hasTimeElapsed(0.0)) {
            this.timer.start();
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.HAT, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.JACKET, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE, new Random().nextBoolean());
            Globals.MC.gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, new Random().nextBoolean());
        }
    }
}

