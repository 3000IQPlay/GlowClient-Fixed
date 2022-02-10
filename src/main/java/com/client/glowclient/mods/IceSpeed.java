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

import com.client.glowclient.mods.AutoWalk;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IceSpeed
extends ToggleMod {
    public static final SettingDouble speed = SettingUtils.settingDouble("IceSpeed", "Speed", "Speed on ice", 4.65, 0.05, 0.0, 6.0);

    public IceSpeed() {
        super(Category.MOVEMENT, "IceSpeed", false, -1, "Go faster on ice");
    }

    @Override
    public String getHUDTag() {
        return "";
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        double speed1 = speed.getDouble() / 100.0;
        BlockPos pos = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY - 0.6, Globals.MC.player.posZ);
        if (Globals.MC.world.getBlockState(pos).getBlock() == Blocks.ICE || Globals.MC.world.getBlockState(pos).getBlock() == Blocks.PACKED_ICE || Globals.MC.world.getBlockState(pos).getBlock() == Blocks.FROSTED_ICE) {
            if (Globals.MC.player.isSneaking() || Globals.MC.player.moveForward == 0.0f && Globals.MC.player.moveStrafing == 0.0f) {
                return;
            }
            if (Globals.MC.player.moveForward > 0.0f && !Globals.MC.player.collidedHorizontally) {
                Globals.MC.player.setSprinting(true);
            }
            float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
            if (Globals.MC.player.onGround) {
                for (Module mod : ModuleManager.getMods()) {
                    if (!(mod instanceof AutoWalk) || mod.isEnabled() || Globals.MC.gameSettings.keyBindLeft.isKeyDown() || Globals.MC.gameSettings.keyBindRight.isKeyDown() || Globals.MC.gameSettings.keyBindForward.isKeyDown() || Globals.MC.gameSettings.keyBindBack.isKeyDown()) continue;
                    Globals.MC.player.motionX = 0.0;
                    Globals.MC.player.motionZ = 0.0;
                }
                if (Globals.MC.gameSettings.keyBindBack.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
                    Globals.MC.player.motionX = 0.0;
                    Globals.MC.player.motionZ = 0.0;
                } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindRight.isKeyDown()) {
                    Globals.MC.player.motionX = 0.0;
                    Globals.MC.player.motionZ = 0.0;
                } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw - 80.8961f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw - 80.8961f) * speed1;
                } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw + 80.8961f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw + 80.8961f) * speed1;
                } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw - 90.32081f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw - 90.32081f) * speed1;
                } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown() && Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw + 90.32081f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw + 90.32081f) * speed1;
                } else if (Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw) * speed1;
                } else if (Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
                    Globals.MC.player.motionX += (double)WMath.sin(yaw) * speed1;
                    Globals.MC.player.motionZ -= (double)WMath.cos(yaw) * speed1;
                } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw - 80.1107f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw - 80.1107f) * speed1;
                } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown()) {
                    Globals.MC.player.motionX -= (double)WMath.sin(yaw + 80.1107f) * speed1;
                    Globals.MC.player.motionZ += (double)WMath.cos(yaw + 80.1107f) * speed1;
                }
            }
        }
    }
}

