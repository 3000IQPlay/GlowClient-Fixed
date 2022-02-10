//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package com.client.glowclient.sponge.mixin;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={KeyBinding.class})
public abstract class MixinKeyBinding
implements Comparable<KeyBinding> {
    @Shadow
    private boolean pressed;

    @Overwrite
    public boolean isKeyDown() {
        return this.pressed;
    }
}

