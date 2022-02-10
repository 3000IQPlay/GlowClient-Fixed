//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  net.minecraftforge.registries.IForgeRegistryEntry$Impl
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Block.class})
public abstract class MixinBlock
extends IForgeRegistryEntry.Impl<Block> {
    @Shadow
    protected int lightValue;

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Deprecated
    @Overwrite
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        MixinBlock.addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox((IBlockAccess)worldIn, pos));
        SpongeHooks.onAddCollisionBoxToList((Block)Block.class.cast((Object)this), state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
    }

    @Shadow
    protected static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
        AxisAlignedBB axisalignedbb;
        if (blockBox != Block.NULL_AABB && entityBox.intersects(axisalignedbb = blockBox.offset(pos))) {
            collidingBoxes.add(axisalignedbb);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Deprecated
    @Overwrite
    @SideOnly(value=Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);
        switch (side) {
            case DOWN: {
                if (!(axisalignedbb.minY > 0.0)) break;
                return true;
            }
            case UP: {
                if (!(axisalignedbb.maxY < 1.0)) break;
                return true;
            }
            case NORTH: {
                if (!(axisalignedbb.minZ > 0.0)) break;
                return true;
            }
            case SOUTH: {
                if (!(axisalignedbb.maxZ < 1.0)) break;
                return true;
            }
            case WEST: {
                if (!(axisalignedbb.minX > 0.0)) break;
                return true;
            }
            case EAST: {
                if (!(axisalignedbb.maxX < 1.0)) break;
                return true;
            }
        }
        if (HookUtils.isXrayActivated) {
            return true;
        }
        return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Deprecated
    @Overwrite
    public boolean isFullCube(IBlockState state) {
        return !HookUtils.isXrayActivated;
    }
}

