//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$LogicOp
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.ChunkPos
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Sphere
 */
package com.client.glowclient.utils.render;

import com.client.glowclient.utils.client.Globals;
import java.awt.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class RenderUtils {
    public static void drawEntityESP(Entity entity, int red, int green, int blue) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glPushMatrix();
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Globals.MC.getRenderPartialTicks() - renderPosX), (double)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)Globals.MC.getRenderPartialTicks() - renderPosY), (double)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Globals.MC.getRenderPartialTicks() - renderPosZ));
            RenderUtils.drawColorBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX, entity.getEntityBoundingBox().minY - 0.0 - entity.posY, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ), 0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)255.0);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX, entity.getEntityBoundingBox().minY - 0.0 - entity.posY, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ));
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2848);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawTargetESP(Entity entity, int red, int green, int blue) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glPushMatrix();
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Globals.MC.getRenderPartialTicks() - renderPosX), (double)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)Globals.MC.getRenderPartialTicks() - renderPosY), (double)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Globals.MC.getRenderPartialTicks() - renderPosZ));
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)255.0);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX, entity.getEntityBoundingBox().minY - 0.0 - entity.posY, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ));
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2848);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawFreecamESP(double x1, double y1, double z1, int red, int green, int blue) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)3553);
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)0.1825f);
            double x = x1 - renderPosX;
            double y = y1 - renderPosY;
            double z = z1 - renderPosZ;
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 0.6, y + 1.8, z + 0.6));
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)1.0);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 0.6, y + 1.8, z + 0.6));
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawLogoutESP(double d, double d1, double d2, double r, double g, double b) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)r, (double)g, (double)b, (double)0.1825f);
        RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + 0.6, d1 + 1.8, d2 + 0.6));
        GL11.glColor4d((double)r, (double)g, (double)b, (double)1.0);
        RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + 0.6, d1 + 1.8, d2 + 0.6));
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void chunkEspTall(ChunkPos blockPos, int red, int green, int blue, int alpha) {
        int length2 = 16;
        int length = 16;
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            double x = (double)(blockPos.x * 16) - renderPosX;
            double z = (double)(blockPos.z * 16) - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            RenderUtils.drawColorBox(new AxisAlignedBB(x, 0.0 - renderPosY, z, x + (double)length2, 256.0 - renderPosY, z + (double)length), red, green, blue, alpha);
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, 0.0 - renderPosY, z, x + (double)length2, 256.0 - renderPosY, z + (double)length));
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void chunkEspFlat(ChunkPos blockPos, int red, int green, int blue, int alpha) {
        int length2 = 16;
        int length = 16;
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            double x = (double)(blockPos.x * 16) - renderPosX;
            double z = (double)(blockPos.z * 16) - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, 0.0 - renderPosY, z, x + (double)length2, 0.0 - renderPosY, z + (double)length));
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawBlockESP(BlockPos blockPos, int red, int green, int blue, int alpha, int outlineR, int outlineG, int outlineB, int outlineA, int outlineWidth) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)outlineWidth);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
            RenderUtils.drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glColor4d((double)((float)outlineR / 255.0f), (double)((float)outlineG / 255.0f), (double)((float)outlineB / 255.0f), (double)((float)outlineA / 255.0f));
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
            GL11.glLineWidth((float)2.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void blockEspFrame(BlockPos blockPos, double red, double green, double blue) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)1.0);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void blockEspBox(BlockPos blockPos, double red, double green, double blue) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            double x = (double)blockPos.getX() - renderPosX;
            double y = (double)blockPos.getY() - renderPosY;
            double z = (double)blockPos.getZ() - renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4d((double)red, (double)green, (double)blue, (double)0.15f);
            RenderUtils.drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void box(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
            RenderUtils.drawColorBox(new AxisAlignedBB(x -= renderPosX, y -= renderPosY, z -= renderPosZ, x2 -= renderPosX, y2 -= renderPosY, z2 -= renderPosZ), red, green, blue, alpha);
            GL11.glColor4d((double)0.0, (double)0.0, (double)0.0, (double)0.5);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x -= renderPosX, y -= renderPosY, z -= renderPosZ, x2 -= renderPosX, y2 -= renderPosY, z2 -= renderPosZ));
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.color((float)0.0f, (float)0.0f, (float)255.0f, (float)255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp((GlStateManager.LogicOp)GlStateManager.LogicOp.OR_REVERSE);
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.draw();
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.draw();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        vertexbuffer.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
        Sphere s = new Sphere();
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)10.0f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            s.setDrawStyle(100013);
            GL11.glTranslated((double)(x - renderPosX), (double)(y - renderPosY), (double)(z - renderPosZ));
            s.draw(size, slices, stacks);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        RenderUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        RenderUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        RenderUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, int borderC, int insideC) {
        RenderUtils.drawRect(x, y, x1, y1, insideC);
        RenderUtils.drawRect(x, y, x1, y + size, borderC);
        RenderUtils.drawRect(x, y1, x1, (float)y1 + size, borderC);
        RenderUtils.drawRect(x1, y, (float)x1 + size, (float)y1 + size, borderC);
        RenderUtils.drawRect(x, y, (float)x + size, (float)y1 + size, borderC);
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        RenderUtils.drawRect((float)x, (float)y, (float)x2, (float)y2, col2);
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glLineWidth((float)l1);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float par1, float par2, float par3, int par4) {
        if (par2 < par1) {
            float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        RenderUtils.drawRect(par1, par3, par2 + 1.0f, par3 + 1.0f, par4);
    }

    public static void drawVLine(float par1, float par2, float par3, int par4) {
        if (par3 < par2) {
            float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        RenderUtils.drawRect(par1, par2 + 1.0f, par1 + 1.0f, par3, par4);
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
        float alpha = (float)(paramColor >> 24 & 0xFF) / 255.0f;
        float red = (float)(paramColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(paramColor >> 8 & 0xFF) / 255.0f;
        float blue = (float)(paramColor & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        RenderUtils.drawGradientRect(x, y, x2, y2, col2, col3);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
        float yc;
        float xc;
        float a;
        int i;
        GL11.glPushMatrix();
        float f1 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(color & 0xFF) / 255.0f;
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f1);
        GL11.glLineWidth((float)width);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glEnable((int)32925);
        if (angle > 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i < angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)radius);
                yc = (float)(Math.sin(a) * (double)radius);
                GL11.glVertex2f((float)xc, (float)yc);
                ++i;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin((int)3);
            i = 0;
            while ((double)i > angle) {
                a = (float)((double)i * (angle * Math.PI / (double)points));
                xc = (float)(Math.cos(a) * (double)(-radius));
                yc = (float)(Math.sin(a) * (double)(-radius));
                GL11.glVertex2f((float)xc, (float)yc);
                --i;
            }
            GL11.glEnd();
        }
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)32925);
        GL11.glDisable((int)3479);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        cx *= 2.0f;
        cy *= 2.0f;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f((float)(x + cx), (float)(y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)6);
        for (int i = 0; i <= 360; ++i) {
            double x = Math.sin((double)i * Math.PI / 180.0) * r;
            double y = Math.cos((double)i * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)((double)cx + x), (double)((double)cy + y));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }
}

