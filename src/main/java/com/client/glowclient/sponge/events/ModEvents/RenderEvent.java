//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events.ModEvents;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.geometry.GeometryTessellator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderEvent
extends Event {
    private final GeometryTessellator tessellator;
    private final Vec3d renderPos;

    public RenderEvent(GeometryTessellator tessellator, Vec3d renderPos) {
        this.tessellator = tessellator;
        this.renderPos = renderPos;
    }

    public GeometryTessellator getTessellator() {
        return this.tessellator;
    }

    public BufferBuilder getBuffer() {
        return this.tessellator.getBuffer();
    }

    public Vec3d getRenderPos() {
        return this.renderPos;
    }

    public void setTranslation(Vec3d translation) {
        this.getBuffer().setTranslation(-translation.x, -translation.y, -translation.z);
    }

    public void resetTranslation() {
        this.setTranslation(this.renderPos);
    }

    public static class Render2DEvent
    extends Event {
        private final ScaledResolution resolution = new ScaledResolution(Globals.MC);
        private final SurfaceBuilder surfaceBuilder = new SurfaceBuilder();
        private final float partialTicks;

        public Render2DEvent(float partialTicks) {
            this.partialTicks = partialTicks;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }

        public double getScreenWidth() {
            return this.resolution.getScaledWidth_double();
        }

        public double getScreenHeight() {
            return this.resolution.getScaledHeight_double();
        }

        public SurfaceBuilder getSurfaceBuilder() {
            return this.surfaceBuilder;
        }
    }
}

