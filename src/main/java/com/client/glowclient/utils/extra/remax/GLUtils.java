/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.utils.extra.remax;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class GLUtils {
    private static Map<Integer, Boolean> glCapMap = new HashMap<Integer, Boolean>();

    public static void setGLCap(int cap, boolean flag) {
        glCapMap.put(cap, GL11.glGetBoolean((int)cap));
        if (flag) {
            GL11.glEnable((int)cap);
        } else {
            GL11.glDisable((int)cap);
        }
    }

    public static void revertGLCap(int cap) {
        Boolean origCap = glCapMap.get(cap);
        if (origCap != null) {
            if (origCap.booleanValue()) {
                GL11.glEnable((int)cap);
            } else {
                GL11.glDisable((int)cap);
            }
        }
    }

    public static void revertAllCaps() {
        for (int cap : glCapMap.keySet()) {
            GLUtils.revertGLCap(cap);
        }
    }
}

