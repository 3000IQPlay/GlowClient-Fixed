//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.CombatRules
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.kami.EntityUtil;
import com.client.glowclient.utils.mod.mods.friends.FriendManager;
import com.client.glowclient.utils.render.RenderUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class CrystalAura
extends ToggleMod {
    public static final SettingDouble range = SettingUtils.settingDouble("CrystalAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);
    public static SettingBoolean throughWalls = SettingUtils.settingBoolean("CrystalAura", "ThroughWalls", "Hit through walls", false);
    public static SettingBoolean friendDetect = SettingUtils.settingBoolean("CrystalAura", "FriendDetect", "Only attacks friends", true);
    public static SettingMode mode = SettingUtils.settingMode("CrystalAura", "Mode", "Mode of CrystalAura - Credits to 086 for Kami mode", "GlowClient", "Simple", "Kami");
    private long currentMS = 0L;
    private long lastMS = -1L;
    private BlockPos render;
    private Entity renderEnt;

    public CrystalAura() {
        super(Category.COMBAT, "CrystalAura", false, -1, "Automatically explodes crystals. Thanks 086 for Kami mode");
    }

    public boolean isVisible(Entity target) {
        return throughWalls.getBoolean() || Globals.MC.player.canEntityBeSeen(target);
    }

    @Override
    public String getHUDTag() {
        return String.format("%.1f", range.getDouble());
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Simple")) {
            try {
                double speed = 4.0;
                EntityPlayerSP player = Globals.MC.player;
                ItemStack stack = Globals.MC.player.getHeldItemMainhand();
                ItemStack stack2 = Globals.MC.player.getHeldItemOffhand();
                if (!(stack != null && (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown() || stack2 != null && (stack2.getItem() instanceof ItemFood || stack2.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown())) {
                    this.currentMS = System.nanoTime() / 1000000L;
                    if (this.hasDelayRun((long)(1000.0 / speed))) {
                        for (Entity e : Globals.MC.world.loadedEntityList) {
                            if (e == null || !((double)player.getDistance(e) < range.getDouble()) || !this.isVisible(e)) continue;
                            if (e instanceof EntityEnderCrystal) {
                                RotationSpoofing.faceEntityServer(e, this);
                                Globals.MC.playerController.attackEntity((EntityPlayer)player, e);
                                player.swingArm(EnumHand.MAIN_HAND);
                                this.lastMS = System.nanoTime() / 1000000L;
                                continue;
                            }
                            RotationSpoofing.resetSpoofedRotation(this);
                        }
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @SubscribeEvent
    public void Kami(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Kami")) {
            try {
                boolean animals = false;
                boolean mobs = false;
                boolean players = true;
                ItemStack stack = Globals.MC.player.getHeldItemMainhand();
                ItemStack stack2 = Globals.MC.player.getHeldItemOffhand();
                if (!(stack != null && (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown() || stack2 != null && (stack2.getItem() instanceof ItemFood || stack2.getItem() instanceof ItemAppleGold) && Globals.MC.gameSettings.keyBindUseItem.isKeyDown())) {
                    EnumFacing f;
                    int crystalSlot;
                    EntityEnderCrystal crystal = Globals.MC.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> (EntityEnderCrystal)entity).min(Comparator.comparing(c -> Float.valueOf(Globals.MC.player.getDistance((Entity)c)))).orElse(null);
                    if (crystal != null && (double)Globals.MC.player.getDistance((Entity)crystal) <= range.getDouble() && this.isVisible((Entity)crystal)) {
                        this.currentMS = System.nanoTime() / 1000000L;
                        if (this.hasDelayRun(250L)) {
                            this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)Globals.MC.player);
                            Globals.MC.playerController.attackEntity((EntityPlayer)Globals.MC.player, (Entity)crystal);
                            Globals.MC.player.swingArm(EnumHand.MAIN_HAND);
                            this.lastMS = System.nanoTime() / 1000000L;
                        }
                        return;
                    }
                    RotationSpoofing.resetSpoofedRotation(this);
                    int n = crystalSlot = Globals.MC.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? Globals.MC.player.inventory.currentItem : -1;
                    if (crystalSlot == -1) {
                        for (int l = 0; l < 9; ++l) {
                            if (Globals.MC.player.inventory.getStackInSlot(l).getItem() != Items.END_CRYSTAL) continue;
                            crystalSlot = -1;
                            break;
                        }
                    }
                    boolean offhand = false;
                    if (Globals.MC.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                        offhand = true;
                    } else if (crystalSlot == -1) {
                        return;
                    }
                    List<BlockPos> blocks = this.findCrystalBlocks();
                    ArrayList<Entity> entities = new ArrayList();
                    if (friendDetect.getBoolean()) {
                        if (players) {
                            entities.addAll(Globals.MC.world.playerEntities.stream().filter(entityPlayer -> !FriendManager.getFriends().isFriend(entityPlayer.getName())).collect(Collectors.toList()));
                        }
                        entities.addAll(Globals.MC.world.loadedEntityList.stream().filter(entity -> EntityUtil.isLiving(entity) && (EntityUtil.isPassive(entity) ? animals : mobs)).collect(Collectors.toList()));
                    } else {
                        if (players) {
                            entities.addAll(Globals.MC.world.playerEntities.stream().collect(Collectors.toList()));
                        }
                        entities.addAll(Globals.MC.world.loadedEntityList.stream().filter(entity -> EntityUtil.isLiving(entity) && (EntityUtil.isPassive(entity) ? animals : mobs)).collect(Collectors.toList()));
                    }
                    BlockPos q = null;
                    double damage = 0.5;
                    for (Entity entity2 : entities) {
                        if (entity2 == Globals.MC.player || ((EntityLivingBase)entity2).getHealth() <= 0.0f) continue;
                        for (BlockPos blockPos : blocks) {
                            double self;
                            double d;
                            double b = entity2.getDistanceSq(blockPos);
                            if (b >= 169.0 || !((d = CrystalAura.calculateDamage((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, entity2)) > damage) || (self = CrystalAura.calculateDamage((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, Globals.MC.player)) > d && !(d < (double)((EntityLivingBase)entity2).getHealth()) || self - 0.5 > (double)Globals.MC.player.getHealth()) continue;
                            damage = d;
                            q = blockPos;
                            this.renderEnt = entity2;
                        }
                    }
                    if (damage == 0.5) {
                        this.render = null;
                        this.renderEnt = null;
                        RotationSpoofing.resetSpoofedRotation(this);
                        return;
                    }
                    this.render = q;
                    if (!offhand && Globals.MC.player.inventory.currentItem != crystalSlot) {
                        RotationSpoofing.resetSpoofedRotation(this);
                        return;
                    }
                    this.lookAtPacket((double)q.getX() + 0.5, (double)q.getY() - 0.5, (double)q.getZ() + 0.5, (EntityPlayer)Globals.MC.player);
                    RayTraceResult result = Globals.MC.world.rayTraceBlocks(new Vec3d(Globals.MC.player.posX, Globals.MC.player.posY + (double)Globals.MC.player.getEyeHeight(), Globals.MC.player.posZ), new Vec3d((double)q.getX() + 0.5, (double)q.getY() - 0.5, (double)q.getZ() + 0.5));
                    if (result == null || result.sideHit == null) {
                        RotationSpoofing.resetSpoofedRotation(this);
                        f = EnumFacing.UP;
                    } else {
                        f = result.sideHit;
                    }
                    CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            if (this.render != null) {
                RenderUtils.drawBlockESP(this.render, 255, 255, 255, 64, 150, 150, 150, 128, 1);
                if (this.renderEnt != null) {
                    Vec3d p = EntityUtil.getInterpolatedRenderPos(this.renderEnt, Globals.MC.getRenderPartialTicks());
                    CrystalAura.drawLineFromPosToPos((double)this.render.getX() - renderPosX + 0.5, (double)this.render.getY() - renderPosY + 1.0, (double)this.render.getZ() - renderPosZ + 0.5, p.x, p.y, p.z, this.renderEnt.getEyeHeight(), 1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean hasDelayRun(long time) {
        return this.currentMS - this.lastMS >= time;
    }

    private static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)(posy2 + up), (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.enableLighting();
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        RotationSpoofing.setRotationServer((float)v[0], (float)v[1], this);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (Globals.MC.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || Globals.MC.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && Globals.MC.world.getBlockState(boost).getBlock() == Blocks.AIR && Globals.MC.world.getBlockState(boost2).getBlock() == Blocks.AIR && Globals.MC.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty();
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Globals.MC.player.posX), Math.floor(Globals.MC.player.posY), Math.floor(Globals.MC.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection)this.getSphere(CrystalAura.getPlayerPos(), (float)range.getDouble(), (int)range.getDouble(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    private List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f = y;
                    float f2 = sphere ? (float)cy + r : (float)(cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    private static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0f;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double)doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0 - distancedsize) * blockDensity;
        float damage = (int)((v * v + v) / 2.0 * 7.0 * (double)doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = CrystalAura.getBlastReduction((EntityLivingBase)entity, CrystalAura.getDamageMultiplied(damage), new Explosion((World)Globals.MC.world, null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    private static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer)entity;
            DamageSource ds = DamageSource.causeExplosionDamage((Explosion)explosion);
            damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)ep.getTotalArmorValue(), (float)((float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
            int k = EnchantmentHelper.getEnchantmentModifierDamage((Iterable)ep.getArmorInventoryList(), (DamageSource)ds);
            float f = MathHelper.clamp((float)k, (float)0.0f, (float)20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById((int)11))) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entity.getTotalArmorValue(), (float)((float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = Globals.MC.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0.0f : (diff == 2 ? 1.0f : (diff == 1 ? 0.5f : 1.5f)));
    }

    @Override
    public void onDisabled() {
        this.render = null;
        this.renderEnt = null;
        RotationSpoofing.resetSpoofedRotation(this);
    }
}

