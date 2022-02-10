//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.IJumpingMount
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemElytra
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.MovementInput
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP
extends AbstractClientPlayer {
    @Shadow
    protected Minecraft mc;
    @Shadow
    public int sprintingTicksLeft;
    @Shadow
    protected int sprintToggleTimer;
    @Shadow
    public float timeInPortal;
    @Shadow
    public float prevTimeInPortal;
    @Shadow
    private boolean handActive;
    @Shadow
    private EnumHand activeHand;
    @Shadow
    private boolean rowingBoat;
    @Shadow
    private boolean autoJumpEnabled = true;
    @Shadow
    private int autoJumpTime;
    @Shadow
    private boolean wasFallFlying;
    @Shadow
    public MovementInput movementInput;
    @Shadow
    public NetHandlerPlayClient connection;
    @Shadow
    public float renderArmYaw;
    @Shadow
    public float renderArmPitch;
    @Shadow
    public float prevRenderArmYaw;
    @Shadow
    public float prevRenderArmPitch;
    @Shadow
    private int horseJumpPowerCounter;
    @Shadow
    private float horseJumpPower;
    @Shadow
    private boolean serverSprintState;
    @Shadow
    private boolean serverSneakState;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private int positionUpdateTicks;
    @Shadow
    private boolean prevOnGround;

    public MixinEntityPlayerSP() {
        super(null, null);
    }

    @Shadow
    public boolean isRidingHorse() {
        return false;
    }

    @Shadow
    protected void sendHorseJump() {
    }

    @Shadow
    public float getHorseJumpPower() {
        return 1.0f;
    }

    @Shadow
    protected boolean isCurrentViewEntity() {
        return true;
    }

    @Shadow
    private boolean isHeadspaceFree(BlockPos pos, int height) {
        return true;
    }

    @Shadow
    private boolean isOpenBlockSpace(BlockPos pos) {
        return false;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public boolean isRowingBoat() {
        return false;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    protected boolean pushOutOfBlocks(double x, double y, double z) {
        boolean inTranslucentBlock;
        if (this.noClip || HookUtils.isNoPushBlocksActivated) {
            return false;
        }
        BlockPos blockpos = new BlockPos(x, y, z);
        double d0 = x - (double)blockpos.getX();
        double d1 = z - (double)blockpos.getZ();
        int entHeight = Math.max((int)Math.ceil(this.height), 1);
        boolean bl = inTranslucentBlock = !this.isHeadspaceFree(blockpos, entHeight);
        if (inTranslucentBlock) {
            int i = -1;
            double d2 = 9999.0;
            if (this.isHeadspaceFree(blockpos.west(), entHeight) && d0 < d2) {
                d2 = d0;
                i = 0;
            }
            if (this.isHeadspaceFree(blockpos.east(), entHeight) && 1.0 - d0 < d2) {
                d2 = 1.0 - d0;
                i = 1;
            }
            if (this.isHeadspaceFree(blockpos.north(), entHeight) && d1 < d2) {
                d2 = d1;
                i = 4;
            }
            if (this.isHeadspaceFree(blockpos.south(), entHeight) && 1.0 - d1 < d2) {
                d2 = 1.0 - d1;
                i = 5;
            }
            float f = 0.1f;
            if (i == 0) {
                this.motionX = -0.1f;
            }
            if (i == 1) {
                this.motionX = 0.1f;
            }
            if (i == 4) {
                this.motionZ = -0.1f;
            }
            if (i == 5) {
                this.motionZ = 0.1f;
            }
        }
        return false;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void onLivingUpdate() {
        ItemStack itemstack;
        boolean flag4;
        AxisAlignedBB axisalignedbb;
        PlayerSPPushOutOfBlocksEvent event;
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                if (this.mc.currentScreen instanceof GuiContainer) {
                    this.closeScreen();
                }
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            if (this.timeInPortal == 0.0f) {
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.BLOCK_PORTAL_TRIGGER, (float)(this.rand.nextFloat() * 0.4f + 0.8f)));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = false;
        } else if (this.isPotionActive(MobEffects.NAUSEA) && this.getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60) {
            this.timeInPortal += 0.006666667f;
            if (this.timeInPortal > 1.0f) {
                this.timeInPortal = 1.0f;
            }
        } else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }
        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.8f;
        boolean flag2 = this.movementInput.moveForward >= 0.8f;
        this.movementInput.updatePlayerMoveState();
        this.mc.getTutorial().handleMovement(this.movementInput);
        if (this.isHandActive() && !this.isRiding() && !HookUtils.isNoSlowActivated) {
            this.movementInput.moveStrafe *= 0.2f;
            this.movementInput.moveForward *= 0.2f;
            this.sprintToggleTimer = 0;
        }
        boolean flag3 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            flag3 = true;
            this.movementInput.jump = true;
        }
        if (!MinecraftForge.EVENT_BUS.post((Event)(event = new PlayerSPPushOutOfBlocksEvent((EntityPlayer)this, axisalignedbb = this.getEntityBoundingBox())))) {
            axisalignedbb = event.getEntityBoundingBox();
            this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + (double)this.width * 0.35);
            this.pushOutOfBlocks(this.posX - (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - (double)this.width * 0.35);
            this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - (double)this.width * 0.35);
            this.pushOutOfBlocks(this.posX + (double)this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + (double)this.width * 0.35);
        }
        boolean bl = flag4 = (float)this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
        if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= 0.8f && !this.isSprinting() && flag4 && !this.isHandActive() && !this.isPotionActive(MobEffects.BLINDNESS)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            } else {
                this.setSprinting(true);
            }
        }
        if (!this.isSprinting() && this.movementInput.moveForward >= 0.8f && flag4 && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }
        if (!HookUtils.isNoSlowActivated && this.isHandActive()) {
            this.setSprinting(false);
        }
        if (this.isSprinting() && (this.movementInput.moveForward < 0.8f || this.collidedHorizontally || !flag4)) {
            this.setSprinting(false);
        }
        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump && !flag3) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.movementInput.jump && !flag && !this.onGround && this.motionY < 0.0 && !this.isElytraFlying() && !this.capabilities.isFlying && (itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST)).getItem() == Items.ELYTRA && ItemElytra.isUsable((ItemStack)itemstack)) {
            this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_FALL_FLYING));
        }
        this.wasFallFlying = this.isElytraFlying();
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.movementInput.moveStrafe = (float)((double)this.movementInput.moveStrafe / 0.3);
                this.movementInput.moveForward = (float)((double)this.movementInput.moveForward / 0.3);
                this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
            if (this.movementInput.jump) {
                this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0f);
            }
        }
        if (this.isRidingHorse()) {
            IJumpingMount ijumpingmount = (IJumpingMount)this.getRidingEntity();
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                ijumpingmount.setJumpPower(MathHelper.floor((float)(this.getHorseJumpPower() * 100.0f)));
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            } else if (flag) {
                ++this.horseJumpPowerCounter;
                this.horseJumpPower = this.horseJumpPowerCounter < 10 ? (float)this.horseJumpPowerCounter * 0.1f : 0.8f + 2.0f / (float)(this.horseJumpPowerCounter - 9) * 0.1f;
            }
        } else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    private void onUpdateWalkingPlayer() {
        boolean flag1;
        SpongeHooks.onUpdateWalkingPlayerPre();
        boolean flag = this.isSprinting();
        if (flag != this.serverSprintState) {
            if (flag) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
            } else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = flag;
        }
        if ((flag1 = this.isSneaking()) != this.serverSneakState) {
            if (flag1) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SNEAKING));
            } else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = flag1;
        }
        if (this.isCurrentViewEntity()) {
            boolean flag3;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            double d0 = this.posX - this.lastReportedPosX;
            double d1 = axisalignedbb.minY - this.lastReportedPosY;
            double d2 = this.posZ - this.lastReportedPosZ;
            double d3 = this.rotationYaw - this.lastReportedYaw;
            double d4 = this.rotationPitch - this.lastReportedPitch;
            ++this.positionUpdateTicks;
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4 || this.positionUpdateTicks >= 20;
            boolean bl = flag3 = d3 != 0.0 || d4 != 0.0;
            if (this.isRiding()) {
                this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.motionX, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            } else if (flag2 && flag3) {
                this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.posX, axisalignedbb.minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
            } else if (flag2) {
                this.connection.sendPacket((Packet)new CPacketPlayer.Position(this.posX, axisalignedbb.minY, this.posZ, this.onGround));
            } else if (flag3) {
                this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
            } else if (this.prevOnGround != this.onGround) {
                this.connection.sendPacket((Packet)new CPacketPlayer(this.onGround));
            }
            if (flag2) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = axisalignedbb.minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }
            if (flag3) {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
            this.prevOnGround = this.onGround;
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }
        SpongeHooks.onUpdateWalkingPlayerPost();
    }
}

