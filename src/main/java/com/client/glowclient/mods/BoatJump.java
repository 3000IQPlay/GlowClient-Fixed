//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BoatJump
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("BoatJump", "Power", "jump strength", 0.35, 0.05, 0.0, 1.0);

    public BoatJump() {
        super(Category.MOVEMENT, "BoatJump", false, -1, "Jump with boats");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Globals.MC.player != null && Globals.MC.player.getRidingEntity() != null && Globals.MC.player.getRidingEntity() instanceof EntityBoat && Globals.MC.gameSettings.keyBindJump.isKeyDown() && (Globals.MC.player.getRidingEntity().onGround || Globals.MC.player.getRidingEntity().isInWater())) {
            Globals.MC.player.getRidingEntity().motionY = speed.getDouble();
        }
    }
}

