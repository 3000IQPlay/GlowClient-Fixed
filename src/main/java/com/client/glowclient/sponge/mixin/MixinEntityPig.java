//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EntityPig.class})
public abstract class MixinEntityPig
extends EntityAnimal {
    @Shadow
    private boolean boosting;
    @Shadow
    private int boostTime;
    @Shadow
    private int totalBoostTime;

    public MixinEntityPig() {
        super(null);
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public boolean canBeSteered() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer entityplayer = (EntityPlayer)entity;
        return HookUtils.isEntityControlActivated || entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void travel(float strafe, float vertical, float forward) {
        Entity entity;
        Entity entity2 = entity = this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
        if (this.isBeingRidden() && this.canBeSteered()) {
            this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
            this.rotationPitch = entity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                this.boosting = false;
            }
            if (this.canPassengerSteer()) {
                float f = !HookUtils.isEntityControlActivated ? (float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.225f : 0.0f;
                if (this.boosting && !HookUtils.isEntityControlActivated) {
                    f += f * 1.15f * MathHelper.sin((float)((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI));
                }
                this.setAIMoveSpeed(f);
                super.travel(0.0f, 0.0f, 1.0f);
            } else {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f1 = MathHelper.sqrt((double)(d1 * d1 + d0 * d0)) * 4.0f;
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        } else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.travel(strafe, vertical, forward);
        }
    }
}

