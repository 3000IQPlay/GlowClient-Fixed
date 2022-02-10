//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelBoat
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderBoat
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.client.glowclient.utils.mod.mods.boattravel.ModelBoatTaxi;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RenderBoat.class})
public abstract class MixinRenderBoat
extends Render<EntityBoat> {
    @Shadow
    protected ModelBase modelBoat = new ModelBoat();
    protected ModelBase modelBoat2 = new ModelBoatTaxi();

    public MixinRenderBoat() {
        super(null);
    }

    @Shadow
    public void setupRotation(EntityBoat entityIn, float entityYaw, float partialTicks) {
        GlStateManager.rotate((float)(180.0f - entityYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        float f = (float)entityIn.getTimeSinceHit() - partialTicks;
        float f1 = entityIn.getDamageTaken() - partialTicks;
        if (f1 < 0.0f) {
            f1 = 0.0f;
        }
        if (f > 0.0f) {
            GlStateManager.rotate((float)(MathHelper.sin((float)f) * f * f1 / 10.0f * (float)entityIn.getForwardDirection()), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        GlStateManager.scale((float)-1.0f, (float)-1.0f, (float)1.0f);
    }

    @Shadow
    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translate((float)((float)x), (float)((float)y + 0.375f), (float)((float)z));
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
        SpongeHooks.onRenderBoat(entity, entityYaw);
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

}

