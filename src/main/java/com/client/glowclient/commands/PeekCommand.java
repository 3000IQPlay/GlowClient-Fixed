//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryBasic
 *  net.minecraft.inventory.ItemStackHelper
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.NonNullList
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.mc.console.Console;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class PeekCommand
extends Command {
    private static Block[] SHULKERS;
    private static final Minecraft MC;

    public PeekCommand() {
        super("peek");
        SHULKERS = new Block[]{Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX};
    }

    private static boolean isShulkerBox(Block block) {
        for (Block b : SHULKERS) {
            if (b != block) continue;
            return true;
        }
        return false;
    }

    private static InventoryBasic getFromItemNBT(NBTTagCompound tag) {
        NonNullList items = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
        String customName = "Shulker Box";
        if (tag.hasKey("Items", 9)) {
            ItemStackHelper.loadAllItems((NBTTagCompound)tag, (NonNullList)items);
        }
        if (tag.hasKey("CustomName", 8)) {
            customName = tag.getString("CustomName");
        }
        InventoryBasic inventoryBasic = new InventoryBasic(customName, true, items.size());
        for (int i = 0; i < items.size(); ++i) {
            inventoryBasic.setInventorySlotContents(i, (ItemStack)items.get(i));
        }
        return inventoryBasic;
    }

    @Override
    public void execute(String cmd, String[] args) {
        ItemStack stack = null;
        if (!PeekCommand.MC.player.getHeldItemOffhand().isEmpty()) {
            stack = PeekCommand.MC.player.getHeldItemOffhand();
        }
        if (!PeekCommand.MC.player.getHeldItemMainhand().isEmpty()) {
            stack = PeekCommand.MC.player.getHeldItemMainhand();
        }
        if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (PeekCommand.isShulkerBox(block)) {
                Console.write("\u00a7bOpening Shulker Box");
                if (stack.hasTagCompound()) {
                    MC.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekCommand.MC.player.inventory, (IInventory)PeekCommand.getFromItemNBT(stack.getTagCompound().getCompoundTag("BlockEntityTag"))));
                } else {
                    MC.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekCommand.MC.player.inventory, (IInventory)new InventoryBasic("Shulker Box", true, 27)));
                }
                return;
            }
            Console.write("\u00a7cYou are not holding a Shulker Box");
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "peek";
    }

    static {
        MC = Minecraft.getMinecraft();
    }
}

