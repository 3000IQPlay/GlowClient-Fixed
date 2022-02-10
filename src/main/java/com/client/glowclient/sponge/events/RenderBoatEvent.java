/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.events;

import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderBoatEvent
extends Event {
    private float yaw;
    private EntityBoat boat;

    public RenderBoatEvent(EntityBoat boatIn, float entityYaw) {
        this.boat = boatIn;
        this.yaw = entityYaw;
    }

    public void setYaw(float yawIn) {
        this.yaw = yawIn;
    }

    public float getYaw() {
        return this.yaw;
    }

    public EntityBoat getBoat() {
        return this.boat;
    }
}

