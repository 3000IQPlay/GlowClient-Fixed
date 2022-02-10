//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.utils.base.command.builder.CommandInjection;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiChat.class})
public abstract class MixinGuiChat
extends GuiScreen {
    @Shadow
    protected GuiTextField inputField;

    @Inject(method={"keyTyped"}, at={@At(value="HEAD")}, cancellable=true)
    public void runGivenCommand(char typedChar, int keyCode, CallbackInfo ci) {
        CommandInjection.injectKeyTyped(this.inputField, keyCode, ci);
    }
}

