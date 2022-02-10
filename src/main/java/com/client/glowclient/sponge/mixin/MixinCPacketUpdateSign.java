//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.INetHandlerPlayServer
 *  net.minecraft.network.play.client.CPacketUpdateSign
 *  net.minecraft.util.math.BlockPos
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={CPacketUpdateSign.class})
public abstract class MixinCPacketUpdateSign
implements Packet<INetHandlerPlayServer> {
    @Shadow
    private BlockPos pos;
    @Shadow
    private String[] lines;

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.pos = buf.readBlockPos();
        this.lines = new String[4];
        for (int i = 0; i < 4; ++i) {
            this.lines[i] = buf.readString(384);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeBlockPos(this.pos);
        for (int i = 0; i < 4; ++i) {
            if (ModuleManager.getModuleFromName("ColorSigns").isEnabled()) {
                buf.writeString(this.lines[i].replace("&", "\u00a7\u00a7\u00a7cc"));
                continue;
            }
            buf.writeString(this.lines[i]);
        }
    }
}

