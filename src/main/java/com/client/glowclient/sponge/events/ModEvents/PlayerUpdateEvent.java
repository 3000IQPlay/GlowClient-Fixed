/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraftforge.event.entity.living.LivingEvent
 */
package com.client.glowclient.sponge.events.ModEvents;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PlayerUpdateEvent
extends LivingEvent {
    public PlayerUpdateEvent(EntityLivingBase e) {
        super(e);
    }
}

