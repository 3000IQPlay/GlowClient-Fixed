/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.event.entity.EntityEvent
 */
package com.client.glowclient.sponge.events.ModEvents;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityAddedEvent
extends EntityEvent {
    public EntityAddedEvent(Entity entity) {
        super(entity);
    }
}

