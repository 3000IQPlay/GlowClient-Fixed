/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.base.mod;

public enum Category {
    PLAYER("Player", "Mods that interact with the local player"),
    RENDER("Render", "2D/3D rendering mods"),
    SERVER("Server", "Any mod that has to do with the server"),
    SERVICE("Service", "Background mods"),
    MOVEMENT("Movement", "Movement mods"),
    WIP("Development", "Mods under development"),
    OTHER("Other", "Window for other mods"),
    COMBAT("Combat", "Mods used for combat"),
    JEWISHTRICKS("Jewish Tricks", "Emperor's Jewish Tricks");

    private String Name;
    private String description;

    private Category(String name, String description) {
        this.Name = name;
        this.description = description;
    }

    public String getName() {
        return this.Name;
    }

    public String getDescription() {
        return this.description;
    }
}

