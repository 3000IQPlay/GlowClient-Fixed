/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.classes;

public class ArrayHelper {
    public static <T> T getOrDefault(T[] array, int index, T defaultValue) {
        if (array != null && index >= 0 && index < array.length) {
            return array[index];
        }
        return defaultValue;
    }

    public static <T> boolean isInRange(T[] array, int index) {
        return array != null && index >= 0 && index < array.length;
    }
}

