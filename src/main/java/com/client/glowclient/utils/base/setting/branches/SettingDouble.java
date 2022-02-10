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

public class SettingDouble
extends Setting {
    private double values;
    private double steps;
    private double mins;
    private double maxs;

    public SettingDouble(String modname, String name, String description, double defaultvalue, double step, double min, double max) {
        this.desc = description;
        this.names = name;
        this.values = defaultvalue;
        this.steps = step;
        this.mins = min;
        this.maxs = max;
        this.modnames = modname;
    }

    public double getDouble() {
        return this.values;
    }

    public int getInt() {
        return (int)this.values;
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
        this.values = (Double)val;
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
        this.values = SettingUtils.getFloat(json, "value", (float)this.values);
    }
}

