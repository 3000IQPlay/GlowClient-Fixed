//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.client.ForgeHooksClient
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.CameraClip;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer
implements IResourceManagerReloadListener {
    @Shadow
    private Minecraft mc;
    @Shadow
    private final float thirdPersonDistance = 4.0f;
    @Shadow
    private float thirdPersonDistancePrev = 4.0f;
    @Shadow
    private boolean cloudFog;
    @Shadow
    private float farPlaneDistance;

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void hurtCameraEffect(float partialTicks) {
        if (!HookUtils.isNoHurtcamActivated && this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
            float f = (float)entitylivingbase.hurtTime - partialTicks;
            if (entitylivingbase.getHealth() <= 0.0f) {
                float f1 = (float)entitylivingbase.deathTime + partialTicks;
                GlStateManager.rotate((float)(40.0f - 8000.0f / (f1 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            if (f < 0.0f) {
                return;
            }
            f /= (float)entitylivingbase.maxHurtTime;
            f = MathHelper.sin((float)(f * f * f * f * (float)Math.PI));
            float f2 = entitylivingbase.attackedAtYaw;
            GlStateManager.rotate((float)(-f2), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-f * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void orientCamera(float partialTicks) {
        Entity entity = Globals.MC.getRenderViewEntity();
        partialTicks = 0.0f;
        double gayY = entity.posY;
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        double d1 = entity.prevPosY + (gayY - entity.prevPosY) * (double)partialTicks + (double)f;
        if (HookUtils.isCameraClipActivated) {
            d1 = entity.prevPosY + (gayY - entity.prevPosY) * (double)partialTicks + (double)f + 1160.0;
        }
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
            f = (float)((double)f + 1.0);
            GlStateManager.translate((float)0.0f, (float)0.3f, (float)0.0f);
            if (!Globals.MC.gameSettings.debugCamEnable) {
                BlockPos blockpos = new BlockPos(entity);
                IBlockState iblockstate = Globals.MC.world.getBlockState(blockpos);
                ForgeHooksClient.orientBedCamera((IBlockAccess)Globals.MC.world, (BlockPos)blockpos, (IBlockState)iblockstate, (Entity)entity);
                GlStateManager.rotate((float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GlStateManager.rotate((float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks), (float)-1.0f, (float)0.0f, (float)0.0f);
            }
        } else if (Globals.MC.gameSettings.thirdPersonView > 0) {
            double d3 = HookUtils.isCameraClipActivated ? (double)this.thirdPersonDistancePrev + (CameraClip.doubleS.getDouble() - (double)this.thirdPersonDistancePrev) : (double)(this.thirdPersonDistancePrev + (4.0f - this.thirdPersonDistancePrev) * partialTicks);
            if (Globals.MC.gameSettings.debugCamEnable) {
                GlStateManager.translate((float)0.0f, (float)0.0f, (float)((float)(-d3)));
            } else {
                float f1 = entity.rotationYaw;
                float f2 = entity.rotationPitch;
                if (Globals.MC.gameSettings.thirdPersonView == 2) {
                    f2 += 180.0f;
                }
                double d4 = (double)(-MathHelper.sin((float)(f1 * ((float)Math.PI / 180))) * MathHelper.cos((float)(f2 * ((float)Math.PI / 180)))) * d3;
                double d5 = (double)(MathHelper.cos((float)(f1 * ((float)Math.PI / 180))) * MathHelper.cos((float)(f2 * ((float)Math.PI / 180)))) * d3;
                double d6 = (double)(-MathHelper.sin((float)(f2 * ((float)Math.PI / 180)))) * d3;
                for (int i = 0; i < 8; ++i) {
                    double d7;
                    RayTraceResult raytraceresult;
                    float f3 = (i & 1) * 2 - 1;
                    float f4 = (i >> 1 & 1) * 2 - 1;
                    float f5 = (i >> 2 & 1) * 2 - 1;
                    if ((raytraceresult = Globals.MC.world.rayTraceBlocks(new Vec3d(d0 + (double)(f3 *= 0.1f), d1 + (double)(f4 *= 0.1f), d2 + (double)(f5 *= 0.1f)), new Vec3d(d0 - d4 + (double)f3 + (double)f5, d1 - d6 + (double)f4, d2 - d5 + (double)f5))) == null || !((d7 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d1, d2))) < d3)) continue;
                    d3 = d7;
                }
                if (Globals.MC.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GlStateManager.rotate((float)(entity.rotationPitch - f2), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)(entity.rotationYaw - f1), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.translate((float)0.0f, (float)0.0f, (float)((float)(-d3)));
                GlStateManager.rotate((float)(f1 - entity.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)(f2 - entity.rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        } else {
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.05f);
        }
        if (!Globals.MC.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            float roll = 0.0f;
            if (entity instanceof EntityAnimal) {
                EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }
            IBlockState state = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)Globals.MC.world, (Entity)entity, (float)partialTicks);
            EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer)EntityRenderer.class.cast(this), entity, state, (double)partialTicks, yaw, pitch, roll);
            MinecraftForge.EVENT_BUS.post((Event)event);
            GlStateManager.rotate((float)event.getRoll(), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)event.getPitch(), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)event.getYaw(), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GlStateManager.translate((float)0.0f, (float)(-f), (float)0.0f);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        d1 = entity.prevPosY + (gayY - entity.prevPosY) * (double)partialTicks + (double)f;
        d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        this.cloudFog = Globals.MC.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
    }
}

