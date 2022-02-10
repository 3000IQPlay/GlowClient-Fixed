//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.ContainerPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTotem
extends ToggleMod {
    private int tot;
    private static final Minecraft MC = Minecraft.getMinecraft();

    public AutoTotem() {
        super(Category.COMBAT, "AutoTotem", false, -1, "Automatically places totem in offhand");
    }

    @Override
    public String getHUDTag() {
        int count = 0;
        String items = "";
        for (int slot = 0; slot < AutoTotem.MC.player.inventory.getSizeInventory(); ++slot) {
            ItemStack Stack = AutoTotem.MC.player.inventory.getStackInSlot(slot);
            if (Stack == null || !Stack.getItem().equals((Object)Items.TOTEM_OF_UNDYING)) continue;
            int total = count += Stack.getCount();
            items = String.valueOf(total);
        }
        return items;
    }

    @Override
    public void onEnabled() {
        this.tot = 0;
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        EntityPlayerSP player = AutoTotem.MC.player;
        if (this.tot > 0) {
            --this.tot;
            return;
        }
        NonNullList inv = player.inventory.mainInventory;
        if (EntityEquipmentSlot.OFFHAND == null || player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() != Items.TOTEM_OF_UNDYING) {
            for (int inventoryIndex = 0; inventoryIndex < inv.size(); ++inventoryIndex) {
                if (inv.get(inventoryIndex) == ItemStack.EMPTY || ((ItemStack)inv.get(inventoryIndex)).getItem() != Items.TOTEM_OF_UNDYING) continue;
                this.replaceTotem(inventoryIndex);
                break;
            }
            this.tot = 3;
        }
    }

    public void replaceTotem(int inventoryIndex) {
        if (AutoTotem.MC.player.openContainer instanceof ContainerPlayer) {
            AutoTotem.MC.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.MC.player);
            AutoTotem.MC.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.MC.player);
            AutoTotem.MC.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.MC.player);
        }
    }
}

