//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.util.ResourceLocation
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.EnchColors;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.RainbowUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RenderItem.class})
public abstract class MixinRenderItem {
    @Shadow
    private TextureManager textureManager;
    @Shadow
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    @Shadow
    private void renderModel(IBakedModel model, int color) {
    }

    @Overwrite
    private void renderEffect(IBakedModel model) {
        GlStateManager.depthMask((boolean)false);
        GlStateManager.depthFunc((int)514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        this.textureManager.bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode((int)5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)8.0f, (float)8.0f, (float)8.0f);
        float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
        GlStateManager.translate((float)f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        if (ModuleManager.getModuleFromName("EnchColors").isEnabled()) {
            if (EnchColors.rainbow.getBoolean()) {
                this.renderModel(model, Colors.toRGBA(RainbowUtils.getRainbowColor(1L, 1.0f).getRed(), RainbowUtils.getRainbowColor(1L, 1.0f).getGreen(), RainbowUtils.getRainbowColor(1L, 1.0f).getBlue(), 255));
            } else {
                this.renderModel(model, Colors.toRGBA(EnchColors.red.getInt(), EnchColors.green.getInt(), EnchColors.blue.getInt(), 255));
            }
        } else {
            this.renderModel(model, -8372020);
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)8.0f, (float)8.0f, (float)8.0f);
        float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0f / 8.0f;
        GlStateManager.translate((float)(-f1), (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        if (ModuleManager.getModuleFromName("EnchColors").isEnabled()) {
            if (EnchColors.rainbow.getBoolean()) {
                this.renderModel(model, Colors.toRGBA(RainbowUtils.getRainbowColor(1L, 1.0f).getRed(), RainbowUtils.getRainbowColor(1L, 1.0f).getGreen(), RainbowUtils.getRainbowColor(1L, 1.0f).getBlue(), 255));
            } else {
                this.renderModel(model, Colors.toRGBA(EnchColors.red.getInt(), EnchColors.green.getInt(), EnchColors.blue.getInt(), 255));
            }
        } else {
            this.renderModel(model, -8372020);
        }
        GlStateManager.popMatrix();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc((int)515);
        GlStateManager.depthMask((boolean)true);
        this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }
}

