//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.indev;

import com.client.glowclient.sponge.events.ModEvents.ChatMessageEvent;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import com.client.glowclient.utils.classes.conversion.StringConversionUtils;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.world.block.BlockUtils;
import java.awt.Robot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GlowBot
extends ToggleMod {
    public static SettingBoolean Private = SettingUtils.settingBoolean("GlowBot", "Private", "Restrict access so only friended players can use it", true);
    public static SettingString prefix = SettingUtils.settingString("GlowBot", "Prefix", "Command Prefix", "+");
    private static boolean shouldMovePitch = false;
    private static double pitch = 0.0;
    private static boolean shouldMoveYaw = false;
    private static double yaw = 0.0;
    private static boolean shouldUse = false;
    private static boolean shouldPunch = false;
    private static boolean shouldBreak = false;
    private static BlockPos pus;
    private static IBlockState pusState;
    private static boolean shouldJump;

    public GlowBot() {
        super(Category.SERVER, "GlowBot", false, -1, "WIP Multi Media bot");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    private boolean isValidSender(String playerName) {
        if (Private.getBoolean()) {
            return FriendManager.getFriends().isFriend(playerName);
        }
        return true;
    }

    @SubscribeEvent
    public void onMessage(ChatMessageEvent event) {
        if (this.isValidSender(event.getSender().getName())) {
            String pootch;
            if (event.getMessage().toLowerCase().startsWith(prefix.getString() + "help")) {
                Globals.MC.player.sendChatMessage("(Prefix = " + prefix.getString() + ") List of commands: SetYaw SetPitch Interact Punch Break");
            }
            if (this.isCommand(event, "setpitch") && StringConversionUtils.isStringDouble(pootch = event.getMessage().toLowerCase().replace(prefix.getString() + "setpitch ", ""))) {
                pitch = Double.parseDouble(pootch);
                shouldMovePitch = true;
            }
            if (this.isCommand(event, "setyaw ") && StringConversionUtils.isStringDouble(pootch = event.getMessage().toLowerCase().replace(prefix.getString() + "setyaw ", ""))) {
                yaw = Double.parseDouble(pootch);
                shouldMoveYaw = true;
            }
            if (this.isCommand(event, "interact")) {
                shouldUse = true;
            }
            if (this.isCommand(event, "punch")) {
                shouldPunch = true;
            }
            if (this.isCommand(event, "break")) {
                pus = Globals.MC.objectMouseOver.getBlockPos();
                shouldBreak = true;
            }
            if (this.isCommand(event, "jump")) {
                shouldJump = true;
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        try {
            Robot robot = new Robot();
            if (shouldMovePitch) {
                Globals.MC.player.rotationPitch = (float)pitch;
                shouldMovePitch = false;
            }
            if (shouldMoveYaw) {
                Globals.MC.player.rotationYaw = (float)yaw;
                shouldMoveYaw = false;
            }
            if (shouldUse) {
                robot.mousePress(4);
                robot.mouseRelease(4);
                shouldUse = false;
            }
            if (shouldPunch) {
                robot.mousePress(16);
                robot.mouseRelease(16);
                shouldPunch = false;
            }
            if (shouldBreak) {
                if (Globals.MC.world.getBlockState(pus).getBlock() != Blocks.AIR && Globals.MC.objectMouseOver != null && GlowBot.getDistanceToEntity((Entity)Globals.MC.player, pus) < 5.0) {
                    BlockUtils.breakBlockViewClient(pus);
                    pusState = Globals.MC.world.getBlockState(pus);
                } else {
                    shouldBreak = false;
                }
                if (pusState != Globals.MC.world.getBlockState(pus) && Globals.MC.world.getBlockState(pus).getBlock() == Blocks.AIR) {
                    shouldBreak = false;
                }
            }
            if (shouldJump) {
                if (!this.simpleTimer.isStarted()) {
                    this.simpleTimer.start();
                }
                if (this.simpleTimer.hasTimeElapsed(0.0)) {
                    robot.keyPress(32);
                }
                if (this.simpleTimer.hasTimeElapsed(100.0)) {
                    robot.keyRelease(32);
                    this.simpleTimer.stop();
                    shouldJump = false;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean isCommand(ChatMessageEvent event, String command) {
        return event.getMessage().toLowerCase().startsWith(prefix.getString() + command);
    }

    public static double getDistanceToEntity(Entity entity, BlockPos pos) {
        double deltaX = entity.posX - (double)pos.getX();
        double deltaY = entity.posY - (double)pos.getY();
        double deltaZ = entity.posZ - (double)pos.getZ();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    static {
        shouldJump = false;
    }
}

