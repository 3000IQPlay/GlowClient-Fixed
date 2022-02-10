/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 */
package com.client.glowclient.utils.mod.mods.trajectories;

import net.minecraft.item.Item;

public interface IProjectile {
    public Item getItem();

    default public double getForce(int charge) {
        return 1.5;
    }

    default public double getMaxForce() {
        return 1.5;
    }

    default public double getMinForce() {
        return 1.5;
    }

    default public double getGravity() {
        return 0.03;
    }

    default public double getDrag() {
        return 0.99;
    }

    default public double getWaterDrag() {
        return 0.8;
    }

    default public double getProjectileSize() {
        return 0.25;
    }
}

