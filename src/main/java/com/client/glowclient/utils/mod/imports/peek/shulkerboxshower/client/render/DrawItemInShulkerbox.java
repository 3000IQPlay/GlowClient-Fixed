//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.inventory.ItemStackHelper
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 */
package com.client.glowclient.utils.mod.imports.peek.shulkerboxshower.client.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class DrawItemInShulkerbox {
    public int x = 0;
    public int y = 0;
    private Minecraft MC = Minecraft.getMinecraft();
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");

    public void draw(GuiScreen gui, ItemStack itemStack) {
        List<ItemStack> list = this.arrangementItem(itemStack);
        if (!list.isEmpty()) {
            this.drawItemStack(gui, list, this.x + 4, this.y - 100);
        }
    }

    public void draw(GuiScreen gui, ItemStack itemStack, ItemStack itemStack1, int x, int y) {
        List<ItemStack> list1;
        List<ItemStack> list = this.arrangementItem(itemStack);
        if (!list.isEmpty()) {
            int size = list.size();
            int i = 3;
            this.drawItemStack(gui, list, x + 7, y - 110 - 18 + 42 + i * 18);
        }
        if (!(list1 = this.arrangementItem(itemStack1)).isEmpty()) {
            this.drawItemStack(gui, list1, x + 7, y - 100);
        }
    }

    private List<ItemStack> arrangementItem(ItemStack itemStack) {
        NBTTagCompound blockEntityTag;
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        NBTTagCompound nbttagcompound = itemStack.getTagCompound();
        if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = nbttagcompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            NonNullList nonNullList = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
            ItemStackHelper.loadAllItems((NBTTagCompound)blockEntityTag, (NonNullList)nonNullList);
            return nonNullList;
        }
        return list;
    }

    private void drawItemStack(GuiScreen gui, List<ItemStack> list, int x, int y) {
        int i;
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.disableBlend();
        int i1 = i = list.size() / 9 + (list.size() % 9 == 0 ? 0 : 1);
        if (i1 == 3) {
            i = 1;
        } else if (i1 == 1) {
            i = 3;
        }
        this.MC.getTextureManager().bindTexture(GUI_TEXTURE);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        gui.drawTexturedModalRect(x - 8, y + 12 + i * 18, 0, 0, 176, 5);
        gui.drawTexturedModalRect(x - 8, y + 12 + i * 18 + 5, 0, 16, 176, i1 * 18);
        gui.drawTexturedModalRect(x - 8, y + 17 + i * 18 + i1 * 18, 0, 160, 176, 6);
        GlStateManager.enableDepth();
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)32.0f);
        int size = list.size();
        for (int l = 0; l < size; ++l) {
            this.drawItemStack(this.MC.getRenderItem(), list.get(l), l % 9 * 18 + x, i * 18 + (l / 9 + 1) * 18 + y + 1);
        }
        GlStateManager.disableLighting();
        this.MC.getRenderItem().zLevel = 0.0f;
    }

    private void drawItemStack(RenderItem itemRender, ItemStack stack, int x, int y) {
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) {
            font = this.MC.fontRenderer;
        }
        GlStateManager.enableDepth();
        itemRender.zLevel = 120.0f;
        RenderHelper.enableGUIStandardItemLighting();
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        String count = stack.getCount() == 1 ? "" : String.valueOf(stack.getCount());
        String more = "+" + stack.getCount() % stack.getMaxStackSize();
        String count1 = stack.getCount() / stack.getMaxStackSize() + "S" + more;
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, count);
        itemRender.zLevel = 0.0f;
    }
}

