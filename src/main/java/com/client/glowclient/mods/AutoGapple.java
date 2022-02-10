//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.MobEffects
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemStack
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
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoGapple
extends ToggleMod {
    private boolean isEating = false;
    public static final SettingBoolean healths = SettingUtils.settingBoolean("AutoGapple", "Health", "Eat a gapple under specific health", true);
    public static final SettingBoolean regen = SettingUtils.settingBoolean("AutoGapple", "Regen", "Eat a gapple once you run out of regen", true);
    public static final SettingDouble threshold = SettingUtils.settingDouble("AutoGapple", "Regen", "Eat a gapple once you run out of regen", 10.0, 1.0, 1.0, 20.0);

    public AutoGapple() {
        super(Category.COMBAT, "AutoGapple", false, -1, "Gapples when you run out of regen");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        try {
            int foodSlot = -1;
            ItemStack foodStack = null;
            ItemStack heldItem = Globals.MC.player.getHeldItemMainhand();
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = Globals.MC.player.inventory.getStackInSlot(i);
                if (!(stack.getItem() instanceof ItemAppleGold)) continue;
                foodSlot = i;
                foodStack = stack;
                break;
            }
            if (foodStack != null && foodStack.getItem() instanceof ItemAppleGold) {
                int health = (int)(Globals.MC.player.getHealth() + Globals.MC.player.getAbsorptionAmount());
                if (!Globals.MC.player.isHandActive() && Globals.MC.rightClickDelayTimer == 0 && regen.getBoolean() && !Globals.MC.player.isPotionActive(MobEffects.REGENERATION)) {
                    this.isEating = true;
                    Globals.MC.player.inventory.currentItem = foodSlot;
                    Bindings.keyBindUseItem.setPressed(true);
                    Globals.MC.rightClickMouse();
                    return;
                }
                if (healths.getBoolean() && health < threshold.getInt()) {
                    this.isEating = true;
                    Globals.MC.player.inventory.currentItem = foodSlot;
                    Bindings.keyBindUseItem.setPressed(true);
                    Globals.MC.rightClickMouse();
                    return;
                }
            }
            if (Globals.MC.player.isPotionActive(MobEffects.REGENERATION) && heldItem.getItem() instanceof ItemAppleGold) {
                Bindings.keyBindUseItem.setPressed(false);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

