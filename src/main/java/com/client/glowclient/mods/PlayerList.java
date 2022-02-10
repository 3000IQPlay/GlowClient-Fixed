//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;
import com.client.glowclient.utils.world.entity.CombatUtils;
import com.client.glowclient.utils.world.entity.EnchantmentUtils;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerList
extends ToggleMod {
    public PlayerList() {
        super(Category.RENDER, "PlayerList", false, -1, "Renders a list of players and information");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        SurfaceBuilder builder = new SurfaceBuilder();
        double posY = 0.0;
        double posX = 2.0;
        for (Entity entity : Globals.MC.world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == Globals.MC.player) continue;
            posY += CombatUtils.isWearingArmor((EntityPlayer)entity) || CombatUtils.isHoldingItem((EntityPlayer)entity) ? 24.0 : 10.0;
            int i = 0;
            int ncolor = Colors.WHITE;
            if (FriendManager.getFriends().isFriend(entity.getName())) {
                ncolor = Colors.AQUA;
            }
            if (EnemyManager.getEnemies().isEnemy(entity.getName())) {
                ncolor = Colors.toRGBA(255, 0, 0, 255);
            }
            float hp = MathHelper.clamp((float)((EntityPlayer)entity).getHealth(), (float)0.0f, (float)((EntityPlayer)entity).getMaxHealth()) / ((EntityPlayer)entity).getMaxHealth();
            int color = ((EntityPlayer)entity).getHealth() + ((EntityPlayer)entity).getAbsorptionAmount() > ((EntityPlayer)entity).getMaxHealth() ? Colors.YELLOW : Colors.toRGBA((int)((255.0f - hp) * 255.0f), (int)(255.0f * hp), 0, 255);
            float h = ((EntityPlayer)entity).getHealth() + ((EntityPlayer)entity).getAbsorptionAmount();
            String health = String.format("%.1f", Float.valueOf(h));
            if (HUD.FONT != null) {
                builder.reset().fontRenderer(HUD.FONT).color(ncolor).text(entity.getName(), posX + 1.0, posY + 1.0, true).color(ncolor).text(entity.getName(), posX, posY).color(color).text(health, posX + SurfaceHelper.getStringWidth(HUD.FONT, entity.getName() + " ") + 1.0, posY + 1.0, true).color(color).text(health, posX + SurfaceHelper.getStringWidth(HUD.FONT, entity.getName() + " "), posY);
            } else {
                builder.reset().fontRenderer(HUD.FONT).color(ncolor).text(entity.getName(), posX, posY, true).color(color).text(health, posX + SurfaceHelper.getStringWidth(HUD.FONT, entity.getName() + " "), posY, true);
            }
            builder.reset().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(((EntityPlayer)entity).getHeldItemMainhand(), posX + 74.0, posY - 16.0).itemOverlay(((EntityPlayer)entity).getHeldItemMainhand(), posX + 74.0, posY - 16.0).item(((EntityPlayer)entity).getHeldItemOffhand(), posX + 58.0, posY - 16.0).itemOverlay(((EntityPlayer)entity).getHeldItemOffhand(), posX + 58.0, posY - 16.0).task(SurfaceBuilder::disableItemRendering);
            double xI = posX;
            double yI = posY;
            List<EnchantmentUtils.EntityEnchantment> enchantmentsmain = EnchantmentUtils.getEnchantmentsSorted(((EntityPlayer)entity).getHeldItemMainhand().getEnchantmentTagList());
            if (enchantmentsmain == null) continue;
            for (EnchantmentUtils.EntityEnchantment enchant : enchantmentsmain) {
                double d = 0;
                builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(Fonts.VERDANA50).color(Colors.WHITE).text(enchant.getShortName(), posX + 74.0, yI - 3.0, true).color(Colors.WHITE).text(enchant.getShortName(), posX + 74.0, yI - 3.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                yI -= (double)SurfaceHelper.getTextHeight(0.5);
                if (!(d <= yI - 16.0)) continue;
            }
            double xI3 = posX;
            double yI3 = posY;
            enchantmentsmain = EnchantmentUtils.getEnchantmentsSorted(((EntityPlayer)entity).getHeldItemOffhand().getEnchantmentTagList());
            if (enchantmentsmain == null) continue;
            for (EnchantmentUtils.EntityEnchantment enchant : enchantmentsmain) {
                double d = 0;
                builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(Fonts.VERDANA50).color(Colors.WHITE).text(enchant.getShortName(), posX + 58.0, yI3 - 3.0, true).color(Colors.WHITE).text(enchant.getShortName(), posX + 58.0, yI3 - 3.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                yI3 -= (double)SurfaceHelper.getTextHeight(0.5);
                if (!(d <= yI3 - 16.0)) continue;
            }
            for (int j = 0; j < 4; ++j) {
                ItemStack stack = CombatUtils.getWearingArmor(j, (EntityPlayer)entity);
                builder.reset().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(stack, posX + (double)(7 * i++), posY - 16.0).itemOverlay(stack, posX - 7.0 + (double)(7 * i++), posY - 16.0).task(SurfaceBuilder::disableItemRendering);
                double xI2 = posX + 2.0 + (double)(j * 14);
                double yI2 = posY - 3.0;
                List<EnchantmentUtils.EntityEnchantment> enchantments = EnchantmentUtils.getEnchantmentsSorted(stack.getEnchantmentTagList());
                if (enchantments == null) continue;
                for (EnchantmentUtils.EntityEnchantment enchant : enchantments) {
                    double d = 0;
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(Fonts.VERDANA50).color(Colors.WHITE).text(enchant.getShortName(), xI2 + 1.0, yI2 + 1.0, true).color(Colors.WHITE).text(enchant.getShortName(), xI2, yI2).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                    yI2 -= (double)SurfaceHelper.getTextHeight(0.5);
                    if (!(d <= yI2)) continue;
                }
            }
        }
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @Override
    public String getHUDTag() {
        return "";
    }
}

