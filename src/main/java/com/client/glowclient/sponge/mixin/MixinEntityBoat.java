//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.item.EntityBoat$Status
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EntityBoat.class})
public abstract class MixinEntityBoat
extends Entity {
    @Shadow
    private float momentum;
    @Shadow
    private float outOfControlTicks;
    @Shadow
    private float deltaRotation;
    @Shadow
    private int lerpSteps;
    @Shadow
    private double lerpX;
    @Shadow
    private double lerpY;
    @Shadow
    private double lerpZ;
    @Shadow
    private double lerpYaw;
    @Shadow
    private double lerpPitch;
    @Shadow
    private boolean leftInputDown;
    @Shadow
    private boolean rightInputDown;
    @Shadow
    private boolean forwardInputDown;
    @Shadow
    private boolean backInputDown;
    @Shadow
    private double waterLevel;
    @Shadow
    private float boatGlide;
    @Shadow
    private EntityBoat.Status status;
    @Shadow
    private EntityBoat.Status previousStatus;
    @Shadow
    private double lastYd;

    public MixinEntityBoat() {
        super(null);
    }

    @Shadow
    public float getWaterLevelAbove() {
        return 1.0f;
    }

    @Shadow
    public void setPaddleState(boolean left, boolean right) {
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    private void controlBoat() {
        if (this.isBeingRidden()) {
            float f = 0.0f;
            if (!HookUtils.isBoatStrafingActivated) {
                if (this.leftInputDown) {
                    this.deltaRotation += -1.0f;
                }
                if (this.rightInputDown) {
                    this.deltaRotation += 1.0f;
                }
            }
            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005f;
            }
            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += 0.04f;
            }
            if (this.backInputDown) {
                f -= 0.005f;
            }
            this.motionX += (double)(MathHelper.sin((float)(-this.rotationYaw * ((float)Math.PI / 180))) * f);
            this.motionZ += (double)(MathHelper.cos((float)(this.rotationYaw * ((float)Math.PI / 180))) * f);
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    private void updateMotion() {
        double d0 = -0.04f;
        double shit = HookUtils.isNoBoatGravityActivated ? 0.0 : (double)-0.04f;
        double d1 = shit;
        double d2 = 0.0;
        this.momentum = 0.05f;
        if (this.previousStatus == EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.IN_AIR && this.status != EntityBoat.Status.ON_LAND) {
            this.waterLevel = this.getEntityBoundingBox().minY + (double)this.height;
            this.setPosition(this.posX, (double)(this.getWaterLevelAbove() - this.height) + 0.101, this.posZ);
            this.motionY = 0.0;
            this.lastYd = 0.0;
            this.status = EntityBoat.Status.IN_WATER;
        } else {
            if (this.status == EntityBoat.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getEntityBoundingBox().minY) / (double)this.height;
                this.momentum = 0.9f;
            } else if (this.status == EntityBoat.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4;
                this.momentum = 0.9f;
            } else if (this.status == EntityBoat.Status.UNDER_WATER) {
                d2 = 0.01f;
                this.momentum = 0.45f;
            } else if (this.status == EntityBoat.Status.IN_AIR) {
                this.momentum = 0.9f;
            } else if (this.status == EntityBoat.Status.ON_LAND) {
                this.momentum = this.boatGlide;
                if (this.getControllingPassenger() instanceof EntityPlayer) {
                    this.boatGlide /= 2.0f;
                }
            }
            this.motionX *= (double)this.momentum;
            this.motionZ *= (double)this.momentum;
            this.deltaRotation *= this.momentum;
            this.motionY += d1;
            if (d2 > 0.0) {
                double d3 = 0.65;
                this.motionY += d2 * 0.06153846016296973;
                double d4 = 0.75;
                this.motionY *= 0.75;
            }
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees((float)(entityToUpdate.rotationYaw - this.rotationYaw));
        float f1 = MathHelper.clamp((float)f, (float)-105.0f, (float)105.0f);
        if (!HookUtils.isNoBoatClampingActivated) {
            entityToUpdate.prevRotationYaw += f1 - f;
            entityToUpdate.rotationYaw += f1 - f;
        }
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }
}

