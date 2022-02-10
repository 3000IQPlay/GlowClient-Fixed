//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.extra.wurst.InventoryUtils;
import com.client.glowclient.utils.extra.wurst.WBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTool
extends ToggleMod {
    private static final boolean useSwords = false;
    private static final Minecraft MC = Minecraft.getMinecraft();
    public int slot = -1;
    private static int oldSlot = -1;
    private static BlockPos pus;
    private static int timer;

    public AutoTool() {
        super(Category.PLAYER, "AutoTool", false, -1, "Automatically selects best tool");
    }

    @Override
    public void onDisabled() {
        if (oldSlot != -1) {
            AutoTool.MC.player.inventory.currentItem = oldSlot;
            oldSlot = -1;
        }
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (AutoTool.MC.gameSettings.keyBindAttack.isKeyDown() && AutoTool.MC.objectMouseOver != null && AutoTool.MC.objectMouseOver.getBlockPos() != null) {
            AutoTool.setSlot(AutoTool.MC.objectMouseOver.getBlockPos());
        }
        if (oldSlot == -1) {
            return;
        }
        if (timer <= 0) {
            AutoTool.MC.player.inventory.currentItem = oldSlot;
            oldSlot = -1;
            return;
        }
        if (!AutoTool.MC.gameSettings.keyBindAttack.isKeyDown() || AutoTool.MC.player.capabilities.isCreativeMode || !WBlock.canBeClicked(pus)) {
            --timer;
        }
    }

    public static void setSlot(BlockPos pos) {
        if (AutoTool.MC.player.capabilities.isCreativeMode) {
            return;
        }
        if (!WBlock.canBeClicked(pos)) {
            return;
        }
        float bestSpeed = AutoTool.MC.player.inventory.getCurrentItem() != null ? InventoryUtils.getStrVsBlock(AutoTool.MC.player.inventory.getCurrentItem(), pos) : 1.0f;
        int bestSlot = -1;
        for (int i = 0; i < 9; ++i) {
            float speed;
            ItemStack stack = AutoTool.MC.player.inventory.getStackInSlot(i);
            if (InventoryUtils.isEmptySlot(stack) || stack.getItem() instanceof ItemSword || !((speed = InventoryUtils.getStrVsBlock(stack, pos)) > bestSpeed)) continue;
            bestSpeed = speed;
            bestSlot = i;
        }
        if (bestSlot == -1) {
            return;
        }
        if (oldSlot == -1) {
            oldSlot = AutoTool.MC.player.inventory.currentItem;
        }
        AutoTool.MC.player.inventory.currentItem = bestSlot;
        pus = pos;
        timer = 4;
    }
}

