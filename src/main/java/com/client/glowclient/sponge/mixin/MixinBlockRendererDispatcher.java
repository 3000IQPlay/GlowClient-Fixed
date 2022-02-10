//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.BlockFluidRenderer
 *  net.minecraft.client.renderer.BlockModelRenderer
 *  net.minecraft.client.renderer.BlockModelShapes
 *  net.minecraft.client.renderer.BlockRendererDispatcher
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.ChestRenderer
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.crash.CrashReportCategory
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumBlockRenderType
 *  net.minecraft.util.ReportedException
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.WorldType
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ChestRenderer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={BlockRendererDispatcher.class})
public abstract class MixinBlockRendererDispatcher
implements IResourceManagerReloadListener {
    //@Final
    @Shadow
    private BlockModelShapes blockModelShapes;
    //@Final
    @Shadow
    private BlockModelRenderer blockModelRenderer;
    //@Final
    @Shadow
    private ChestRenderer chestRenderer = new ChestRenderer();
    //@Final
    @Shadow
    private BlockFluidRenderer fluidRenderer;

    @Shadow
    public IBakedModel getModelForState(IBlockState state) {
        return this.blockModelShapes.getModelForState(state);
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn) {
        try {
            EnumBlockRenderType enumblockrendertype = state.getRenderType();
            if (enumblockrendertype == EnumBlockRenderType.INVISIBLE) {
                return false;
            }
            if (blockAccess.getWorldType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
                try {
                    state = state.getActualState(blockAccess, pos);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            switch (enumblockrendertype) {
                case MODEL: {
                    IBakedModel model = this.getModelForState(state);
                    state = state.getBlock().getExtendedState(state, blockAccess, pos);
                    SpongeHooks.onRenderBlock(pos, state, blockAccess, bufferBuilderIn);
                    if (HookUtils.isXrayActivated) {
                        if (state.getBlock() == Blocks.DIAMOND_ORE || state.getBlock() == Blocks.IRON_ORE || state.getBlock() == Blocks.GOLD_ORE || state.getBlock() == Blocks.EMERALD_ORE || state.getBlock() == Blocks.LAPIS_ORE) {
                            return this.blockModelRenderer.renderModel(blockAccess, model, state, pos, bufferBuilderIn, false);
                        }
                    } else {
                        return this.blockModelRenderer.renderModel(blockAccess, model, state, pos, bufferBuilderIn, true);
                    }
                }
                case ENTITYBLOCK_ANIMATED: {
                    return false;
                }
                case LIQUID: {
                    return this.fluidRenderer.renderFluid(blockAccess, state, pos, bufferBuilderIn);
                }
            }
            return false;
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport((Throwable)throwable, (String)"Tesselating block in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo((CrashReportCategory)crashreportcategory, (BlockPos)pos, (Block)state.getBlock(), (int)state.getBlock().getMetaFromState(state));
            throw new ReportedException(crashreport);
        }
    }
}

