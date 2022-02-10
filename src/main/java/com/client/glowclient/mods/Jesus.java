//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.AddCollisionBoxToListEvent;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.SimpleTimer;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Jesus
extends ToggleMod {
    public static SettingMode mode1 = SettingUtils.settingMode("Jesus", "Mode", "Mode of Jesus", "Solid", "Solid", "Dip");
    boolean gay;
    private AxisAlignedBB WATER_WALK_AA = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.99, 1.0);
    SimpleTimer timer = new SimpleTimer();

    public Jesus() {
        super(Category.MOVEMENT, "Jesus", false, -1, "Walk on solid water");
    }

    @Override
    public String getHUDTag() {
        String mode = "";
        if (mode1.getMode().equals("Dip")) {
            mode = "Dip";
        }
        if (mode1.getMode().equals("Solid")) {
            mode = "Solid";
        }
        return mode;
    }

    public void togglegay() {
        this.gay = !this.gay;
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (!this.timer.isStarted()) {
            this.timer.start();
        }
        if (mode1.getMode().equals("Solid") && !ModuleManager.getModuleFromName("Freecam").isEnabled() && EntityUtils.isInWater((Entity)Globals.MC.player) && !Globals.MC.player.isSneaking()) {
            Globals.MC.player.motionY = 0.1;
            if (Globals.MC.player.getRidingEntity() != null && !(Globals.MC.player.getRidingEntity() instanceof EntityBoat)) {
                Globals.MC.player.getRidingEntity().motionY = 0.3;
            }
        }
        if (mode1.getMode().equals("Dip") && !Globals.MC.player.isSneaking()) {
            double gaynum = this.gay ? 0.02 : -0.01;
            BlockPos pos2 = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY + 0.5, Globals.MC.player.posZ);
            BlockPos pos = new BlockPos(Globals.MC.player.posX, Globals.MC.player.posY + 0.6, Globals.MC.player.posZ);
            if (Globals.MC.world.getBlockState(pos2).getBlock() == Blocks.WATER || Globals.MC.world.getBlockState(pos2).getBlock() == Blocks.LAVA) {
                if (Globals.MC.world.getBlockState(pos).getBlock() == Blocks.WATER || Globals.MC.world.getBlockState(pos).getBlock() == Blocks.LAVA) {
                    Globals.MC.player.motionY = 0.1;
                } else {
                    Globals.MC.player.motionY = gaynum;
                    this.togglegay();
                }
            }
        }
    }

    @SubscribeEvent
    public void onAddCollisionBox(AddCollisionBoxToListEvent event) {
        if (mode1.getMode().equals("Solid") && Globals.MC.player != null && event.getBlock() instanceof BlockLiquid && (EntityUtils.isDrivenByPlayer(event.getEntity()) || EntityUtils.isLocalPlayer(event.getEntity())) && !(event.getEntity() instanceof EntityBoat) && !Globals.MC.player.isSneaking() && Globals.MC.player.fallDistance < 3.0f && !EntityUtils.isInWater((Entity)Globals.MC.player) && (EntityUtils.isAboveWater((Entity)Globals.MC.player, false) || EntityUtils.isAboveWater(Utils.getRidingEntity(), false)) && Jesus.isAboveBlock((Entity)Globals.MC.player, event.getPos())) {
            AxisAlignedBB axisalignedbb = this.WATER_WALK_AA.offset(event.getPos());
            if (event.getEntityBox().intersects(axisalignedbb)) {
                event.getCollidingBoxes().add(axisalignedbb);
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPacketSending(PacketEvent.Send event) {
        try {
            if (mode1.getMode().equals("Solid") && event.getPacket() instanceof CPacketPlayer && EntityUtils.isAboveWater((Entity)Globals.MC.player, true) && !EntityUtils.isInWater((Entity)Globals.MC.player) && !this.isAboveLand((Entity)Globals.MC.player)) {
                int ticks = Globals.MC.player.ticksExisted % 2;
                double y = ((CPacketPlayer)event.getPacket()).y;
                if (ticks == 0) {
                    ((CPacketPlayer)event.getPacket()).y = y + 0.02;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean isAboveLand(Entity entity) {
        if (entity == null) {
            return false;
        }
        double y = entity.posY - 0.01;
        for (int x = MathHelper.floor((double)entity.posX); x < MathHelper.ceil((double)entity.posX); ++x) {
            for (int z = MathHelper.floor((double)entity.posZ); z < MathHelper.ceil((double)entity.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor((double)y), z);
                if (!Globals.MC.world.getBlockState(pos).getBlock().isFullBlock(Globals.MC.world.getBlockState(pos))) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean isAboveBlock(Entity entity, BlockPos pos) {
        return entity.posY >= (double)pos.getY();
    }
}

