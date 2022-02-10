/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.clickgui.buttons;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.clickgui.utils.ColorUtils;
import com.client.glowclient.clickgui.utils.GuiUtils;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import org.lwjgl.opengl.GL11;

public class SubSlider
extends BaseButton {
    private final Button parent;
    private SettingDouble option;
    private float value;
    private int currentWidth;
    private boolean dragging = false;

    public SubSlider(Button parent, SettingDouble option) {
        super(parent.getWindow().getX() + 4, parent.getY() + 4, parent.getWindow().getWidth() - 8, 14);
        this.parent = parent;
        this.window = parent.getWindow();
        if (option != null) {
            this.value = (float)option.getDouble();
        }
        this.option = option;
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered() && button == 0) {
            this.dragging = true;
        }
    }

    @Override
    public void processMouseRelease(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.dragging && button == 0) {
            this.dragging = false;
            this.getWidthFromValue();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        if (this.dragging) {
            this.currentWidth = mouseX - this.getX();
            if (this.currentWidth < 0) {
                this.currentWidth = 0;
            } else if (this.currentWidth > 92) {
                this.currentWidth = 92;
            }
            this.updateValueFromWidth();
        }
        this.y = this.window.getRenderYButton();
        this.x = this.window.getX() + 4;
        this.updateIsMouseHovered(mouseX, mouseY);
        SurfaceHelper.drawRect(this.getX(), this.getY(), -1, this.height, Colors.toRGBA(red, green, blue, 255));
        SurfaceHelper.drawRect(this.getX(), this.getY(), this.getWidth(), this.height, Colors.toRGBA(0, 0, 0, 0));
        SurfaceHelper.drawRect(this.getX(), this.getY(), this.currentWidth, this.height, this.getColor());
        GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
        SurfaceBuilder builder = new SurfaceBuilder();
        if (HUD.FONT != null) {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.option.getName() + ": " + GuiUtils.roundSlider(this.value), this.getX() + 2, this.getY() + 2, true).color(Colors.WHITE).text(this.option.getName() + ": " + GuiUtils.roundSlider(this.value), this.getX() + 2, this.getY() + 2);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.option.getName() + ": " + GuiUtils.roundSlider(this.value), this.getX() + 2, this.getY() + 2, true);
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
    public void openGui() {
        if (this.option != null) {
            this.value = (float)this.option.getDouble();
        }
        this.getWidthFromValue();
    }

    @Override
    public String getName() {
        return this.option.getName();
    }

    protected int getWidthFromValue() {
        float val = this.value;
        val -= this.getMin();
        val /= this.getMax() - this.getMin();
        this.currentWidth = (int)GuiUtils.reCheckSliderRange(val *= 92.0f, 0.0f, 92.0f);
        return this.currentWidth;
    }

    protected void updateValueFromWidth() {
        float val = (float)this.currentWidth / 92.0f;
        val *= this.getMax() - this.getMin();
        val += this.getMin();
        val = GuiUtils.roundSliderStep(val, this.getStep());
        this.value = val = GuiUtils.reCheckSliderRange(val, this.getMin(), this.getMax());
        this.option.setValue(GuiUtils.roundSliderForConfig(val));
    }

    public Button getParent() {
        return this.parent;
    }

    public float getMax() {
        return (float)this.option.getMax();
    }

    public float getMin() {
        return (float)this.option.getMin();
    }

    public float getStep() {
        return (float)this.option.getStep();
    }
}

