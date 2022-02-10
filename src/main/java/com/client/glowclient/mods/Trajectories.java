//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.math.AngleN;
import com.client.glowclient.utils.mod.mods.trajectories.Projectile;
import com.client.glowclient.utils.mod.mods.trajectories.SimulationResult;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Trajectories
extends ToggleMod {
    public Trajectories() {
        super(Category.RENDER, "Trajectories", false, -1, "Draws trajectory line of projectiles");
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onRender(RenderEvent event) {
        try {
            Projectile projectile = Projectile.getProjectileByItemStack(Globals.MC.player.getHeldItemMainhand());
            if (!projectile.isNull()) {
                SimulationResult result = projectile.getSimulatedTrajectoryFromEntity((Entity)Globals.MC.player, AngleN.degrees(Globals.MC.player.rotationPitch, Globals.MC.player.rotationYaw), projectile.getForce(Globals.MC.player.getHeldItemMainhand().getMaxItemUseDuration() - Globals.MC.player.getItemInUseCount()), 0);
                if (result == null) {
                    return;
                }
                if (result.getPathTraveled().size() > 1) {
                    event.setTranslation(Globals.MC.player.getPositionVector());
                    GlStateManager.enableDepth();
                    GlStateManager.glLineWidth((float)3.0f);
                    event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
                    Iterator<Vec3d> it = result.getPathTraveled().iterator();
                    Vec3d previous = it.next();
                    while (it.hasNext()) {
                        Vec3d next = it.next();
                        event.getBuffer().pos(previous.x, previous.y, previous.z).color(HUD.red.getInt(), HUD.green.getInt(), HUD.blue.getInt(), 200).endVertex();
                        event.getBuffer().pos(next.x, next.y, next.z).color(HUD.red.getInt(), HUD.green.getInt(), HUD.blue.getInt(), 200).endVertex();
                        previous = next;
                    }
                    event.getTessellator().draw();
                    GlStateManager.glLineWidth((float)1.0f);
                    GlStateManager.disableDepth();
                    event.resetTranslation();
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

