/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package com.client.glowclient.utils.base.setting;

import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingInt;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SettingUtils {
    public static SettingBoolean settingBoolean(String modname, String name, String desc, boolean defaultValue) {
        return new SettingBoolean(modname, name, desc, defaultValue);
    }

    public static SettingDouble settingDouble(String modname, String name, String desc, double defaultValue, double step, double min, double max) {
        return new SettingDouble(modname, name, desc, defaultValue, step, min, max);
    }

    public static SettingString settingString(String modname, String name, String desc, String defaultValue) {
        return new SettingString(modname, name, desc, defaultValue);
    }

    public static SettingMode settingMode(String modname, String name, String desc, String defaultValue, String ... modes) {
        return new SettingMode(modname, name, desc, defaultValue, modes);
    }

    public static SettingBoolean settingToggled(String modname, String name, String desc, boolean defaultValue) {
        return new SettingBoolean(modname, name, desc, defaultValue);
    }

    public static SettingInt settingKeybind(String modname, String name, String desc, int defaultValue) {
        return new SettingInt(modname, name, desc, defaultValue, 0, 0, 0);
    }

    public static SettingString settingPrefix(String name, String desc, String defaultValue) {
        return new SettingString("Command", name, desc, defaultValue);
    }

    public static SettingBoolean settingPacketServer(String name) {
        return new SettingBoolean("AntiPacketsServer", name, "Cancel SPacket" + name, false);
    }

    public static SettingBoolean settingPacket(String name) {
        return new SettingBoolean("AntiPackets", name, "Cancel CPacket" + name, false);
    }

    public static int getInt(JsonObject object, String name, int def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().intValue();
        }
        return def;
    }

    public static short getShort(JsonObject object, String name, short def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().shortValue();
        }
        return def;
    }

    public static byte getByte(JsonObject object, String name, byte def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().byteValue();
        }
        return def;
    }

    public static long getLong(JsonObject object, String name, long def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().longValue();
        }
        return def;
    }

    public static float getFloat(JsonObject object, String name, float def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().floatValue();
        }
        return def;
    }

    public static double getDouble(JsonObject object, String name, double def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsJsonPrimitive().getAsNumber().doubleValue();
        }
        return def;
    }

    public static boolean getBoolean(JsonObject object, String name, boolean def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            return element.getAsJsonPrimitive().getAsBoolean();
        }
        return def;
    }

    public static String getString(JsonObject object, String name, String def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return element.getAsJsonPrimitive().getAsString();
        }
        return def;
    }

    public static JsonArray getArray(JsonObject object, String name, JsonArray def) {
        JsonElement element;
        if (object.has(name) && (element = object.get(name)).isJsonArray()) {
            return element.getAsJsonArray();
        }
        return def;
    }
}

