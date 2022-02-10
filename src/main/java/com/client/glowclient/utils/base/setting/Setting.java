/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package com.client.glowclient.utils.base.setting;

import com.google.gson.JsonObject;

public abstract class Setting {
    private static Setting INSTANCE;
    protected String names;
    protected String desc;
    protected String modnames;

    public Setting() {
        INSTANCE = this;
    }

    public abstract void setValue(Object var1);

    public static Setting getInstance() {
        return INSTANCE;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getName() {
        return this.names;
    }

    public String getModName() {
        return this.modnames;
    }

    public abstract void encode(JsonObject var1);

    public abstract void decode(String var1, JsonObject var2);

    public String getFullName() {
        return this.modnames + "." + this.names;
    }
}

