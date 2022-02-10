//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketChangeGameState
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.WorldChangeEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoWeather
extends ToggleMod {
    private boolean isRaining = false;
    private float rainStrength = 0.0f;
    private float previousRainStrength = 0.0f;

    public NoWeather() {
        super(Category.RENDER, "NoWeather", false, -1, "Removes weather");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    private void saveState(World world) {
        if (world != null) {
            this.setState(world.getWorldInfo().isRaining(), world.rainingStrength, world.prevRainingStrength);
        } else {
            this.setState(false, 1.0f, 1.0f);
        }
    }

    private void setState(boolean raining, float rainStrength, float previousRainStrength) {
        this.isRaining = raining;
        this.setState(rainStrength, previousRainStrength);
    }

    private void setState(float rainStrength, float previousRainStrength) {
        this.rainStrength = rainStrength;
        this.previousRainStrength = previousRainStrength;
    }

    private void disableRain() {
        if (Globals.MC.world != null) {
            Globals.MC.world.getWorldInfo().setRaining(false);
            Globals.MC.world.setRainStrength(0.0f);
        }
    }

    public void resetState() {
        if (Globals.MC.world != null) {
            Globals.MC.world.getWorldInfo().setRaining(this.isRaining);
            Globals.MC.world.rainingStrength = this.rainStrength;
            Globals.MC.world.prevRainingStrength = this.previousRainStrength;
        }
    }

    @Override
    public void onEnabled() {
        this.saveState((World)Globals.MC.world);
    }

    @Override
    public void onDisabled() {
        this.resetState();
    }

    @SubscribeEvent
    public void onWorldChange(WorldChangeEvent event) {
        this.saveState(event.getWorld());
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.ClientTickEvent event) {
        this.disableRain();
    }

    @SubscribeEvent
    public void onPacketIncoming(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChangeGameState) {
            int state = ((SPacketChangeGameState)event.getPacket()).getGameState();
            float strength = ((SPacketChangeGameState)event.getPacket()).getValue();
            boolean isRainState = false;
            switch (state) {
                case 1: {
                    isRainState = true;
                    this.setState(true, 0.0f, 0.0f);
                    break;
                }
                case 2: {
                    isRainState = true;
                    this.setState(false, 1.0f, 1.0f);
                    break;
                }
                case 7: {
                    isRainState = true;
                    this.setState(strength, strength);
                }
            }
            if (isRainState) {
                this.disableRain();
                event.setCanceled(true);
            }
        }
    }
}

