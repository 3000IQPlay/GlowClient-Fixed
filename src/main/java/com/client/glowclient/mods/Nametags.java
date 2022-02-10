//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntityDonkey
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.passive.EntityLlama
 *  net.minecraft.entity.passive.EntityMule
 *  net.minecraft.entity.passive.EntityOcelot
 *  net.minecraft.entity.passive.EntityParrot
 *  net.minecraft.entity.passive.EntitySkeletonHorse
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.entity.passive.EntityZombieHorse
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
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
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.remax.GLUtils;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;
import com.client.glowclient.utils.world.entity.CombatUtils;
import com.client.glowclient.utils.world.entity.EnchantmentUtils;
import com.client.glowclient.utils.world.entity.EntityUtils;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Nametags
extends ToggleMod {
    public static SettingBoolean players = SettingUtils.settingBoolean("Nametags", "Players", "Shows Players", true);
    public static SettingBoolean mobs = SettingUtils.settingBoolean("Nametags", "Mobs", "Shows Mobs", false);
    public static SettingBoolean passive = SettingUtils.settingBoolean("Nametags", "Passive", "Shows Passive", false);
    public static SettingMode scaling = SettingUtils.settingMode("Nametags", "Scaling", "Scaling mode of nametags", "Auto", "Auto", "Custom");
    public static SettingBoolean visibility = SettingUtils.settingBoolean("Nametags", "Visibility", "Visibility Detector", false);
    public static SettingBoolean shielding = SettingUtils.settingBoolean("Nametags", "Shielding", "Detects if player is shielding", false);
    public static SettingBoolean healthb = SettingUtils.settingBoolean("Nametags", "Health", "Entity Health", true);
    public static SettingBoolean items = SettingUtils.settingBoolean("Nametags", "Items", "Equipped items", true);
    public static final SettingDouble scale = SettingUtils.settingDouble("Nametags", "Scale", "Scale of nametag", 1.0, 1.0, 0.0, 25.0);

    public Nametags() {
        super(Category.RENDER, "Nametags", false, -1, "Bigger and Better nametags");
    }

    @SubscribeEvent
    public void onRenderPlayerNameTag(RenderLivingEvent.Specials.Pre event) {
        if (EntityUtils.isLiving((Entity)event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRender(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            Globals.MC.world.loadedEntityList.stream().filter(entity -> entity != Globals.MC.world.getEntityByID(-100)).filter(EntityUtils::isLiving).filter(entity -> !Objects.equals((Object)Globals.MC.player, entity)).filter(EntityUtils::isAlive).filter(EntityUtils::isValidEntity).filter(entity -> !EntityUtils.isFakeLocalPlayer(entity)).map(entity -> (EntityLivingBase)entity).forEach(p -> {
                if (p == null) {
                    return;
                }
                if (p instanceof EntityBat) {
                    return;
                }
                if (!players.getBoolean() && p instanceof EntityPlayer) {
                    return;
                }
                if (!mobs.getBoolean()) {
                    if (p instanceof EntityMob) {
                        return;
                    }
                    if (p instanceof EntityGhast) {
                        return;
                    }
                    if (p instanceof EntityMagmaCube) {
                        return;
                    }
                    if (p instanceof EntitySlime) {
                        return;
                    }
                }
                if (!passive.getBoolean()) {
                    if (p instanceof EntityAnimal) {
                        return;
                    }
                    if (p instanceof EntityArmorStand) {
                        return;
                    }
                    if (p instanceof EntityGolem) {
                        return;
                    }
                    if (p instanceof EntityIronGolem) {
                        return;
                    }
                    if (p instanceof EntitySquid) {
                        return;
                    }
                }
                double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)Globals.MC.getRenderPartialTicks() - renderPosX;
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)Globals.MC.getRenderPartialTicks() - renderPosY;
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)Globals.MC.getRenderPartialTicks() - renderPosZ;
                this.renderNameTag((EntityLivingBase)p, pX, pY, pZ, SurfaceBuilder.getBuilder());
            });
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void renderNameTag(EntityLivingBase entity, double pX, double pY, double pZ, SurfaceBuilder builder) {
        try {
            pY += entity.isSneaking() ? 0.5 : 0.7;
            float var13 = 0.0f;
            if (scaling.getMode().equals("Auto")) {
                var13 = Globals.MC.player.getDistance((Entity)entity) / 5.0f;
            }
            if (scaling.getMode().equals("Custom")) {
                var13 = (float)scale.getDouble();
            }
            if (var13 < 1.625f) {
                var13 = 2.0f;
            }
            String in = visibility.getBoolean() ? (Globals.MC.player.canEntityBeSeen((Entity)entity) ? "\u00a7a [V]" : "\u00a7c [V]") : "";
            String in2 = shielding.getBoolean() ? (CombatUtils.isPlayerShielding(entity) ? "\u00a7a [S]" : "\u00a7c [S]") : "";
            String text = entity.getDisplayName().getUnformattedText();
            float h = entity.getHealth() + entity.getAbsorptionAmount();
            String health = healthb.getBoolean() ? String.format("%.1f", Float.valueOf(h)) : "";
            float hp = MathHelper.clamp((float)entity.getHealth(), (float)0.0f, (float)entity.getMaxHealth()) / entity.getMaxHealth();
            int color = entity.getHealth() + entity.getAbsorptionAmount() > entity.getMaxHealth() ? Colors.YELLOW : Colors.toRGBA((int)((255.0f - hp) * 255.0f), (int)(255.0f * hp), 0, 255);
            RenderManager renderManager = Globals.MC.getRenderManager();
            float scale = var13 * 0.01f;
            GL11.glPushMatrix();
            GlStateManager.translate((double)((float)pX), (double)((double)((float)pY) - 0.5 + (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY)), (double)((float)pZ));
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            if (Globals.MC.gameSettings.thirdPersonView == 2) {
                GL11.glRotatef((float)(-renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            } else {
                GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
            }
            GL11.glScalef((float)(-scale), (float)(-scale), (float)(scale /= 100.0f));
            GLUtils.setGLCap(2896, false);
            GLUtils.setGLCap(2929, false);
            int width = (int)SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in + in2);
            GLUtils.setGLCap(3042, true);
            SurfaceHelper.drawRect(-1, -1, 1, 1, Colors.toRGBA(0, 0, 0, 0));
            GL11.glBlendFunc((int)770, (int)771);
            double x = -width / 2;
            double y = -SurfaceHelper.getStringHeight(HUD.FONT) - 1.0;
            int ncolor = Colors.WHITE;
            if (FriendManager.getFriends().isFriend(entity.getName())) {
                ncolor = Colors.AQUA;
            }
            if (EnemyManager.getEnemies().isEnemy(entity.getName())) {
                ncolor = Colors.toRGBA(255, 0, 0, 255);
            }
            if (HUD.FONT != null) {
                builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in + in2) + 4.0, SurfaceHelper.getStringHeight(HUD.FONT) + 4.0).end().task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::enableTexture2D).fontRenderer(HUD.FONT).color(ncolor).text(text, x + 1.0, y - 1.0 + 1.0, true).color(ncolor).text(text, x, y - 1.0).color(color).text(health, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " ") + 1.0, y - 1.0 + 1.0, true).color(color).text(health, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " "), y - 1.0).color(Colors.GRAY).text(in, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health) + 1.0, y - 1.0 + 1.0, true).color(Colors.GRAY).text(in, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health), y - 1.0).color(Colors.GRAY).text(in2, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in) + 1.0, y - 1.0 + 1.0, true).color(Colors.GRAY).text(in2, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in), y - 1.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
            } else {
                builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in) + 4.0, SurfaceHelper.getStringHeight(HUD.FONT) + 4.0).end().task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::enableTexture2D).fontRenderer(HUD.FONT).color(ncolor).text(text, x, y - 1.0, true).color(color).text(health, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " "), y - 1.0, true).color(Colors.GRAY).text(in, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health), y - 1.0, true).color(Colors.GRAY).text(in2, x + SurfaceHelper.getStringWidth(HUD.FONT, text + " " + health + in), y - 1.0, true).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
            }
            if (entity instanceof EntityHorse || entity instanceof EntityDonkey || entity instanceof EntityMule || entity instanceof EntitySkeletonHorse || entity instanceof EntityZombieHorse) {
                double sped = MathHelper.clamp((double)entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), (double)0.1125, (double)0.3375);
                int cspeed = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() > 0.325 ? Colors.AQUA : Colors.toRGBA(0, (int)(255.0 * (sped * 2.5)), (int)((255.0 - sped * 2.5) * 255.0), 255);
                double sp = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                String speed = String.format("%.2f", sp * 42.1422829447);
                AbstractHorse cfr_ignored_0 = (AbstractHorse)entity;
                IAttribute jum = AbstractHorse.JUMP_STRENGTH;
                double jums = entity.getEntityAttribute(jum).getAttributeValue();
                double jemp = MathHelper.clamp((double)entity.getEntityAttribute(jum).getAttributeValue(), (double)0.4, (double)1.0);
                int cjump = entity.getEntityAttribute(jum).getAttributeValue() > 0.967 ? Colors.AQUA : Colors.toRGBA(0, (int)(255.0 * (jemp * 0.8)), (int)((255.0 - jemp * 0.8) * 255.0), 255);
                double jums2 = -0.1817584952 * Math.pow(jums, 3.0) + 3.689713992 * Math.pow(jums, 2.0) + 2.128599134 * jums - 0.343930367;
                String jump = String.format("%.2f", jums2);
                builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 8.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Speed: " + speed + "m/s") + 4.0, 6.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(cspeed).text(speed + "m/s", x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Speed: "), y + 1.0 - 7.0, true).color(Colors.WHITE).text("Speed: ", x + 1.0, y + 1.0 - 7.0, true).color(cspeed).text(speed + "m/s", x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Speed: "), y - 7.0).color(Colors.WHITE).text("Speed: ", x, y - 7.0).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 14.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Jump: " + jump + "m") + 4.0, 6.0).end().task(SurfaceBuilder::enableTexture2D).color(cjump).text(jump + "m", x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Jump: "), y + 1.0 - 13.0, true).color(Colors.WHITE).text("Jump: ", x + 1.0, y + 1.0 - 13.0, true).color(cjump).text(jump + "m", x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Jump: "), y - 13.0).color(Colors.WHITE).text("Jump: ", x, y - 13.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
            }
            if (entity instanceof EntityHorse || entity instanceof EntityDonkey || entity instanceof EntityMule || entity instanceof EntityLlama || entity instanceof EntityOcelot || entity instanceof EntityParrot || entity instanceof EntityWolf) {
                UUID id1;
                if (entity instanceof EntityWolf && ((EntityWolf)entity).getOwnerId() != null && (id1 = ((EntityWolf)entity).getOwnerId()) != null) {
                    String id = this.getNameFromUUID(((EntityWolf)entity).getOwnerId());
                    if (Globals.MC.world.getPlayerEntityByUUID(id1) != null && Globals.MC.world.getPlayerEntityByUUID(id1).getName() != null) {
                        id = Globals.MC.world.getPlayerEntityByUUID(id1).getName();
                    }
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 11.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: " + id) + 4.0, 9.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(id, x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y + 1.0 - 8.0, true).color(Colors.WHITE).text(id, x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y - 8.0).color(Colors.WHITE).text("Owner: ", x + 1.0, y + 1.0 - 8.0, true).color(Colors.WHITE).text("Owner: ", x, y - 8.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                }
                if (entity instanceof EntityOcelot && ((EntityOcelot)entity).getOwnerId() != null && (id1 = ((EntityOcelot)entity).getOwnerId()) != null) {
                    String id = this.getNameFromUUID(((EntityOcelot)entity).getOwnerId());
                    if (Globals.MC.world.getPlayerEntityByUUID(id1) != null && Globals.MC.world.getPlayerEntityByUUID(id1).getName() != null) {
                        id = Globals.MC.world.getPlayerEntityByUUID(id1).getName();
                    }
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 11.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: " + id) + 4.0, 9.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(id, x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y + 1.0 - 8.0, true).color(Colors.WHITE).text(id, x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y - 8.0).color(Colors.WHITE).text("Owner: ", x + 1.0, y + 1.0 - 8.0, true).color(Colors.WHITE).text("Owner: ", x, y - 8.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                }
                if (entity instanceof EntityParrot && ((EntityParrot)entity).getOwnerId() != null && (id1 = ((EntityParrot)entity).getOwnerId()) != null) {
                    String id = this.getNameFromUUID(((EntityParrot)entity).getOwnerId());
                    if (Globals.MC.world.getPlayerEntityByUUID(id1) != null && Globals.MC.world.getPlayerEntityByUUID(id1).getName() != null) {
                        id = Globals.MC.world.getPlayerEntityByUUID(id1).getName();
                    }
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 11.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: " + id) + 4.0, 9.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(id, x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y + 1.0 - 8.0, true).color(Colors.WHITE).text(id, x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y - 8.0).color(Colors.WHITE).text("Owner: ", x + 1.0, y + 1.0 - 8.0, true).color(Colors.WHITE).text("Owner: ", x, y - 8.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                }
                if (entity instanceof AbstractHorse && ((AbstractHorse)entity).getOwnerUniqueId() != null) {
                    if (entity instanceof EntityLlama) {
                        id1 = ((AbstractHorse)entity).getOwnerUniqueId();
                        if (id1 != null) {
                            String id = this.getNameFromUUID(((AbstractHorse)entity).getOwnerUniqueId());
                            if (Globals.MC.world.getPlayerEntityByUUID(id1) != null && Globals.MC.world.getPlayerEntityByUUID(id1).getName() != null) {
                                id = Globals.MC.world.getPlayerEntityByUUID(id1).getName();
                            }
                            builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 11.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: " + id) + 4.0, 9.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(id, x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y + 1.0 - 8.0, true).color(Colors.WHITE).text(id, x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y - 8.0).color(Colors.WHITE).text("Owner: ", x + 1.0, y + 1.0 - 8.0, true).color(Colors.WHITE).text("Owner: ", x, y - 8.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                        }
                    } else {
                        id1 = ((AbstractHorse)entity).getOwnerUniqueId();
                        if (id1 != null) {
                            String id = this.getNameFromUUID(((AbstractHorse)entity).getOwnerUniqueId());
                            if (Globals.MC.world.getPlayerEntityByUUID(id1) != null && Globals.MC.world.getPlayerEntityByUUID(id1).getName() != null) {
                                id = Globals.MC.world.getPlayerEntityByUUID(id1).getName();
                            }
                            builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 23.0, SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: " + id) + 4.0, 9.0).end().task(SurfaceBuilder::enableTexture2D).fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(id, x + 1.0 + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y + 1.0 - 20.0, true).color(Colors.WHITE).text(id, x + SurfaceHelper.getStringWidth(Fonts.VERDANA75, "Owner: "), y - 20.0).color(Colors.WHITE).text("Owner: ", x + 1.0, y + 1.0 - 20.0, true).color(Colors.WHITE).text("Owner: ", x, y - 20.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                        }
                    }
                }
            }
            GlStateManager.clear((int)256);
            List items = StreamSupport.stream(entity.getEquipmentAndArmor().spliterator(), false).filter(Objects::nonNull).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
            if (!items.isEmpty()) {
                double itemSize = 16.0;
                double xI = -(16.0 * (double)items.size() / 2.0);
                double yI = y + 10.0 - 16.0;
                for (int index = 0; index < items.size(); ++index) {
                    double xx = xI + (double)index * 16.0;
                    ItemStack stack2 = (ItemStack)items.get(index);
                    if (Nametags.items.getBoolean()) {
                        builder.reset().push().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(stack2, xx, yI - 12.0).itemOverlay(stack2, xx, yI - 12.0).task(SurfaceBuilder::disableItemRendering).pop();
                    }
                    double xI2 = xI + (double)(index * 17);
                    double yI2 = yI - 15.0;
                    List<EnchantmentUtils.EntityEnchantment> enchantments = EnchantmentUtils.getEnchantmentsSorted(stack2.getEnchantmentTagList());
                    if (enchantments == null) continue;
                    for (EnchantmentUtils.EntityEnchantment enchant : enchantments) {
                        double d = 0;
                        if (Nametags.items.getBoolean()) {
                            builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(Fonts.VERDANA50).color(Colors.WHITE).text(enchant.getShortName(), xI2 + 1.0, yI2 + 1.0, true).color(Colors.WHITE).text(enchant.getShortName(), xI2, yI2).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                        }
                        if (!(d <= (yI2 -= (double)SurfaceHelper.getTextHeight(0.5)))) continue;
                    }
                }
            }
            GL11.glPushMatrix();
            GL11.glPopMatrix();
            GLUtils.setGLCap(2929, true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private String getNameFromUUID(UUID uuid) {
        return uuid.toString();
    }
}

