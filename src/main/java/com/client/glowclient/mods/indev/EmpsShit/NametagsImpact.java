//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods.indev.EmpsShit;

import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.Plane;
import com.client.glowclient.utils.mc.math.VectorUtils;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.MinecraftFontRenderer;
import com.client.glowclient.utils.world.entity.EnchantmentUtils;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class NametagsImpact
extends ToggleMod {
    public static final SettingBoolean players = SettingUtils.settingBoolean("NametagsImpact", "Players", "Shows Players", true);
    public static final SettingBoolean mobs = SettingUtils.settingBoolean("NametagsImpact", "Mobs", "Shows Mobs", false);
    public static final SettingBoolean passive = SettingUtils.settingBoolean("NametagsImpact", "Passive", "Shows Passive", false);

    public NametagsImpact() {
        super(Category.JEWISHTRICKS, "NametagsImpact", false, -1, "Informative nametags");
    }

    @Override
    public void onEnabled() {
        ModuleManager.getModuleFromName("Nametags").disable();
    }

    @SubscribeEvent
    public void onRenderPlayerNameTag(RenderLivingEvent.Specials.Pre event) {
        if (EntityUtils.isPlayer((Entity)event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRender2D(RenderEvent.Render2DEvent event) {
        Globals.MC.world.loadedEntityList.stream().filter(EntityUtils::isLiving).filter(entity -> !Objects.equals((Object)Globals.MC.player, entity) && !EntityUtils.isFakeLocalPlayer(entity)).filter(EntityUtils::isAlive).filter(EntityUtils::isValidEntity).map(entity -> (EntityLivingBase)entity).forEach(living -> {
            Vec3d bottomPos = EntityUtils.getInterpolatedPos((Entity)living, Globals.MC.getRenderPartialTicks());
            Vec3d topPos = bottomPos.add(0.0, living.getRenderBoundingBox().maxY - living.posY, 0.0);
            Plane top = VectorUtils.toScreen(topPos);
            Plane bot = VectorUtils.toScreen(bottomPos);
            if (living == null) {
                return;
            }
            if (living instanceof EntityBat) {
                return;
            }
            if (!players.getBoolean() && living instanceof EntityPlayer) {
                return;
            }
            if (!mobs.getBoolean()) {
                if (living instanceof EntityMob) {
                    return;
                }
                if (living instanceof EntityGhast) {
                    return;
                }
                if (living instanceof EntityMagmaCube) {
                    return;
                }
                if (living instanceof EntitySlime) {
                    return;
                }
            }
            if (!passive.getBoolean()) {
                if (living instanceof EntityAnimal) {
                    return;
                }
                if (living instanceof EntityArmorStand) {
                    return;
                }
                if (living instanceof EntityGolem) {
                    return;
                }
                if (living instanceof EntityIronGolem) {
                    return;
                }
            }
            if (!top.isVisible() && !bot.isVisible()) {
                return;
            }
            double topX = top.getX();
            double topY = top.getY() + 1.0;
            AtomicDouble offset = new AtomicDouble();
            double os = offset.get();
            offset.set(os + this.draw(SurfaceBuilder.getBuilder(), (EntityLivingBase)living, topX, topY));
        });
    }

    public double draw(SurfaceBuilder builder, EntityLivingBase living, double topX, double topY) {
        List items = StreamSupport.stream(living.getEquipmentAndArmor().spliterator(), false).filter(Objects::nonNull).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
        if (!items.isEmpty()) {
            double itemSize = 8.0;
            double xI = topX + 8.0 * (double)items.size() / 3.33;
            double yI = topY - 8.0;
            for (int index = 0; index < items.size(); ++index) {
                double xx = xI - (double)index * 8.0;
                ItemStack stack2 = (ItemStack)items.get(index);
                GL11.glPushMatrix();
                GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                builder.reset().push().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(stack2, xx * 2.0, yI * 2.0 - 24.0).itemOverlay(stack2, xx * 2.0, yI * 2.0 - 24.0).task(SurfaceBuilder::disableItemRendering).pop();
                GL11.glPopMatrix();
                double xI2 = xI - (double)(index * 8);
                double yI2 = yI - 15.0;
                List<EnchantmentUtils.EntityEnchantment> enchantments = EnchantmentUtils.getEnchantmentsSorted(stack2.getEnchantmentTagList());
                if (enchantments == null) continue;
                for (EnchantmentUtils.EntityEnchantment enchant : enchantments) {
                    double d = 0;
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).scale(0.3).color(Colors.WHITE).text(enchant.getShortName(), xI2 * 3.33333, yI2 * 3.33333, true).scale(1.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                    yI2 -= (double)SurfaceHelper.getTextHeight(0.4);
                    if (!(d <= yI2)) continue;
                }
            }
        }
        MinecraftFontRenderer FONT = null;
        String text = living.getName();
        float h = living.getHealth();
        String health = String.format("%.0f", Float.valueOf(h));
        float hp = MathHelper.clamp((float)living.getHealth(), (float)0.0f, (float)20.0f);
        int color = Colors.GREEN;
        if (living.getHealth() >= 15.0f) {
            color = Colors.GREEN;
        }
        if (living.getHealth() <= 15.0f && living.getHealth() >= 5.0f) {
            color = Colors.YELLOW;
        }
        if (living.getHealth() <= 5.0f) {
            color = Colors.RED;
        }
        int culur = !FriendManager.getFriends().isFriend(living.getName()) ? Colors.toRGBA(175, 175, 175, 255) : Colors.AQUA;
        double x = topX - (double)builder.getFontWidth(text + " " + health) / 4.0;
        double y = topY - (double)builder.getFontHeight() - 1.0;
        SurfaceHelper.drawOutlinedRect(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(FONT, text + " " + health) / 2.0 + 4.0, SurfaceHelper.getStringHeight(FONT) / 2.0 + 2.0, Colors.toRGBA(175, 175, 175, 200));
        builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 150)).rectangle(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(FONT, text + " " + health) / 2.0 + 4.0, SurfaceHelper.getStringHeight(FONT) / 2.0 + 2.0).end().task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::enableTexture2D).color(culur).scale(0.5).text(text, x * 2.0, y * 2.0 - 2.0, true).color(color).text(health, x * 2.0 + SurfaceHelper.getStringWidth(FONT, text + " "), y * 2.0 - 2.0, true).scale(1.0).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
        return (double)SurfaceHelper.getTextHeight() + 1337.0;
    }
}

