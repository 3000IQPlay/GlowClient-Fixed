/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.clickgui.buttons.submenu;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.clickgui.utils.ColorUtils;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import org.lwjgl.opengl.GL11;

public abstract class SubMenu
extends BaseButton {
    private final Button parent;
    private String menuName;
    private String menuDesc;

    SubMenu(Button parent, String guiName, String guiDesc) {
        super(parent.getWindow().getX() + 4, parent.getY() + 4, parent.getWindow().getWidth() - 8, 14);
        this.parent = parent;
        this.window = parent.getWindow();
        this.menuName = guiName;
        this.menuDesc = guiDesc;
    }

    @Override
    public abstract void processMouseClick(int var1, int var2, int var3);

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
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.menuName, this.getX() + 2 + 1, this.getY() + 2 + 1, true).color(Colors.WHITE).text(this.menuName, this.getX() + 2, this.getY() + 2);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.menuName, this.getX() + 2, this.getY() + 2, true);
        }
        if (this.isMouseHovered() && HUD.descriptions.getBoolean()) {
            SurfaceHelper.drawOutlinedRectShaded(mouseX, mouseY + 3, (int)SurfaceHelper.getStringWidth(HUD.FONT, this.menuDesc) + 2, -12, Colors.toRGBA(10, 10, 10, 255), 175, 1.0f);
            builder.reset().fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.menuDesc, mouseX + 1, mouseY - 7);
        }
    }

    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(3, this.isMouseHovered(), true);
    }

    @Override
    public boolean shouldRender() {
        return this.parent.isOpen() && this.parent.shouldRender();
    }

    @Override
    public String getName() {
        return this.menuName;
    }

    public Button getParent() {
        return this.parent;
    }
}

