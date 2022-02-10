//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.GuiIngameForge
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class NoRender
extends ToggleMod {
    public static SettingBoolean totem = SettingUtils.settingBoolean("NoRender", "TotemAnim", "Disables totem animation", true);
    public static SettingBoolean effects = SettingUtils.settingBoolean("NoRender", "EffectsHUD", "Disables effects hud", true);
    public static SettingBoolean boss = SettingUtils.settingBoolean("NoRender", "BossOverlay", "Disables boss bars", true);
    public static SettingBoolean tut = SettingUtils.settingBoolean("NoRender", "Scoreboard", "Disables Scoreboard", true);
    public static SettingBoolean hurtcam = SettingUtils.settingBoolean("NoRender", "Hurtcam", "Disables hurtcam", true);
    public static SettingBoolean block = SettingUtils.settingBoolean("NoRender", "BlockOverlay", "Disables Suffocation HUD", true);
    public static SettingBoolean fog = SettingUtils.settingBoolean("NoRender", "Fog", "Disables world fog", true);
    public static SettingBoolean skylight = SettingUtils.settingBoolean("NoRender", "SkyLight", "Disables sky lighting updates", true);

    public NoRender() {
        super(Category.RENDER, "NoRender", false, -1, "Stops the rendering of some factors");
    }

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.FogDensity event) {
        if (fog.getBoolean() && (event.getState().getMaterial().equals((Object)Material.WATER) || event.getState().getMaterial().equals((Object)Material.LAVA))) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (fog.getBoolean()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (block.getBoolean()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (boss.getBoolean()) {
            GuiIngameForge.renderBossHealth = false;
            GL11.glBlendFunc((int)770, (int)771);
        } else {
            GuiIngameForge.renderBossHealth = true;
        }
        GuiIngameForge.renderObjective = !tut.getBoolean();
        GuiIngameForge.renderVignette = !block.getBoolean();
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HELMET) || event.getType().equals((Object)RenderGameOverlayEvent.ElementType.PORTAL)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        try {
            ItemStack item = Globals.MC.entityRenderer.itemActivationItem;
            if (totem.getBoolean() && item != null && item.getItem() == Items.TOTEM_OF_UNDYING) {
                Globals.MC.entityRenderer.itemActivationItem = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onEnabled() {
        HookUtils.isNoHurtcamActivated = hurtcam.getBoolean();
        HookUtils.isNoEffectsHudActivated = effects.getBoolean();
        HookUtils.isNoSkylightActivated = skylight.getBoolean();
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        HookUtils.isNoHurtcamActivated = hurtcam.getBoolean();
        HookUtils.isNoEffectsHudActivated = effects.getBoolean();
        HookUtils.isNoSkylightActivated = skylight.getBoolean();
    }

    @Override
    public void onDisabled() {
        if (hurtcam.getBoolean()) {
            HookUtils.isNoHurtcamActivated = false;
        }
        if (effects.getBoolean()) {
            HookUtils.isNoEffectsHudActivated = false;
        }
        if (skylight.getBoolean()) {
            HookUtils.isNoSkylightActivated = false;
        }
        GuiIngameForge.renderBossHealth = true;
        GuiIngameForge.renderObjective = true;
    }
}

