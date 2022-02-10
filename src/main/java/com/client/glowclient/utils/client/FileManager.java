package com.client.glowclient.utils.client;

import com.client.glowclient.utils.base.setting.builder.*;
import org.apache.commons.io.*;
import com.client.glowclient.utils.mod.mods.friends.*;
import java.io.*;

public class FileManager
{
    private static final FileManager INSTANCE;
    static String os = System.getProperty("os.name");
    public static FileManager getInstance() {
        return FileManager.INSTANCE;
    }



    private FileManager() {
        System.out.print("I Fixed This... (Now hopefully multiplatform?) -sun_misc_Unsafe");
    }

    public void saveConfig() {
        final String config = SettingManager.encodeConfig();
        try {
            final File file = new File("GlowClientConfig.json");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            IOUtils.write(config.getBytes(), new FileOutputStream(file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFriends() {
        try {
            final File file = new File("GlowClientFriends.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            FriendManager.getFriends();
            for (final Friend friend : FriendManager.friendsList) {
                out.write(friend.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }

    public void saveEnemies() {
        try {
            final File file = new File("GlowClientEnemies.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            EnemyManager.getEnemies();
            for (final Enemy enemy : EnemyManager.enemyList) {
                out.write(enemy.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }

    public void loadConfig() {
        String launcherJson = null;
        final File file = new File("GlowClientConfig.json");
        try {
            if (!file.exists()) {
                file.createNewFile();
                this.saveConfig();
                launcherJson = "{}";
            }
            else {
                launcherJson = IOUtils.toString(new FileInputStream(file));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SettingManager.decodeConfig(launcherJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFriends() {
        try {
            final File file = new File("GlowClientFriends.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                FriendManager.getFriends().addFriend(name);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEnemies() {
        try {
            final File file = new File("GlowClientEnemies.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                EnemyManager.getEnemies().addEnemy(name);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        this.saveConfig();
        this.saveFriends();
        this.saveEnemies();
    }

    public void loadAll() {
        this.loadConfig();
        this.loadEnemies();
        this.loadFriends();
    }

    static {
        INSTANCE = new FileManager();
    }
}
