//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.Vector3d
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemLingeringPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.utils.extra.pepsimod;

import com.client.glowclient.utils.extra.pepsimod.Default;
import com.client.glowclient.utils.extra.pepsimod.TargettingTranslator;
import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.opengl.GL11;

public class PUtils
extends Default {
    private static final Minecraft MC = Minecraft.getMinecraft();
    public static final char COLOR_ESCAPE = '\u00a7';
    public static final String[] colorCodes = new String[]{"c", "9", "f", "1", "4"};
    public static final Timer timer = new Timer();
    public static final ServerData SERVER2_DATA = new ServerData("server", "serverhere", false);
    public static final String[] CLIENT_CREATORS = new String[0];
    public static final KeyBinding[] controls = new KeyBinding[]{PUtils.MC.gameSettings.keyBindForward, PUtils.MC.gameSettings.keyBindBack, PUtils.MC.gameSettings.keyBindRight, PUtils.MC.gameSettings.keyBindLeft, PUtils.MC.gameSettings.keyBindJump, PUtils.MC.gameSettings.keyBindSneak};
    private static final Random random = new Random(System.currentTimeMillis());
    public static String buttonPrefix = "\u00a7c";
    public static Color RAINBOW_COLOR = new Color(0, 0, 0);
    public static Field block_id = null;

    public static int rand(int min, int max) {
        if (min == max) {
            return max;
        }
        return min + random.nextInt(max - min);
    }

    public static boolean rand() {
        return random.nextBoolean();
    }

    public static int ceilDiv(int x, int y) {
        return Math.floorDiv(x, y) + (x % y == 0 ? 0 : 1);
    }

    public static int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static boolean canEntityBeSeen(Entity entityIn, EntityPlayer player, TargettingTranslator.TargetBone bone) {
        return entityIn.world.rayTraceBlocks(new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ), new Vec3d(entityIn.posX, PUtils.getTargetHeight(entityIn, bone), entityIn.posZ), false, true, false) == null;
    }

    public static double getTargetHeight(Entity entity, TargettingTranslator.TargetBone bone) {
        double targetHeight = entity.posY;
        if (bone == TargettingTranslator.TargetBone.HEAD) {
            targetHeight = entity.getEyeHeight();
        } else if (bone == TargettingTranslator.TargetBone.MIDDLE) {
            targetHeight = entity.getEyeHeight() / 2.0f;
        }
        return targetHeight;
    }

    public static void setBlockIdFields() {
        try {
            if (block_id != null) {
                return;
            }
            Class<Block> clazz = Block.class;
            block_id = clazz.getDeclaredField("block id");
            Method setPepsimod_id = clazz.getDeclaredMethod("setBlock_id", new Class[0]);
            Block.REGISTRY.forEach(block -> {
                try {
                    setPepsimod_id.invoke(block, new Object[0]);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                    FMLCommonHandler.instance().exitJava(8742043, true);
                }
            });
        }
        catch (Throwable e) {
            e.printStackTrace();
            FMLCommonHandler.instance().exitJava(2349573, true);
        }
    }

    public static int getBlockId(Block block) {
        try {
            return (Integer)block_id.get((Object)block);
        }
        catch (Throwable e) {
            e.printStackTrace();
            FMLCommonHandler.instance().exitJava(97348562, true);
            return -1;
        }
    }

    public static AxisAlignedBB cloneBB(AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }

    public static Vector3d vector3d(BlockPos pos) {
        Vector3d v3d = new Vector3d();
        v3d.x = pos.getX();
        v3d.y = pos.getY();
        v3d.z = pos.getZ();
        return v3d;
    }

    public static Vector3d sub(Vector3d in, Vector3d with) {
        in.x -= with.x;
        in.y -= with.y;
        in.z -= with.z;
        return in;
    }

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(PUtils.getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
        return PUtils.getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return PUtils.getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static boolean isThrowable(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }

    public static String roundCoords(double d) {
        return String.format("%.2f", d);
    }

    public static String getFacing() {
        Entity entity = MC.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();
        String s = "Invalid";
        switch (enumfacing) {
            case NORTH: {
                s = "-Z";
                break;
            }
            case SOUTH: {
                s = "+Z";
                break;
            }
            case WEST: {
                s = "-X";
                break;
            }
            case EAST: {
                s = "+X";
            }
        }
        return s;
    }

    public static boolean isClientCreator(String uuid) {
        for (String s : CLIENT_CREATORS) {
            if (!s.equals(uuid)) continue;
            return true;
        }
        return false;
    }

    public static void renderItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            try {
                GlStateManager.translate((float)0.0f, (float)0.0f, (float)32.0f);
                PUtils.MC.getRenderItem().zLevel = 200.0f;
                MC.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
                MC.getRenderItem().renderItemOverlayIntoGUI(PUtils.MC.fontRenderer, stack, x, y, "");
                PUtils.MC.getRenderItem().zLevel = 0.0f;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    public static ItemStack getWearingArmor(int armorType) {
        return PUtils.MC.player.inventoryContainer.getSlot(5 + armorType).getStack();
    }

    public static void drawNameplateNoScale(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-viewerYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch), (float)1.0f, (float)0.0f, (float)0.0f);
        isSneaking = false;
        double distance = Math.max(1.6, (double)(MC.getRenderViewEntity().getDistance((Entity)PUtils.MC.player) / 4.0f));
        GlStateManager.scale((double)(-(distance /= 100.0)), (double)(-distance), (double)distance);
        GlStateManager.disableLighting();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(8 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 0x20FFFFFF);
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, isSneaking ? 0x20FFFFFF : -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    public static void renderFloatingText(String text, float x, float y, float z, int color, boolean renderBlackBackground, float partialTickTime) {
        RenderManager renderManager = MC.getRenderManager();
        float playerX = (float)(PUtils.MC.player.lastTickPosX + (PUtils.MC.player.posX - PUtils.MC.player.lastTickPosX) * (double)partialTickTime);
        float playerY = (float)(PUtils.MC.player.lastTickPosY + (PUtils.MC.player.posY - PUtils.MC.player.lastTickPosY) * (double)partialTickTime);
        float playerZ = (float)(PUtils.MC.player.lastTickPosZ + (PUtils.MC.player.posZ - PUtils.MC.player.lastTickPosZ) * (double)partialTickTime);
        float dx = x - playerX;
        float dy = y - playerY;
        float dz = z - playerZ;
        float distanceRatio = (float)(5.0 / PUtils.MC.player.getDistance((double)x, (double)y, (double)z));
        float scale = 0.03f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(dx *= distanceRatio), (float)(dy *= distanceRatio), (float)(dz *= distanceRatio));
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int textWidth = PUtils.MC.fontRenderer.getStringWidth(text);
        int lineHeight = 10;
        if (renderBlackBackground) {
            int stringMiddle = textWidth / 2;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            GlStateManager.disableTexture2D();
            buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos((double)(-stringMiddle - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            buffer.pos((double)(-stringMiddle - 1), (double)(8 + lineHeight - lineHeight), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            buffer.pos((double)(stringMiddle + 1), (double)(8 + lineHeight - lineHeight), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            buffer.pos((double)(stringMiddle + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        PUtils.MC.fontRenderer.drawString(text, -textWidth / 2, 0, color);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glPopMatrix();
    }

    public static void renderFloatingItemIcon(float x, float y, float z, Item item, float partialTickTime) {
        RenderManager renderManager = MC.getRenderManager();
        float playerX = (float)(PUtils.MC.player.lastTickPosX + (PUtils.MC.player.posX - PUtils.MC.player.lastTickPosX) * (double)partialTickTime);
        float playerY = (float)(PUtils.MC.player.lastTickPosY + (PUtils.MC.player.posY - PUtils.MC.player.lastTickPosY) * (double)partialTickTime);
        float playerZ = (float)(PUtils.MC.player.lastTickPosZ + (PUtils.MC.player.posZ - PUtils.MC.player.lastTickPosZ) * (double)partialTickTime);
        float dx = x - playerX;
        float dy = y - playerY;
        float dz = z - playerZ;
        float scale = 0.025f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)dx, (float)dy, (float)dz);
        GL11.glRotatef((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        PUtils.renderItemTexture(-8.0, -8.0, item, 16, 16);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glPopMatrix();
    }

    public static void renderItemTexture(double x, double y, Item item, int width, int height) {
        IBakedModel iBakedModel = MC.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(item));
        TextureAtlasSprite textureAtlasSprite = MC.getTextureMapBlocks().getAtlasSprite(iBakedModel.getParticleTexture().getIconName());
        MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        PUtils.renderTexture(x, y, textureAtlasSprite, width, height, 0.0);
    }

    private static void renderTexture(double x, double y, TextureAtlasSprite textureAtlasSprite, int width, int height, double zLevel) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x, y + (double)height, zLevel).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldrenderer.pos(x + (double)width, y + (double)height, zLevel).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldrenderer.pos(x + (double)width, y, zLevel).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV()).endVertex();
        worldrenderer.pos(x, y, zLevel).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public static int getBestTool(Block block) {
        float best = -1.0f;
        int index = -1;
        for (int i = 0; i < 9; ++i) {
            float str;
            ItemStack itemStack = PUtils.MC.player.inventory.getStackInSlot(i);
            if (itemStack == null || !((str = itemStack.getItem().getDestroySpeed(itemStack, block.getDefaultState())) > best)) continue;
            best = str;
            index = i;
        }
        return index;
    }

    public static int getArmorType(ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }

    public static boolean isAttackable(EntityLivingBase entity) {
        return entity != null && entity != PUtils.MC.player && entity.isEntityAlive();
    }

    public static EntityLivingBase getClosestEntityWithoutReachFactor() {
        EntityLivingBase closestEntity = null;
        double distance = 9999.0;
        for (Object object : PUtils.MC.world.loadedEntityList) {
            EntityLivingBase entity;
            if (!(object instanceof EntityLivingBase) || !PUtils.isAttackable(entity = (EntityLivingBase)object)) continue;
            double newDistance = PUtils.MC.player.getDistanceSq((Entity)entity);
            if (closestEntity != null) {
                if (!(distance > newDistance)) continue;
                closestEntity = entity;
                distance = newDistance;
                continue;
            }
            closestEntity = entity;
            distance = newDistance;
        }
        return closestEntity;
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
        float alpha = (float)(paramColor >> 24 & 0xFF) / 255.0f;
        float red = (float)(paramColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(paramColor >> 8 & 0xFF) / 255.0f;
        float blue = (float)(paramColor & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static Vec3d adjustVectorForBone(Vec3d vec3d, Entity entity, TargettingTranslator.TargetBone bone) {
        return vec3d;
    }
}

