//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.FutureCallback
 *  javax.annotation.Nullable
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityDonkey
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.passive.EntityLlama
 *  net.minecraft.entity.passive.EntityMule
 *  net.minecraft.entity.passive.EntityOcelot
 *  net.minecraft.entity.passive.EntityParrot
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.FileManager;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.console.Console;
import com.client.glowclient.utils.mod.mods.friends.EnemyManager;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfoHelper;
import com.google.common.util.concurrent.FutureCallback;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MCF
extends ToggleMod {
    public MCF() {
        super(Category.PLAYER, "MCF", false, -1, "Middle Click Friends");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    @SubscribeEvent
    public void onClick(MouseEvent event) {
        RayTraceResult obj;
        if (event.getButton() == 2 && event.isButtonstate() && (obj = Globals.MC.objectMouseOver) != null && obj.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity entity = obj.entityHit;
            if (entity instanceof EntityOtherPlayerMP) {
                String arg = entity.getName();
                if (!FriendManager.getFriends().isFriend(arg)) {
                    if (EnemyManager.getEnemies().isEnemy(arg)) {
                        Console.write(String.format("\u00a7c%s is already an enemy", arg));
                    } else {
                        FriendManager.getFriends().addFriend(arg);
                        FileManager.getInstance().saveFriends();
                    }
                } else {
                    FriendManager.getFriends().removeFriend(arg);
                    FileManager.getInstance().saveFriends();
                }
            }
            if (entity instanceof EntityHorse || entity instanceof EntityDonkey || entity instanceof EntityMule || entity instanceof EntityLlama || entity instanceof EntityOcelot || entity instanceof EntityParrot || entity instanceof EntityWolf) {
                UUID id1 = new UUID(1L, 1L);
                if ((entity instanceof EntityHorse || entity instanceof EntityDonkey || entity instanceof EntityMule || entity instanceof EntityLlama) && ((AbstractHorse)entity).getOwnerUniqueId() != null) {
                    id1 = ((AbstractHorse)entity).getOwnerUniqueId();
                }
                if (entity instanceof EntityOcelot && ((EntityOcelot)entity).getOwnerId() != null) {
                    id1 = ((EntityOcelot)entity).getOwnerId();
                }
                if (entity instanceof EntityParrot && ((EntityParrot)entity).getOwnerId() != null) {
                    id1 = ((EntityParrot)entity).getOwnerId();
                }
                if (entity instanceof EntityWolf && ((EntityWolf)entity).getOwnerId() != null) {
                    id1 = ((EntityWolf)entity).getOwnerId();
                }
                PlayerInfoHelper.invokeEfficiently(id1, new FutureCallback<PlayerInfo>(){

                    public void onSuccess(@Nullable PlayerInfo result) {
                        if (result == null) {
                            return;
                        }
                        if (!result.isOfflinePlayer()) {
                            Console.write("Name of owner: " + result.getName());
                        }
                    }

                    public void onFailure(Throwable t) {
                    }
                });
            }
        }
    }
}

