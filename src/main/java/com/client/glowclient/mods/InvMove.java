//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreenOptionsSounds
 *  net.minecraft.client.gui.GuiVideoSettings
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package com.client.glowclient.mods;

import com.client.glowclient.clickgui.ClickGUI;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class InvMove
extends ToggleMod {
    public InvMove() {
        super(Category.PLAYER, "InvMove", true, -1, "Move around while in your inventory");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void LocalPlayerUpdate(PlayerUpdateEvent event) {
        block2: {
            KeyBinding[] keys;
            block3: {
                if (ModuleManager.getModuleFromName("AutoWalk").isEnabled()) break block2;
                keys = new KeyBinding[]{Globals.MC.gameSettings.keyBindForward, Globals.MC.gameSettings.keyBindBack, Globals.MC.gameSettings.keyBindLeft, Globals.MC.gameSettings.keyBindRight, Globals.MC.gameSettings.keyBindJump, Globals.MC.gameSettings.keyBindSprint};
                if (!(Globals.MC.currentScreen instanceof GuiOptions) && !(Globals.MC.currentScreen instanceof GuiVideoSettings) && !(Globals.MC.currentScreen instanceof GuiScreenOptionsSounds) && !(Globals.MC.currentScreen instanceof GuiContainer) && !(Globals.MC.currentScreen instanceof GuiIngameMenu) && !(Globals.MC.currentScreen instanceof ClickGUI)) break block3;
                for (KeyBinding bind : keys) {
                    KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)Keyboard.isKeyDown((int)bind.getKeyCode()));
                }
                break block2;
            }
            if (Globals.MC.currentScreen != null) break block2;
            for (KeyBinding bind : keys) {
                if (Keyboard.isKeyDown((int)bind.getKeyCode())) continue;
                KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)false);
            }
        }
    }
}

