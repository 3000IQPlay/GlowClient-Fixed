//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.IJumpingMount
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.inventory.IInventoryChangedListener
 *  net.minecraft.world.World
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={AbstractHorse.class})
public abstract class MixinAbstractHorse
extends EntityAnimal
implements IInventoryChangedListener,
IJumpingMount {
    public MixinAbstractHorse() {
        super((World)Globals.MC.world);
    }

    @Shadow
    protected boolean getHorseWatchableBoolean(int p_110233_1_) {
        return false;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public boolean isHorseSaddled() {
        if (HookUtils.isEntityControlActivated) {
            return true;
        }
        return this.getHorseWatchableBoolean(4);
    }
}

