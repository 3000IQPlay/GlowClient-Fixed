/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 *  net.minecraftforge.event.world.WorldEvent
 */
package com.client.glowclient.sponge.events.ModEvents;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class WorldChangeEvent
extends WorldEvent {
    public WorldChangeEvent(World world) {
        super(world);
    }

    public boolean isWorldNull() {
        return this.getWorld() == null;
    }
}

