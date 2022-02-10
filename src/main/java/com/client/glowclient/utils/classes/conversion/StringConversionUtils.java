/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.classes.conversion;

public class StringConversionUtils {
    public static boolean isStringDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isStringInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

