//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.world.GameType
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.Reach;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={PlayerControllerMP.class})
public abstract class MixinPlayerControllerMP {
    @Shadow
    private Minecraft mc;
    @Shadow
    private GameType currentGameType = GameType.SURVIVAL;

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public float getBlockReachDistance() {
        if (HookUtils.isExtendedReachActivated) {
            return this.currentGameType.isCreative() ? (float)Reach.distance.getDouble() + 0.5f : (float)Reach.distance.getDouble();
        }
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
}

