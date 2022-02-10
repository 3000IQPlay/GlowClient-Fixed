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

public class SettingBoolean
extends Setting {
    private boolean values;

    public SettingBoolean(String modname, String name, String description, boolean defaultvalue) {
        this.desc = description;
        this.names = name;
        this.values = defaultvalue;
        this.modnames = modname;
    }

    public boolean getBoolean() {
        return this.values;
    }

    @Override
    public void setValue(Object val) {
        this.values = (Boolean)val;
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
        json.addProperty("value", Boolean.valueOf(this.values));
    }

    @Override
    public void decode(String fieldName, JsonObject json) {
        this.values = SettingUtils.getBoolean(json, "value", this.values);
    }
}

