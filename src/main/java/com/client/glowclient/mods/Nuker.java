//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.AutoTool;
import com.client.glowclient.mods.FastBreak;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.BlockUtils;
import com.client.glowclient.utils.extra.wurst.RotationUtils;
import com.client.glowclient.utils.extra.wurst.WBlock;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import com.client.glowclient.utils.mc.console.Console;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Nuker
extends ToggleMod {
    public static final SettingDouble range = SettingUtils.settingDouble("Nuker", "Range", "How far you nuke blocks", 4.5, 0.5, 1.0, 10.0);
    public static SettingBoolean flat = SettingUtils.settingBoolean("Nuker", "Flat", "Nukes blocks above your y level", true);
    public static SettingBoolean selective = SettingUtils.settingBoolean("Nuker", "Selective", "Nukes a specific block", false);
    public static SettingBoolean silent = SettingUtils.settingBoolean("Nuker", "Silent", "Doesnt look at blocks being broken", true);
    public static boolean gayangle = false;
    public BlockPos currentBlock = null;
    private int id;
    private IBlockState block;

    public Nuker() {
        super(Category.PLAYER, "Nuker", false, -1, "Destroy blocks around you");
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("%.1f", range.getDouble());
        return mode;
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
        Globals.MC.playerController.resetBlockRemoving();
        this.currentBlock = null;
        this.block = null;
        this.id = -1;
    }

    @SubscribeEvent
    public void onClick(MouseEvent event) {
        if (event.getButton() == 1 && event.isButtonstate() && selective.getBoolean() && Globals.MC.objectMouseOver != null && Globals.MC.objectMouseOver.getBlockPos() != null && Globals.MC.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            this.id = WBlock.getId(Globals.MC.objectMouseOver.getBlockPos());
            this.block = WBlock.getState(Globals.MC.objectMouseOver.getBlockPos());
            Console.write("Now nuking " + WBlock.getName(Globals.MC.world.getBlockState(Globals.MC.objectMouseOver.getBlockPos()).getBlock()) + "(" + WBlock.getId(Globals.MC.objectMouseOver.getBlockPos()) + ")");
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onUpdate(PlayerUpdateEvent event) {
        this.currentBlock = null;
        Iterable<BlockPos> validBlocks = selective.getBoolean() ? (flat.getBoolean() ? BlockUtils.getValidBlocksByDistance(range.getDouble(), false, pos -> (double)pos.getY() >= WMinecraft.getPlayer().posY && this.id == WBlock.getId(pos)) : BlockUtils.getValidBlocksByDistance(range.getDouble(), false, pos -> this.id == WBlock.getId(pos))) : (flat.getBoolean() ? BlockUtils.getValidBlocksByDistance(range.getDouble(), false, pos -> (double)pos.getY() >= WMinecraft.getPlayer().posY) : BlockUtils.getValidBlocksByDistance(range.getDouble(), false, pos -> WBlock.getState(pos) != Blocks.BEDROCK.getBlockState()));
        if (WMinecraft.getPlayer().capabilities.isCreativeMode) {
            Globals.MC.playerController.resetBlockRemoving();
            Vec3d eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
            double closestDistanceSq = Double.POSITIVE_INFINITY;
            for (BlockPos pos2 : validBlocks) {
                BlockUtils.breakBlockPacketSpam(pos2);
                double currentDistanceSq = eyesPos.squareDistanceTo(new Vec3d((Vec3i)pos2));
                if (currentDistanceSq >= closestDistanceSq) continue;
                closestDistanceSq = currentDistanceSq;
                this.currentBlock = pos2;
            }
            return;
        }
        Iterator<BlockPos> iterator = validBlocks.iterator();
        if (iterator.hasNext()) {
            BlockPos pos3 = iterator.next();
            if (this.isEnabled() && pos3 != null && ModuleManager.getModuleFromName("FastBreak").isEnabled() && FastBreak.fast.getBoolean()) {
                BlockUtils.breakBlockPacketSpam(pos3);
            }
            if (this.isEnabled() && pos3 != null && ModuleManager.getModuleFromName("AutoTool").isEnabled()) {
                AutoTool.setSlot(pos3);
            }
            if (silent.getBoolean()) {
                com.client.glowclient.utils.world.block.BlockUtils.breakBlockViewServer(pos3, this);
            } else {
                RotationSpoofing.resetSpoofedRotation(this);
                com.client.glowclient.utils.world.block.BlockUtils.breakBlockViewClient(pos3);
            }
            this.currentBlock = pos3;
        }
        if (this.currentBlock == null) {
            Globals.MC.playerController.resetBlockRemoving();
            RotationSpoofing.resetSpoofedRotation(this);
        }
    }

    @Override
    public void onEnabled() {
        if (selective.getBoolean()) {
            Console.write("Right click a block to start nuking.");
        }
    }
}

