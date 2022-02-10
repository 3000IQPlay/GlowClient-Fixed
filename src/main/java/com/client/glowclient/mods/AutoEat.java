//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.FoodStats
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.binding.Bindings;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoEat
extends ToggleMod {
    private boolean isEating = false;

    public AutoEat() {
        super(Category.PLAYER, "AutoEat", false, -1, "Auto eats when you get hungry");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        try {
            FoodStats foodStats = Globals.MC.player.getFoodStats();
            int foodSlot = -1;
            ItemStack foodStack = null;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = Globals.MC.player.inventory.getStackInSlot(i);
                if (stack == null || !(stack.getItem() instanceof ItemFood) || stack.getItem() instanceof ItemAppleGold) continue;
                foodSlot = i;
                foodStack = stack;
                break;
            }
            if (foodStack != null) {
                ItemFood itemFood = (ItemFood)foodStack.getItem();
                if (20 - foodStats.getFoodLevel() >= itemFood.getHealAmount(foodStack) && !Globals.MC.player.isHandActive() && Globals.MC.rightClickDelayTimer == 0) {
                    this.isEating = true;
                    Globals.MC.player.inventory.currentItem = foodSlot;
                    Bindings.keyBindUseItem.setPressed(true);
                    Globals.MC.rightClickMouse();
                    return;
                }
            }
            if (this.isEating && !Globals.MC.player.isHandActive()) {
                this.isEating = false;
                Bindings.keyBindUseItem.setPressed(false);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

