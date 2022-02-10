//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraft.util.MovementInput
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.remax.GLUtils;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.world.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Freecam
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("FreeCam", "Speed", "Flight Speed", 0.15, 0.05, 0.0, 0.5);
    public static SettingBoolean tracer = SettingUtils.settingBoolean("FreeCam", "Tracer", "Shows tracer line", true);
    public static SettingBoolean esp = SettingUtils.settingBoolean("FreeCam", "ESPbox", "Shows ESP outline", true);
    public static SettingBoolean name = SettingUtils.settingBoolean("FreeCam", "Nametag", "Shows player nametag", true);
    private double posX;
    private double posY;
    private double posZ;
    private float pitch;
    private float yaw;
    private double startPosX;
    private double startPosY;
    private double startPosZ;
    private float startPitch;
    private float startYaw;
    private boolean isRidingEntity;
    private Entity ridingEntity;

    public Freecam() {
        super(Category.PLAYER, "Freecam", false, -1, "View different parts of the world without moving character");
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
                try {
                    GL11.glPushMatrix();
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
                    GL11.glPopMatrix();
                }
                catch (Exception eyes) {
                    // empty catch block
                }
            }
            if (name.getBoolean()) {
                event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
                double pX = this.posX - 0.6 - renderPosX;
                double pY = this.posY - 1.8 - renderPosY;
                double pZ = this.posZ - 0.6 - renderPosZ;
                double distance = Globals.MC.player.getDistance(this.posX, this.posY, this.posZ);
                this.renderNameTag(Globals.MC.player.getName() + " [Freecam]", pX + 0.6, pY + 2.3, pZ + 0.6, distance, SurfaceBuilder.getBuilder());
                event.getTessellator().draw();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        String name = "FREECAM";
        if (ModuleManager.getModuleFromName("Blink").isEnabled()) {
            name = "FREECAM/BLINK";
        }
    }

    @Override
    public void onEnabled() {
        if (Globals.MC.player != null) {
            boolean bl = this.isRidingEntity = Globals.MC.player.getRidingEntity() != null;
            if (Globals.MC.player.getRidingEntity() == null) {
                this.posX = Globals.MC.player.posX;
                this.posY = Globals.MC.player.posY;
                this.posZ = Globals.MC.player.posZ;
            } else {
                this.posX = Globals.MC.player.posX;
                this.posY = Globals.MC.player.posY;
                this.posZ = Globals.MC.player.posZ;
                this.ridingEntity = Globals.MC.player.getRidingEntity();
                Globals.MC.player.dismountRidingEntity();
            }
            this.pitch = Globals.MC.player.rotationPitch;
            this.yaw = Globals.MC.player.rotationYaw;
            Globals.MC.player.capabilities.isFlying = true;
            Globals.MC.player.capabilities.setFlySpeed((float)speed.getDouble());
            Globals.MC.player.noClip = true;
        }
    }

    @Override
    public void onDisabled() {
        WorldUtils.reloadChunks();
        EntityPlayerSP localPlayer = Globals.MC.player;
        if (localPlayer != null) {
            Globals.MC.player.setPositionAndRotation(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
            this.posZ = 0.0;
            this.posY = 0.0;
            this.posX = 0.0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            try {
                Globals.MC.player.capabilities.isFlying = ModuleManager.getModuleFromName("ElytraFlight").isEnabled();
            }
            catch (Throwable t) {
                Globals.MC.player.capabilities.isFlying = false;
            }
            Globals.MC.player.capabilities.setFlySpeed(0.05f);
            if (this.isRidingEntity) {
                Globals.MC.player.startRiding(this.ridingEntity, true);
            }
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        MovementInput movementInput = Globals.MC.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = Globals.MC.player.rotationYaw;
        double move = 0.5 + speed.getDouble();
        if (!Globals.MC.gameSettings.keyBindJump.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
            Globals.MC.player.motionY = 0.0;
        }
        if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
            Globals.MC.player.motionY = 0.5 + speed.getDouble();
        }
        if (Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
            Globals.MC.player.motionY = -(0.5 + speed.getDouble());
        }
        if (Globals.MC.gameSettings.keyBindJump.isKeyDown() && Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
            Globals.MC.player.motionY = 0.0;
        }
        if (forward == 0.0 && strafe == 0.0) {
            Globals.MC.player.motionX = 0.0;
            Globals.MC.player.motionZ = 0.0;
        } else if (!Globals.MC.player.onGround) {
            Globals.MC.player.motionX = forward * move * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * move * Math.sin(Math.toRadians(yaw + 90.0f));
            Globals.MC.player.motionZ = forward * move * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * move * Math.cos(Math.toRadians(yaw + 90.0f));
        }
        Globals.MC.player.capabilities.isFlying = true;
        Globals.MC.player.noClip = true;
        Globals.MC.player.onGround = false;
        Globals.MC.player.fallDistance = 0.0f;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput || event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPacketReceived(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            this.startPosX = packet.getX();
            this.startPosY = packet.getY();
            this.startPosZ = packet.getZ();
            this.startPitch = packet.getPitch();
            this.startYaw = packet.getYaw();
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.posX = this.startPosX;
        this.posY = this.startPosY;
        this.posZ = this.startPosZ;
        this.pitch = this.startPitch;
        this.yaw = this.startYaw;
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
        SurfaceHelper.drawRect(-1555, -1555, 1, 1, Colors.toRGBA(0, 0, 0, 0));
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

