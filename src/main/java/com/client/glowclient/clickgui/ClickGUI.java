//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.input.Mouse
 */
package com.client.glowclient.clickgui;

import com.client.glowclient.clickgui.Window;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceHelper;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

public class ClickGUI
extends GuiScreen {
    private static ClickGUI INSTANCE;
    private Window[] windows;

    public ClickGUI() {
        INSTANCE = this;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawScreen(int x, int y, float ticks) {
        SurfaceHelper.drawRect(0, 0, this.width, this.height, Colors.toRGBA(0, 0, 0, 150));
        for (Window window : this.windows) {
            window.draw(x, y);
        }
        SurfaceHelper.drawRect(-1, -1, 1, 1, Colors.toRGBA(0, 0, 0, 150));
    }

    public void mouseClicked(int x, int y, int b) throws IOException {
        for (Window window : this.windows) {
            window.processMouseClick(x, y, b);
        }
        super.mouseClicked(x, y, b);
    }

    public void mouseReleased(int x, int y, int state) {
        for (Window window : this.windows) {
            window.processMouseRelease(x, y, state);
        }
        super.mouseReleased(x, y, state);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dWheel = MathHelper.clamp((int)Mouse.getEventDWheel(), (int)-1, (int)1);
        if (dWheel != 0) {
            dWheel *= -1;
            int x = Mouse.getEventX() * this.width / Globals.MC.displayWidth;
            int y = this.height - Mouse.getEventY() * this.height / Globals.MC.displayHeight - 1;
            for (Window window : this.windows) {
                window.handleScroll(dWheel, x, y);
            }
        }
    }

    protected void keyTyped(char eventChar, int eventKey) {
        for (Window window : this.windows) {
            window.processKeyPress(eventChar, eventKey);
        }
        if (eventKey == 1) {
            Globals.MC.displayGuiScreen(null);
            if (Globals.MC.currentScreen == null) {
                Globals.MC.setIngameFocus();
            }
        }
    }

    public void setWindows(Window ... windows) {
        this.windows = windows;
    }

    public void initWindows() {
        for (Window window : this.windows) {
            window.init(window.getCategory());
        }
    }

    public static ClickGUI getInstance() {
        return INSTANCE;
    }

    public Window[] getWindows() {
        return this.windows;
    }
}

