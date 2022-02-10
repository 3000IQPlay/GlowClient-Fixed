//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemStack
 */
package com.client.glowclient.utils.world.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class CombatUtils {
    private static Entity KillAuraTarget = null;
    private static Entity BowAimTarget = null;

    public static void setKillAuraTarget(@Nullable Entity targetEntity) {
        KillAuraTarget = targetEntity;
    }

    public static Entity getKillAuraTarget() {
        return KillAuraTarget;
    }

    public static boolean isKillAuraTarget(Entity entity) {
        return KillAuraTarget != null && KillAuraTarget.equals((Object)entity);
    }

    public static Entity getBowAimTarget() {
        return BowAimTarget;
    }

    public static void setBowAimTarget(@Nullable Entity targetEntity) {
        BowAimTarget = targetEntity;
    }

    public static boolean isBowAimTarget(Entity entity) {
        return BowAimTarget != null && BowAimTarget.equals((Object)entity);
    }

    public static boolean isPlayerShielding(EntityLivingBase entity) {
        if (entity.getHeldItemMainhand().getItemUseAction() == EnumAction.BLOCK || entity.getHeldItemMainhand().getItemUseAction() == EnumAction.BLOCK) {
            if (entity.getHeldItemMainhand().getItem() == Items.SHIELD) {
                return true;
            }
            if (entity.getHeldItemMainhand().getItem() == Items.SHIELD) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getWearingArmor(int armorType, EntityPlayer entity) {
        return entity.inventoryContainer.getSlot(5 + armorType).getStack();
    }

    public static boolean isWearingArmor(EntityPlayer entity) {
        return !entity.inventoryContainer.getSlot(5).getStack().isEmpty() || !entity.inventoryContainer.getSlot(6).getStack().isEmpty() || !entity.inventoryContainer.getSlot(7).getStack().isEmpty() || !entity.inventoryContainer.getSlot(8).getStack().isEmpty();
    }

    public static boolean isHoldingItem(EntityPlayer entity) {
        return !entity.getHeldItemMainhand().isEmpty() || !entity.getHeldItemOffhand().isEmpty();
    }
}

