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
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LiquidSpeed
extends ToggleMod {
    public static final SettingDouble doubleS = SettingUtils.settingDouble("LiquidSpeed", "LavaSpeed", "Speed of lava travel", 1.775, 0.01, 1.0, 2.0);
    public static final SettingDouble doubleS2 = SettingUtils.settingDouble("LiquidSpeed", "WaterSpeed", "Speed of water travel", 1.1, 0.01, 1.0, 2.0);

    public LiquidSpeed() {
        super(Category.MOVEMENT, "LiquidSpeed", false, -1, "Go faster in lava/water");
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("W:%.1f,", doubleS2.getDouble());
        String mode2 = String.format("L:%.1f", doubleS.getDouble());
        return mode + mode2;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        BlockPos pos = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY + 0.4, Globals.MC.player.posZ);
        if (!ModuleManager.getModuleFromName("Freecam").isEnabled()) {
            if (Globals.MC.world.getBlockState(pos).getBlock() == Blocks.LAVA) {
                if (Globals.MC.gameSettings.keyBindForward.isKeyDown() || Globals.MC.gameSettings.keyBindRight.isKeyDown() || Globals.MC.gameSettings.keyBindLeft.isKeyDown() || Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
                    Globals.MC.player.motionX *= doubleS.getDouble();
                    Globals.MC.player.motionZ *= doubleS.getDouble();
                }
                if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                    Globals.MC.player.motionY = 0.06;
                }
                if (Globals.MC.gameSettings.keyBindSneak.isKeyDown()) {
                    Globals.MC.player.motionY = -0.14;
                }
            }
            if (Globals.MC.player.isInWater()) {
                if (Globals.MC.gameSettings.keyBindForward.isKeyDown() || Globals.MC.gameSettings.keyBindRight.isKeyDown() || Globals.MC.gameSettings.keyBindLeft.isKeyDown() || Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
                    Globals.MC.player.motionX *= doubleS2.getDouble();
                    Globals.MC.player.motionZ *= doubleS2.getDouble();
                }
                if (Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
                    Globals.MC.player.motionY *= doubleS2.getDouble() / 1.2;
                }
            }
        }
    }
}

