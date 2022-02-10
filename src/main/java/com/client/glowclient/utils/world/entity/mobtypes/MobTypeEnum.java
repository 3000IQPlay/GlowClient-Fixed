/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.world.entity.mobtypes;

public enum MobTypeEnum {
    PLAYER,
    HOSTILE,
    NEUTRAL,
    FRIENDLY,
    INVALID;


    public boolean isValid() {
        return this.ordinal() > 0;
    }
}

