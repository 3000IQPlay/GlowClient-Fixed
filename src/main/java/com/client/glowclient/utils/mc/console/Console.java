//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  joptsimple.internal.Strings
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.Style
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextFormatting
 */
package com.client.glowclient.utils.mc.console;

import com.client.glowclient.utils.client.Globals;
import com.google.common.base.Strings;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Console {
    private static final ThreadLocal<AtomicInteger> INDENTATION = new ThreadLocal();
    private static final int MIN_INDENT = 1;

    private static AtomicInteger getOrCreate() {
        AtomicInteger count = INDENTATION.get();
        if (count == null) {
            count = new AtomicInteger(1);
            INDENTATION.set(count);
        }
        return count;
    }

    public static void start() {
        Console.getOrCreate().set(1);
    }

    public static void write(String msg, Style style) {
        String tab = joptsimple.internal.Strings.repeat((char)'\uf802', (int)Math.max(Console.getOrCreate().get(), 1)) + "\u00a79[GlowClient]\u00a77 ";
        if (style == null) {
            Console.printMessageNaked(tab, msg);
        } else {
            Console.printMessageNaked(tab, msg, style);
        }
    }

    private static void writeNoTag(String msg, Style style) {
        String tab = joptsimple.internal.Strings.repeat((char)'\uf802', (int)Math.max(Console.getOrCreate().get(), 1)) + "\u00a7f";
        if (style == null) {
            Console.printMessageNaked(tab, msg);
        } else {
            Console.printMessageNaked(tab, msg, style);
        }
    }

    public static void write(String msg) {
        Console.write(msg, null);
    }

    public static void writeNoTag(String msg) {
        Console.writeNoTag(msg, null);
    }

    public static void incrementIndent() {
        Console.getOrCreate().incrementAndGet();
    }

    public static void decrementIndent() {
        Console.getOrCreate().decrementAndGet();
    }

    public static int getIndents() {
        return Console.getOrCreate().get();
    }

    public static void setIndents(int indents) {
        Console.getOrCreate().set(indents);
    }

    public static void finished() {
        INDENTATION.remove();
    }

    private static void printMessageNaked(String startWith, String message, Style firstStyle, Style secondStyle) {
        if (Globals.MC.player != null && !Strings.isNullOrEmpty((String)message)) {
            if (message.contains("\n")) {
                Scanner scanner = new Scanner(message);
                scanner.useDelimiter("\n");
                Style s1 = firstStyle;
                Style s2 = secondStyle;
                while (scanner.hasNext()) {
                    Console.printMessageNaked(startWith, scanner.next(), s1, s2);
                    Style cpy = s1;
                    s1 = s2;
                    s2 = cpy;
                }
            } else {
                TextComponentString string = new TextComponentString(startWith + message.replaceAll("\r", ""));
                string.setStyle(firstStyle);
                Globals.MC.player.sendMessage((ITextComponent)string);
            }
        }
    }

    private static void printMessageNaked(String append, String message, Style style) {
        Console.printMessageNaked(append, message, style, style);
    }

    private static void printMessageNaked(String append, String message) {
        Console.printMessageNaked(append, message, new Style().setColor(TextFormatting.GRAY), new Style().setColor(TextFormatting.GRAY));
    }
}

