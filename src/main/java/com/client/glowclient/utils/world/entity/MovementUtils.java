//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.MovementInput
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.utils.world.entity;

import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.extra.wurst.WMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;

public class MovementUtils {
    public static void movePlayerDirectional(double speed) {
        float yaw = (float)Math.toRadians(WMinecraft.getPlayer().rotationYaw);
        if (Globals.MC.gameSettings.keyBindBack.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
            Globals.MC.player.motionX += (double)(WMath.sin(yaw) * 0.0f);
            Globals.MC.player.motionZ -= (double)(WMath.cos(yaw) * 0.0f);
        } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindRight.isKeyDown()) {
            Globals.MC.player.motionX -= (double)(WMath.sin(yaw - 80.1107f) * 0.0f);
            Globals.MC.player.motionZ += (double)(WMath.cos(yaw - 80.1107f) * 0.0f);
        } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw - 80.8961f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw - 80.8961f) * speed;
        } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw + 80.8961f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw + 80.8961f) * speed;
        } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown() && Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw - 90.32081f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw - 90.32081f) * speed;
        } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown() && Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw + 90.32081f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw + 90.32081f) * speed;
        } else if (Globals.MC.gameSettings.keyBindForward.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw) * speed;
        } else if (Globals.MC.gameSettings.keyBindBack.isKeyDown()) {
            Globals.MC.player.motionX += (double)WMath.sin(yaw) * speed;
            Globals.MC.player.motionZ -= (double)WMath.cos(yaw) * speed;
        } else if (Globals.MC.gameSettings.keyBindRight.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw - 80.1107f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw - 80.1107f) * speed;
        } else if (Globals.MC.gameSettings.keyBindLeft.isKeyDown()) {
            Globals.MC.player.motionX -= (double)WMath.sin(yaw + 80.1107f) * speed;
            Globals.MC.player.motionZ += (double)WMath.cos(yaw + 80.1107f) * speed;
        }
    }

    public static void moveEntityStrafe(double speed, Entity entity) {
        if (entity != null) {
            MovementInput movementInput = Globals.MC.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = Globals.MC.player.rotationYaw;
            if (forward == 0.0 && strafe == 0.0) {
                entity.motionX = 0.0;
                entity.motionZ = 0.0;
            } else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += (float)(forward > 0.0 ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += (float)(forward > 0.0 ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                entity.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
                entity.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
            }
        }
    }

    public static void mutliplyEntitySpeed(Entity entity, double multiplier) {
        if (entity != null) {
            entity.motionX *= multiplier;
            entity.motionZ *= multiplier;
        }
    }

    public static boolean isEntityMoving(Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityPlayer) {
                return Globals.MC.gameSettings.keyBindForward.isKeyDown() || Globals.MC.gameSettings.keyBindBack.isKeyDown() || Globals.MC.gameSettings.keyBindLeft.isKeyDown() || Globals.MC.gameSettings.keyBindRight.isKeyDown();
            }
            return entity.motionX != 0.0 || entity.motionY != 0.0 || entity.motionZ != 0.0;
        }
        return false;
    }

    public static double getEntitySpeed(Entity entity) {
        if (entity != null) {
            double distTraveledLastTickX = entity.posX - entity.prevPosX;
            double distTraveledLastTickZ = entity.posZ - entity.prevPosZ;
            double sped = MathHelper.sqrt((double)(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ));
            return sped * 20.0;
        }
        return 0.0;
    }
}

