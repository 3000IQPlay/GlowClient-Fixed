//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.ChunkRenderContainer
 *  net.minecraft.client.renderer.DestroyBlockProgress
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderList
 *  net.minecraft.client.renderer.VboRenderList
 *  net.minecraft.client.renderer.Vector3d
 *  net.minecraft.client.renderer.chunk.ChunkRenderDispatcher
 *  net.minecraft.client.renderer.chunk.CompiledChunk
 *  net.minecraft.client.renderer.chunk.IRenderChunkFactory
 *  net.minecraft.client.renderer.chunk.ListChunkFactory
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.client.renderer.chunk.VboChunkFactory
 *  net.minecraft.client.renderer.culling.ClippingHelper
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.client.shader.ShaderGroup
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.ClassInheritanceMultiMap
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$PooledMutableBlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IWorldEventListener
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.client.MinecraftForgeClient
 *  net.minecraftforge.common.ForgeModContainer
 *  org.lwjgl.util.vector.Vector3f
 *  org.lwjgl.util.vector.Vector4f
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.Speed;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.sponge.mixinutils.renderglobal.ContainerLocalRenderInformation;
import com.client.glowclient.sponge.mixinutils.renderglobal.ViewFrustum;
import com.client.glowclient.utils.client.Globals;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.*;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.VboRenderList;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeModContainer;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal
implements IWorldEventListener,
IResourceManagerReloadListener {
    @Shadow
    private Minecraft mc;
    @Shadow
    private TextureManager renderEngine;
    @Shadow
    private RenderManager renderManager;
    @Shadow
    private WorldClient world;
    @Shadow
    private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
    @Shadow
    private List<ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity((int)69696);
    @Shadow
    private final Set<TileEntity> setTileEntities = Sets.newHashSet();
    private ViewFrustum viewFrustum2;
    @Shadow
    private final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
    @Shadow
    private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
    @Shadow
    private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
    @Shadow
    private Framebuffer entityOutlineFramebuffer;
    @Shadow
    private ShaderGroup entityOutlineShader;
    @Shadow
    private double frustumUpdatePosX = Double.MIN_VALUE;
    @Shadow
    private double frustumUpdatePosY = Double.MIN_VALUE;
    @Shadow
    private double frustumUpdatePosZ = Double.MIN_VALUE;
    @Shadow
    private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
    @Shadow
    private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
    @Shadow
    private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
    @Shadow
    private double lastViewEntityX = Double.MIN_VALUE;
    @Shadow
    private double lastViewEntityY = Double.MIN_VALUE;
    @Shadow
    private double lastViewEntityZ = Double.MIN_VALUE;
    @Shadow
    private double lastViewEntityPitch = Double.MIN_VALUE;
    @Shadow
    private double lastViewEntityYaw = Double.MIN_VALUE;
    @Shadow
    private ChunkRenderDispatcher renderDispatcher;
    @Shadow
    private ChunkRenderContainer renderContainer;
    @Shadow
    private int renderDistanceChunks = -1;
    @Shadow
    private int renderEntitiesStartupCounter = 2;
    @Shadow
    private int countEntitiesTotal;
    @Shadow
    private int countEntitiesRendered;
    @Shadow
    private int countEntitiesHidden;
    @Shadow
    private boolean debugFixTerrainFrustum;
    @Shadow
    private ClippingHelper debugFixedClippingHelper;
    @Shadow
    private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
    @Shadow
    private final Vector3d debugTerrainFrustumPosition = new Vector3d();
    @Shadow
    private boolean vboEnabled;
    @Shadow
    IRenderChunkFactory renderChunkFactory;
    @Shadow
    private double prevRenderSortX;
    @Shadow
    private double prevRenderSortY;
    @Shadow
    private double prevRenderSortZ;
    @Shadow
    private boolean displayListEntitiesDirty = true;
    @Shadow
    private boolean entityOutlinesRendered;
    @Shadow
    private final Set<BlockPos> setLightUpdates = Sets.newHashSet();

    @Shadow
    protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
        return new Vector3f();
    }

    @Shadow
    private void fixTerrainFrustum(double x, double y, double z) {
    }

    @Shadow
    private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
        return null;
    }

    @Shadow
    protected void stopChunkUpdates() {
    }

    @Shadow
    private void generateStars() {
    }

    @Shadow
    private void generateSky() {
    }

    @Shadow
    private void generateSky2() {
    }

    @Shadow
    private void renderBlockLayer(BlockRenderLayer blockLayerIn) {
    }

    @Shadow
    private boolean isOutlineActive(Entity entityIn, Entity viewer, ICamera camera) {
        return false;
    }

    @Shadow
    protected boolean isRenderEntityOutlines() {
        return false;
    }

    @Shadow
    private void preRenderDamagedBlocks() {
    }

    @Shadow
    private void postRenderDamagedBlocks() {
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
        try {
            if (Globals.MC.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
                this.loadRenderers();
            }

            this.world.profiler.startSection("camera");
            double d0 = viewEntity.posX - this.frustumUpdatePosX;
            double d1 = viewEntity.posY - this.frustumUpdatePosY;
            double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
            if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
                this.frustumUpdatePosX = viewEntity.posX;
                this.frustumUpdatePosY = viewEntity.posY;
                this.frustumUpdatePosZ = viewEntity.posZ;
                this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
                this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
                this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
                this.viewFrustum2.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
            }

            this.world.profiler.endStartSection("renderlistcamera");
            double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
            double d4;
            if (HookUtils.isSpeedCameraActivated) {
                d4 = Speed.startY;
            } else {
                d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
            }

            double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
            this.renderContainer.initialize(d3, d4, d5);
            this.world.profiler.endStartSection("cull");
            if (this.debugFixedClippingHelper != null) {
                Frustum frustum = new Frustum(this.debugFixedClippingHelper);
                frustum.setPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
                camera = frustum;
            }

            Globals.MC.profiler.endStartSection("culling");
            BlockPos blockpos1 = new BlockPos(d3, d4 + (double)viewEntity.getEyeHeight(), d5);
            RenderChunk renderchunk = this.viewFrustum2.getRenderChunk(blockpos1);
            BlockPos blockpos = new BlockPos(MathHelper.floor(d3 / 16.0D) * 16, MathHelper.floor(d4 / 16.0D) * 16, MathHelper.floor(d5 / 16.0D) * 16);
            this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY || viewEntity.posZ != this.lastViewEntityZ || (double)viewEntity.rotationPitch != this.lastViewEntityPitch || (double)viewEntity.rotationYaw != this.lastViewEntityYaw;
            this.lastViewEntityX = viewEntity.posX;
            this.lastViewEntityY = viewEntity.posY;
            this.lastViewEntityZ = viewEntity.posZ;
            this.lastViewEntityPitch = (double)viewEntity.rotationPitch;
            this.lastViewEntityYaw = (double)viewEntity.rotationYaw;
            boolean flag = this.debugFixedClippingHelper != null;
            Globals.MC.profiler.endStartSection("update");
            ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1;
            RenderChunk renderchunk3;
            if (!flag && this.displayListEntitiesDirty) {
                this.displayListEntitiesDirty = false;
                this.renderInfos = Lists.newArrayList();
                Queue queue = Queues.newArrayDeque();
                Entity.setRenderDistanceWeight(MathHelper.clamp((double)Globals.MC.gameSettings.renderDistanceChunks / 8.0D, 1.0D, 2.5D));
                boolean flag1 = Globals.MC.renderChunksMany;
                if (renderchunk != null) {
                    boolean flag2 = false;
                    ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new ContainerLocalRenderInformation(renderchunk, (EnumFacing)null, 0);
                    Set set1 = this.getVisibleFacings(blockpos1);
                    if (set1.size() == 1) {
                        Vector3f vector3f = this.getViewVector(viewEntity, partialTicks);
                        EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
                        set1.remove(enumfacing);
                    }

                    if (set1.isEmpty()) {
                        flag2 = true;
                    }

                    if (flag2 && !playerSpectator) {
                        this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
                    } else {
                        if (playerSpectator && this.world.getBlockState(blockpos1).isOpaqueCube()) {
                            flag1 = false;
                        }

                        renderchunk.setFrameIndex(frameCount);
                        queue.add(renderglobal$containerlocalrenderinformation3);
                    }
                } else {
                    int i = blockpos1.getY() > 0 ? 248 : 8;

                    for(int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; ++j) {
                        for(int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; ++k) {
                            RenderChunk renderchunk1 = this.viewFrustum2.getRenderChunk(new BlockPos((j << 4) + 8, i, (k << 4) + 8));
                            if (renderchunk1 != null && ((ICamera)camera).isBoundingBoxInFrustum(renderchunk1.boundingBox.expand(0.0D, blockpos1.getY() > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY, 0.0D))) {
                                renderchunk1.setFrameIndex(frameCount);
                                queue.add(new ContainerLocalRenderInformation(renderchunk1, (EnumFacing)null, 0));
                            }
                        }
                    }
                }

                Globals.MC.profiler.startSection("iteration");

                while(true) {
                    if (queue.isEmpty()) {
                        Globals.MC.profiler.endSection();
                        break;
                    }

                    renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)queue.poll();
                    renderchunk3 = renderglobal$containerlocalrenderinformation1.renderChunk;
                    EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation1.facing;
                    this.renderInfos.add(renderglobal$containerlocalrenderinformation1);
                    EnumFacing[] var46 = EnumFacing.values();
                    int var48 = var46.length;

                    for(int var30 = 0; var30 < var48; ++var30) {
                        EnumFacing enumfacing1 = var46[var30];
                        RenderChunk renderchunk2 = this.getRenderChunkOffset(blockpos, renderchunk3, enumfacing1);
                        if ((!flag1 || !renderglobal$containerlocalrenderinformation1.hasDirection(enumfacing1.getOpposite())) && (!flag1 || enumfacing2 == null || renderchunk3.getCompiledChunk().isVisible(enumfacing2.getOpposite(), enumfacing1)) && renderchunk2 != null && renderchunk2.setFrameIndex(frameCount) && ((ICamera)camera).isBoundingBoxInFrustum(renderchunk2.boundingBox)) {
                            ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = new ContainerLocalRenderInformation(renderchunk2, enumfacing1, renderglobal$containerlocalrenderinformation1.counter + 1);
                            renderglobal$containerlocalrenderinformation.setDirection(renderglobal$containerlocalrenderinformation1.setFacing, enumfacing1);
                            queue.add(renderglobal$containerlocalrenderinformation);
                        }
                    }
                }
            }

            Globals.MC.profiler.endStartSection("captureFrustum");
            if (this.debugFixTerrainFrustum) {
                this.fixTerrainFrustum(d3, d4, d5);
                this.debugFixTerrainFrustum = false;
            }

            Globals.MC.profiler.endStartSection("rebuildNear");
            Set set = this.chunksToUpdate;
            this.chunksToUpdate = Sets.newLinkedHashSet();
            Iterator var37 = this.renderInfos.iterator();

            while(true) {
                while(true) {
                    do {
                        if (!var37.hasNext()) {
                            this.chunksToUpdate.addAll(set);
                            Globals.MC.profiler.endSection();
                            return;
                        }

                        renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)var37.next();
                        renderchunk3 = renderglobal$containerlocalrenderinformation1.renderChunk;
                    } while(!renderchunk3.needsUpdate() && !set.contains(renderchunk3));

                    this.displayListEntitiesDirty = true;
                    BlockPos blockpos2 = renderchunk3.getPosition().add(8, 8, 8);
                    boolean flag3 = blockpos2.distanceSq(blockpos1) < 768.0D;
                    if (!ForgeModContainer.alwaysSetupTerrainOffThread && (renderchunk3.needsImmediateUpdate() || flag3)) {
                        Globals.MC.profiler.startSection("build near");
                        this.renderDispatcher.updateChunkNow(renderchunk3);
                        renderchunk3.clearNeedsUpdate();
                        Globals.MC.profiler.endSection();
                    } else {
                        this.chunksToUpdate.add(renderchunk3);
                    }
                }
            }
        } catch (Exception var34) {
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void loadRenderers() {
        if (this.world != null) {
            Entity entity;
            if (this.renderDispatcher == null) {
                this.renderDispatcher = new ChunkRenderDispatcher();
            }
            this.displayListEntitiesDirty = true;
            Blocks.LEAVES.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
            Blocks.LEAVES2.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            boolean flag = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (flag && !this.vboEnabled) {
                this.renderContainer = new RenderList();
                this.renderChunkFactory = new ListChunkFactory();
            } else if (!flag && this.vboEnabled) {
                this.renderContainer = new VboRenderList();
                this.renderChunkFactory = new VboChunkFactory();
            }
            if (flag != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }
            if (this.viewFrustum2 != null) {
                this.viewFrustum2.deleteGlResources();
            }
            this.stopChunkUpdates();
            Set<TileEntity> set = this.setTileEntities;
            synchronized (set) {
                this.setTileEntities.clear();
            }
            this.viewFrustum2 = new ViewFrustum((World)this.world, this.mc.gameSettings.renderDistanceChunks, (RenderGlobal)RenderGlobal.class.cast(this), this.renderChunkFactory);
            if (this.world != null && (entity = this.mc.getRenderViewEntity()) != null) {
                this.viewFrustum2.updateChunkPositions(entity.posX, entity.posZ);
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public int getRenderedChunks() {
        int i = 0;
        for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
            CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
            if (compiledchunk == CompiledChunk.DUMMY || compiledchunk.isEmpty()) continue;
            ++i;
        }
        return i;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public int renderBlockLayer(BlockRenderLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
        RenderHelper.disableStandardItemLighting();
        if (blockLayerIn == BlockRenderLayer.TRANSLUCENT) {
            Globals.MC.profiler.startSection("translucent_sort");
            double d0 = entityIn.posX - this.prevRenderSortX;
            double d1 = entityIn.posY - this.prevRenderSortY;
            double d2 = entityIn.posZ - this.prevRenderSortZ;
            if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0) {
                this.prevRenderSortX = entityIn.posX;
                this.prevRenderSortY = entityIn.posY;
                this.prevRenderSortZ = entityIn.posZ;
                int k = 0;
                for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
                    if (!renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) || k++ >= 15) continue;
                    this.renderDispatcher.updateTransparencyLater(renderglobal$containerlocalrenderinformation.renderChunk);
                }
            }
            this.mc.profiler.endSection();
        }
        this.mc.profiler.startSection("filterempty");
        int l = 0;
        boolean flag = blockLayerIn == BlockRenderLayer.TRANSLUCENT;
        int i1 = flag ? this.renderInfos.size() - 1 : 0;
        int i = flag ? -1 : this.renderInfos.size();
        int j1 = flag ? -1 : 1;
        for (int j = i1; j != i; j += j1) {
            RenderChunk renderchunk = this.renderInfos.get((int)j).renderChunk;
            if (renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) continue;
            ++l;
            this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
        }
        this.mc.profiler.func_194339_b(() -> "render_" + (Object)blockLayerIn);
        this.renderBlockLayer(blockLayerIn);
        this.mc.profiler.endSection();
        return l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
        int pass = MinecraftForgeClient.getRenderPass();
        if (this.renderEntitiesStartupCounter > 0) {
            if (pass <= 0) {
                --this.renderEntitiesStartupCounter;
            }
        } else {
            double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double)partialTicks;
            double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double)partialTicks;
            double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double)partialTicks;
            this.world.profiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.prepare(this.world, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.getRenderViewEntity(), this.mc.objectMouseOver, partialTicks);
            this.renderManager.cacheActiveRenderInfo(this.world, this.mc.fontRenderer, this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, partialTicks);
            if (pass == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
            }

            Entity entity = this.mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double d4;
            if (HookUtils.isSpeedCameraActivated) {
                d4 = Speed.startY;
            } else {
                d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            }

            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            TileEntityRendererDispatcher.staticPlayerX = d3;
            TileEntityRendererDispatcher.staticPlayerY = d4;
            TileEntityRendererDispatcher.staticPlayerZ = d5;
            this.renderManager.setRenderPosition(d3, d4, d5);
            this.mc.entityRenderer.enableLightmap();
            this.world.profiler.endStartSection("global");
            List list = this.world.getLoadedEntityList();
            if (pass == 0) {
                this.countEntitiesTotal = list.size();
            }

            for(int i = 0; i < this.world.weatherEffects.size(); ++i) {
                Entity entity1 = (Entity)this.world.weatherEffects.get(i);
                if (entity1.shouldRenderInPass(pass)) {
                    ++this.countEntitiesRendered;
                    if (entity1.isInRangeToRender3d(d0, d1, d2)) {
                        this.renderManager.renderEntityStatic(entity1, partialTicks, false);
                    }
                }
            }

            this.world.profiler.endStartSection("entities");
            List list1 = Lists.newArrayList();
            List list2 = Lists.newArrayList();
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
            Iterator var22 = this.renderInfos.iterator();

            label228:
            while(true) {
                ClassInheritanceMultiMap classinheritancemultimap;
                do {
                    ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1;
                    if (!var22.hasNext()) {
                        blockpos$pooledmutableblockpos.release();
                        if (!list2.isEmpty()) {
                            var22 = list2.iterator();

                            while(var22.hasNext()) {
                                Entity entity3 = (Entity)var22.next();
                                this.renderManager.renderMultipass(entity3, partialTicks);
                            }
                        }

                        if (pass == 0 && this.isRenderEntityOutlines() && (!list1.isEmpty() || this.entityOutlinesRendered)) {
                            this.world.profiler.endStartSection("entityOutlines");
                            this.entityOutlineFramebuffer.framebufferClear();
                            this.entityOutlinesRendered = !list1.isEmpty();
                            if (!list1.isEmpty()) {
                                GlStateManager.depthFunc(519);
                                GlStateManager.disableFog();
                                this.entityOutlineFramebuffer.bindFramebuffer(false);
                                RenderHelper.disableStandardItemLighting();
                                this.renderManager.setRenderOutlines(true);

                                for(int j = 0; j < list1.size(); ++j) {
                                    this.renderManager.renderEntityStatic((Entity)list1.get(j), partialTicks, false);
                                }

                                this.renderManager.setRenderOutlines(false);
                                RenderHelper.enableStandardItemLighting();
                                GlStateManager.depthMask(false);
                                this.entityOutlineShader.render(partialTicks);
                                GlStateManager.enableLighting();
                                GlStateManager.depthMask(true);
                                GlStateManager.enableFog();
                                GlStateManager.enableBlend();
                                GlStateManager.enableColorMaterial();
                                GlStateManager.depthFunc(515);
                                GlStateManager.enableDepth();
                                GlStateManager.enableAlpha();
                            }

                            this.mc.getFramebuffer().bindFramebuffer(false);
                        }

                        this.world.profiler.endStartSection("blockentities");
                        RenderHelper.enableStandardItemLighting();
                        TileEntityRendererDispatcher.instance.preDrawBatch();
                        var22 = this.renderInfos.iterator();

                        while(true) {
                            List list3;
                            do {
                                if (!var22.hasNext()) {
                                    synchronized(this.setTileEntities) {
                                        Iterator var36 = this.setTileEntities.iterator();

                                        while(var36.hasNext()) {
                                            TileEntity tileentity = (TileEntity)var36.next();
                                            if (tileentity.shouldRenderInPass(pass) && camera.isBoundingBoxInFrustum(tileentity.getRenderBoundingBox())) {
                                                TileEntityRendererDispatcher.instance.render(tileentity, partialTicks, -1);
                                            }
                                        }
                                    }

                                    TileEntityRendererDispatcher.instance.drawBatch(pass);
                                    this.preRenderDamagedBlocks();
                                    var22 = this.damagedBlocks.values().iterator();

                                    while(var22.hasNext()) {
                                        DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)var22.next();
                                        BlockPos blockpos = destroyblockprogress.getPosition();
                                        if (this.world.getBlockState(blockpos).getBlock().hasTileEntity()) {
                                            TileEntity tileentity1 = this.world.getTileEntity(blockpos);
                                            if (tileentity1 instanceof TileEntityChest) {
                                                TileEntityChest tileentitychest = (TileEntityChest)tileentity1;
                                                if (tileentitychest.adjacentChestXNeg != null) {
                                                    blockpos = blockpos.offset(EnumFacing.WEST);
                                                    tileentity1 = this.world.getTileEntity(blockpos);
                                                } else if (tileentitychest.adjacentChestZNeg != null) {
                                                    blockpos = blockpos.offset(EnumFacing.NORTH);
                                                    tileentity1 = this.world.getTileEntity(blockpos);
                                                }
                                            }

                                            IBlockState iblockstate = this.world.getBlockState(blockpos);
                                            if (tileentity1 != null && iblockstate.hasCustomBreakingProgress()) {
                                                TileEntityRendererDispatcher.instance.render(tileentity1, partialTicks, destroyblockprogress.getPartialBlockDamage());
                                            }
                                        }
                                    }

                                    this.postRenderDamagedBlocks();
                                    this.mc.entityRenderer.disableLightmap();
                                    this.mc.profiler.endSection();
                                    return;
                                }

                                renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)var22.next();
                                list3 = renderglobal$containerlocalrenderinformation1.renderChunk.getCompiledChunk().getTileEntities();
                            } while(list3.isEmpty());

                            Iterator var41 = list3.iterator();

                            while(var41.hasNext()) {
                                TileEntity tileentity2 = (TileEntity)var41.next();
                                if (tileentity2.shouldRenderInPass(pass) && camera.isBoundingBoxInFrustum(tileentity2.getRenderBoundingBox())) {
                                    TileEntityRendererDispatcher.instance.render(tileentity2, partialTicks, -1);
                                }
                            }
                        }
                    }

                    renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)var22.next();
                    Chunk chunk = this.world.getChunk(renderglobal$containerlocalrenderinformation1.renderChunk.getPosition());
                    classinheritancemultimap = chunk.getEntityLists()[renderglobal$containerlocalrenderinformation1.renderChunk.getPosition().getY() / 16];
                } while(classinheritancemultimap.isEmpty());

                Iterator var26 = classinheritancemultimap.iterator();

                while(true) {
                    Entity entity2;
                    boolean flag1;
                    do {
                        do {
                            boolean flag;
                            do {
                                do {
                                    if (!var26.hasNext()) {
                                        continue label228;
                                    }

                                    entity2 = (Entity)var26.next();
                                } while(!entity2.shouldRenderInPass(pass));

                                flag = this.renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.isRidingOrBeingRiddenBy(this.mc.player);
                            } while(!flag);

                            flag1 = this.mc.getRenderViewEntity() instanceof EntityLivingBase ? ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping() : false;
                        } while(entity2 == this.mc.getRenderViewEntity() && this.mc.gameSettings.thirdPersonView == 0 && !flag1);
                    } while(!(entity2.posY < 0.0D) && !(entity2.posY >= 256.0D) && !this.world.isBlockLoaded(blockpos$pooledmutableblockpos.setPos(entity2)));

                    ++this.countEntitiesRendered;
                    this.renderManager.renderEntityStatic(entity2, partialTicks, false);
                    if (this.isOutlineActive(entity2, entity, camera)) {
                        list1.add(entity2);
                    }

                    if (this.renderManager.isRenderMultipass(entity2)) {
                        list2.add(entity2);
                    }
                }
            }
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public String getDebugInfoRenders() {
        int i = this.viewFrustum2.renderChunks.length;
        int j = this.getRenderedChunks();
        return String.format("C: %d/%d %sD: %d, L: %d, %s", j, i, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.setLightUpdates.size(), this.renderDispatcher == null ? "null" : this.renderDispatcher.getDebugInfo());
    }

    public RenderChunk getRenderChunkOffset(BlockPos playerPos, RenderChunk renderChunkBase, EnumFacing facing) {
        BlockPos blockpos = renderChunkBase.getBlockPosOffset16(facing);
        if (MathHelper.abs((int)(playerPos.getX() - blockpos.getX())) > this.renderDistanceChunks * 16) {
            return null;
        }
        if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
            return MathHelper.abs((int)(playerPos.getZ() - blockpos.getZ())) > this.renderDistanceChunks * 16 ? null : this.viewFrustum2.getRenderChunk(blockpos);
        }
        return null;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void markBlocksForUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean updateImmediately) {
        this.viewFrustum2.markBlocksForUpdate(minX, minY, minZ, maxX, maxY, maxZ, updateImmediately);
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth((float)2.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            if (iblockstate.getMaterial() != Material.AIR && this.world.getWorldBorder().contains(blockpos)) {
                double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d4 = HookUtils.isSpeedCameraActivated ? Speed.startY : player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)iblockstate.getSelectedBoundingBox((World)this.world, blockpos).grow((double)0.002f).offset(-d3, -d4, -d5), (float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
            }
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
}

