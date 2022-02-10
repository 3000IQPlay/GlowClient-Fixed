//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiUtilRenderComponents
 *  net.minecraft.client.model.ModelSign
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.tileentity.TileEntitySignRenderer
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.init.Blocks
 *  net.minecraft.tileentity.TileEntitySign
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.text.ITextComponent
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.ColorSigns;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={TileEntitySignRenderer.class})
public abstract class MixinTileEntitySignRenderer
extends TileEntitySpecialRenderer<TileEntitySign> {
    @Shadow
    private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
    @Shadow
    private final ModelSign model = new ModelSign();

    @Overwrite
    public void render(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Block block = te.getBlockType();
        GlStateManager.pushMatrix();
        float f = 0.6666667f;
        if (block == Blocks.STANDING_SIGN) {
            GlStateManager.translate((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
            float f1 = (float)(te.getBlockMetadata() * 360) / 16.0f;
            GlStateManager.rotate((float)(-f1), (float)0.0f, (float)1.0f, (float)0.0f);
            this.model.signStick.showModel = true;
        } else {
            int k = te.getBlockMetadata();
            float f2 = 0.0f;
            if (k == 2) {
                f2 = 180.0f;
            }
            if (k == 4) {
                f2 = 90.0f;
            }
            if (k == 5) {
                f2 = -90.0f;
            }
            GlStateManager.translate((float)((float)x + 0.5f), (float)((float)y + 0.5f), (float)((float)z + 0.5f));
            GlStateManager.rotate((float)(-f2), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.translate((float)0.0f, (float)-0.3125f, (float)-0.4375f);
            this.model.signStick.showModel = false;
        }
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode((int)5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale((float)4.0f, (float)2.0f, (float)1.0f);
            GlStateManager.translate((float)0.0625f, (float)0.0625f, (float)0.0625f);
            GlStateManager.matrixMode((int)5888);
        } else {
            this.bindTexture(SIGN_TEXTURE);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)0.6666667f, (float)-0.6666667f, (float)-0.6666667f);
        this.model.renderSign();
        GlStateManager.popMatrix();
        FontRenderer fontrenderer = this.getFontRenderer();
        float f3 = 0.010416667f;
        GlStateManager.translate((float)0.0f, (float)0.33333334f, (float)0.046666667f);
        GlStateManager.scale((float)0.010416667f, (float)-0.010416667f, (float)0.010416667f);
        GlStateManager.glNormal3f((float)0.0f, (float)0.0f, (float)-0.010416667f);
        GlStateManager.depthMask((boolean)false);
        boolean i = false;
        if (destroyStage < 0) {
            for (int j = 0; j < te.signText.length; ++j) {
                String s;
                if (te.signText[j] == null) continue;
                ITextComponent itextcomponent = te.signText[j];
                int length = ColorSigns.doubleS.getInt();
                List list = GuiUtilRenderComponents.splitText((ITextComponent)itextcomponent, (int)length, (FontRenderer)fontrenderer, (boolean)false, (boolean)true);
                String string = s = list != null && !list.isEmpty() ? ((ITextComponent)list.get(0)).getFormattedText() : "";
                if (j == te.lineBeingEdited) {
                    s = "> " + s + " <";
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, 0);
                    continue;
                }
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, 0);
            }
        }
        GlStateManager.depthMask((boolean)true);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
        if (destroyStage >= 0) {
            GlStateManager.matrixMode((int)5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode((int)5888);
        }
    }
}

