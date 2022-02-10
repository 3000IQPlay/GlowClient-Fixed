//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.renderer.ChunkRenderContainer
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.SpongeHooks;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={ChunkRenderContainer.class})
public abstract class MixinChunkRenderContainer
implements IBlockAccess {
    @Shadow
    protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity((int)17424);

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void addRenderChunk(RenderChunk renderChunkIn, BlockRenderLayer layer) {
        SpongeHooks.onRenderChunk(renderChunkIn, layer);
        this.renderChunks.add(renderChunkIn);
    }
}

