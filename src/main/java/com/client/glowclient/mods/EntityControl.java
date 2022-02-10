//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.MovementUtils;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityControl
extends ToggleMod {
    double speed = 0.08;

    public EntityControl() {
        super(Category.MOVEMENT, "EntityControl", false, -1, "Allows the control of pigs, llamas, and horses");
    }

    @Override
    public void onEnabled() {
        HookUtils.isEntityControlActivated = true;
    }

    @Override
    public void onDisabled() {
        HookUtils.isEntityControlActivated = false;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        HookUtils.isEntityControlActivated = true;
        if (Globals.MC.player.getRidingEntity() != null && Utils.getRidingEntity() instanceof EntityPig) {
            if (Utils.getRidingEntity() instanceof EntityPig && Globals.MC.player.getRidingEntity().onGround && Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                Globals.MC.player.getRidingEntity().motionY = 0.6;
            }
            this.speed = Globals.MC.player.getRidingEntity().onGround ? 0.1 : 0.015;
            MovementUtils.moveEntityStrafe(this.speed, Globals.MC.player.getRidingEntity());
        }
    }
}

