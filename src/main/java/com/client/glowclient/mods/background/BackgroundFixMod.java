//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.sponge.events.LocalPlayerUpdateMovementEvent;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.classes.tasks.Task;
import com.client.glowclient.utils.classes.tasks.TaskManager;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BackgroundFixMod
extends ServiceMod {
    private Task.TaskProcessing processing = null;

    public BackgroundFixMod() {
        super("BackgroundFixMod", "Repairs Minecraft inconsistencies and errors");
    }

    @SubscribeEvent
    public void onMovementUpdatePre(LocalPlayerUpdateMovementEvent.Pre event) {
        this.processing = TaskManager.getTop(Task.Type.LOOK);
        if (this.processing != null) {
            this.processing.preProcessing();
        }
    }

    @SubscribeEvent
    public void onMovementUpdatePost(LocalPlayerUpdateMovementEvent.Post event) {
        if (this.processing != null) {
            this.processing.postProcessing();
        }
    }

    @SubscribeEvent
    public void onPigmanUpdate(LivingEvent.LivingUpdateEvent event) {
        try {
            if (event.getEntityLiving() instanceof EntityPigZombie) {
                EntityPigZombie pigZombie = (EntityPigZombie)event.getEntityLiving();
                if (pigZombie.isArmsRaised()) {
                    pigZombie.angerLevel = 400;
                } else if (pigZombie.isAngry()) {
                    --pigZombie.angerLevel;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        event.getEntityLiving().extinguish();
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        try {
            Globals.MC.player.inPortal = false;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

