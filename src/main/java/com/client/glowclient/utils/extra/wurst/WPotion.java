//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.potion.PotionUtils
 */
package com.client.glowclient.utils.extra.wurst;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;

public final class WPotion {
    public static List<PotionEffect> getEffectsFromStack(ItemStack stack) {
        return PotionUtils.getEffectsFromStack((ItemStack)stack);
    }

    public static int getIdFromEffect(PotionEffect effect) {
        return Potion.getIdFromPotion((Potion)effect.getPotion());
    }

    public static int getIdFromResourceLocation(String location) {
        return Potion.getIdFromPotion((Potion)Potion.getPotionFromResourceLocation((String)location));
    }
}

