//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Tracers
extends ToggleMod {
    public static SettingBoolean players = SettingUtils.settingBoolean("Tracers", "Players", "Draws Players", true);
    public static SettingBoolean passive = SettingUtils.settingBoolean("Tracers", "Passive", "Draws Passive", false);
    public static SettingBoolean hostile = SettingUtils.settingBoolean("Tracers", "Hostile", "Draws Hostile Mobs", false);
    public static SettingBoolean items = SettingUtils.settingBoolean("Tracers", "Items", "Draws Items", false);
    public static SettingBoolean distance2 = SettingUtils.settingBoolean("Tracers", "Distance", "Turns Red if close", true);
    public static final SettingDouble width = SettingUtils.settingDouble("Tracers", "Width", "Line thickness", 1.5, 0.5, 0.5, 10.0);
    private static final Minecraft MC = Minecraft.getMinecraft();

    public Tracers() {
        super(Category.RENDER, "Tracers", false, -1, "Draws lines to entities");
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        if (this.isEnabled()) {
            try {
                double renderPosX = Tracers.MC.getRenderManager().renderPosX;
                double renderPosY = Tracers.MC.getRenderManager().renderPosY;
                double renderPosZ = Tracers.MC.getRenderManager().renderPosZ;
                GL11.glPushMatrix();
                GL11.glEnable((int)2848);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3553);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)((float)width.getDouble()));
                for (Entity entities : Tracers.MC.world.loadedEntityList) {
                    float distance = Tracers.MC.player.getDistance(entities);
                    if (entities == Tracers.MC.player) continue;
                    double posX = entities.lastTickPosX + (entities.posX - entities.lastTickPosX) * (double)MC.getRenderPartialTicks() - renderPosX;
                    double posY = entities.lastTickPosY + (entities.posY - entities.lastTickPosY) * (double)MC.getRenderPartialTicks() - renderPosY;
                    double posZ = entities.lastTickPosZ + (entities.posZ - entities.lastTickPosZ) * (double)MC.getRenderPartialTicks() - renderPosZ;
                    if (FriendManager.getFriends().isFriend(entities.getName())) {
                        GL11.glColor4f((float)0.15f, (float)1.0f, (float)1.0f, (float)1.0f);
                    } else if (EnemyManager.getEnemies().isEnemy(entities.getName())) {
                        GL11.glColor4f((float)1.0f, (float)0.0f, (float)1.0f, (float)1.0f);
                    } else {
                        if (!distance2.getBoolean()) {
                            float red = (float)HUD.red.getInt() / 255.0f;
                            float green = (float)HUD.green.getInt() / 255.0f;
                            float blue = (float)HUD.blue.getInt() / 255.0f;
                            GL11.glColor3f((float)red, (float)green, (float)blue);
                        }
                        if (distance2.getBoolean()) {
                            if (distance <= 6.0f) {
                                GL11.glColor3f((float)1.0f, (float)0.0f, (float)0.0f);
                            } else if (distance <= 40.0f) {
                                GL11.glColor3f((float)(1.0f - distance / 50.0f), (float)(distance / 50.0f), (float)0.0f);
                            } else if (distance > 40.0f) {
                                GL11.glColor3f((float)0.1f, (float)0.8f, (float)0.1f);
                            }
                        }
                    }
                    Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(WMinecraft.getPlayer().rotationPitch))).rotateYaw(-((float)Math.toRadians(WMinecraft.getPlayer().rotationYaw)));
                    GL11.glBegin((int)2);
                    GL11.glVertex3d((double)eyes.x, (double)((double)WMinecraft.getPlayer().getEyeHeight() + eyes.y), (double)eyes.z);
                    if (players.getBoolean()) {
                        if (entities instanceof EntityPlayerSP) continue;
                        if (entities instanceof EntityPlayer) {
                            GL11.glVertex3d((double)posX, (double)(posY + 1.0), (double)posZ);
                        }
                    }
                    if (passive.getBoolean() && entities instanceof EntityAnimal) {
                        GL11.glVertex3d((double)posX, (double)(posY + 1.0), (double)posZ);
                    }
                    if (hostile.getBoolean() && entities instanceof EntityMob) {
                        GL11.glVertex3d((double)posX, (double)(posY + 1.0), (double)posZ);
                    }
                    if (items.getBoolean() && entities instanceof EntityItem) {
                        GL11.glVertex3d((double)posX, (double)(posY + 1.0), (double)posZ);
                    }
                    GL11.glEnd();
                }
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3042);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
                GL11.glPopMatrix();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

