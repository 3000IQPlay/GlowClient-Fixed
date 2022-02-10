//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotShulkerBox
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.utils.client.Globals;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotShulkerBox;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(value=Side.CLIENT)
@Mixin(value={SlotShulkerBox.class})
public abstract class MixinSlotShulkerBox
extends Slot {
    MixinSlotShulkerBox() {
        super(null, 0, 0, 0);
    }

    @Inject(method={"isItemValid(Lnet/minecraft/item/ItemStack;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    public void isItemValid(CallbackInfoReturnable<Boolean> ci2) {
        if (Globals.MC.player.isCreative()) {
            ci2.setReturnValue(true);
        }
    }
}

