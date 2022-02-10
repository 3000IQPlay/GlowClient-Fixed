//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods.indev.EmpsShit;

import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class TracersImpact
extends ToggleMod {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public TracersImpact() {
        super(Category.JEWISHTRICKS, "TracersImpact", false, -1, "Draws lines to entities");
    }

    @Override
    public void onEnabled() {
        ModuleManager.getModuleFromName("Tracers").disable();
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        if (this.isEnabled()) {
            try {
                double renderPosX = TracersImpact.MC.getRenderManager().renderPosX;
                double renderPosY = TracersImpact.MC.getRenderManager().renderPosY;
                double renderPosZ = TracersImpact.MC.getRenderManager().renderPosZ;
                try {
                    GL11.glPushMatrix();
                    GL11.glEnable((int)2848);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)3553);
                    GL11.glDepthMask((boolean)false);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glEnable((int)3042);
                    GL11.glLineWidth((float)0.5f);
                    for (Entity entities : TracersImpact.MC.world.loadedEntityList) {
                        float distance = TracersImpact.MC.player.getDistance(entities);
                        if (entities == TracersImpact.MC.player) continue;
                        double posX = entities.lastTickPosX + (entities.posX - entities.lastTickPosX) * (double)MC.getRenderPartialTicks() - renderPosX;
                        double posY = entities.lastTickPosY + (entities.posY - entities.lastTickPosY) * (double)MC.getRenderPartialTicks() - renderPosY;
                        double posZ = entities.lastTickPosZ + (entities.posZ - entities.lastTickPosZ) * (double)MC.getRenderPartialTicks() - renderPosZ;
                        GL11.glColor4f((float)0.15f, (float)1.0f, (float)1.0f, (float)1.0f);
                        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(WMinecraft.getPlayer().rotationPitch))).rotateYaw(-((float)Math.toRadians(WMinecraft.getPlayer().rotationYaw)));
                        GL11.glBegin((int)2);
                        GL11.glVertex3d((double)eyes.x, (double)((double)WMinecraft.getPlayer().getEyeHeight() + eyes.y), (double)eyes.z);
                        if (entities instanceof EntityPlayerSP) continue;
                        if (entities instanceof EntityPlayer) {
                            GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
                        }
                        GL11.glEnd();
                        GL11.glBegin((int)2);
                        if (entities instanceof EntityPlayer) {
                            GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
                            GL11.glVertex3d((double)posX, (double)(posY + 1.75), (double)posZ);
                        }
                        GL11.glEnd();
                    }
                    GL11.glDisable((int)3042);
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)3553);
                    GL11.glEnable((int)2929);
                    GL11.glDisable((int)2848);
                    GL11.glPopMatrix();
                }
                catch (Exception exception) {}
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

