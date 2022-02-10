//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketPlayerListItem
 *  net.minecraft.network.play.server.SPacketPlayerListItem$Action
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.event.world.WorldEvent$Unload
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
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.remax.GLUtils;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class LogoutESP
extends ToggleMod {
    private final Set<LogoutPos> logoutSpots = new HashSet<LogoutPos>();

    public LogoutESP() {
        super(Category.RENDER, "LogoutESP", false, -1, "Draws outline at player logout spots");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onPacketRecieved(PacketEvent.Receive event) {
        SPacketPlayerListItem playerListPacket;
        if (event.getPacket() instanceof SPacketPlayerListItem && ((playerListPacket = (SPacketPlayerListItem)event.getPacket()).getAction().equals((Object)SPacketPlayerListItem.Action.REMOVE_PLAYER) || playerListPacket.getAction().equals((Object)SPacketPlayerListItem.Action.ADD_PLAYER))) {
            try {
                playerListPacket.getEntries().stream().filter(Objects::nonNull).filter(data -> {
                    String name = this.getNameFromComponent(data.getProfile());
                    return !Strings.isNullOrEmpty((String)name) && !this.isLocalPlayer(name) || playerListPacket.getAction().equals((Object)SPacketPlayerListItem.Action.REMOVE_PLAYER);
                }).forEach(data -> {
                    String name = this.getNameFromComponent(data.getProfile());
                    UUID id = data.getProfile().getId();
                    switch (playerListPacket.getAction()) {
                        case ADD_PLAYER: {
                            this.logoutSpots.removeIf(pos -> pos.id.equals(id));
                            break;
                        }
                        case REMOVE_PLAYER: {
                            EntityPlayer player = Globals.MC.world.getPlayerEntityByUUID(id);
                            if (player == null) break;
                            AxisAlignedBB BB = player.getEntityBoundingBox();
                            Vec3d[] pos2 = new Vec3d[]{new Vec3d(BB.minX, BB.minY, BB.minZ), new Vec3d(BB.maxX, BB.maxY, BB.maxZ)};
                            this.logoutSpots.add(new LogoutPos(pos2, id, player.getName()));
                        }
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            this.logoutSpots.forEach(position -> {
                RenderUtils.drawLogoutESP(position.pos[1].x - 0.6 - renderPosX, position.pos[1].y - 1.8 - renderPosY, position.pos[1].z - 0.6 - renderPosZ, 0.0, 0.41, 1.0);
                double pX = position.pos[1].x - 0.6 - renderPosX;
                double pY = position.pos[1].y - 1.8 - renderPosY;
                double pZ = position.pos[1].z - 0.6 - renderPosZ;
                double distance = Globals.MC.player.getDistance((position.pos[0].x + position.pos[1].x) / 2.0, position.pos[0].y, (position.pos[0].z + position.pos[1].z) / 2.0);
                String xp = String.format("%.0f", position.pos[1].x - 0.6);
                String yp = String.format("%.0f", position.pos[1].y);
                String zp = String.format("%.0f", position.pos[1].z - 0.6);
                this.renderNameTag(position.name, pX + 0.3, pY + 0.45, pZ + 0.3, distance, xp, yp, zp);
            });
            event.getTessellator().draw();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        this.logoutSpots.removeIf(pos -> {
            double distance = Globals.MC.player.getDistance((pos.pos[0].x + pos.pos[1].x) / 2.0, pos.pos[0].y, (pos.pos[0].z + pos.pos[1].z) / 2.0);
            return false;
        });
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        this.logoutSpots.clear();
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.logoutSpots.clear();
    }

    private float getLateralDistanceFromPlayer(Entity entityIn) {
        float f = (float)(entityIn.posX - Globals.MC.player.posX);
        float f2 = (float)(entityIn.posZ - Globals.MC.player.posZ);
        return MathHelper.sqrt((float)(f * f + f2 * f2));
    }

    private String getNameFromComponent(GameProfile profile) {
        return Objects.nonNull((Object)profile) ? profile.getName() : "";
    }

    private boolean isLocalPlayer(String username) {
        return Objects.nonNull((Object)Globals.MC.player) && Globals.MC.player.getDisplayName().getUnformattedText().equals(username);
    }

    public void renderNameTag(String name, double pX, double pY, double pZ, double distance, String xp, String yp, String zp) {
        SurfaceBuilder builder = new SurfaceBuilder();
        float var13 = (float)distance / 4.0f;
        if (var13 < 1.6f) {
            var13 = 1.6f;
        }
        if ((double)var13 > 10.0) {
            var13 = 10.0f;
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
        int width = (int)SurfaceHelper.getStringWidth(HUD.FONT, name + "_Logout_(" + xp + "," + yp + "," + zp + ")");
        GLUtils.setGLCap(3042, true);
        GL11.glBlendFunc((int)770, (int)771);
        SurfaceHelper.drawRect(9999, 9999, 1, 1, Integer.MIN_VALUE);
        double x = -width / 2;
        double y = -SurfaceHelper.getStringHeight(HUD.FONT) - 1.0;
        this.logoutSpots.forEach(position -> {
            String name2 = name + "_Logout_(" + xp + "," + yp + "," + zp + ")";
            if (HUD.FONT == null) {
                builder.reset().fontRenderer(HUD.FONT).color(Colors.GRAY).text(name2, x, y, true);
            } else {
                builder.reset().fontRenderer(HUD.FONT).color(Colors.GRAY).text(name2, x + 1.0, y + 1.0, true).color(Colors.GRAY).text(name2, x, y);
            }
        });
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GLUtils.revertAllCaps();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    private class LogoutPos {
        final Vec3d[] pos;
        final UUID id;
        final String name;

        private LogoutPos(Vec3d[] position, UUID uuid, String name) {
            this.pos = position;
            this.id = uuid;
            this.name = name;
        }

        public boolean equals(Object other) {
            if (!(other instanceof LogoutPos)) {
                return false;
            }
            return other == this || this.id.equals(((LogoutPos)other).id);
        }

        public int hashCode() {
            return this.id.hashCode();
        }
    }
}

