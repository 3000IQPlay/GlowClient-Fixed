//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.extra.wurst.WPlayerController;
import com.client.glowclient.utils.mod.mods.timermod.TimerUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Scaffold
extends ToggleMod {
    public static SettingBoolean usetimer = SettingUtils.settingBoolean("Scaffold", "UseTimer", "Uses Timer", false);
    public static SettingBoolean tower = SettingUtils.settingBoolean("Scaffold", "Tower", "Go up faster", false);
    private static boolean replaceableAngleReset = false;
    public static boolean gayangle = false;
    SimpleTimer timer = new SimpleTimer();

    public Scaffold() {
        super(Category.PLAYER, "Scaffold", false, -1, "Placed blocks under player when walking");
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
        HookUtils.isSafewalkActivated = false;
        TimerUtils.resetTimerSpeed();
    }

    @SubscribeEvent
    public void onUpdate(PlayerUpdateEvent event) {
        BlockPos belowPlayer = new BlockPos((Entity)Globals.MC.player).down();
        BlockPos towerPos = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY - 0.4, Globals.MC.player.posZ);
        if (tower.getBoolean() && Globals.MC.gameSettings.keyBindJump.isKeyDown()) {
            if (Globals.MC.player.onGround) {
                Globals.MC.player.motionY = 0.42f;
            }
            if (Globals.MC.world.getBlockState(towerPos).getBlock() != Blocks.AIR && !Globals.MC.player.onGround) {
                Globals.MC.player.motionY = -1.0;
            }
        }
        HookUtils.isSafewalkActivated = true;
        if (!WBlock.getMaterial(belowPlayer).isReplaceable()) {
            TimerUtils.resetTimerSpeed();
            if (!replaceableAngleReset) {
                this.timer.start();
                replaceableAngleReset = true;
            }
            if (this.timer.hasTimeElapsed(200.0)) {
                RotationSpoofing.resetSpoofedRotation(this);
            }
            return;
        }
        replaceableAngleReset = false;
        if (Globals.MC.player.motionY == 0.0 && Globals.MC.player.motionX == 0.0 && Globals.MC.player.motionZ == 0.0) {
            RotationSpoofing.resetSpoofedRotation(this);
            return;
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Globals.MC.player.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !Block.getBlockFromItem((Item)stack.getItem()).getDefaultState().isFullBlock()) continue;
            newSlot = i;
            break;
        }
        if (newSlot == -1) {
            return;
        }
        int oldSlot = Globals.MC.player.inventory.currentItem;
        Globals.MC.player.inventory.currentItem = newSlot;
        if (ModuleManager.getModuleFromName("Nuker").isEnabled()) {
            if (!ModuleManager.getModuleFromName((String)"Nuker").isSpoofingRotation) {
                this.placeBlockScaffold(belowPlayer);
            }
        } else {
            this.placeBlockScaffold(belowPlayer);
        }
        Globals.MC.player.inventory.currentItem = oldSlot;
        if (usetimer.getBoolean()) {
            TimerUtils.setTimerSpeed(0.37f);
            Globals.MC.player.motionX *= 1.1;
            Globals.MC.player.motionZ *= 1.1;
        } else {
            TimerUtils.setTimerSpeed(1.0f);
        }
    }

    private void placeBlockScaffold(final BlockPos pos) {
        try {
            final Vec3d eyesPos = new Vec3d(Globals.MC.player.posX, Globals.MC.player.posY + Globals.MC.player.getEyeHeight(), Globals.MC.player.posZ);
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbor = pos.offset(side);
                final EnumFacing side2 = side.getOpposite();
                final Vec3d hitVec;
                if (eyesPos.squareDistanceTo(new Vec3d(pos).add(0.5, 0.5, 0.5)) < eyesPos.squareDistanceTo(new Vec3d(neighbor).add(0.5, 0.5, 0.5)) && WBlock.canBeClicked(neighbor) && eyesPos.squareDistanceTo(hitVec = new Vec3d(neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5))) <= 18.0625) {
                    RotationSpoofing.faceVectorServer(hitVec, this);
                    WPlayerController.processRightClickBlock(neighbor, side2, hitVec);
                    Utils.getNetworkManager().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    Globals.MC.rightClickDelayTimer = 4;
                }
            }
        }
        catch (Throwable t) {}
    }
}

