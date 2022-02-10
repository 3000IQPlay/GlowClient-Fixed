//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package com.client.glowclient.mods.indev.invis;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RemoveEntities
extends ToggleMod {
    public RemoveEntities() {
        super(Category.JEWISHTRICKS, "RemoveEntities", false, -1, "Removes all entities in your render distance - client sided");
    }

    @Override
    public void onEnabled() {
        try {
            for (Entity entity : Globals.MC.world.loadedEntityList) {
                if (entity == null || entity instanceof EntityPlayer) continue;
                Globals.MC.world.removeEntityFromWorld(entity.entityId);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

