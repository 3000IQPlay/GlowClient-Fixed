//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.MobEffects
 *  net.minecraft.potion.PotionEffect
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HighJump
extends ToggleMod {
    public HighJump() {
        super(Category.MOVEMENT, "HighJump", false, -1, "Gives jump boost effect");
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        WMinecraft.getPlayer().addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 25, 4));
    }

    @Override
    public void onDisabled() {
        WMinecraft.getPlayer().removePotionEffect(MobEffects.JUMP_BOOST);
    }
}

