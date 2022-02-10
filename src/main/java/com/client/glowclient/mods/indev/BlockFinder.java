//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockPortal
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods.indev;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.sponge.events.ModEvents.WorldChangeEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.render.RenderUtils;
import java.util.ArrayList;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BlockFinder
extends ToggleMod {
    public static SettingDouble range2 = SettingUtils.settingDouble("BlockFinder", "Range", "Range of Block Finder WARNING: larger values cause immense lag", 70.0, 1.0, 10.0, 500.0);
    public static SettingBoolean outline = SettingUtils.settingBoolean("BlockFinder", "Outline", "Outlines select blocks", true);
    public static SettingBoolean tracer = SettingUtils.settingBoolean("BlockFinder", "Tracer", "Draws a line to select blocks", false);
    private final ArrayList<BlockPos> matchingBlocks = new ArrayList();

    public BlockFinder() {
        super(Category.RENDER, "BlockFinder", false, -1, "Find select blocks in your world");
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        try {
            for (BlockPos pos : this.matchingBlocks) {
                if (!BlockFinder.mc.world.getChunk(pos).isLoaded() || !BlockFinder.mc.world.getChunk(Globals.MC.player.getPosition()).isLoaded()) continue;
                if (outline.getBoolean()) {
                    BlockFinder.drawPortalESP(pos, 255, 255, 255, 0, 255, 255, 255, 255, 1);
                }
                if (!tracer.getBoolean()) continue;
                BlockFinder.drawLine((double)pos.getX() + 0.5 - BlockFinder.mc.getRenderManager().renderPosX, (double)pos.getY() + 0.5 - BlockFinder.mc.getRenderManager().renderPosY, (double)pos.getZ() + 0.5 - BlockFinder.mc.getRenderManager().renderPosZ, 0.0, 255.0f, 255.0f, 255.0f, 255.0f);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onDisabled() {
        this.matchingBlocks.removeAll(this.matchingBlocks);
    }

    @SubscribeEvent
    public void onWorldChange(WorldChangeEvent event) {
        this.matchingBlocks.removeAll(this.matchingBlocks);
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        block10: {
            try {
                if (!this.simpleTimer.isStarted()) {
                    this.simpleTimer.start();
                }
                if (this.simpleTimer.hasTimeElapsed(20000.0)) {
                    this.simpleTimer.start();
                    this.matchingBlocks.clear();
                }
                int range = range2.getInt();
                int modulo = BlockFinder.mc.player.ticksExisted % 64;
                int startY = 255 - modulo * 4;
                int endY = startY - 4;
                BlockPos playerPos = new BlockPos(BlockFinder.mc.player.posX, 0.0, BlockFinder.mc.player.posZ);
                for (int y = startY; y > endY; --y) {
                    for (int x = range; x > -range; --x) {
                        for (int z = range; z > -range; --z) {
                            if (this.matchingBlocks.size() < 10000) {
                                BlockPos pos = playerPos.add(x, y, z);
                                if (BlockFinder.moreThanOnce(this.matchingBlocks, pos)) {
                                    this.matchingBlocks.remove((Object)pos);
                                }
                                if (!BlockFinder.mc.world.getChunk(pos).isLoaded()) {
                                    this.matchingBlocks.remove((Object)pos);
                                }
                                if (!(WBlock.getBlock(pos) instanceof BlockPortal) || this.matchingBlocks.contains((Object)pos)) continue;
                                this.matchingBlocks.add(pos);
                                continue;
                            }
                            break block10;
                        }
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private static boolean moreThanOnce(ArrayList<BlockPos> list, BlockPos searched) {
        int numCount = 0;
        for (BlockPos thisNum : list) {
            if (thisNum != searched) continue;
            ++numCount;
        }
        return numCount > 1;
    }

    private static void drawPortalESP(BlockPos blockPos, int red, int green, int blue, int alpha, int outlineR, int outlineG, int outlineB, int outlineA, int outlineWidth) {
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
            if (WBlock.getState(blockPos).equals((Object)BlockPortal.getStateById((int)4186))) {
                GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
                RenderUtils.drawColorBox(new AxisAlignedBB(x, y, z + 0.37, x + 1.0, y + 1.0, z + 0.63), 0.0f, 0.0f, 0.0f, 0.0f);
                GL11.glColor4d((double)((float)outlineR / 255.0f), (double)((float)outlineG / 255.0f), (double)((float)outlineB / 255.0f), (double)((float)outlineA / 255.0f));
                RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z + 0.37, x + 1.0, y + 1.0, z + 0.63));
            }
            if (WBlock.getState(blockPos).equals((Object)BlockPortal.getStateById((int)8282))) {
                GL11.glColor4d((double)((float)red / 255.0f), (double)((float)green / 255.0f), (double)((float)blue / 255.0f), (double)((float)alpha / 255.0f));
                RenderUtils.drawColorBox(new AxisAlignedBB(x, y, z + 0.63, x + 1.0, y + 1.0, z + 0.37), 0.0f, 0.0f, 0.0f, 0.0f);
                GL11.glColor4d((double)((float)outlineR / 255.0f), (double)((float)outlineG / 255.0f), (double)((float)outlineB / 255.0f), (double)((float)outlineA / 255.0f));
                RenderUtils.drawSelectionBoundingBox(new AxisAlignedBB(x + 0.37, y, z + 0.0, x + 0.63, y + 1.0, z + 1.0));
            }
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

    private static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw)));
        BlockFinder.drawLineFromPosToPos(eyes.x, eyes.y + (double)BlockFinder.mc.player.getEyeHeight(), eyes.z, posx, posy, posz, up, red, green, blue, opacity);
    }

    private static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)0.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)(posy2 + up), (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
    }
}

