//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package com.client.glowclient.utils.world.entity.uuid;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfoHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.entity.EntityPlayerSP;

public class PlayerInfo {
    private static Gson GSON = new Gson();
    private static JsonParser PARSER = new JsonParser();
    private final UUID id;
    private final UUID offlineId;
    private final boolean isOfflinePlayer;
    private final List<Name> names;

    public PlayerInfo(UUID id) {
        List<Name> temp;
        this.id = id;
        try {
            temp = PlayerInfo.getHistoryOfNames(id);
        }
        catch (Throwable t) {
            temp = Collections.emptyList();
        }
        this.names = ImmutableList.copyOf(temp);
        this.offlineId = EntityPlayerSP.getOfflineUUID((String)this.getName());
        this.isOfflinePlayer = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PlayerInfo(String name) {
        JsonArray ar = new JsonArray();
        ar.add(name);
        UUID _id = UUID.randomUUID();
        ImmutableList _temp = (ImmutableList) Collections.emptyList();
        boolean _offline = true;
        try {
            JsonArray array = PlayerInfo.getResources(new URL("https://api.mojang.com/profiles/minecraft"), "POST", (JsonElement)ar).getAsJsonArray();
            JsonObject node = array.get(0).getAsJsonObject();
            UUID uuid = PlayerInfoHelper.getIdFromString(node.get("id").getAsString());
            Objects.requireNonNull(uuid);
            _id = uuid;
            _temp = ImmutableList.copyOf(PlayerInfo.getHistoryOfNames(_id));
            _offline = false;
        }
        catch (Throwable t) {
            _id = EntityPlayerSP.getOfflineUUID((String)name);
            _temp = (ImmutableList) Collections.singletonList(new Name(name));
            _offline = true;
        }
        finally {
            this.id = _id;
            this.names = _temp;
            this.offlineId = EntityPlayerSP.getOfflineUUID((String)this.getName());
            this.isOfflinePlayer = _offline;
        }
    }

    public static List<Name> getHistoryOfNames(UUID id) {
        ArrayList defaultl = Lists.newArrayList();
        try {
            JsonArray array = PlayerInfo.getResources(new URL("https://api.mojang.com/user/profiles/" + PlayerInfoHelper.getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
            ArrayList temp = Lists.newArrayList();
            for (JsonElement e : array) {
                JsonObject node = e.getAsJsonObject();
                String name = node.get("name").getAsString();
                long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
                temp.add(new Name(name, changedAt));
            }
            Collections.sort(temp);
            return temp;
        }
        catch (Exception exception) {
            return defaultl;
        }
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getOfflineId() {
        return this.offlineId;
    }

    public boolean isOfflinePlayer() {
        return this.isOfflinePlayer;
    }

    public String getName() {
        if (!this.names.isEmpty()) {
            return this.names.get(0).getName();
        }
        return null;
    }

    public List<Name> getNameHistory() {
        return this.names;
    }

    public String getNameHistoryAsString() {
        StringBuilder builder = new StringBuilder();
        if (!this.names.isEmpty()) {
            Iterator<Name> it = this.names.iterator();
            it.next();
            while (it.hasNext()) {
                Name next = it.next();
                builder.append(next.getName());
                if (!it.hasNext()) continue;
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public boolean isLocalPlayer() {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), Globals.MC.player.getName()) == 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof PlayerInfo && this.id.equals(((PlayerInfo)obj).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.id.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static JsonElement getResources(URL url, String request, JsonElement element) throws Exception {
        JsonElement data;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request);
            connection.setRequestProperty("Content-Type", "application/json");
            if (element != null) {
                DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeBytes(GSON.toJson(element));
                output.close();
            }
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            String json = builder.toString();
            data = PARSER.parse(json);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    private static JsonElement getResources(URL url, String request) throws Exception {
        return PlayerInfo.getResources(url, request, null);
    }

    public static class Name
    implements Comparable<Name> {
        private final String name;
        private final long changedAt;

        public Name(String name, long changedAt) {
            this.name = name;
            this.changedAt = changedAt;
        }

        public Name(String name) {
            this(name, 0L);
        }

        public String getName() {
            return this.name;
        }

        public long getTimeChanged() {
            return this.changedAt;
        }

        @Override
        public int compareTo(Name o) {
            return Long.compare(o.changedAt, this.changedAt);
        }

        public boolean equals(Object obj) {
            return obj instanceof Name && this.name.equalsIgnoreCase(((Name)obj).getName()) && this.changedAt == ((Name)obj).changedAt;
        }

        public int hashCode() {
            return Objects.hash(this.name, this.changedAt);
        }
    }
}

