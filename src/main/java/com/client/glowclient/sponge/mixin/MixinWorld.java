//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.profiler.Profiler
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$PooledMutableBlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.IWorldEventListener
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(value=Side.CLIENT)
@Mixin(value={World.class})
public abstract class MixinWorld
implements IBlockAccess {
    @Shadow
    public Profiler profiler;
    @Shadow
    protected IChunkProvider chunkProvider;
    @Shadow
    protected List<IWorldEventListener> eventListeners;

    @Shadow
    protected abstract boolean isChunkLoaded(int var1, int var2, boolean var3);

    @Shadow
    public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty) {
        return this.isAreaLoaded(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius, allowEmpty);
    }

    @Shadow
    private boolean isAreaLoaded(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, boolean allowEmpty) {
        if (yEnd >= 0 && yStart < 256) {
            zStart >>= 4;
            zEnd >>= 4;
            for (int i = xStart >>= 4; i <= (xEnd >>= 4); ++i) {
                for (int j = zStart; j <= zEnd; ++j) {
                    if (this.isChunkLoaded(i, j, allowEmpty)) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Inject(method={"checkLightFor"}, at={@At(value="HEAD")}, cancellable=true)
    public void preCheckLightFor(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (HookUtils.isNoSkylightActivated) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Overwrite
    public boolean handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn) {
        int k3;
        int j2 = MathHelper.floor((double)bb.minX);
        int k2 = MathHelper.ceil((double)bb.maxX);
        int l2 = MathHelper.floor((double)bb.minY);
        int i3 = MathHelper.ceil((double)bb.maxY);
        int j3 = MathHelper.floor((double)bb.minZ);
        if (!this.isAreaLoaded(j2, l2, j3, k2, i3, k3 = MathHelper.ceil((double)bb.maxZ), true)) {
            return false;
        }
        boolean flag = false;
        Vec3d vec3d = Vec3d.ZERO;
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
        for (int l3 = j2; l3 < k2; ++l3) {
            for (int i4 = l2; i4 < i3; ++i4) {
                for (int j4 = j3; j4 < k3; ++j4) {
                    double d0;
                    blockpos$pooledmutableblockpos.setPos(l3, i4, j4);
                    IBlockState iblockstate1 = this.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
                    Block block = iblockstate1.getBlock();
                    Boolean result = block.isEntityInsideMaterial((IBlockAccess)this, (BlockPos)blockpos$pooledmutableblockpos, iblockstate1, entityIn, (double)i3, materialIn, false);
                    if (result != null && result.booleanValue()) {
                        flag = true;
                        vec3d = block.modifyAcceleration((World)World.class.cast(this), (BlockPos)blockpos$pooledmutableblockpos, entityIn, vec3d);
                        continue;
                    }
                    if (result != null && !result.booleanValue() || iblockstate1.getMaterial() != materialIn || !((double)i3 >= (d0 = (double)((float)(i4 + 1) - BlockLiquid.getLiquidHeightPercent((int)((Integer)iblockstate1.getValue((IProperty)BlockLiquid.LEVEL))))))) continue;
                    flag = true;
                    vec3d = block.modifyAcceleration((World)World.class.cast(this), (BlockPos)blockpos$pooledmutableblockpos, entityIn, vec3d);
                }
            }
        }
        blockpos$pooledmutableblockpos.release();
        if (vec3d.length() > 0.0 && entityIn.isPushedByWater() && !HookUtils.isNoPushWaterActivated) {
            vec3d = vec3d.normalize();
            double d1 = 0.014;
            entityIn.motionX += vec3d.x * 0.014;
            entityIn.motionY += vec3d.y * 0.014;
            entityIn.motionZ += vec3d.z * 0.014;
        }
        return flag;
    }
}

