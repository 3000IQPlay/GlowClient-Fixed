//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiIngame
 *  net.minecraft.client.gui.ScaledResolution
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiIngame.class})
public abstract class MixinGuiIngame
extends Gui {
    @Shadow
    private Minecraft mc;

    @Inject(method={"renderPotionEffects"}, at={@At(value="HEAD")}, cancellable=true)
    public void prerenderPotionEffects(ScaledResolution resolution, CallbackInfo callbackInfo) {
        if (HookUtils.isNoEffectsHudActivated) {
            callbackInfo.cancel();
        }
    }
}

