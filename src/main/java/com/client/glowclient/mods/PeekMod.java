//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryBasic
 *  net.minecraft.inventory.ItemStackHelper
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.text.translation.I18n
 *  net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post
 *  net.minecraftforge.client.event.RenderTooltipEvent$PostBackground
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.mod.imports.peek.shulkerboxshower.client.render.DrawItemInShulkerbox;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PeekMod
extends ToggleMod {
    public static SettingBoolean rightClick = SettingUtils.settingBoolean("PeekMod", "RightClick", "Right click a held shulker to view its inventory", true);
    public static SettingBoolean tooltips = SettingUtils.settingBoolean("PeekMod", "ToolTips", "Shows shulker inventory when hovered over", true);
    private static Block[] SHULKERS;
    private static final Minecraft MC;
    private DrawItemInShulkerbox drawer;

    public PeekMod() {
        super(Category.RENDER, "PeekMod", false, -1, "Get a view into shulker boxes without placing them");
        SHULKERS = new Block[]{Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX};
        this.drawer = new DrawItemInShulkerbox();
    }

    @Override
    public boolean isDrawn() {
        return false;
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

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (rightClick.getBoolean() && PeekMod.MC.gameSettings.keyBindUseItem.isKeyDown()) {
            Block block;
            ItemStack stack = null;
            if (!PeekMod.MC.player.getHeldItemOffhand().isEmpty()) {
                stack = PeekMod.MC.player.getHeldItemOffhand();
            }
            if (!PeekMod.MC.player.getHeldItemMainhand().isEmpty()) {
                stack = PeekMod.MC.player.getHeldItemMainhand();
            }
            if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemBlock && PeekMod.isShulkerBox(block = ((ItemBlock)stack.getItem()).getBlock())) {
                if (stack.hasTagCompound()) {
                    MC.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekMod.MC.player.inventory, (IInventory)PeekMod.getFromItemNBT(stack.getTagCompound().getCompoundTag("BlockEntityTag"))));
                } else {
                    MC.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekMod.MC.player.inventory, (IInventory)new InventoryBasic("Shulker Box", true, 27)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onTooltipGen(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof ItemShulkerBox) {
            final List<String> list = event.getToolTip();
            final List<String> temp = new ArrayList<String>();
            for (final String s : list) {
                if (s.matches("^.*\\sx\\d+$")) {
                    temp.add(s);
                }
            }
            for (String s : temp) {
                list.remove(s);
            }
            if (list.size() < 2) {
                return;
            }
            String[] strings = I18n.translateToLocal((String)"container.shulkerBox.more").split("%s");
            if (((String)list.get(1)).contains(strings[0]) && ((String)list.get(1)).contains(strings[1])) {
                list.remove(1);
            }
        }
    }

    @SubscribeEvent
    public void afterDrawGui(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (tooltips.getBoolean() && event.getGui() instanceof GuiContainer) {
            GuiContainer gui = (GuiContainer)event.getGui();
            Slot slotUnderMouse = gui.getSlotUnderMouse();
            ItemStack itemInHand = PeekMod.MC.player.inventory.getItemStack();
            if (null == slotUnderMouse) {
                if (!itemInHand.isEmpty() && itemInHand.getItem() instanceof ItemShulkerBox) {
                    this.drawer.draw((GuiScreen)gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
                }
            } else if (slotUnderMouse.getHasStack()) {
                ItemStack itemUnderMouse = slotUnderMouse.getStack();
                if (itemUnderMouse.getItem() instanceof ItemShulkerBox) {
                    boolean flag;
                    boolean bl = flag = !itemInHand.isEmpty() && itemInHand.getItem() instanceof ItemShulkerBox;
                    if (flag) {
                        this.drawer.draw((GuiScreen)gui, itemInHand, itemUnderMouse, event.getMouseX() + 10, event.getMouseY());
                    } else {
                        this.drawer.draw((GuiScreen)gui, itemUnderMouse);
                    }
                } else if (itemInHand.getItem() instanceof ItemShulkerBox) {
                    this.drawer.draw((GuiScreen)gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
                }
            } else if (itemInHand.getItem() instanceof ItemShulkerBox) {
                this.drawer.draw((GuiScreen)gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
            }
        }
    }

    @SubscribeEvent
    public void onTooltipRender(RenderTooltipEvent.PostBackground event) {
        if (null == event.getStack()) {
            return;
        }
        if (event.getStack().getItem() instanceof ItemShulkerBox) {
            this.drawer.x = event.getX();
            this.drawer.y = event.getY();
        }
    }

    static {
        MC = Minecraft.getMinecraft();
    }
}

