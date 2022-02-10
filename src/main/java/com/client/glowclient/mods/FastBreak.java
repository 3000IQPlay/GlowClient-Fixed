//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.BlockUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastBreak
extends ToggleMod {
    public static SettingBoolean delay = SettingUtils.settingBoolean("FastBreak", "DelayTimer", "Removes hit glideSpeed timer", true);
    public static SettingBoolean fast = SettingUtils.settingBoolean("FastBreak", "Fast", "Breaks blocks fast", true);

    public FastBreak() {
        super(Category.PLAYER, "FastBreak", false, -1, "Break blocks faster");
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        try {
            if (fast.getBoolean() && Globals.MC.playerController.getIsHittingBlock() && this.isEnabled() && !ModuleManager.getModuleFromName("Nuker").isEnabled() && ModuleManager.getModuleFromName("Nuker") != null) {
                BlockPos pus = Globals.MC.objectMouseOver.getBlockPos();
                BlockUtils.breakBlockPacketSpam(pus);
            }
            if (delay.getBoolean()) {
                Globals.MC.playerController.blockHitDelay = 0;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

