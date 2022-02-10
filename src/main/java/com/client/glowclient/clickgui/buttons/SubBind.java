/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.clickgui.buttons;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.Window;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class SubBind
extends BaseButton {
    private final Button parent;
    private Window window;
    private boolean accepting = false;

    public SubBind(Button parent) {
        super(parent.getWindow().getX() + 4, parent.getY() + 4, parent.getWindow().getWidth() - 8, 14);
        this.parent = parent;
        this.window = parent.getWindow();
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered() && button == 0) {
            this.accepting = true;
        }
    }

    @Override
    public void processKeyPress(char character, int key) {
        if (this.accepting) {
            if (key == 211 || key == 14 || key == 1) {
                this.parent.getModule().setBind(-1);
                this.accepting = false;
            } else {
                this.parent.getModule().setBind(key);
                this.accepting = false;
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        String keyname = !this.accepting ? (this.parent.getModule().getBind() == -1 ? "Bind: NONE" : "Bind: " + Keyboard.getKeyName((int)this.parent.getModule().getBind())) : "Press a key...";
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 4;
        this.updateIsMouseHovered(mouseX, mouseY);
        SurfaceHelper.drawRect(this.getX(), this.getY(), this.getWidth(), this.height, this.getColor());
        SurfaceHelper.drawRect(this.getX(), this.getY(), -1, this.height, Colors.toRGBA(red, green, blue, 255));
        GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
        SurfaceBuilder builder = new SurfaceBuilder();
        if (HUD.FONT != null) {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(keyname, this.getX() + 2 + 1, this.getY() + 2 + 1, true).color(Colors.WHITE).text(keyname, this.getX() + 2, this.getY() + 2);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(keyname, this.getX() + 2, this.getY() + 2, true);
        }
        if (this.isMouseHovered() && HUD.descriptions.getBoolean()) {
            SurfaceHelper.drawOutlinedRectShaded(mouseX, mouseY + 3, (int)SurfaceHelper.getStringWidth(HUD.FONT, "Keybind of mod") + 2, -12, Colors.toRGBA(10, 10, 10, 255), 175, 1.0f);
            builder.reset().fontRenderer(HUD.FONT).color(Colors.WHITE).text("Keybind of mod", mouseX + 1, mouseY - 7);
        }
    }

    @Override
    public int getColor() {
        if (this.isMouseHovered()) {
            return Colors.toRGBA(150, 150, 150, 50);
        }
        return Colors.toRGBA(0, 0, 0, 50);
    }

    @Override
    public boolean shouldRender() {
        return this.parent.isOpen() && this.parent.shouldRender();
    }

    @Override
    public String getName() {
        return "Bind: " + this.parent.getModule().getBind();
    }

    public Button getParent() {
        return this.parent;
    }
}

