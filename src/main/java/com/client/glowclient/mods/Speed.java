//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.extra.wurst.WMath;
import com.client.glowclient.utils.mod.mods.timermod.TimerUtils;
import com.client.glowclient.utils.world.block.BlockUtils;
import com.client.glowclient.utils.world.entity.MovementUtils;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Speed
extends ToggleMod {
    public static SettingMode mode = SettingUtils.settingMode("Speed", "Mode", "Speed Mode", "BunnyHop", "BunnyHop", "Strafe", "Boost", "GroundBoost", "Development");
    public static SettingBoolean useTimer = SettingUtils.settingBoolean("Speed", "UseTimer", "Uses timer to boost the player", true);
    public static SettingBoolean sprint = SettingUtils.settingBoolean("Speed", "Sprint", "Automatically sprint", true);
    public static SettingBoolean spoofCamera = SettingUtils.settingBoolean("Speed", "SpoofCam", "Spoof the camera to stop shakyness with some speed modes", true);
    public static double startY;
    private static boolean oneTime;
    private double highChainVal = 0.0;
    private double lowChainVal = 0.0;

    public Speed() {
        super(Category.MOVEMENT, "Speed", false, -1, "Move faster");
    }

    @Override
    public String getHUDTag() {
        return mode.getMode();
    }

    @SubscribeEvent
    public void Sprint(PlayerUpdateEvent event) {
        if (sprint.getBoolean() && MovementUtils.isEntityMoving((Entity)Globals.MC.player) && Globals.MC.player.getRidingEntity() == null && !Globals.MC.player.collidedHorizontally && !Globals.MC.player.isSprinting()) {
            Globals.MC.player.setSprinting(true);
        }
    }

    @SubscribeEvent
    public void BunnyHop(PlayerUpdateEvent event) {
        if (mode.getMode().equals("BunnyHop")) {
            float jump = 0.48f;
            if (MovementUtils.isEntityMoving((Entity)Globals.MC.player)) {
                if (spoofCamera.getBoolean()) {
                    HookUtils.isSpeedCameraActivated = false;
                }
                if (useTimer.getBoolean()) {
                    TimerUtils.setTimerSpeed(1.09f);
                }
                float move = Globals.MC.player.motionY < -0.05 ? 1.79f : 1.18f;
                if (Globals.MC.player.moveForward > 0.0f && !Globals.MC.player.collidedHorizontally) {
                    Globals.MC.player.setSprinting(true);
                }
                if (Globals.MC.player.onGround) {
                    Globals.MC.player.motionY += (double)jump;
                    Globals.MC.player.motionX *= (double)move;
                    Globals.MC.player.motionZ *= (double)move;
                }
            }
        }
    }

    @SubscribeEvent
    public void Strafe(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Strafe")) {
            float moveG = 1.75f;
            float move = 0.26f;
            if (useTimer.getBoolean()) {
                TimerUtils.setTimerSpeed(1.09f);
            }
            if (MovementUtils.isEntityMoving((Entity)Globals.MC.player)) {
                if (spoofCamera.getBoolean()) {
                    HookUtils.isSpeedCameraActivated = false;
                }
                if (Globals.MC.player.onGround) {
                    MovementUtils.mutliplyEntitySpeed((Entity)Globals.MC.player, moveG);
                    Globals.MC.player.motionY += 0.48;
                } else {
                    MovementUtils.moveEntityStrafe(move, (Entity)Globals.MC.player);
                }
            }
        }
    }

    @SubscribeEvent
    public void Boost(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Boost")) {
            double bounceHeight = 0.4;
            float move = 0.26f;
            if (Globals.MC.player.onGround) {
                startY = Globals.MC.player.posY;
            }
            if (MovementUtils.getEntitySpeed((Entity)Globals.MC.player) <= 1.0) {
                this.lowChainVal = 1.0;
                this.highChainVal = 1.0;
            }
            if (MovementUtils.isEntityMoving((Entity)Globals.MC.player) && !Globals.MC.player.collidedHorizontally && !BlockUtils.isBlockAboveEntitySolid((Entity)Globals.MC.player) && BlockUtils.isBlockBelowEntitySolid((Entity)Globals.MC.player)) {
                oneTime = true;
                HookUtils.isSpeedCameraActivated = spoofCamera.getBoolean() && Globals.MC.player.getRidingEntity() == null;
                Random random = new Random();
                boolean rando = random.nextBoolean();
                if (Globals.MC.player.posY >= startY + bounceHeight) {
                    Globals.MC.player.motionY = -bounceHeight;
                    this.lowChainVal += 1.0;
                    if (this.lowChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.lowChainVal == 2.0) {
                        move = 0.15f;
                    }
                    if (this.lowChainVal == 3.0) {
                        move = 0.175f;
                    }
                    if (this.lowChainVal == 4.0) {
                        move = 0.2f;
                    }
                    if (this.lowChainVal == 5.0) {
                        move = 0.225f;
                    }
                    if (this.lowChainVal == 6.0) {
                        move = 0.25f;
                    }
                    if (this.lowChainVal >= 7.0) {
                        move = 0.27895f;
                    }
                    if (useTimer.getBoolean()) {
                        TimerUtils.setTimerSpeed(1.0f);
                    }
                }
                if (Globals.MC.player.posY == startY) {
                    Globals.MC.player.motionY = bounceHeight;
                    this.highChainVal += 1.0;
                    if (this.highChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.highChainVal == 2.0) {
                        move = 0.175f;
                    }
                    if (this.highChainVal == 3.0) {
                        move = 0.325f;
                    }
                    if (this.highChainVal == 4.0) {
                        move = 0.375f;
                    }
                    if (this.highChainVal == 5.0) {
                        move = 0.4f;
                    }
                    if (this.highChainVal >= 6.0) {
                        move = 0.43395f;
                    }
                    if (useTimer.getBoolean()) {
                        if (rando) {
                            TimerUtils.setTimerSpeed(1.3f);
                        } else {
                            TimerUtils.setTimerSpeed(1.0f);
                        }
                    }
                }
                MovementUtils.moveEntityStrafe(move, (Entity)Globals.MC.player);
            } else {
                if (oneTime) {
                    Globals.MC.player.motionY = -0.1;
                    oneTime = false;
                }
                HookUtils.isSpeedCameraActivated = false;
                this.highChainVal = 0.0;
                this.lowChainVal = 0.0;
                this.speedOff();
            }
        }
    }

    @SubscribeEvent
    public void GroundBoost(PlayerUpdateEvent event) {
        if (mode.getMode().equals("GroundBoost")) {
            double bounceHeight = 0.4;
            float move = 0.26f;
            if (Globals.MC.player.onGround) {
                startY = Globals.MC.player.posY;
            }
            if (MovementUtils.getEntitySpeed((Entity)Globals.MC.player) <= 1.0) {
                this.lowChainVal = 1.0;
                this.highChainVal = 1.0;
            }
            if (MovementUtils.isEntityMoving((Entity)Globals.MC.player) && !Globals.MC.player.collidedHorizontally && !BlockUtils.isBlockAboveEntitySolid((Entity)Globals.MC.player) && BlockUtils.isBlockBelowEntitySolid((Entity)Globals.MC.player)) {
                oneTime = true;
                HookUtils.isSpeedCameraActivated = spoofCamera.getBoolean() && Globals.MC.player.getRidingEntity() == null;
                Random random = new Random();
                boolean rando = random.nextBoolean();
                if (Globals.MC.player.posY >= startY + bounceHeight) {
                    Globals.MC.player.motionY = -bounceHeight;
                    this.lowChainVal += 1.0;
                    if (this.lowChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.lowChainVal == 2.0) {
                        move = 0.175f;
                    }
                    if (this.lowChainVal == 3.0) {
                        move = 0.275f;
                    }
                    if (this.lowChainVal == 4.0) {
                        move = 0.35f;
                    }
                    if (this.lowChainVal == 5.0) {
                        move = 0.375f;
                    }
                    if (this.lowChainVal == 6.0) {
                        move = 0.4f;
                    }
                    if (this.lowChainVal == 7.0) {
                        move = 0.425f;
                    }
                    if (this.lowChainVal == 8.0) {
                        move = 0.45f;
                    }
                    if (this.lowChainVal == 9.0) {
                        move = 0.475f;
                    }
                    if (this.lowChainVal == 10.0) {
                        move = 0.5f;
                    }
                    if (this.lowChainVal == 11.0) {
                        move = 0.5f;
                    }
                    if (this.lowChainVal == 12.0) {
                        move = 0.525f;
                    }
                    if (this.lowChainVal == 13.0) {
                        move = 0.525f;
                    }
                    if (this.lowChainVal == 14.0) {
                        move = 0.535f;
                    }
                    if (this.lowChainVal == 15.0) {
                        move = 0.535f;
                    }
                    if (this.lowChainVal == 16.0) {
                        move = 0.545f;
                    }
                    if (this.lowChainVal >= 17.0) {
                        move = 0.545f;
                    }
                    if (useTimer.getBoolean()) {
                        TimerUtils.setTimerSpeed(1.0f);
                    }
                }
                if (Globals.MC.player.posY == startY) {
                    Globals.MC.player.motionY = bounceHeight;
                    this.highChainVal += 1.0;
                    if (this.highChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.highChainVal == 2.0) {
                        move = 0.175f;
                    }
                    if (this.highChainVal == 3.0) {
                        move = 0.375f;
                    }
                    if (this.highChainVal == 4.0) {
                        move = 0.6f;
                    }
                    if (this.highChainVal == 5.0) {
                        move = 0.775f;
                    }
                    if (this.highChainVal == 6.0) {
                        move = 0.825f;
                    }
                    if (this.highChainVal == 7.0) {
                        move = 0.875f;
                    }
                    if (this.highChainVal == 8.0) {
                        move = 0.925f;
                    }
                    if (this.highChainVal == 9.0) {
                        move = 0.975f;
                    }
                    if (this.highChainVal == 10.0) {
                        move = 1.05f;
                    }
                    if (this.highChainVal == 11.0) {
                        move = 1.1f;
                    }
                    if (this.highChainVal == 12.0) {
                        move = 1.1f;
                    }
                    if (this.highChainVal == 13.0) {
                        move = 1.15f;
                    }
                    if (this.highChainVal == 14.0) {
                        move = 1.15f;
                    }
                    if (this.highChainVal == 15.0) {
                        move = 1.175f;
                    }
                    if (this.highChainVal == 16.0) {
                        move = 1.175f;
                    }
                    if (this.highChainVal >= 17.0) {
                        move = 1.2f;
                    }
                    if (useTimer.getBoolean()) {
                        if (rando) {
                            TimerUtils.setTimerSpeed(1.3f);
                        } else {
                            TimerUtils.setTimerSpeed(1.0f);
                        }
                    }
                }
                MovementUtils.moveEntityStrafe(move, (Entity)Globals.MC.player);
            } else {
                if (oneTime) {
                    Globals.MC.player.motionY = -0.1;
                    oneTime = false;
                }
                HookUtils.isSpeedCameraActivated = false;
                this.highChainVal = 0.0;
                this.lowChainVal = 0.0;
                this.speedOff();
            }
        }
    }

    @SubscribeEvent
    public void Development(PlayerUpdateEvent event) {
        if (mode.getMode().equals("Development")) {
            double bounceHeight = 0.4;
            float move = 0.26f;
            if (Globals.MC.player.onGround) {
                startY = Globals.MC.player.posY;
            }
            if (MovementUtils.getEntitySpeed((Entity)Globals.MC.player) <= 1.0) {
                this.lowChainVal = 1.0;
                this.highChainVal = 1.0;
            }
            if (MovementUtils.isEntityMoving((Entity)Globals.MC.player) && !Globals.MC.player.collidedHorizontally && !BlockUtils.isBlockAboveEntitySolid((Entity)Globals.MC.player) && BlockUtils.isBlockBelowEntitySolid((Entity)Globals.MC.player)) {
                oneTime = true;
                HookUtils.isSpeedCameraActivated = spoofCamera.getBoolean() && Globals.MC.player.getRidingEntity() == null;
                Random random = new Random();
                boolean rando = random.nextBoolean();
                if (Globals.MC.player.posY >= startY + bounceHeight) {
                    Globals.MC.player.motionY = -bounceHeight;
                    this.lowChainVal += 1.0;
                    if (this.lowChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.lowChainVal == 2.0) {
                        move = 0.175f;
                    }
                    if (this.lowChainVal == 3.0) {
                        move = 0.275f;
                    }
                    if (this.lowChainVal == 4.0) {
                        move = 0.35f;
                    }
                    if (this.lowChainVal == 5.0) {
                        move = 0.375f;
                    }
                    if (this.lowChainVal == 6.0) {
                        move = 0.4f;
                    }
                    if (this.lowChainVal == 7.0) {
                        move = 0.425f;
                    }
                    if (this.lowChainVal == 8.0) {
                        move = 0.45f;
                    }
                    if (this.lowChainVal == 9.0) {
                        move = 0.475f;
                    }
                    if (this.lowChainVal == 10.0) {
                        move = 0.5f;
                    }
                    if (this.lowChainVal == 11.0) {
                        move = 0.5f;
                    }
                    if (this.lowChainVal == 12.0) {
                        move = 0.525f;
                    }
                    if (this.lowChainVal == 13.0) {
                        move = 0.525f;
                    }
                    if (this.lowChainVal == 14.0) {
                        move = 0.535f;
                    }
                    if (this.lowChainVal == 15.0) {
                        move = 0.535f;
                    }
                    if (this.lowChainVal == 16.0) {
                        move = 0.545f;
                    }
                    if (this.lowChainVal >= 17.0) {
                        move = 0.545f;
                    }
                    if (useTimer.getBoolean()) {
                        TimerUtils.setTimerSpeed(1.0f);
                    }
                }
                if (Globals.MC.player.posY == startY) {
                    Globals.MC.player.motionY = bounceHeight;
                    this.highChainVal += 1.0;
                    if (this.highChainVal == 1.0) {
                        move = 0.075f;
                    }
                    if (this.highChainVal == 2.0) {
                        move = 0.175f;
                    }
                    if (this.highChainVal == 3.0) {
                        move = 0.375f;
                    }
                    if (this.highChainVal == 4.0) {
                        move = 0.6f;
                    }
                    if (this.highChainVal == 5.0) {
                        move = 0.775f;
                    }
                    if (this.highChainVal == 6.0) {
                        move = 0.825f;
                    }
                    if (this.highChainVal == 7.0) {
                        move = 0.875f;
                    }
                    if (this.highChainVal == 8.0) {
                        move = 0.925f;
                    }
                    if (this.highChainVal == 9.0) {
                        move = 0.975f;
                    }
                    if (this.highChainVal == 10.0) {
                        move = 1.05f;
                    }
                    if (this.highChainVal == 11.0) {
                        move = 1.1f;
                    }
                    if (this.highChainVal == 12.0) {
                        move = 1.1f;
                    }
                    if (this.highChainVal == 13.0) {
                        move = 1.15f;
                    }
                    if (this.highChainVal == 14.0) {
                        move = 1.15f;
                    }
                    if (this.highChainVal == 15.0) {
                        move = 1.175f;
                    }
                    if (this.highChainVal == 16.0) {
                        move = 1.175f;
                    }
                    if (this.highChainVal >= 17.0) {
                        move = 1.175f;
                    }
                    if (useTimer.getBoolean()) {
                        if (rando) {
                            TimerUtils.setTimerSpeed(1.3f);
                        } else {
                            TimerUtils.setTimerSpeed(1.0f);
                        }
                    }
                }
                MovementUtils.moveEntityStrafe(move, (Entity)Globals.MC.player);
            } else {
                if (oneTime) {
                    Globals.MC.player.motionY = -0.1;
                    oneTime = false;
                }
                HookUtils.isSpeedCameraActivated = false;
                this.highChainVal = 0.0;
                this.lowChainVal = 0.0;
                this.speedOff();
            }
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (!mode.getMode().equals("Development") || !MovementUtils.isEntityMoving((Entity)Globals.MC.player) || !(MovementUtils.getEntitySpeed((Entity)Globals.MC.player) <= 1.0) || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            // empty if block
        }
    }

    @Override
    public void onDisabled() {
        if (mode.getMode().equals("Boost") || mode.getMode().equals("GroundBoost")) {
            Globals.MC.player.motionY = -0.1;
        }
        HookUtils.isSpeedCameraActivated = false;
        this.highChainVal = 0.0;
        this.lowChainVal = 0.0;
        TimerUtils.setTimerSpeed(1.0f);
    }

    private void speedOff() {
        float yaw = (float)Math.toRadians(Globals.MC.player.rotationYaw);
        if (BlockUtils.isBlockAboveEntitySolid((Entity)Globals.MC.player)) {
            if (Globals.MC.gameSettings.keyBindForward.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown() && Globals.MC.player.onGround && !ModuleManager.getModuleFromName("TunnelSpeed").isEnabled()) {
                Globals.MC.player.motionX -= (double)WMath.sin(yaw) * 0.15;
                Globals.MC.player.motionZ += (double)WMath.cos(yaw) * 0.15;
            }
        } else if (Globals.MC.player.collidedHorizontally) {
            if (Globals.MC.gameSettings.keyBindForward.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown() && Globals.MC.player.onGround) {
                Globals.MC.player.motionX -= (double)WMath.sin(yaw) * 0.03;
                Globals.MC.player.motionZ += (double)WMath.cos(yaw) * 0.03;
            }
        } else if (!BlockUtils.isBlockBelowEntitySolid((Entity)Globals.MC.player)) {
            if (Globals.MC.gameSettings.keyBindForward.isKeyDown() && !Globals.MC.gameSettings.keyBindSneak.isKeyDown() && Globals.MC.player.onGround) {
                Globals.MC.player.motionX -= (double)WMath.sin(yaw) * 0.03;
                Globals.MC.player.motionZ += (double)WMath.cos(yaw) * 0.03;
            }
        } else {
            Globals.MC.player.motionX = 0.0;
            Globals.MC.player.motionZ = 0.0;
        }
    }

    static {
        oneTime = false;
    }
}

