//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package com.client.glowclient.clickgui.buttons.submenu;

import com.client.glowclient.clickgui.ClickGUI;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.clickgui.buttons.submenu.SubMenu;
import com.client.glowclient.clickgui.subguis.GuiAntiPacketsServer;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.gui.GuiScreen;

public class MenuPacketServer
extends SubMenu {
    public MenuPacketServer(Button parent, String guiName, String guiDesc) {
        super(parent, guiName, guiDesc);
        this.window = parent.getWindow();
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered() && button == 0) {
            Globals.MC.displayGuiScreen((GuiScreen)new GuiAntiPacketsServer(ClickGUI.getInstance()));
        }
    }
}

