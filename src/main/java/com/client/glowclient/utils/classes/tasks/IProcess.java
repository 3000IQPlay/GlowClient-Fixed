/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package com.client.glowclient.utils.classes.tasks;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;

public interface IProcess {
    public void process(DataEntry var1);

    public static class DataEntry {
        private final Map<String, Object> data = Maps.newTreeMap((Comparator)String.CASE_INSENSITIVE_ORDER);

        public <T> T getOrDefault(String o, T defaultValue) {
            try {
                return (T)this.data.get(o);
            }
            catch (Throwable t) {
                return defaultValue;
            }
        }

        public <T> T get(String o) {
            return this.getOrDefault(o, null);
        }

        public void set(String name, Object o) {
            this.data.put(name, o);
        }
    }
}

