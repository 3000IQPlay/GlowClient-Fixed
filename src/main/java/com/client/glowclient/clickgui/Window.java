//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.clickgui;

import com.client.glowclient.clickgui.BaseButton;
import com.client.glowclient.clickgui.buttons.Button;
import com.client.glowclient.clickgui.buttons.SubBind;
import com.client.glowclient.clickgui.buttons.SubButton;
import com.client.glowclient.clickgui.buttons.SubMode;
import com.client.glowclient.clickgui.buttons.SubSlider;
import com.client.glowclient.clickgui.buttons.submenu.MenuPacketClient;
import com.client.glowclient.clickgui.buttons.submenu.MenuPacketServer;
import com.client.glowclient.clickgui.buttons.submenu.SubMenu;
import com.client.glowclient.clickgui.utils.ColorUtils;
import com.client.glowclient.mods.AntiPackets;
import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.base.setting.builder.SettingManager;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.pepsimod.PUtils;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class Window
extends BaseButton {
    public boolean isOpen = true;
    private final String text;
    private List<BaseButton> buttons = Collections.synchronizedList(new ArrayList());
    private int modulesCounted = 0;
    private int scroll = 0;
    private int renderYButton = 0;
    private boolean isDragging = false;
    private int dragX = 0;
    private int dragY = 0;
    private Category category;

    public Window(int x, int y, String name, Category category) {
        super(x, y, 100, 12);
        this.text = name;
        this.category = category;
    }

    @Override
    public void processMouseClick(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isMouseHovered()) {
            if (button == 0) {
                this.isDragging = true;
                this.dragX = mouseX - this.getX();
                this.dragY = mouseY - this.getY();
            } else if (button == 1) {
                this.isOpen = !this.isOpen;
            }
        }
        for (BaseButton but : this.buttons) {
            if (!but.shouldRender()) continue;
            but.processMouseClick(mouseX, mouseY, button);
        }
    }

    @Override
    public void processMouseRelease(int mouseX, int mouseY, int button) {
        this.updateIsMouseHovered(mouseX, mouseY);
        if (this.isDragging) {
            this.isDragging = false;
        }
        for (BaseButton but : this.buttons) {
            if (!but.shouldRender()) continue;
            but.processMouseRelease(mouseX, mouseY, button);
        }
    }

    @Override
    public void processKeyPress(char character, int key) {
        for (BaseButton but : this.buttons) {
            if (!but.shouldRender()) continue;
            but.processKeyPress(character, key);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1284);
        this.scroll = Math.max(0, this.scroll);
        this.scroll = Math.min(this.getDisplayableCount() - this.getModulesToDisplay(), this.scroll);
        this.updateIsMouseHovered(mouseX, mouseY);
        this.renderYButton = this.getY();
        SurfaceHelper.drawRect(this.getX(), this.getY(), this.getWidth(), 13, Colors.toRGBA(red, green, blue, 225));
        if (this.isOpen) {
            SurfaceHelper.drawRect(this.getX(), this.getY() + 13, this.getWidth(), this.getDisplayedHeight() + 4 - 13, Colors.toRGBA(0, 0, 0, 150));
        } else {
            SurfaceHelper.drawRect(this.getX(), this.getY() + 13, this.getWidth(), this.getDisplayedHeight() + 1 - 13, Colors.toRGBA(0, 0, 0, 150));
        }
        GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
        SurfaceBuilder builder = new SurfaceBuilder();
        if (HUD.FONT != null) {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.text, this.getX() + 2 + 1, this.getY() + 2 + 1, true).color(Colors.WHITE).text(this.text, this.getX() + 2, this.getY() + 2);
        } else {
            builder.reset().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(HUD.FONT).color(Colors.WHITE).text(this.text, this.getX() + 2, this.getY() + 2, true);
        }
        if (this.isOpen) {
            if (this.shouldScroll()) {
                int barHeight = this.getScrollbarHeight();
                int barY = this.getScrollbarY();
                barY = Math.min(barY, this.getScrollingModuleCount() * 15 - 1 - barHeight);
                PUtils.drawRect(this.getX() + 97, this.getY() + 13 + barY + 5, this.getX() + 99, Math.min(this.getY() + 13 + barY + barHeight, this.getY() + this.getDisplayedHeight()), Colors.toRGBA(red, green, blue, 255));
            }
            this.modulesCounted = 0;
            for (int i = this.getScroll(); i < this.getModulesToDisplay() + this.getScroll(); ++i) {
                BaseButton but = this.getNextEntry();
                ++this.modulesCounted;
                but.draw(mouseX, mouseY);
            }
        }
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    @Override
    public int getHeight() {
        int i = this.height;
        for (BaseButton but : this.buttons) {
            if (!but.shouldRender()) continue;
            i += 15;
        }
        return i;
    }

    @Override
    public void openGui() {
        for (BaseButton but : this.buttons) {
            but.openGui();
        }
    }

    @Override
    public boolean shouldRender() {
        return true;
    }

    @Override
    public String getName() {
        return this.text;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void setOpen(boolean val) {
        this.isOpen = val;
    }

    @Override
    public int getColor() {
        return ColorUtils.getColorForGuiEntry(1, this.isMouseHovered(), false);
    }

    public void init(Category category) {
        for (Module module : ModuleManager.getMods()) {
            Module mud;
            if (module.getModCategory() != category) continue;
            Button b = this.addButton(new Button(this, module));
            this.addSubBind(new SubBind(b));
            for (SettingDouble settingDouble : SettingManager.getSettingDoubles()) {
                mud = ModuleManager.getModuleFromName(settingDouble.getModName());
                if (module != mud) continue;
                this.addSubSlider(new SubSlider(b, settingDouble));
            }
            for (SettingBoolean settingBoolean : SettingManager.getSettingBooleans()) {
                mud = ModuleManager.getModuleFromName(settingBoolean.getModName());
                if (module != mud || settingBoolean.getName().equals("Toggled") || settingBoolean.getModName().equals("AntiPackets")) continue;
                this.addSubButton(new SubButton(b, settingBoolean));
            }
            for (SettingMode settingMode : SettingManager.getSettingModes()) {
                mud = ModuleManager.getModuleFromName(settingMode.getModName());
                if (module != mud) continue;
                this.addSubMode(new SubMode(b, settingMode));
            }
            if (!(module instanceof AntiPackets)) continue;
            this.addSubMenu(new MenuPacketServer(b, "Server Packets", "Modify packets received from the server"));
            this.addSubMenu(new MenuPacketClient(b, "Client Packets", "Modify packets sent to the server"));
        }
    }

    private Button addButton(Button b) {
        this.buttons.add(b);
        return b;
    }

    private SubButton addSubButton(SubButton b) {
        this.buttons.add(this.buttons.indexOf(b.getParent()) + 1, b);
        b.getParent().getSubEntries().add(b);
        return b;
    }

    private SubBind addSubBind(SubBind b) {
        this.buttons.add(this.buttons.indexOf(b.getParent()) + 1, b);
        b.getParent().getSubEntries().add(b);
        return b;
    }

    private SubMode addSubMode(SubMode b) {
        this.buttons.add(this.buttons.indexOf(b.getParent()) + 1, b);
        b.getParent().getSubEntries().add(b);
        return b;
    }

    private SubSlider addSubSlider(SubSlider slider) {
        this.buttons.add(this.buttons.indexOf(slider.getParent()) + 1, slider);
        slider.getParent().getSubEntries().add(slider);
        return slider;
    }

    private SubMenu addSubMenu(SubMenu b) {
        this.buttons.add(this.buttons.indexOf(b.getParent()) + 1, b);
        b.getParent().getSubEntries().add(b);
        return b;
    }

    private BaseButton getNextEntry() {
        int a = 0;
        int i = this.scroll;
        while (true) {
            BaseButton but;
            if ((but = this.buttons.get(i)).shouldRender()) {
                if (this.modulesCounted != 0 && a < this.modulesCounted) {
                    ++a;
                } else {
                    return but;
                }
            }
            ++i;
        }
    }

    public int getRenderYButton() {
        return this.renderYButton += 15;
    }

    private int getDisplayedHeight() {
        int max = this.maxDisplayHeight();
        int normal = this.getHeight();
        return Math.min(max + 14, normal);
    }

    private void updateIsMouseHoveredFull(int mouseX, int mouseY) {
        int x = this.getX();
        int y = this.getY();
        int maxX = x + this.width;
        int maxY = y + this.getDisplayedHeight();
        this.isHoveredCached = x <= mouseX && mouseX <= maxX && y <= mouseY && mouseY <= maxY;
    }

    private int getScrollbarHeight() {
        double maxHeight = this.maxDisplayHeight();
        double maxAllowedModules = this.getScrollingModuleCount();
        double displayable = this.getDisplayableCount();
        return (int)Math.floor(maxHeight * (maxAllowedModules / displayable));
    }

    private int getScrollbarY() {
        int displayable = this.getDisplayableCount();
        int rest = displayable - this.scroll;
        int resultRaw = displayable - rest;
        return resultRaw * 15;
    }

    private int getScroll() {
        if (this.shouldScroll()) {
            return this.scroll;
        }
        return 1;
    }

    private boolean shouldScroll() {
        return this.getScrollingModuleCount() - 1 < this.getDisplayableCount();
    }

    public void handleScroll(int dWheel, int x, int y) {
        this.updateIsMouseHoveredFull(x, y);
        if (this.isMouseHovered() && this.shouldScroll()) {
            this.scroll += dWheel;
        }
    }

    private int maxDisplayHeight() {
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        int height = scaledresolution.getScaledHeight();
        height = Math.floorDiv(height, 15);
        --height;
        return height *= 15;
    }

    private int getDisplayableCount() {
        int i = 0;
        for (BaseButton but : this.buttons) {
            if (!but.shouldRender()) continue;
            ++i;
        }
        return i;
    }

    private int getScrollingModuleCount() {
        ScaledResolution scaledresolution = new ScaledResolution(Globals.MC);
        int height = scaledresolution.getScaledHeight();
        height = Math.floorDiv(height, 15);
        return --height;
    }

    private int getModulesToDisplay() {
        if (this.shouldScroll()) {
            return this.getScrollingModuleCount();
        }
        return this.getDisplayableCount();
    }

    public Category getCategory() {
        return this.category;
    }
}

