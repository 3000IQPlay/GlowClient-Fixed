//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiDisconnected
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.resources.DefaultResourcePack
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.datasync.DataParameter
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketVehicleMove
 *  net.minecraft.util.Timer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.FMLLog
 */
package com.client.glowclient.utils.extra.pepsimod;

import com.google.common.collect.ImmutableSet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.Timer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLLog;

public class ExtraReflection {
    private static final Minecraft MC = Minecraft.getMinecraft();
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;
    public static Field sleeping;
    public static Field PLAYER_MODEL_FLAG;
    public static Field minX;
    public static Field minY;
    public static Field minZ;
    public static Field maxX;
    public static Field maxY;
    public static Field maxZ;
    public static Field y_vec3d;
    public static Field timer;
    public static Field boundingBox;
    public static Field debugFps;
    public static Field itemRenderer;
    public static Field pressed;
    public static Field ridingEntity;
    public static Field horseJumpPower;
    public static Field cPacketPlayer_x;
    public static Field cPacketPlayer_y;
    public static Field cPacketPlayer_z;
    public static Field landMovementFactor;
    public static Field inWater;
    public static Field rightClickDelayTimer;
    public static Field curBlockDamageMP;
    public static Field blockHitDelay;
    public static Field cPacketPlayer_onGround;
    public static Field parentScreen;
    public static Field DEFAULT_RESOURCE_DOMAINS;
    public static Field cPacketVehicleMove_y;
    public static Method updateFallState;
    public static Method rightClickMouse;
    private static Field modifiersField;

    public static Field getField(Class c, String ... names) {
        for (String s : names) {
            try {
                Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            }
            catch (IllegalAccessException e) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }
        throw new IllegalStateException("Field with names: " + names + " not found!");
    }

    public static Method getMethod(Class c, String[] names, Class<?> ... args) {
        for (String s : names) {
            try {
                Method m = c.getDeclaredMethod(s, args);
                m.setAccessible(true);
                return m;
            }
            catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
            }
        }
        throw new IllegalStateException("Method with names: " + names + " not found!");
    }

    public static void init() {
        try {
            renderPosX = ExtraReflection.getField(RenderManager.class, "RenderManager_renderPosX", "renderPosX", "o");
            renderPosY = ExtraReflection.getField(RenderManager.class, "RenderManager_renderPosY", "renderPosY", "p");
            renderPosZ = ExtraReflection.getField(RenderManager.class, "RenderManager_renderPosZ", "renderPosZ", "q");
            sleeping = ExtraReflection.getField(EntityPlayer.class, "sleeping", "sleeping", "bK");
            PLAYER_MODEL_FLAG = ExtraReflection.getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "PLAYER_MODEL_FLAG", "br");
            minX = ExtraReflection.getField(AxisAlignedBB.class, "minX", "minX", "a");
            minY = ExtraReflection.getField(AxisAlignedBB.class, "minY", "minY", "b");
            minZ = ExtraReflection.getField(AxisAlignedBB.class, "minZ", "minZ", "c");
            maxX = ExtraReflection.getField(AxisAlignedBB.class, "maxX", "maxX", "d");
            maxY = ExtraReflection.getField(AxisAlignedBB.class, "maxY", "maxY", "e");
            maxZ = ExtraReflection.getField(AxisAlignedBB.class, "maxZ", "maxZ", "f");
            y_vec3d = ExtraReflection.getField(Vec3d.class, "y", "y", "c");
            timer = ExtraReflection.getField(Minecraft.class, "timer", "timer", "Y");
            boundingBox = ExtraReflection.getField(Entity.class, "boundingBox", "boundingBox", "av");
            debugFps = ExtraReflection.getField(Minecraft.class, "debugFPS", "debugFPS", "ar");
            itemRenderer = ExtraReflection.getField(ItemRenderer.class, "itemRenderer", "itemRenderer", "k");
            pressed = ExtraReflection.getField(KeyBinding.class, "pressed", "pressed", "i");
            ridingEntity = ExtraReflection.getField(Entity.class, "ridingEntity", "ridingEntity", "au");
            horseJumpPower = ExtraReflection.getField(EntityPlayerSP.class, "horseJumpPower", "horseJumpPower", "cq");
            cPacketPlayer_x = ExtraReflection.getField(CPacketPlayer.class, "x", "x", "a");
            cPacketPlayer_y = ExtraReflection.getField(CPacketPlayer.class, "y", "y", "b");
            cPacketPlayer_z = ExtraReflection.getField(CPacketPlayer.class, "z", "z", "c");
            inWater = ExtraReflection.getField(Entity.class, "inWater", "inWater", "U");
            landMovementFactor = ExtraReflection.getField(EntityLivingBase.class, "landMovementFactor", "landMovementFactor", "bC");
            rightClickDelayTimer = ExtraReflection.getField(Minecraft.class, "rightClickDelayTimer", "rightClickDelayTimer", "as");
            blockHitDelay = ExtraReflection.getField(PlayerControllerMP.class, "blockHitDelay", "blockHitDelay", "g");
            curBlockDamageMP = ExtraReflection.getField(PlayerControllerMP.class, "curBlockDamageMP", "curBlockDamageMP", "e");
            cPacketPlayer_onGround = ExtraReflection.getField(CPacketPlayer.class, "onGround", "onGround", "f");
            parentScreen = ExtraReflection.getField(GuiDisconnected.class, "parentScreen", "parentScreen", "h");
            DEFAULT_RESOURCE_DOMAINS = ExtraReflection.getField(DefaultResourcePack.class, "DEFAULT_RESOURCE_DOMAINS", "DEFAULT_RESOURCE_DOMAINS", "a");
            cPacketVehicleMove_y = ExtraReflection.getField(CPacketVehicleMove.class, "y", "y", "b");
            updateFallState = ExtraReflection.getMethod(Entity.class, new String[]{"updateFallState", "updateFallState", "a"}, Double.TYPE, Boolean.TYPE, IBlockState.class, BlockPos.class);
            rightClickMouse = ExtraReflection.getMethod(Minecraft.class, new String[]{"rightClickMouse", "rightClickMouse", "aB"}, new Class[0]);
            ExtraReflection.setDEFAULT_RESOURCE_DOMAINS((Set<String>)ImmutableSet.builder().addAll((Iterable)DefaultResourcePack.DEFAULT_RESOURCE_DOMAINS).add((Object)"wdl").build());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getCPacketVehicleMove_y(CPacketVehicleMove n) {
        try {
            return (Double)cPacketVehicleMove_y.get((Object)n);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void setcPacketVehicleMove_y(CPacketVehicleMove n, double y) {
        try {
            cPacketVehicleMove_y.set((Object)n, y);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void setDEFAULT_RESOURCE_DOMAINS(Set<String> n) {
        try {
            DEFAULT_RESOURCE_DOMAINS.set(null, n);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static GuiScreen getParentScreen(GuiDisconnected disconnected) {
        try {
            return (GuiScreen)parentScreen.get((Object)disconnected);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void rightClickMouse() {
        try {
            rightClickMouse.invoke((Object)MC, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setcPacketPlayer_onGround(CPacketPlayer packet, boolean onGround) {
        try {
            cPacketPlayer_onGround.set((Object)packet, onGround);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getPressed(KeyBinding binding) {
        try {
            return (Boolean)pressed.get((Object)binding);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getCurBlockDamageMP() {
        try {
            return ((Float)curBlockDamageMP.get((Object)ExtraReflection.MC.playerController)).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCurBlockDamageMP(float val) {
        try {
            curBlockDamageMP.set((Object)ExtraReflection.MC.playerController, Float.valueOf(val));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getBlockHitDelay() {
        try {
            return (Integer)blockHitDelay.get((Object)ExtraReflection.MC.playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setBlockHitDelay(int val) {
        try {
            blockHitDelay.set((Object)ExtraReflection.MC.playerController, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setRightClickDelayTimer(int val) {
        try {
            ExtraReflection.MC.rightClickDelayTimer = val;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setInWater(Entity entity, boolean y) {
        try {
            inWater.set((Object)entity, y);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setLandMovementFactor(EntityLivingBase entity, float y) {
        try {
            landMovementFactor.set((Object)entity, Float.valueOf(y));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayer_x(CPacketPlayer packet, double x) {
        try {
            cPacketPlayer_x.set((Object)packet, x);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayer_y(CPacketPlayer packet, double y) {
        try {
            cPacketPlayer_y.set((Object)packet, y);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayer_z(CPacketPlayer packet, double z) {
        try {
            cPacketPlayer_z.set((Object)packet, z);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setHorseJumpPower(float value) {
        try {
            horseJumpPower.set((Object)ExtraReflection.MC.player, Float.valueOf(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void updateEntityFallState(Entity e, double d, boolean b, IBlockState state, BlockPos pos) {
        try {
            updateFallState.invoke((Object)e, new Object[]{d, b, state, pos});
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalStateException(exception);
        }
    }

    public static Entity getRidingEntity(Entity toGetFrom) {
        try {
            return (Entity)ridingEntity.get((Object)toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setPressed(KeyBinding keyBinding, boolean state) {
        try {
            pressed.set((Object)keyBinding, state);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static RenderItem getItemRenderer() {
        try {
            return (RenderItem)itemRenderer.get((Object)MC.getItemRenderer());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getDebugFps() {
        try {
            return (Integer)debugFps.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static AxisAlignedBB getBoundingBox(Entity entity) {
        try {
            return (AxisAlignedBB)boundingBox.get((Object)entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Timer getTimer() {
        try {
            return (Timer)timer.get((Object)MC);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setY_vec3d(Vec3d vec, double val) {
        try {
            y_vec3d.set((Object)vec, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMinX(AxisAlignedBB bb) {
        try {
            return (Double)minX.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMinY(AxisAlignedBB bb) {
        try {
            return (Double)minY.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMinZ(AxisAlignedBB bb) {
        try {
            return (Double)minZ.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMaxX(AxisAlignedBB bb) {
        try {
            return (Double)maxX.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMaxY(AxisAlignedBB bb) {
        try {
            return (Double)maxY.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getMaxZ(AxisAlignedBB bb) {
        try {
            return (Double)maxZ.get((Object)bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMinX(AxisAlignedBB bb, double val) {
        try {
            minX.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMinY(AxisAlignedBB bb, double val) {
        try {
            minY.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMinZ(AxisAlignedBB bb, double val) {
        try {
            minZ.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMaxX(AxisAlignedBB bb, double val) {
        try {
            maxX.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMaxY(AxisAlignedBB bb, double val) {
        try {
            maxY.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setMaxZ(AxisAlignedBB bb, double val) {
        try {
            maxZ.set((Object)bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter)PLAYER_MODEL_FLAG.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosX(RenderManager mgr) {
        try {
            return (Double)renderPosX.get((Object)mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosY(RenderManager mgr) {
        try {
            return (Double)renderPosY.get((Object)mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosZ(RenderManager mgr) {
        try {
            return (Double)renderPosZ.get((Object)mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosX() {
        try {
            return (Double)renderPosX.get((Object)MC.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosY() {
        try {
            return (Double)renderPosY.get((Object)MC.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosZ() {
        try {
            return (Double)renderPosZ.get((Object)MC.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getSleeping(EntityPlayer mgr) {
        try {
            return (Boolean)sleeping.get((Object)mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    static {
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

