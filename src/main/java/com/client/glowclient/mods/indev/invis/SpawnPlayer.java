//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.indev.invis;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnPlayer
extends ToggleMod {
    private EntityOtherPlayerMP clonedPlayer;

    public SpawnPlayer() {
        super(Category.JEWISHTRICKS, "SpawnPlayer", false, -1, "Spawns a player entity with your rotation");
    }

    @Override
    public void onEnabled() {
        try {
            this.clonedPlayer = new EntityOtherPlayerMP((World)Globals.MC.world, Globals.MC.getSession().getProfile());
            this.clonedPlayer.copyLocationAndAnglesFrom((Entity)Globals.MC.player);
            this.clonedPlayer.rotationYawHead = Globals.MC.player.rotationYawHead;
            Globals.MC.world.addEntityToWorld(-109, (Entity)this.clonedPlayer);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (this.clonedPlayer != null) {
            this.clonedPlayer.rotationYawHead = Globals.MC.player.rotationYawHead;
            this.clonedPlayer.rotationYaw = Globals.MC.player.rotationYaw;
            this.clonedPlayer.rotationPitch = Globals.MC.player.rotationPitch;
            if (Globals.MC.player.getHeldItemMainhand() != null) {
                this.clonedPlayer.setHeldItem(EnumHand.MAIN_HAND, Globals.MC.player.getHeldItemMainhand());
            }
            if (Globals.MC.player.getHeldItemOffhand() != null) {
                this.clonedPlayer.setHeldItem(EnumHand.OFF_HAND, Globals.MC.player.getHeldItemOffhand());
            }
        }
    }

    @Override
    public void onDisabled() {
        try {
            Globals.MC.world.removeEntityFromWorld(-109);
            this.clonedPlayer = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

