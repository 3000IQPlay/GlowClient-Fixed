/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.clickgui.buttons;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.Window;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.clickgui.utils.ColorUtils;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import org.lwjgl.opengl.GL11;

public class SubMode
extends BaseButton {
    private final Button parent;
    private Window window;
    private SettingMode option;
    private static int arrayNumber;
    private boolean reset = false;
    SimpleTimer timer = new SimpleTimer();

    public SubMode(Button parent, SettingMode option) {
        super(parent.getWindow().getX() + 4, parent.getY() + 4, parent.getWindow().getWidth() - 8, 14);
        this.parent = parent;
        this.window = parent.getWindow();
        this.option = option;
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (!this.reset) {
            this.timer.start();
            this.reset = true;
        }
        if (this.isMouseHovered() && this.parent.isOpen()) {
            if (button == 0) {
                arrayNumber = this.option.getModes().indexOf(this.option.getMode());
                if (++arrayNumber != this.option.getModes().size()) {
                    this.option.setValue(this.option.getModes().get(arrayNumber));
                } else {
                    this.option.setValue(this.option.getModes().get(0));
                    arrayNumber = 0;
                }
            }
            if (button == 1 && this.timer.hasTimeElapsed(50.0)) {
                arrayNumber = this.option.getModes().indexOf(this.option.getMode());
                if (--arrayNumber != -1) {
                    this.option.setValue(this.option.getModes().get(arrayNumber));
                } else {
                    this.option.setValue(this.option.getModes().get(this.option.getModes().size() - 1));
                    arrayNumber = this.option.getModes().size() - 1;
                }
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
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
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.option.getName() + ": " + this.option.getMode(), this.getX() + 2 + 1, this.getY() + 2 + 1, true).color(Colors.WHITE).text(this.option.getName() + ": " + this.option.getMode(), this.getX() + 2, this.getY() + 2);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.option.getName() + ": " + this.option.getMode(), this.getX() + 2, this.getY() + 2, true);
        }
        if (this.isMouseHovered() && HUD.descriptions.getBoolean()) {
            SurfaceHelper.drawOutlinedRectShaded(mouseX, mouseY + 3, (int)SurfaceHelper.getStringWidth(HUD.FONT, this.option.getDescription()) + 2, -12, Colors.toRGBA(10, 10, 10, 255), 175, 1.0f);
            builder.reset().fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.option.getDescription(), mouseX + 1, mouseY - 7);
        }
    }

    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(2, this.isMouseHovered(), false);
    }

    @Override
    public boolean shouldRender() {
        return this.parent.isOpen() && this.parent.shouldRender();
    }

    @Override
    public String getName() {
        return this.option.getName();
    }

    public Button getParent() {
        return this.parent;
    }
}

