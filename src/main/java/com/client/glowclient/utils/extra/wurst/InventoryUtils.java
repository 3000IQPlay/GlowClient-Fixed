//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketCreativeInventoryAction
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.potion.PotionUtils
 *  net.minecraft.util.math.BlockPos
 */
package com.client.glowclient.utils.extra.wurst;

import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.extra.wurst.WConnection;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;

public class InventoryUtils {
    private static final Item NULL_ITEM = Item.getItemFromBlock((Block)Blocks.AIR);

    public static boolean placeStackInHotbar(ItemStack stack) {
        for (int i = 0; i < 9; ++i) {
            if (!InventoryUtils.isSlotEmpty(i)) continue;
            WConnection.sendPacket((Packet)new CPacketCreativeInventoryAction(36 + i, stack));
            return true;
        }
        return false;
    }

    public static void placeStackInArmor(int armorSlot, ItemStack stack) {
        WMinecraft.getPlayer().inventory.armorInventory.set(armorSlot, stack);
    }

    public static boolean isSlotEmpty(int slot) {
        return WMinecraft.getPlayer().inventory.getStackInSlot(slot).getItem() == NULL_ITEM;
    }

    public static boolean isEmptySlot(ItemStack slot) {
        return slot.getItem() == NULL_ITEM;
    }

    public static boolean isSplashPotion(ItemStack stack) {
        return stack.getItem() == Items.SPLASH_POTION;
    }

    public static ItemStack createSplashPotion() {
        return new ItemStack((Item)Items.SPLASH_POTION);
    }

    public static int getArmorType(ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }

    public static float getStrVsBlock(ItemStack stack, BlockPos pos) {
        return stack.getDestroySpeed(WBlock.getState(pos));
    }

    public static boolean hasEffect(ItemStack stack, Potion potion) {
        for (PotionEffect effect : PotionUtils.getEffectsFromStack((ItemStack)stack)) {
            if (effect.getPotion() != potion) continue;
            return true;
        }
        return false;
    }

    public static boolean checkHeldItem(ItemValidator validator) {
        ItemStack stack = WMinecraft.getPlayer().inventory.getCurrentItem();
        if (InventoryUtils.isEmptySlot(stack)) {
            return false;
        }
        return validator.isValid(stack.getItem());
    }

    public static interface ItemValidator {
        public boolean isValid(Item var1);
    }
}

