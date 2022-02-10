//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBed
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.extra.wurst.BlockUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedAura
extends ToggleMod {
    public static final SettingDouble range = SettingUtils.settingDouble("BedAura", "Range", "Range to destroy bed", 5.0, 0.5, 0.0, 10.0);
    private double delay = 0.0;
    SimpleTimer timer = new SimpleTimer();
    private static final Minecraft MC = Minecraft.getMinecraft();
    public int itemMoveTick = 3;
    public int bedSlot = -1;
    private int itemTimer;
    private boolean shouldRestock = true;
    private static BlockUtils.BlockValidator validator = pos -> {
        IBlockState state = BedAura.MC.world.getBlockState(pos);
        return state.getBlock() instanceof BlockBed;
    };

    public BedAura() {
        super(Category.COMBAT, "BedAura", false, -1, "For Nether - Explode beds");
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("%.1f", range.getDouble());
        return mode;
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onDisabled() {
        RotationSpoofing.resetSpoofedRotation(this);
    }

    public void replaceBed(int inventoryIndex) {
        if (inventoryIndex == -1) {
            inventoryIndex = this.bedSlot;
        } else {
            this.itemMoveTick = 0;
            this.bedSlot = inventoryIndex;
        }
        if (inventoryIndex == -1) {
            return;
        }
        switch (this.itemMoveTick) {
            case 0: {
                BedAura.MC.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)BedAura.MC.player);
                break;
            }
            case 1: {
                BedAura.MC.playerController.windowClick(0, 36 + BedAura.MC.player.inventory.currentItem, 0, ClickType.PICKUP, (EntityPlayer)BedAura.MC.player);
                break;
            }
            case 2: {
                BedAura.MC.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, (EntityPlayer)BedAura.MC.player);
                this.bedSlot = -1;
            }
        }
        ++this.itemMoveTick;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (!this.timer.isStarted()) {
            this.timer.start();
        }
        if (this.timer.hasTimeElapsed(this.delay) && (BedAura.MC.player.dimension == -1 || BedAura.MC.player.dimension == 1)) {
            Iterable<BlockPos> validBlocks = BlockUtils.getValidBlocksByDistance(range.getDouble(), false, validator);
            for (BlockPos pos : validBlocks) {
                Vec3d vec = new Vec3d((Vec3i)pos);
                BlockUtils.rightClickBlockLegit(pos);
                RotationSpoofing.faceVectorServer(vec, this);
            }
        } else {
            RotationSpoofing.resetSpoofedRotation(this);
        }
        this.replaceBed(-1);
        if (this.shouldRestock && this.itemMoveTick == 3) {
            if (this.itemTimer > 0) {
                --this.itemTimer;
                return;
            }
            ItemStack hand = BedAura.MC.player.getHeldItem(EnumHand.MAIN_HAND);
            NonNullList inv = BedAura.MC.player.inventory.mainInventory;
            if (hand == null || hand.isEmpty()) {
                RotationSpoofing.resetSpoofedRotation(this);
                for (int inventoryIndex = 0; inventoryIndex < inv.size(); ++inventoryIndex) {
                    ItemStack stack;
                    if (inventoryIndex != BedAura.MC.player.inventory.currentItem && !(stack = (ItemStack)inv.get(inventoryIndex)).isEmpty() && stack.getItem() instanceof ItemBed) {
                        this.replaceBed(inventoryIndex);
                        break;
                    }
                    this.shouldRestock = false;
                }
            }
        }
    }
}

