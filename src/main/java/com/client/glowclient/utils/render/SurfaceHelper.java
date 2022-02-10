//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.ForgeHooksClient
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.utils.render;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.fonts.Fonts;
import com.client.glowclient.utils.render.fonts.MinecraftFontRenderer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class SurfaceHelper
implements Globals {
    public static void drawString(@Nullable MinecraftFontRenderer fontRenderer, String text, double x, double y, int color, boolean shadow) {
        if (fontRenderer == null) {
            SurfaceHelper.MC.fontRenderer.drawString(text, (float)Math.round(x), (float)Math.round(y), color, shadow);
        } else {
            fontRenderer.drawString(text, x, y, color, shadow);
        }
    }

    public static int getTextWitFont(final String text) {
        return getTextWidthFont(text, 1.0);
    }

    public static double getStringWidth(@Nullable MinecraftFontRenderer fontRenderer, String text) {
        if (fontRenderer == null) {
            return SurfaceHelper.MC.fontRenderer.getStringWidth(text);
        }
        return fontRenderer.getStringWidth(text);
    }

    public static double getStringHeight(@Nullable MinecraftFontRenderer fontRenderer) {
        if (fontRenderer == null) {
            return SurfaceHelper.MC.fontRenderer.FONT_HEIGHT;
        }
        return fontRenderer.getHeight();
    }

    public static void drawRect(int x, int y, int w, int h, int color) {
        GL11.glLineWidth((float)1.0f);
        Gui.drawRect((int)x, (int)y, (int)(x + w), (int)(y + h), (int)color);
    }

    public static void drawOutlinedRect(double x, double y, double w, double h, int color) {
        SurfaceHelper.drawOutlinedRect(x, y, w, h, color, 1.0f);
    }

    public static void drawOutlinedRectShaded(int x, int y, int w, int h, int colorOutline, int shade, float width) {
        int shaded = 0xFFFFFF & colorOutline | (shade & 0xFF) << 24;
        SurfaceHelper.drawRect(x, y, w, h, shaded);
        SurfaceHelper.drawOutlinedRect(x, y, w, h, colorOutline, width);
    }

    public static void drawOutlinedRect(double x, double y, double w, double h, int color, float width) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)r, (float)g, (float)b, (float)a);
        GL11.glLineWidth((float)width);
        BufferBuilder2.begin(2, DefaultVertexFormats.POSITION);
        BufferBuilder2.pos(x, y, 0.0).endVertex();
        BufferBuilder2.pos(x, y + h, 0.0).endVertex();
        BufferBuilder2.pos(x + w, y + h, 0.0).endVertex();
        BufferBuilder2.pos(x + w, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawTexturedRect(int x, int y, int textureX, int textureY, int width, int height, int zLevel) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder2.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625f), (double)((float)(textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625f), (double)((float)(textureY + 0) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625f), (double)((float)(textureY + 0) * 0.00390625f)).endVertex();
        tessellator.draw();
    }

    public static void drawText(String msg, int x, int y, int color) {
        SurfaceHelper.MC.fontRenderer.drawString(msg, x, y, color);
    }

    public static void drawTextShadow(String msg, int x, int y, int color) {
        SurfaceHelper.MC.fontRenderer.drawStringWithShadow(msg, (float)x, (float)y, color);
    }

    public static void drawTextShadowCentered(String msg, float x, float y, int color) {
        float offsetX = (float)SurfaceHelper.getTextWidth(msg) / 2.0f;
        float offsetY = (float)SurfaceHelper.getTextHeight() / 2.0f;
        SurfaceHelper.MC.fontRenderer.drawStringWithShadow(msg, x - offsetX, y - offsetY, color);
    }

    public static void drawText(String msg, double x, double y, int color, double scale, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.scale((double)scale, (double)scale, (double)scale);
        SurfaceHelper.MC.fontRenderer.drawString(msg, (float)(x * (1.0 / scale)), (float)(y * (1.0 / scale)), color, shadow);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void drawText(String msg, double x, double y, int color, double scale) {
        SurfaceHelper.drawText(msg, x, y, color, scale, false);
    }

    public static void drawTextShadow(String msg, double x, double y, int color, double scale) {
        SurfaceHelper.drawText(msg, x, y, color, scale, true);
    }

    public static int getTextWidth(String text, double scale) {
        return (int)((double)SurfaceHelper.MC.fontRenderer.getStringWidth(text) * scale);
    }

    public static int getTextWidth(String text) {
        return SurfaceHelper.getTextWidth(text, 1.0);
    }

    public static int getTextWidthFont(String text, double scale) {
        return (int)((double)Fonts.VERDANA.getStringWidth(text) * scale);
    }

    public static int getTextWidthFont(String text) {
        return SurfaceHelper.getTextWidthFont(text, 1.0);
    }

    public static int getTextHeight() {
        return SurfaceHelper.MC.fontRenderer.FONT_HEIGHT;
    }

    public static int getTextHeight(double scale) {
        return (int)((double)SurfaceHelper.MC.fontRenderer.FONT_HEIGHT * scale);
    }

    public static void drawItem(ItemStack item, double x, double y) {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        SurfaceHelper.MC.getRenderItem().zLevel = 100.0f;
        SurfaceHelper.renderItemAndEffectIntoGUI((EntityLivingBase)SurfaceHelper.MC.player, item, x, y, 16.0);
        SurfaceHelper.MC.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawItemWithOverlay(ItemStack item, double x, double y, double scale) {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        SurfaceHelper.MC.getRenderItem().zLevel = 100.0f;
        SurfaceHelper.renderItemAndEffectIntoGUI((EntityLivingBase)SurfaceHelper.MC.player, item, x, y, 16.0);
        SurfaceHelper.renderItemOverlayIntoGUI(SurfaceHelper.MC.fontRenderer, item, x, y, null, scale);
        SurfaceHelper.MC.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawPotionEffect(PotionEffect potion, int x, int y) {
        int index = potion.getPotion().getStatusIconIndex();
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        MC.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
        SurfaceHelper.drawTexturedRect(x, y, index % 8 * 18, 198 + index / 8 * 18, 18, 18, 100);
        potion.getPotion().renderHUDEffect(x, y, potion, MC, 255.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    public static void drawHead(ResourceLocation skinResource, double x, double y, float scale) {
        GlStateManager.pushMatrix();
        SurfaceHelper.MC.renderEngine.bindTexture(skinResource);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.scale((float)scale, (float)scale, (float)scale);
        SurfaceHelper.drawScaledCustomSizeModalRect(x * (double)(1.0f / scale), y * (double)(1.0f / scale), 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0, 64.0);
        SurfaceHelper.drawScaledCustomSizeModalRect(x * (double)(1.0f / scale), y * (double)(1.0f / scale), 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0, 64.0);
        GlStateManager.popMatrix();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void renderItemAndEffectIntoGUI(@Nullable EntityLivingBase living, ItemStack stack, double x, double y, double scale) {
        if (!stack.isEmpty()) {
            SurfaceHelper.MC.getRenderItem().zLevel += 50.0f;
            try {
                SurfaceHelper.renderItemModelIntoGUI(stack, x, y, MC.getRenderItem().getItemModelWithOverrides(stack, null, living), scale);
            }
            catch (Throwable t) {
                Utils.handleThrowable(t);
            }
            finally {
                SurfaceHelper.MC.getRenderItem().zLevel -= 50.0f;
            }
        }
    }

    private static void renderItemModelIntoGUI(ItemStack stack, double x, double y, IBakedModel bakedmodel, double scale) {
        GlStateManager.pushMatrix();
        MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        MC.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc((int)516, (float)0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.translate((double)x, (double)y, (double)1.0);
        GlStateManager.translate((float)8.0f, (float)8.0f, (float)0.0f);
        GlStateManager.scale((float)1.0f, (float)-1.0f, (float)1.0f);
        GlStateManager.scale((double)scale, (double)scale, (double)scale);
        if (bakedmodel.isGui3d()) {
            GlStateManager.enableLighting();
        } else {
            GlStateManager.disableLighting();
        }
        bakedmodel = ForgeHooksClient.handleCameraTransforms((IBakedModel)bakedmodel, (ItemCameraTransforms.TransformType)ItemCameraTransforms.TransformType.GUI, (boolean)false);
        MC.getRenderItem().renderItem(stack, bakedmodel);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        MC.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }

    protected static void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, double xPosition, double yPosition, @Nullable String text, double scale) {
        double SCALE_RATIO = 1.23076923077;
        if (!stack.isEmpty()) {
            EntityPlayerSP entityplayersp;
            float f3;
            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fr.drawStringWithShadow(s, (float)(xPosition + 19.0 - 2.0 - (double)fr.getStringWidth(s)), (float)(yPosition + 6.0 + 3.0), 0xFFFFFF);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableBlend();
            }
            if (stack.getItem().showDurabilityBar(stack)) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                double health = stack.getItem().getDurabilityForDisplay(stack);
                int rgbfordisplay = stack.getItem().getRGBDurabilityForDisplay(stack);
                int i = Math.round(13.0f - (float)health * 13.0f);
                int j = rgbfordisplay;
                SurfaceHelper.draw(xPosition + scale / 8.0, yPosition + scale / 1.23076923077, 13.0, 2.0, 0, 0, 0, 255);
                SurfaceHelper.draw(xPosition + scale / 8.0, yPosition + scale / 1.23076923077, i, 1.0, j >> 16 & 0xFF, j >> 8 & 0xFF, j & 0xFF, 255);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            float f = f3 = (entityplayersp = Minecraft.getMinecraft().player) == null ? 0.0f : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());
            if (f3 > 0.0f) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                SurfaceHelper.draw(xPosition, yPosition + scale * (double)(1.0f - f3), 16.0, scale * (double)f3, 255, 255, 255, 127);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    private static void draw(double x, double y, double width, double height, int red, int green, int blue, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(x + 0.0, y + 0.0, 0.0).color(red, green, blue, alpha).endVertex();
        renderer.pos(x + 0.0, y + height, 0.0).color(red, green, blue, alpha).endVertex();
        renderer.pos(x + width, y + height, 0.0).color(red, green, blue, alpha).endVertex();
        renderer.pos(x + width, y + 0.0, 0.0).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

    protected static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, double uWidth, double vHeight, double width, double height, double tileWidth, double tileHeight) {
        double f = 1.0 / tileWidth;
        double f1 = 1.0 / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0).tex((double)u * f, (double)(v + (float)vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0).tex((double)(u + (float)uWidth) * f, (double)(v + (float)vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0).tex((double)(u + (float)uWidth) * f, (double)v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex((double)u * f, (double)v * f1).endVertex();
        tessellator.draw();
    }

    public static int getHeadWidth(float scale) {
        return (int)(scale * 12.0f);
    }

    public static int getHeadWidth() {
        return SurfaceHelper.getHeadWidth(1.0f);
    }

    public static int getHeadHeight(float scale) {
        return (int)(scale * 12.0f);
    }

    public static int getHeadHeight() {
        return SurfaceHelper.getHeadWidth(1.0f);
    }
}

