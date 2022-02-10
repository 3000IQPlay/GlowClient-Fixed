//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAir
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemLingeringPotion
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class WItem {
    public static boolean isNull(Item item) {
        return item == null || item instanceof ItemAir;
    }

    public static boolean isNull(ItemStack stack) {
        return stack == null || stack.getItem() instanceof ItemAir;
    }

    public static int getArmorType(ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }

    public static boolean isThrowable(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }

    public static boolean isPotion(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemPotion || stack.getItem() instanceof ItemSplashPotion;
    }

    public static Item getFromRegistry(ResourceLocation location) {
        return (Item)Item.REGISTRY.getObject(location);
    }

    public static int getStackSize(ItemStack stack) {
        return stack.getMaxStackSize();
    }
}

