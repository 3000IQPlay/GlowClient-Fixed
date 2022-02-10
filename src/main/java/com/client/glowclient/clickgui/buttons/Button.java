/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.clickgui.buttons;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.Window;
import com.client.glowclient.clickgui.utils.ColorUtils;
import com.client.glowclient.clickgui.utils.GuiUtils;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Button
extends BaseButton {
    private List<BaseButton> subEntries = Collections.synchronizedList(new ArrayList());
    private boolean isOpen = false;
    private Module module;

    public Button(Window window, Module module) {
        super(window.getX() + 2, window.getY() + 2, window.getWidth() - 6, 14);
        this.window = window;
        this.module = module;
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered()) {
            if (button == 0) {
                GuiUtils.toggleMod(this.module);
            }
            if (button == 1) {
                this.isOpen = !this.isOpen;
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        SurfaceBuilder builder = new SurfaceBuilder();
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 2;
        this.updateIsMouseHovered(mouseX, mouseY);
        if (HUD.FONT != null) {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(this.getColor()).text(this.module.getModName(), this.getX() + 2 + 1, this.getY() + 3 + 1, true).color(this.getColor()).text(this.module.getModName(), this.getX() + 2, this.getY() + 3);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(this.getColor()).text(this.module.getModName(), this.getX() + 2, this.getY() + 3, true);
        }
        if (!this.isMouseHovered()) {
            SurfaceHelper.drawRect(this.getX(), this.getY(), this.getWidth(), this.height, Colors.toRGBA(0, 0, 0, 0));
        } else {
            SurfaceHelper.drawRect(this.getX(), this.getY(), this.getWidth(), this.height, Colors.toRGBA(100, 100, 100, 50));
            if (HUD.descriptions.getBoolean()) {
                SurfaceHelper.drawOutlinedRectShaded(mouseX, mouseY + 3, (int)SurfaceHelper.getStringWidth(HUD.FONT, this.module.getModDescription()) + 2, -12, Colors.toRGBA(10, 10, 10, 255), 175, 1.0f);
                builder.reset().fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.module.getModDescription(), mouseX + 1, mouseY - 7);
            }
        }
    }

    @Override
    public int getHeight() {
        int i = this.height;
        if (this.isOpen) {
            i += 15 * this.subEntries.size();
        }
        return i;
    }

    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(0, this.isMouseHovered(), this.module.isEnabled());
    }

    @Override
    public String getName() {
        return this.module.getModName();
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void setOpen(boolean val) {
        this.isOpen = val;
    }

    public Module getModule() {
        return this.module;
    }

    public List<BaseButton> getSubEntries() {
        return this.subEntries;
    }
}

