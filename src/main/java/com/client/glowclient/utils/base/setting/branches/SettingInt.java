/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package com.client.glowclient.utils.base.setting.branches;

import com.client.glowclient.utils.base.setting.Setting;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.google.gson.JsonObject;

public class SettingInt
extends Setting {
    private int values;
    private int steps;
    private int mins;
    private int maxs;

    public SettingInt(String modname, String name, String description, int defaultvalue, int step, int min, int max) {
        this.desc = description;
        this.names = name;
        this.values = defaultvalue;
        this.steps = step;
        this.mins = min;
        this.maxs = max;
        this.modnames = modname;
    }

    public int getInt() {
        return this.values;
    }

    public double getMax() {
        return this.maxs;
    }

    public double getMin() {
        return this.mins;
    }

    public double getStep() {
        return this.steps;
    }

    @Override
    public void setValue(Object val) {
        this.values = (Integer)val;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public String getName() {
        return this.names;
    }

    @Override
    public String getModName() {
        return this.modnames;
    }

    @Override
    public void encode(JsonObject json) {
        json.addProperty("value", (Number)this.values);
    }

    @Override
    public void decode(String fieldName, JsonObject json) {
        this.values = SettingUtils.getInt(json, "value", this.values);
    }
}

