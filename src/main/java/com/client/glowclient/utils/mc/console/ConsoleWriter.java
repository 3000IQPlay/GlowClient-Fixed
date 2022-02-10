/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.mc.console;

import com.client.glowclient.utils.mc.console.Console;

public interface ConsoleWriter {
    default public void write(String msg) {
        Console.write(msg);
    }

    default public void incrementIndent() {
        Console.incrementIndent();
    }

    default public void decrementIndent() {
        Console.decrementIndent();
    }
}

