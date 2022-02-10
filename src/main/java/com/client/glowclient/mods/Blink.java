//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.remax.GLUtils;
import com.client.glowclient.utils.extra.wurst.ChatUtils;
import com.client.glowclient.utils.extra.wurst.WConnection;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Blink
extends ToggleMod {
    public static SettingBoolean tracer = SettingUtils.settingBoolean("Blink", "Tracer", "Shows tracer line", true);
    public static SettingBoolean esp = SettingUtils.settingBoolean("Blink", "ESPbox", "Shows ESPbox", true);
    public static SettingBoolean name = SettingUtils.settingBoolean("Blink", "Nametag", "Shows player nametag", true);
    private double posX;
    private double posY;
    private double posZ;
    private final ArrayList<Packet> packets = new ArrayList();
    private EntityOtherPlayerMP clonedPlayer;

    public Blink() {
        super(Category.SERVER, "Blink", false, -1, "Cancels CPacketPlayer for period of time");
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        String name = "BLINK";
        if (!ModuleManager.getModuleFromName("Freecam").isEnabled()) {
            // empty if block
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRender(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            if (esp.getBoolean()) {
                RenderUtils.drawFreecamESP(this.posX - 0.3, this.posY, this.posZ - 0.3, 255, 255, 255);
            }
            if (tracer.getBoolean()) {
                GL11.glEnable((int)2848);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3553);
                GL11.glDepthMask((boolean)false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)2.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(WMinecraft.getPlayer().rotationPitch))).rotateYaw(-((float)Math.toRadians(WMinecraft.getPlayer().rotationYaw)));
                GL11.glBegin((int)2);
                GL11.glVertex3d((double)eyes.x, (double)((double)WMinecraft.getPlayer().getEyeHeight() + eyes.y), (double)eyes.z);
                GL11.glVertex3d((double)(this.posX - renderPosX), (double)(this.posY - renderPosY + 1.0), (double)(this.posZ - renderPosZ));
                GL11.glEnd();
                GL11.glDisable((int)3042);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
            }
            if (name.getBoolean()) {
                Globals.MC.getRenderManager();
                double pX = this.posX - 0.6 - renderPosX;
                Globals.MC.getRenderManager();
                double pY = this.posY - 1.8 - renderPosY;
                Globals.MC.getRenderManager();
                double pZ = this.posZ - 0.6 - renderPosZ;
                double distance = Globals.MC.player.getDistance(this.posX, this.posY, this.posZ);
                this.renderNameTag(Globals.MC.player.getName() + " [Blink]", pX + 0.6, pY + 2.3, pZ + 0.6, distance, SurfaceBuilder.getBuilder());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onEnabled() {
        if (Globals.MC.player != null) {
            this.posX = Globals.MC.player.posX;
            this.posY = Globals.MC.player.posY;
            this.posZ = Globals.MC.player.posZ;
        }
    }

    @Override
    public void onDisabled() {
        if (!ModuleManager.getModuleFromName("Freecam").isEnabled()) {
            for (Packet packet : this.packets) {
                WConnection.sendPacket(packet);
            }
            this.packets.clear();
        }
    }

    @SubscribeEvent
    public void onPacketSending(PacketEvent event) {
        if (!ModuleManager.getModuleFromName("Freecam").isEnabled()) {
            Packet<?> packet = event.getPacket();
            if (!(packet instanceof CPacketPlayer)) {
                return;
            }
            event.setCanceled(true);
            if (!(packet instanceof CPacketPlayer.Position) && !(packet instanceof CPacketPlayer.PositionRotation)) {
                return;
            }
            this.packets.add(packet);
        }
    }

    public void cancel() {
        this.packets.clear();
        ChatUtils.setEnabled(false);
    }

    public void renderNameTag(String name, double pX, double pY, double pZ, double distance, SurfaceBuilder builder) {
        float var13 = (float)distance / 5.0f;
        if (var13 < 1.625f) {
            var13 = 2.0f;
        }
        RenderManager renderManager = Globals.MC.getRenderManager();
        float scale = var13 * 0.01f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)pX), (float)((float)pY + 1.4f), (float)((float)pZ));
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
        int width = (int)SurfaceHelper.getStringWidth(HUD.FONT, name);
        SurfaceHelper.drawRect(-1, -1, 1, 1, Colors.toRGBA(0, 0, 0, 0));
        GLUtils.setGLCap(3042, true);
        GL11.glBlendFunc((int)770, (int)771);
        double x = -width / 2;
        double y = -SurfaceHelper.getStringHeight(HUD.FONT) - 1.0;
        String name2 = name;
        if (HUD.FONT != null) {
            builder.reset().fontRenderer(HUD.FONT).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(HUD.FONT, name2) + 4.0, SurfaceHelper.getStringHeight(HUD.FONT) + 4.0).end().color(Colors.toRGBA(255, 255, 255, 255)).text(name2, x + 1.0, y + 1.0, true).color(Colors.toRGBA(255, 255, 255, 255)).text(name2, x, y);
        } else {
            builder.reset().fontRenderer(HUD.FONT).task(SurfaceBuilder::disableTexture2D).beginQuads().color(Colors.toRGBA(0, 0, 0, 100)).rectangle(x - 2.0, y - 2.0, SurfaceHelper.getStringWidth(HUD.FONT, name2) + 4.0, SurfaceHelper.getStringHeight(HUD.FONT) + 4.0).end().task(SurfaceBuilder::enableTexture2D).color(Colors.toRGBA(255, 255, 255, 255)).text(name2, x, y - 1.0, true);
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GLUtils.setGLCap(2929, true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }
}

