//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreenOptionsSounds
 *  net.minecraft.client.gui.GuiVideoSettings
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.binding.Bindings;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.BlockUtils;
import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.extra.wurst.WPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoMine
extends ToggleMod {
    public AutoMine() {
        super(Category.PLAYER, "AutoMine", false, -1, "Automatically breaks blocks");
    }

    @Override
    public void onDisabled() {
        Bindings.keyBindAttack.setPressed(false);
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (Globals.MC.objectMouseOver == null || Globals.MC.objectMouseOver.getBlockPos() == null) {
            return;
        }
        BlockPos pos = Globals.MC.objectMouseOver.getBlockPos();
        if (WBlock.getMaterial(Globals.MC.objectMouseOver.getBlockPos()) != Material.AIR && (Globals.MC.currentScreen instanceof GuiOptions || Globals.MC.currentScreen instanceof GuiVideoSettings || Globals.MC.currentScreen instanceof GuiScreenOptionsSounds || Globals.MC.currentScreen instanceof GuiContainer || Globals.MC.currentScreen instanceof GuiIngameMenu)) {
            BlockUtils.breakBlockLegit(pos);
            WPlayer.swingArmClient();
        }
        Bindings.keyBindAttack.setPressed(WBlock.getMaterial(Globals.MC.objectMouseOver.getBlockPos()) != Material.AIR);
    }
}

