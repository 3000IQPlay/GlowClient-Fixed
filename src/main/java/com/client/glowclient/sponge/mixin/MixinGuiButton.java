//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(value=Side.CLIENT)
@Mixin(value={GuiButton.class})
public abstract class MixinGuiButton
extends Gui {
    @Shadow
    public boolean visible;
    @Shadow
    protected boolean hovered;
    @Shadow
    public int x;
    @Shadow
    public int y;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    public boolean enabled;
    @Shadow
    public String displayString;
    private float state = 100.0f;
    private long timer = System.currentTimeMillis();
    private int cs;
    private int alpha;

    @Shadow
    protected abstract void mouseDragged(Minecraft var1, int var2, int var3);

    @Inject(method={"drawButton"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderTileEntityAt(Minecraft MC, int mouseX, int mouseY, float partialTicks, CallbackInfo ci2) {
        ci2.cancel();
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            float glideSpeed = 5.0f;
            this.state = this.hovered ? Math.max(0.0f, this.state -= (float)(System.currentTimeMillis() - this.timer) / 5.0f) : Math.min(100.0f, this.state += (float)(System.currentTimeMillis() - this.timer) / 5.0f);
            this.timer = System.currentTimeMillis();
            int offset = (int)(this.state / 8.0f);
            int c2 = (int)(200.0f - this.state);
            if (this.width > 50) {
                MixinGuiButton.drawRect((int)(this.x + offset), (int)this.y, (int)(this.x + this.width - offset), (int)(this.y + this.height), (int)new Color(this.enabled ? c2 : 125, this.enabled ? c2 : 0, this.enabled ? c2 : 0, c2).getRGB());
            }
            GlStateManager.bindTexture((int)0);
            this.mouseDragged(MC, mouseX, mouseY);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawCenteredString(MC.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xE0E0E0);
            GlStateManager.disableBlend();
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void drawButton(Minecraft MC, int mouseX, int mouseY, float partialTicks) {
        Color color1 = new Color(0.0f, 0.0f, 0.0f, (float)this.alpha / 255.0f);
        int col1 = color1.getRGB();
        if (this.visible) {
            FontRenderer var4 = MC.fontRenderer;
            String languageCode = MC.getLanguageManager().getCurrentLanguage().getLanguageCode();
            if (languageCode.equalsIgnoreCase("de_DE") || languageCode.equalsIgnoreCase("en_US") || languageCode.equalsIgnoreCase("en_GB")) {
                var4 = MC.fontRenderer;
            }
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            boolean bl = this.hovered;
            if (this.enabled) {
                if (this.hovered) {
                    this.alpha += 25;
                    if (this.alpha >= 210) {
                        this.alpha = 210;
                    }
                } else {
                    this.alpha -= 25;
                    if (this.alpha <= 120) {
                        this.alpha = 120;
                    }
                }
            }
            if (this.hovered) {
                if (this.cs >= 4) {
                    this.cs = 4;
                }
                ++this.cs;
            } else {
                if (this.cs <= 0) {
                    this.cs = 0;
                }
                --this.cs;
            }
            if (this.enabled) {
                Gui.drawRect((int)(this.x + this.cs), (int)this.y, (int)(this.x + this.width - this.cs), (int)(this.y + this.height), (int)col1);
            } else {
                Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (int)new Color(0.5f, 0.5f, 0.5f, 0.5f).hashCode());
            }
            this.mouseDragged(MC, mouseX, mouseY);
            var4.drawStringWithShadow(this.displayString, (float)(this.x + this.width / 2 - var4.getStringWidth(this.displayString) / 2), (float)(this.y + (this.height - 5) / 2), 0xE0E0E0);
            GlStateManager.resetColor();
        }
    }
}

