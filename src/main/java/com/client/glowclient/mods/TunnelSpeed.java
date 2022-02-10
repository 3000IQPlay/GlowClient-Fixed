//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TunnelSpeed
extends ToggleMod {
    public TunnelSpeed() {
        super(Category.MOVEMENT, "TunnelSpeed", false, -1, "Goes fast in 2 block high tunnels");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        BlockPos pos = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY + 2.0, Globals.MC.player.posZ);
        BlockPos pos2 = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY - 1.0, Globals.MC.player.posZ);
        if (Globals.MC.world.getBlockState(pos).getBlock() != Blocks.AIR && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.END_PORTAL && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_WATER && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.LAVA && Globals.MC.world.getBlockState(pos).getBlock() != Blocks.FLOWING_LAVA && Globals.MC.world.getBlockState(pos2).getBlock() != Blocks.ICE && Globals.MC.world.getBlockState(pos2).getBlock() != Blocks.FROSTED_ICE && Globals.MC.world.getBlockState(pos2).getBlock() != Blocks.PACKED_ICE && !Globals.MC.player.isInWater()) {
            float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
            if (Globals.MC.gameSettings.keyBindForward.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown() && Globals.MC.player.onGround) {
                Globals.MC.player.motionX -= (double)WMath.sin(yaw) * 0.15;
                Globals.MC.player.motionZ += (double)WMath.cos(yaw) * 0.15;
            }
        }
    }
}

