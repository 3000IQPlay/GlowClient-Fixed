/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package com.client.glowclient.utils.extra.pepsimod;

import com.client.glowclient.utils.extra.pepsimod.IConfigTranslator;
import com.google.gson.JsonObject;

public class TargettingTranslator
implements IConfigTranslator {
    public static final TargettingTranslator INSTANCE = new TargettingTranslator();
    public boolean players = false;
    public boolean animals = false;
    public boolean monsters = false;
    public boolean golems = false;
    public boolean sleeping = false;
    public boolean invisible = false;
    public boolean teams = false;
    public boolean friends = false;
    public boolean through_walls = false;
    public boolean use_cooldown = false;
    public boolean silent = false;
    public boolean rotate = false;
    public TargetBone targetBone = TargetBone.FEET;
    public float fov = 360.0f;
    public float reach = 4.25f;
    public int delay = 20;

    private TargettingTranslator() {
    }

    @Override
    public void encode(JsonObject json) {
        json.addProperty("players", Boolean.valueOf(this.players));
        json.addProperty("animals", Boolean.valueOf(this.animals));
        json.addProperty("monsters", Boolean.valueOf(this.monsters));
        json.addProperty("golems", Boolean.valueOf(this.golems));
        json.addProperty("sleeping", Boolean.valueOf(this.sleeping));
        json.addProperty("invisible", Boolean.valueOf(this.invisible));
        json.addProperty("teams", Boolean.valueOf(this.teams));
        json.addProperty("friends", Boolean.valueOf(this.friends));
        json.addProperty("through_walls", Boolean.valueOf(this.through_walls));
        json.addProperty("use_cooldown", Boolean.valueOf(this.use_cooldown));
        json.addProperty("silent", Boolean.valueOf(this.silent));
        json.addProperty("rotate", Boolean.valueOf(this.rotate));
        json.addProperty("bone", (Number)this.targetBone.ordinal());
        json.addProperty("fov", (Number)Float.valueOf(this.fov));
        json.addProperty("reach", (Number)Float.valueOf(this.reach));
        json.addProperty("glideSpeed", (Number)this.delay);
    }

    @Override
    public void decode(String fieldName, JsonObject json) {
        this.players = this.getBoolean(json, "players", this.players);
        this.animals = this.getBoolean(json, "animals", this.animals);
        this.monsters = this.getBoolean(json, "monsters", this.monsters);
        this.golems = this.getBoolean(json, "golems", this.golems);
        this.sleeping = this.getBoolean(json, "sleeping", this.sleeping);
        this.invisible = this.getBoolean(json, "invisible", this.invisible);
        this.teams = this.getBoolean(json, "teams", this.teams);
        this.friends = this.getBoolean(json, "friends", this.friends);
        this.through_walls = this.getBoolean(json, "through_walls", this.through_walls);
        this.use_cooldown = this.getBoolean(json, "use_cooldown", this.use_cooldown);
        this.silent = this.getBoolean(json, "silent", this.silent);
        this.rotate = this.getBoolean(json, "rotate", this.rotate);
        this.targetBone = TargetBone.getBone(this.getInt(json, "bone", this.targetBone.ordinal()));
        this.fov = this.getFloat(json, "fov", this.fov);
        this.reach = this.getFloat(json, "reach", this.reach);
        this.delay = this.getInt(json, "glideSpeed", this.delay);
    }

    @Override
    public String name() {
        return "targetting";
    }

    public static enum TargetBone {
        HEAD,
        FEET,
        MIDDLE;


        public static TargetBone getBone(int id) {
            return TargetBone.values()[id];
        }
    }
}

