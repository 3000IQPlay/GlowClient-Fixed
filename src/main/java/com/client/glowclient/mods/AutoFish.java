//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$MouseInputEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class AutoFish
extends ToggleMod {
    private int ticksCastDelay = 0;
    private int ticksHookDeployed = 0;
    private boolean previouslyHadRodEquipped = false;
    public final int casting_delay = 20;
    public final double max_sound_distance = 2.0;
    public final int fail_safe_time = 0;

    public AutoFish() {
        super(Category.PLAYER, "AutoFish", false, -1, "Automatically fishes");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isCorrectSplashPacket(SPacketSoundEffect packet) {
        EntityPlayerSP me = Globals.MC.player;
        if (!packet.getSound().equals((Object)SoundEvents.ENTITY_BOBBER_SPLASH)) return false;
        if (me == null) return false;
        if (me.fishEntity == null) return false;
        Vec3d vec3d = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
        if (!(me.fishEntity.getPositionVector().distanceTo(vec3d) <= 2.0)) return false;
        return true;
    }

    private void rightClick() {
        try {
            if (this.ticksCastDelay <= 0) {
                Globals.MC.rightClickMouse();
                this.ticksCastDelay = 20;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void resetLocals() {
        this.ticksCastDelay = 0;
        this.ticksHookDeployed = 0;
        this.previouslyHadRodEquipped = false;
    }

    @Override
    public void onEnabled() {
        this.resetLocals();
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        EntityPlayerSP me = Globals.MC.player;
        ItemStack heldStack = me.getHeldItemMainhand();
        if (this.ticksCastDelay > 20) {
            this.ticksCastDelay = 20;
        } else if (this.ticksCastDelay > 0) {
            --this.ticksCastDelay;
        }
        if (heldStack != null && heldStack.getItem() instanceof ItemFishingRod) {
            if (!this.previouslyHadRodEquipped) {
                this.ticksCastDelay = 20;
                this.previouslyHadRodEquipped = true;
            } else if (me.fishEntity == null) {
                this.rightClick();
            } else {
                ++this.ticksHookDeployed;
            }
        } else {
            this.resetLocals();
        }
    }

    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseInputEvent event) {
        if (Globals.MC.gameSettings.keyBindUseItem.isKeyDown() && this.ticksHookDeployed > 0) {
            this.ticksCastDelay = 20;
        }
    }

    @SubscribeEvent
    public void onPacketIncoming(PacketEvent.Receive event) {
        SPacketSoundEffect packet;
        if (event.getPacket() instanceof SPacketSoundEffect && this.isCorrectSplashPacket(packet = (SPacketSoundEffect)event.getPacket())) {
            this.rightClick();
        }
    }
}

