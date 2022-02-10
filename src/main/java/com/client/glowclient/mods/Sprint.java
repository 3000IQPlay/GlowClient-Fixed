//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.binding.Bindings;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.item.EntityBoat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Sprint
extends ToggleMod {
    private boolean isBound = false;
    public final Modes mode = Modes.ALWAYS;

    public Sprint() {
        super(Category.MOVEMENT, "Sprint", false, -1, "Sprints automatically");
    }

    private void startSprinting() {
        switch (this.mode) {
            case ALWAYS: {
                if (Globals.MC.player.collidedHorizontally || Globals.MC.player.isSprinting()) break;
                Globals.MC.player.setSprinting(true);
                break;
            }
            default: {
                if (!this.isBound) {
                    Bindings.keyBindSprint.bind();
                    this.isBound = true;
                }
                if (Bindings.keyBindSprint.getBinding().isKeyDown()) break;
                Bindings.keyBindSprint.setPressed(true);
            }
        }
    }

    private void stopSprinting() {
        if (this.isBound) {
            Bindings.keyBindSprint.setPressed(false);
            Bindings.keyBindSprint.unbind();
            this.isBound = false;
        }
    }

    @Override
    public void onDisabled() {
        this.stopSprinting();
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        if (!(Utils.getRidingEntity() instanceof EntityBoat) && event.getEntityLiving().moveForward > 0.0f && !event.getEntityLiving().collidedHorizontally && !event.getEntityLiving().isSneaking()) {
            this.startSprinting();
        }
    }

    static enum Modes {
        ALWAYS,
        LEGIT;

    }
}

