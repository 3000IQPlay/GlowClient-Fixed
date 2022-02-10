//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.utils.render;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.MinecraftFontRenderer;
import java.util.Stack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class SurfaceBuilder {
    public static final int COLOR = 1;
    public static final int SCALE = 2;
    public static final int TRANSLATION = 4;
    public static final int ROTATION = 8;
    public static final int ALL = 15;
    private static final SurfaceBuilder INSTANCE = new SurfaceBuilder();
    private final Stack<RenderSettings> settings = new Stack();
    private final RenderSettings DEFAULT_SETTINGS = new RenderSettings();

    public static SurfaceBuilder getBuilder() {
        return INSTANCE;
    }

    public RenderSettings current() {
        return !this.settings.isEmpty() ? this.settings.peek() : this.DEFAULT_SETTINGS;
    }

    public SurfaceBuilder begin(int mode) {
        GL11.glBegin((int)mode);
        return this;
    }

    public SurfaceBuilder beginLines() {
        return this.begin(1);
    }

    public SurfaceBuilder beginLineLoop() {
        return this.begin(2);
    }

    public SurfaceBuilder beginQuads() {
        return this.begin(7);
    }

    public SurfaceBuilder beginPolygon() {
        return this.begin(9);
    }

    public SurfaceBuilder end() {
        GL11.glEnd();
        return this;
    }

    public SurfaceBuilder autoApply(boolean enabled) {
        this.current().setAutoApply(enabled);
        return this;
    }

    public SurfaceBuilder apply() {
        return this.apply(15);
    }

    public SurfaceBuilder apply(int flags) {
        RenderSettings current = this.current();
        if ((flags & 1) == 1) {
            current.applyColor();
        }
        if ((flags & 2) == 2) {
            current.applyScale();
        }
        if ((flags & 4) == 4) {
            current.applyTranslation();
        }
        if ((flags & 8) == 8) {
            current.applyRotation();
        }
        return this;
    }

    public SurfaceBuilder reset() {
        return this.reset(15);
    }

    public SurfaceBuilder reset(int flags) {
        RenderSettings current = this.current();
        if ((flags & 1) == 1) {
            current.resetColor();
        }
        if ((flags & 2) == 2) {
            current.resetScale();
        }
        if ((flags & 4) == 4) {
            current.resetTranslation();
        }
        if ((flags & 8) == 8) {
            current.resetRotation();
        }
        return this;
    }

    public SurfaceBuilder push() {
        GlStateManager.pushMatrix();
        this.settings.push(new RenderSettings());
        return this;
    }

    public SurfaceBuilder pop() {
        if (!this.settings.isEmpty()) {
            this.settings.pop();
        }
        GlStateManager.popMatrix();
        return this;
    }

    public SurfaceBuilder color(double r, double g, double b, double a) {
        this.current().setColor4d(new double[]{MathHelper.clamp((double)r, (double)0.0, (double)1.0), MathHelper.clamp((double)g, (double)0.0, (double)1.0), MathHelper.clamp((double)b, (double)0.0, (double)1.0), MathHelper.clamp((double)a, (double)0.0, (double)1.0)});
        return this;
    }

    public SurfaceBuilder color(int buffer) {
        return this.color((double)(buffer >> 16 & 0xFF) / 255.0, (double)(buffer >> 8 & 0xFF) / 255.0, (double)(buffer & 0xFF) / 255.0, (double)(buffer >> 24 & 0xFF) / 255.0);
    }

    public SurfaceBuilder color(int r, int g, int b, int a) {
        return this.color((double)r / 255.0, (double)g / 255.0, (double)b / 255.0, (double)a / 255.0);
    }

    public SurfaceBuilder scale(double x, double y, double z) {
        this.current().setScale3d(new double[]{x, y, z});
        return this;
    }

    public SurfaceBuilder scale(double s) {
        return this.scale(s, s, s);
    }

    public SurfaceBuilder scale() {
        return this.scale(0.0);
    }

    public SurfaceBuilder translate(double x, double y, double z) {
        this.current().setTranslate3d(new double[]{x, y, z});
        return this;
    }

    public SurfaceBuilder translate(double x, double y) {
        return this.translate(x, y, 0.0);
    }

    public SurfaceBuilder rotate(double angle, double x, double y, double z) {
        this.current().setRotated4d(new double[]{angle, x, y, z});
        return this;
    }

    public SurfaceBuilder width(double width) {
        GlStateManager.glLineWidth((float)((float)width));
        return this;
    }

    public SurfaceBuilder vertex(double x, double y, double z) {
        GL11.glVertex3d((double)x, (double)y, (double)z);
        return this;
    }

    public SurfaceBuilder vertex(double x, double y) {
        GL11.glVertex2d((double)x, (double)y);
        return this;
    }

    public SurfaceBuilder line(double startX, double startY, double endX, double endY) {
        return this.vertex(startX, startY).vertex(endX, endY);
    }

    public SurfaceBuilder rectangle(double x, double y, double w, double h) {
        return this.vertex(x, y).vertex(x, y + h).vertex(x + w, y + h).vertex(x + w, y);
    }

    public SurfaceBuilder fontRenderer(MinecraftFontRenderer fontRenderer) {
        this.current().setFontRenderer(fontRenderer);
        return this;
    }

    public SurfaceBuilder text(String text, double x, double y, boolean shadow) {
        if (this.current().hasFontRenderer()) {
            this.current().getFontRenderer().drawString(text, x, y + 1.0, Colors.toRGBA(this.current().getColor4d()), shadow);
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)x, (double)(y + 1.0), (double)0.0);
            Globals.MC.fontRenderer.drawString(text, 0.0f, 0.0f, Colors.toRGBA(this.current().getColor4d()), shadow);
            GlStateManager.popMatrix();
        }
        return this;
    }

    public SurfaceBuilder text(String text, double x, double y) {
        return this.text(text, x, y, false);
    }

    public SurfaceBuilder textcentered(String text, double x, double y, boolean shadow) {
        double offsetX = SurfaceHelper.getStringWidth(HUD.FONT, text) / 2.0;
        double offsetY = SurfaceHelper.getStringHeight(HUD.FONT) / 2.0;
        if (this.current().hasFontRenderer()) {
            this.current().getFontRenderer().drawString(text, x - SurfaceHelper.getStringWidth(HUD.FONT, text) / 2.0, y, Colors.toRGBA(this.current().getColor4d()), shadow);
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)x, (double)(y + 1.0), (double)0.0);
            Globals.MC.fontRenderer.drawString(text, 0.0f, 0.0f, Colors.toRGBA(this.current().getColor4d()), shadow);
            GlStateManager.popMatrix();
        }
        return this;
    }

    public SurfaceBuilder task(Runnable task) {
        task.run();
        return this;
    }

    public SurfaceBuilder item(ItemStack stack, double x, double y) {
        Globals.MC.getRenderItem().zLevel = 100.0f;
        SurfaceHelper.renderItemAndEffectIntoGUI((EntityLivingBase)Globals.MC.player, stack, x, y, this.current().hasScale() ? this.current().getScale3d()[0] : 16.0);
        Globals.MC.getRenderItem().zLevel = 0.0f;
        return this;
    }

    public SurfaceBuilder itemOverlay(ItemStack stack, double x, double y) {
        SurfaceHelper.renderItemOverlayIntoGUI(Globals.MC.fontRenderer, stack, x, y, null, this.current().hasScale() ? this.current().getScale3d()[0] : 16.0);
        return this;
    }

    public SurfaceBuilder head(ResourceLocation resource, double x, double y) {
        Globals.MC.renderEngine.bindTexture(resource);
        double scale = this.current().hasScale() ? this.current().getScale3d()[0] : 12.0;
        SurfaceHelper.drawScaledCustomSizeModalRect(x * (1.0 / scale), y * (1.0 / scale), 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0, 64.0);
        SurfaceHelper.drawScaledCustomSizeModalRect(x * (1.0 / scale), y * (1.0 / scale), 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0, 64.0);
        return this;
    }

    public int getFontWidth(String text) {
        return this.current().hasFontRenderer() ? this.current().getFontRenderer().getStringWidth(text) : Globals.MC.fontRenderer.getStringWidth(text);
    }

    public int getFontHeight() {
        return this.current().hasFontRenderer() ? this.current().getFontRenderer().getHeight() : Globals.MC.fontRenderer.FONT_HEIGHT;
    }

    public int getFontHeight(String text) {
        return this.getFontHeight();
    }

    private double _getScaled(int index, double p) {
        return p * (1.0 / this.current().getScale3d()[index]);
    }

    public double getScaledX(double x) {
        return this._getScaled(0, x);
    }

    public double getScaledY(double y) {
        return this._getScaled(1, y);
    }

    public double getScaledZ(double z) {
        return this._getScaled(2, z);
    }

    public double getScaled(double p) {
        return this.getScaledX(p);
    }

    public double getItemSize() {
        return 16.0;
    }

    public static void disableTexture2D() {
        GlStateManager.disableTexture2D();
    }

    public static void enableTexture2D() {
        GlStateManager.enableTexture2D();
    }

    public static void enableBlend() {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
    }

    public static void disableBlend() {
        GlStateManager.disableBlend();
    }

    public static void enableFontRendering() {
        GlStateManager.disableDepth();
    }

    public static void disableFontRendering() {
        GlStateManager.enableDepth();
    }

    public static void enableItemRendering() {
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
    }

    public static void disableItemRendering() {
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
    }

    public static void clearColor() {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private static class RenderSettings {
        private static final double[] EMPTY_VECTOR3D = new double[]{0.0, 0.0, 0.0};
        private static final double[] EMPTY_VECTOR4D = new double[]{0.0, 0.0, 0.0, 0.0};
        private double[] color4d = EMPTY_VECTOR4D;
        private double[] scale3d = EMPTY_VECTOR3D;
        private double[] translate3d = EMPTY_VECTOR3D;
        private double[] rotated4d = EMPTY_VECTOR4D;
        private boolean autoApply = true;
        private MinecraftFontRenderer fontRenderer = null;

        private RenderSettings() {
        }

        public double[] getColor4d() {
            return this.color4d;
        }

        public void setColor4d(double[] color4d) {
            this.color4d = color4d;
            if (this.autoApply) {
                this.applyColor();
            }
        }

        public double[] getScale3d() {
            return this.scale3d;
        }

        public void setScale3d(double[] scale3d) {
            this.scale3d = scale3d;
            if (this.autoApply) {
                this.applyScale();
            }
        }

        public double[] getTranslate3d() {
            return this.translate3d;
        }

        public void setTranslate3d(double[] translate3d) {
            this.translate3d = translate3d;
            if (this.autoApply) {
                this.applyTranslation();
            }
        }

        public double[] getRotated4d() {
            return this.rotated4d;
        }

        public void setRotated4d(double[] rotated4d) {
            this.rotated4d = rotated4d;
            if (this.autoApply) {
                this.applyRotation();
            }
        }

        public MinecraftFontRenderer getFontRenderer() {
            return this.fontRenderer;
        }

        public void setFontRenderer(MinecraftFontRenderer fontRenderer) {
            this.fontRenderer = fontRenderer;
        }

        public void setAutoApply(boolean autoApply) {
            this.autoApply = autoApply;
        }

        public boolean hasColor() {
            return this.color4d != EMPTY_VECTOR4D;
        }

        public boolean hasScale() {
            return this.scale3d != EMPTY_VECTOR3D;
        }

        public boolean hasTranslation() {
            return this.translate3d != EMPTY_VECTOR3D;
        }

        public boolean hasRotation() {
            return this.rotated4d != EMPTY_VECTOR4D;
        }

        public boolean hasFontRenderer() {
            return this.fontRenderer != null;
        }

        public void applyColor() {
            if (this.hasColor()) {
                GL11.glColor4d((double)this.color4d[0], (double)this.color4d[1], (double)this.color4d[2], (double)this.color4d[3]);
            }
        }

        public void applyScale() {
            if (this.hasScale()) {
                GL11.glScaled((double)this.scale3d[0], (double)this.scale3d[1], (double)this.scale3d[2]);
            }
        }

        public void applyTranslation() {
            if (this.hasTranslation()) {
                GL11.glTranslated((double)this.translate3d[0], (double)this.translate3d[1], (double)this.translate3d[2]);
            }
        }

        public void applyRotation() {
            if (this.hasRotation()) {
                GL11.glRotated((double)this.rotated4d[0], (double)this.rotated4d[1], (double)this.rotated4d[2], (double)this.rotated4d[3]);
            }
        }

        public void clearColor() {
            this.color4d = EMPTY_VECTOR4D;
        }

        public void clearScale() {
            this.scale3d = EMPTY_VECTOR3D;
        }

        public void clearTranslation() {
            this.translate3d = EMPTY_VECTOR3D;
        }

        public void clearRotation() {
            this.rotated4d = EMPTY_VECTOR4D;
        }

        public void clearFontRenderer() {
            this.fontRenderer = null;
        }

        public void resetColor() {
            if (this.hasColor()) {
                this.clearColor();
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            }
        }

        public void resetScale() {
            if (this.hasScale()) {
                this.clearScale();
                GL11.glScaled((double)1.0, (double)1.0, (double)1.0);
            }
        }

        public void resetTranslation() {
            if (this.hasTranslation()) {
                this.clearTranslation();
                GL11.glTranslated((double)0.0, (double)0.0, (double)0.0);
            }
        }

        public void resetRotation() {
            if (this.hasRotation()) {
                this.clearRotation();
                GL11.glRotated((double)0.0, (double)0.0, (double)0.0, (double)0.0);
            }
        }
    }
}

