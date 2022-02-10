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
import java.util.ArrayList;

public class SettingMode
extends Setting {
    private String values;
    private ArrayList<String> list = new ArrayList();

    public SettingMode(String modname, String name, String description, String defaultvalue, String ... modes) {
        this.desc = description;
        this.names = name;
        this.values = defaultvalue;
        this.modnames = modname;
        for (String mud : modes) {
            this.list.add(mud);
        }
    }

    public ArrayList<String> getModes() {
        return this.list;
    }

    public String getMode() {
        return this.values;
    }

    @Override
    public void setValue(Object val) {
        for (String vul : this.list) {
            if (!val.toString().equals(vul)) continue;
            this.values = (String)val;
        }
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
        json.addProperty("value", this.values);
    }

    @Override
    public void decode(String fieldName, JsonObject json) {
        this.values = SettingUtils.getString(json, "value", this.values);
    }
}

