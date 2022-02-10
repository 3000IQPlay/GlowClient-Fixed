//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.player.EntityPlayer
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
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.world.entity.EntityUtils;
import java.util.Objects;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class EntityESP
extends ToggleMod {
    public static SettingBoolean players = SettingUtils.settingBoolean("EntityESP", "Players", "Shows Players", true);
    public static SettingBoolean hostile = SettingUtils.settingBoolean("EntityESP", "Hostile", "Shows Mobs", false);
    public static SettingBoolean passive = SettingUtils.settingBoolean("EntityESP", "Passive", "Shows Passives", false);
    public static SettingBoolean items = SettingUtils.settingBoolean("EntityESP", "Items", "Show Items", true);
    public static SettingBoolean everything = SettingUtils.settingBoolean("EntityESP", "Everything", "Outlines All Entities - white", false);
    public static SettingMode mode = SettingUtils.settingMode("EntityESP", "Mode", "Mode of EntityESP", "Box", "Box", "Outline", "CSGO");
    public static final SettingDouble width = SettingUtils.settingDouble("EntityESP", "Width", "Width of outline", 3.0, 1.0, 1.0, 10.0);

    public EntityESP() {
        super(Category.RENDER, "EntityESP", false, -1, "Draws outline around entities");
    }

    @Override
    public String getHUDTag() {
        if (mode.getMode().equals("Outline")) {
            return "Outline";
        }
        if (mode.getMode().equals("Box")) {
            return "Box";
        }
        if (mode.getMode().equals("CSGO")) {
            return "CSGO";
        }
        return "";
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        if (this.isEnabled()) {
            for (Entity e : Globals.MC.world.loadedEntityList) {
                if (mode.getMode().equals("Box")) {
                    if (hostile.getBoolean() && e instanceof EntityMob) {
                        RenderUtils.drawEntityESP(e, 255, 0, 0);
                    }
                    if (passive.getBoolean() && e instanceof EntityAnimal) {
                        RenderUtils.drawEntityESP(e, 0, 255, 0);
                    }
                    if (e instanceof EntityPlayerSP) continue;
                    if (players.getBoolean() && e instanceof EntityPlayer) {
                        RenderUtils.drawEntityESP(e, 0, 0, 255);
                    }
                    if (everything.getBoolean() && e instanceof Entity) {
                        RenderUtils.drawEntityESP(e, 255, 255, 255);
                    }
                }
                if (!items.getBoolean() || !(e instanceof EntityItem)) continue;
                RenderUtils.drawEntityESP(e, 0, 255, 255);
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void renderCSGOESP(RenderEvent event) {
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
                if (!hostile.getBoolean()) {
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
                if (mode.getMode().equals("CSGO")) {
                    this.renderNameTag((EntityLivingBase)p, pX, pY, pZ, SurfaceBuilder.getBuilder());
                }
            });
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void renderNameTag(EntityLivingBase entity, double pX, double pY, double pZ, SurfaceBuilder builder) {
        RenderManager renderManager = Globals.MC.getRenderManager();
        double d = entity.isSneaking() ? 0.5 : 0.7;
        float var13 = 1.0f;
        float scale = var13 * 0.01f;
        GL11.glPushMatrix();
        GlStateManager.translate((double)((float)pX), (double)((double)((float)(pY += d)) + 1.4), (double)((float)pZ));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)(scale /= 100.0f));
        GLUtils.setGLCap(2896, false);
        GLUtils.setGLCap(2929, false);
        int width = 117;
        GLUtils.setGLCap(3042, true);
        SurfaceHelper.drawRect(-1, -1, -1, -1, Colors.toRGBA(0, 0, 0, 0));
        GL11.glBlendFunc((int)770, (int)771);
        double x = -width / 2;
        double y = -SurfaceHelper.getStringHeight(HUD.FONT) - 1.0;
        int red = HUD.red.getInt();
        int green = HUD.green.getInt();
        int blue = HUD.blue.getInt();
        int ncolor = Colors.toRGBA(red, green, blue, 150);
        if (FriendManager.getFriends().isFriend(entity.getName())) {
            ncolor = Colors.toRGBA(0, 255, 255, 150);
        }
        if (EnemyManager.getEnemies().isEnemy(entity.getName())) {
            ncolor = Colors.toRGBA(220, 0, 0, 150);
        }
        if (HUD.FONT != null) {
            int thickness = 7;
            int thickness2 = 3;
            builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 255)).rectangle(x - 3.0, y + 22.0 - 3.0, 45.0, thickness2).rectangle(x - 3.0, y + 22.0 - 3.0 + (double)thickness2, thickness2, 45 - thickness2).rectangle(x - 3.0, y + 180.0 + 3.0, thickness2, 45 - thickness2).rectangle(x - 3.0, y + 180.0 + 3.0 + 45.0 - (double)thickness2, 45.0, thickness2).rectangle(x + 110.0 + 3.0 + 4.0, y + 180.0 + 3.0, thickness2, 45 - thickness2).rectangle(x + 110.0 + 3.0 + 4.0 - 45.0 + (double)thickness2, y + 180.0 + 3.0 + 45.0 - (double)thickness2, 45.0, thickness2).rectangle(x + 110.0 + 3.0 + 4.0 - 45.0 + (double)thickness2, y + 22.0 - 3.0, 45.0, thickness2).rectangle(x + 110.0 + 3.0 + 4.0, y + 22.0 - 3.0 + (double)thickness2, thickness2, 45 - thickness2).color(ncolor).rectangle(x, y + 22.0, 45.0, thickness).rectangle(x, y + 22.0 + (double)thickness, thickness, 45 - thickness).rectangle(x, y + 180.0, thickness, 45 - thickness).rectangle(x, y + 180.0 + 45.0 - (double)thickness, 45.0, thickness).rectangle(x + 110.0, y + 180.0, thickness, 45 - thickness).rectangle(x + 110.0 - 45.0 + (double)thickness, y + 180.0 + 45.0 - (double)thickness, 45.0, thickness).rectangle(x + 110.0 - 45.0 + (double)thickness, y + 22.0, 45.0, thickness).rectangle(x + 110.0, y + 22.0 + (double)thickness, thickness, 45 - thickness).end().task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.0f);
        GL11.glPopMatrix();
    }
}

