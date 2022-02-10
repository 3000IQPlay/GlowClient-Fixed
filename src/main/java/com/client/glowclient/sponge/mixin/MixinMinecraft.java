//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.utils.Utils;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public abstract class MixinMinecraft {
    @Inject(method={"shutdown()V"}, at={@At(value="HEAD")})
    public void saveSettingsOnShutdown(CallbackInfo ci) {
        Utils.getFileManager().saveAll();
    }

    @Shadow
    public void loadWorld(@Nullable WorldClient worldClientIn) {
    }
}

