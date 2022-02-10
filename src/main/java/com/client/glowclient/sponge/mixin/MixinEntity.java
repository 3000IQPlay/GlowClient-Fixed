//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockFenceGate
 *  net.minecraft.block.BlockWall
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.crash.CrashReportCategory
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.MoverType
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.datasync.EntityDataManager
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.ReportedException
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$PooledMutableBlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.common.capabilities.ICapabilitySerializable
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Entity.class})
public abstract class MixinEntity
implements ICommandSender,
ICapabilitySerializable<NBTTagCompound> {
    @Shadow
    public boolean preventEntitySpawning;
    @Shadow
    protected int rideCooldown;
    @Shadow
    private Entity ridingEntity;
    @Shadow
    public boolean forceSpawn;
    @Shadow
    public double prevPosX;
    @Shadow
    public double prevPosY;
    @Shadow
    public double prevPosZ;
    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public boolean onGround;
    @Shadow
    public boolean collidedHorizontally;
    @Shadow
    public boolean collidedVertically;
    @Shadow
    public boolean collided;
    @Shadow
    public boolean velocityChanged;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    private boolean isOutsideBorder;
    @Shadow
    public boolean isDead;
    @Shadow
    public float width;
    @Shadow
    public float height;
    @Shadow
    public float prevDistanceWalkedModified;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    public float fallDistance;
    @Shadow
    private int nextStepDistance;
    @Shadow
    private float nextFlap;
    @Shadow
    public double lastTickPosX;
    @Shadow
    public double lastTickPosY;
    @Shadow
    public double lastTickPosZ;
    @Shadow
    public float stepHeight;
    @Shadow
    protected Random rand;
    @Shadow
    public int ticksExisted;
    @Shadow
    private int fire;
    @Shadow
    protected boolean inWater;
    @Shadow
    public boolean ignoreFrustumCheck;
    @Shadow
    public boolean isAirBorne;
    @Shadow
    public int timeUntilPortal;
    @Shadow
    protected boolean inPortal;
    @Shadow
    protected int portalCounter;
    @Shadow
    public int dimension;
    @Shadow
    protected BlockPos lastPortalPos;
    @Shadow
    protected Vec3d lastPortalVec;
    @Shadow
    protected EnumFacing teleportDirection;
    @Shadow
    private boolean invulnerable;
    @Shadow
    protected UUID entityUniqueID;
    @Shadow
    protected String cachedUniqueIdString;
    @Shadow
    protected boolean glowing;
    @Shadow
    private double[] pistonDeltas;
    @Shadow
    private long pistonDeltasGameTime;
    @Shadow
    private AxisAlignedBB boundingBox;
    @Shadow
    public World world;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    private int entityId;
    @Shadow
    protected boolean isImmuneToFire;
    @Shadow
    public float entityCollisionReduction;
    @Shadow
    private List<Entity> riddenByEntities;
    @Shadow
    public boolean noClip;
    @Shadow
    protected EntityDataManager dataManager;

    @Shadow
    protected boolean canTriggerWalking() {
        return true;
    }

    @Shadow
    public boolean isInWater() {
        return this.inWater;
    }

    @Shadow
    public List<Entity> getPassengers() {
        return this.riddenByEntities.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.riddenByEntities);
    }

    @Shadow
    public boolean isWet() {
        if (this.inWater) {
            return true;
        }
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain((double)this.posX, (double)this.posY, (double)this.posZ);
        if (!this.world.isRainingAt((BlockPos)blockpos$pooledmutableblockpos) && !this.world.isRainingAt((BlockPos)blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + (double)this.height, this.posZ))) {
            blockpos$pooledmutableblockpos.release();
            return false;
        }
        blockpos$pooledmutableblockpos.release();
        return true;
    }

    @Shadow
    @Nullable
    public Entity getControllingPassenger() {
        return null;
    }

    @Shadow
    protected void onInsideBlock(IBlockState p_191955_1_) {
    }

    @Shadow
    public boolean isBeingRidden() {
        return !this.getPassengers().isEmpty();
    }

    @Shadow
    public boolean isRiding() {
        return this.getRidingEntity() != null;
    }

    @Shadow
    @Nullable
    public Entity getRidingEntity() {
        return this.ridingEntity;
    }

    @Shadow
    public void addVelocity(double x, double y, double z) {
        this.motionX += x;
        this.motionY += y;
        this.motionZ += z;
        this.isAirBorne = true;
    }

    @Shadow
    public AxisAlignedBB getEntityBoundingBox() {
        return this.boundingBox;
    }

    @Shadow
    public void setFire(int seconds) {
    }

    @Shadow
    public void setEntityBoundingBox(AxisAlignedBB bb) {
        this.boundingBox = bb;
    }

    @Shadow
    public void resetPositionToBB() {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0;
        this.posY = axisalignedbb.minY;
        this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0;
    }

    @Shadow
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Shadow
    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    @Shadow
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_GENERIC_SWIM;
    }

    @Shadow
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    @Shadow
    protected float playFlySound(float p_191954_1_) {
        return 0.0f;
    }

    @Shadow
    protected boolean makeFlySound() {
        return false;
    }

    @Shadow
    public void addEntityCrashInfo(CrashReportCategory category) {
    }

    @Shadow
    protected void dealFireDamage(int amount) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.IN_FIRE, amount);
        }
    }

    @Shadow
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        this.markVelocityChanged();
        return false;
    }

    @Shadow
    public boolean isEntityInvulnerable(DamageSource source) {
        return this.invulnerable && source != DamageSource.OUT_OF_WORLD && !source.isCreativePlayer();
    }

    @Shadow
    protected void markVelocityChanged() {
        this.velocityChanged = true;
    }

    @Shadow
    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        if (!this.isSilent()) {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch);
        }
    }

    @Shadow
    protected void playStepSound(BlockPos pos, Block blockIn) {
    }

    @Shadow
    public boolean isSilent() {
        return false;
    }

    @Shadow
    public boolean isSneaking() {
        return this.getFlag(1);
    }

    @Shadow
    protected boolean getFlag(int flag) {
        return true;
    }

    @Shadow
    public boolean isBurning() {
        boolean flag = this.world != null && this.world.isRemote;
        return !this.isImmuneToFire && (this.fire > 0 || flag && this.getFlag(0));
    }

    @Shadow
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }

    @Shadow
    protected int getFireImmuneTicks() {
        return 1;
    }

    @Shadow
    public boolean hasCustomName() {
        return true;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void applyEntityCollision(Entity entityIn) {
        double d1;
        double d0;
        double d2;
        if (!HookUtils.isNoPushEntitiesActivated && !entityIn.noClip && !this.noClip && (d2 = MathHelper.absMax((double)(d0 = entityIn.posX - this.posX), (double)(d1 = entityIn.posZ - this.posZ))) >= (double)0.01f) {
            d2 = MathHelper.sqrt((double)d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
                d3 = 1.0;
            }
            d0 *= d3;
            d1 *= d3;
            d0 *= (double)0.05f;
            d1 *= (double)0.05f;
            d0 *= (double)(1.0f - this.entityCollisionReduction);
            d1 *= (double)(1.0f - this.entityCollisionReduction);
            if (!this.isBeingRidden()) {
                this.addVelocity(-d0, 0.0, -d1);
            }
            if (!entityIn.isBeingRidden()) {
                entityIn.addVelocity(d0, 0.0, d1);
            }
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    protected void doBlockCollisions() {
        if (!HookUtils.isNoSlowActivated) {
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain((double)(axisalignedbb.minX + 0.001), (double)(axisalignedbb.minY + 0.001), (double)(axisalignedbb.minZ + 0.001));
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain((double)(axisalignedbb.maxX - 0.001), (double)(axisalignedbb.maxY - 0.001), (double)(axisalignedbb.maxZ - 0.001));
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();
            if (this.world.isAreaLoaded((BlockPos)blockpos$pooledmutableblockpos, (BlockPos)blockpos$pooledmutableblockpos1)) {
                for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); ++i) {
                    for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); ++j) {
                        for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); ++k) {
                            blockpos$pooledmutableblockpos2.setPos(i, j, k);
                            IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos2);
                            try {
                                iblockstate.getBlock().onEntityCollision(this.world, (BlockPos)blockpos$pooledmutableblockpos2, iblockstate, (Entity)Entity.class.cast(this));
                                this.onInsideBlock(iblockstate);
                                continue;
                            }
                            catch (Throwable throwable) {
                                CrashReport crashreport = CrashReport.makeCrashReport((Throwable)throwable, (String)"Colliding entity with block");
                                CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
                                CrashReportCategory.addBlockInfo((CrashReportCategory)crashreportcategory, (BlockPos)blockpos$pooledmutableblockpos2, (IBlockState)iblockstate);
                                throw new ReportedException(crashreport);
                            }
                        }
                    }
                }
            }
            blockpos$pooledmutableblockpos.release();
            blockpos$pooledmutableblockpos1.release();
            blockpos$pooledmutableblockpos2.release();
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void move(MoverType type, double x, double y, double z) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
        } else {
            BlockPos blockpos1;
            IBlockState iblockstate1;
            Block block1;
            boolean flag;
            if (type == MoverType.PISTON) {
                long i = this.world.getTotalWorldTime();
                if (i != this.pistonDeltasGameTime) {
                    Arrays.fill(this.pistonDeltas, 0.0);
                    this.pistonDeltasGameTime = i;
                }
                if (x != 0.0) {
                    int j = EnumFacing.Axis.X.ordinal();
                    double d0 = MathHelper.clamp((double)(x + this.pistonDeltas[j]), (double)-0.51, (double)0.51);
                    x = d0 - this.pistonDeltas[j];
                    this.pistonDeltas[j] = d0;
                    if (Math.abs(x) <= (double)1.0E-5f) {
                        return;
                    }
                } else if (y != 0.0) {
                    int l4 = EnumFacing.Axis.Y.ordinal();
                    double d12 = MathHelper.clamp((double)(y + this.pistonDeltas[l4]), (double)-0.51, (double)0.51);
                    y = d12 - this.pistonDeltas[l4];
                    this.pistonDeltas[l4] = d12;
                    if (Math.abs(y) <= (double)1.0E-5f) {
                        return;
                    }
                } else {
                    if (z == 0.0) {
                        return;
                    }
                    int i5 = EnumFacing.Axis.Z.ordinal();
                    double d13 = MathHelper.clamp((double)(z + this.pistonDeltas[i5]), (double)-0.51, (double)0.51);
                    z = d13 - this.pistonDeltas[i5];
                    this.pistonDeltas[i5] = d13;
                    if (Math.abs(z) <= (double)1.0E-5f) {
                        return;
                    }
                }
            }
            this.world.profiler.startSection("move");
            double d10 = this.posX;
            double d11 = this.posY;
            double d1 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                x *= 0.25;
                y *= (double)0.05f;
                z *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double d2 = x;
            double d3 = y;
            double d4 = z;
            if ((type == MoverType.SELF || type == MoverType.PLAYER) && this.onGround && (this.isSneaking() || HookUtils.isSafewalkActivated) && (Entity.class.cast(this) instanceof EntityPlayer || Entity.class.cast(this) instanceof AbstractHorse || Entity.class.cast(this) instanceof EntityPig)) {
                double d5 = 0.05;
                while (x != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(x, (double)(-this.stepHeight), 0.0)).isEmpty()) {
                    x = x < 0.05 && x >= -0.05 ? 0.0 : (x > 0.0 ? (x -= 0.05) : (x += 0.05));
                    d2 = x;
                }
                while (z != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(0.0, (double)(-this.stepHeight), z)).isEmpty()) {
                    z = z < 0.05 && z >= -0.05 ? 0.0 : (z > 0.0 ? (z -= 0.05) : (z += 0.05));
                    d4 = z;
                }
                while (x != 0.0 && z != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(x, (double)(-this.stepHeight), z)).isEmpty()) {
                    x = x < 0.05 && x >= -0.05 ? 0.0 : (x > 0.0 ? (x -= 0.05) : (x += 0.05));
                    d2 = x;
                    z = z < 0.05 && z >= -0.05 ? 0.0 : (z > 0.0 ? (z -= 0.05) : (z += 0.05));
                    d4 = z;
                }
            }
            List list1 = this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().expand(x, y, z));
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            if (y != 0.0) {
                int l = list1.size();
                for (int k = 0; k < l; ++k) {
                    y = ((AxisAlignedBB)list1.get(k)).calculateYOffset(this.getEntityBoundingBox(), y);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, y, 0.0));
            }
            if (x != 0.0) {
                int l5 = list1.size();
                for (int j5 = 0; j5 < l5; ++j5) {
                    x = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(this.getEntityBoundingBox(), x);
                }
                if (x != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0, 0.0));
                }
            }
            if (z != 0.0) {
                int i6 = list1.size();
                for (int k5 = 0; k5 < i6; ++k5) {
                    z = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(this.getEntityBoundingBox(), z);
                }
                if (z != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, z));
                }
            }
            boolean bl = flag = this.onGround || d3 != y && d3 < 0.0;
            if (this.stepHeight > 0.0f && flag && (d2 != x || d4 != z)) {
                double d14 = x;
                double d6 = y;
                double d7 = z;
                AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(axisalignedbb);
                y = this.stepHeight;
                List list = this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().expand(d2, y, d4));
                AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
                AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0, d4);
                double d8 = y;
                int k1 = list.size();
                for (int j1 = 0; j1 < k1; ++j1) {
                    d8 = ((AxisAlignedBB)list.get(j1)).calculateYOffset(axisalignedbb3, d8);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, d8, 0.0);
                double d18 = d2;
                int i2 = list.size();
                for (int l1 = 0; l1 < i2; ++l1) {
                    d18 = ((AxisAlignedBB)list.get(l1)).calculateXOffset(axisalignedbb2, d18);
                }
                axisalignedbb2 = axisalignedbb2.offset(d18, 0.0, 0.0);
                double d19 = d4;
                int k2 = list.size();
                for (int j2 = 0; j2 < k2; ++j2) {
                    d19 = ((AxisAlignedBB)list.get(j2)).calculateZOffset(axisalignedbb2, d19);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, 0.0, d19);
                AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
                double d20 = y;
                int i3 = list.size();
                for (int l2 = 0; l2 < i3; ++l2) {
                    d20 = ((AxisAlignedBB)list.get(l2)).calculateYOffset(axisalignedbb4, d20);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, d20, 0.0);
                double d21 = d2;
                int k3 = list.size();
                for (int j3 = 0; j3 < k3; ++j3) {
                    d21 = ((AxisAlignedBB)list.get(j3)).calculateXOffset(axisalignedbb4, d21);
                }
                axisalignedbb4 = axisalignedbb4.offset(d21, 0.0, 0.0);
                double d22 = d4;
                int i4 = list.size();
                for (int l3 = 0; l3 < i4; ++l3) {
                    d22 = ((AxisAlignedBB)list.get(l3)).calculateZOffset(axisalignedbb4, d22);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, 0.0, d22);
                double d23 = d18 * d18 + d19 * d19;
                double d9 = d21 * d21 + d22 * d22;
                if (d23 > d9) {
                    x = d18;
                    z = d19;
                    y = -d8;
                    this.setEntityBoundingBox(axisalignedbb2);
                } else {
                    x = d21;
                    z = d22;
                    y = -d20;
                    this.setEntityBoundingBox(axisalignedbb4);
                }
                int k4 = list.size();
                for (int j4 = 0; j4 < k4; ++j4) {
                    y = ((AxisAlignedBB)list.get(j4)).calculateYOffset(this.getEntityBoundingBox(), y);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, y, 0.0));
                if (d14 * d14 + d7 * d7 >= x * x + z * z) {
                    x = d14;
                    y = d6;
                    z = d7;
                    this.setEntityBoundingBox(axisalignedbb1);
                }
            }
            this.world.profiler.endSection();
            this.world.profiler.startSection("rest");
            this.resetPositionToBB();
            this.collidedHorizontally = d2 != x || d4 != z;
            this.collidedVertically = d3 != y;
            this.onGround = this.collidedVertically && d3 < 0.0;
            this.collided = this.collidedHorizontally || this.collidedVertically;
            int j6 = MathHelper.floor((double)this.posX);
            int i1 = MathHelper.floor((double)(this.posY - (double)0.2f));
            int k6 = MathHelper.floor((double)this.posZ);
            BlockPos blockpos = new BlockPos(j6, i1, k6);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            if (iblockstate.getMaterial() == Material.AIR && ((block1 = (iblockstate1 = this.world.getBlockState(blockpos1 = blockpos.down())).getBlock()) instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate)) {
                iblockstate = iblockstate1;
                blockpos = blockpos1;
            }
            this.updateFallState(y, this.onGround, iblockstate, blockpos);
            if (d2 != x) {
                this.motionX = 0.0;
            }
            if (d4 != z) {
                this.motionZ = 0.0;
            }
            Block block = iblockstate.getBlock();
            if (d3 != y) {
                block.onLanded(this.world, (Entity)Entity.class.cast(this));
            }
            if (!(!this.canTriggerWalking() || this.onGround && this.isSneaking() && Entity.class.cast(this) instanceof EntityPlayer || this.isRiding())) {
                double d15 = this.posX - d10;
                double d16 = this.posY - d11;
                double d17 = this.posZ - d1;
                if (block != Blocks.LADDER) {
                    d16 = 0.0;
                }
                if (block != null && this.onGround) {
                    block.onEntityWalk(this.world, blockpos, (Entity)Entity.class.cast(this));
                }
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt((double)(d15 * d15 + d17 * d17)) * 0.6);
                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt((double)(d15 * d15 + d16 * d16 + d17 * d17)) * 0.6);
                if (this.distanceWalkedOnStepModified > (float)this.nextStepDistance && iblockstate.getMaterial() != Material.AIR) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : (Entity)Entity.class.cast(this);
                        float f = entity == Entity.class.cast(this) ? 0.35f : 0.4f;
                        float f1 = MathHelper.sqrt((double)(entity.motionX * entity.motionX * (double)0.2f + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * (double)0.2f)) * f;
                        if (f1 > 1.0f) {
                            f1 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), f1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    } else {
                        this.playStepSound(blockpos, block);
                    }
                } else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && iblockstate.getMaterial() == Material.AIR) {
                    this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
                }
            }
            try {
                this.doBlockCollisions();
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport((Throwable)throwable, (String)"Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }
            boolean flag1 = this.isWet();
            if (this.world.isFlammableWithin(this.getEntityBoundingBox().shrink(0.001))) {
                this.dealFireDamage(1);
                if (!flag1) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = -this.getFireImmuneTicks();
            }
            if (flag1 && this.isBurning()) {
                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.getFireImmuneTicks();
            }
            this.world.profiler.endSection();
        }
    }
}

