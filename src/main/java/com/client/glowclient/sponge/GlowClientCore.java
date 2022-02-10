/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.FMLLog
 */
package com.client.glowclient.sponge;

import net.minecraftforge.fml.common.FMLLog;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class GlowClientCore {
    public GlowClientCore() {
        FMLLog.log.info("GlowClient SpongeForge ASM");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.glowclient.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
}

