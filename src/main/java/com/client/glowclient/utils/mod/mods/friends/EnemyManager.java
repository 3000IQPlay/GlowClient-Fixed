//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.StringUtils
 */
package com.client.glowclient.utils.mod.mods.friends;

import com.client.glowclient.utils.mod.mods.friends.Enemy;
import java.util.ArrayList;
import net.minecraft.util.StringUtils;

public class EnemyManager {
    public static EnemyManager enemyManager;
    public static ArrayList<Enemy> enemyList;

    public static EnemyManager getEnemies() {
        if (enemyManager == null) {
            enemyManager = new EnemyManager();
        }
        return enemyManager;
    }

    public void addEnemy(String name) {
        enemyList.add(new Enemy(name));
    }

    public void removeEnemy(String name) {
        for (Enemy enemy : enemyList) {
            if (!enemy.getName().equalsIgnoreCase(name)) continue;
            enemyList.remove(enemy);
            break;
        }
    }

    public boolean isEnemy(String name) {
        boolean isEnemy = false;
        for (Enemy enemy : enemyList) {
            if (!enemy.getName().equalsIgnoreCase(StringUtils.stripControlCodes((String)name))) continue;
            isEnemy = true;
            break;
        }
        return isEnemy;
    }

    static {
        enemyList = new ArrayList();
    }
}

