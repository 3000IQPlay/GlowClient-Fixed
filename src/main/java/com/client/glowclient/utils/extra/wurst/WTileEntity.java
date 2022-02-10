//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockChest$Type
 *  net.minecraft.tileentity.TileEntityChest
 */
package com.client.glowclient.utils.extra.wurst;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntityChest;

public final class WTileEntity {
    public static boolean isTrappedChest(TileEntityChest chest) {
        return chest.getChestType() == BlockChest.Type.TRAP;
    }
}

