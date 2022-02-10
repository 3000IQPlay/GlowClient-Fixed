//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.clickgui.ClickGUI;
import com.client.glowclient.clickgui.Window;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class GuiMod
extends ServiceMod {
    public GuiMod() {
        super("ClickGUI", "Opens the GUI", 54);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (this.keybind.isPressed() && Globals.MC.player != null) {
            for (Window window : ClickGUI.getInstance().getWindows()) {
                window.openGui();
            }
            Globals.MC.displayGuiScreen((GuiScreen)ClickGUI.getInstance());
        }
    }
}

