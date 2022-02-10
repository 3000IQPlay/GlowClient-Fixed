//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.IRangedAttackMob
 *  net.minecraft.entity.passive.AbstractChestHorse
 *  net.minecraft.entity.passive.EntityLlama
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityLlamaSpit
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EntityLlama.class})
public abstract class MixinEntityLlama
extends AbstractChestHorse
implements IRangedAttackMob {
    @Shadow
    private boolean didSpit;

    public MixinEntityLlama() {
        super(null);
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public boolean canBeSteered() {
        return HookUtils.isEntityControlActivated;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    private void spit(EntityLivingBase target) {
        EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.world, (EntityLlama)EntityLlama.class.cast((Object)this));
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0f) - entityllamaspit.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt((double)(d0 * d0 + d2 * d2)) * 0.2f;
        entityllamaspit.shoot(d0, d1 + (double)f, d2, 1.5f, 10.0f);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        this.world.spawnEntity((Entity)entityllamaspit);
        this.didSpit = true;
    }
}

