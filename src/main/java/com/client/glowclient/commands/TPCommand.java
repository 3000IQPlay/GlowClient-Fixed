//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.server.SPacketPlayerPosLook$EnumFlags
 *  net.minecraft.util.math.MathHelper
 */
package com.client.glowclient.commands;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.command.Command;
import com.client.glowclient.utils.classes.conversion.StringConversionUtils;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.mc.console.Console;
import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;

public class TPCommand
extends Command {
    public TPCommand() {
        super("tp");
    }

    @Override
    public void execute(String cmd, String[] args) {
        if (args.length < 4) {
            Console.write("\u00a7cNot enough data given");
        }
        String x = args[1];
        String y = args[2];
        String z = args[3];
        if (Globals.MC.player != null && StringConversionUtils.isStringDouble(x) && StringConversionUtils.isStringDouble(y) && StringConversionUtils.isStringDouble(z)) {
            TPCommand.doTeleport((Entity)Globals.MC.player, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Globals.MC.player.rotationYaw, Globals.MC.player.rotationPitch);
            if (Globals.MC.player.getRidingEntity() != null) {
                TPCommand.doTeleport(Globals.MC.player.getRidingEntity(), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Globals.MC.player.rotationYaw, Globals.MC.player.rotationPitch);
            }
            Utils.getNetworkManager().sendPacket((Packet)new CPacketPlayer.Position((double)Integer.parseInt(x), (double)Integer.parseInt(y), (double)Integer.parseInt(z), true));
        }
        Console.write("\u00a7bTeleported to: " + x + ", " + y + ", " + z);
    }

    private static void doTeleport(Entity teleportingEntity, double argX, double argY, double argZ, float argYaw, float argPitch) {
        if (teleportingEntity instanceof EntityPlayerMP) {
            EnumSet<SPacketPlayerPosLook.EnumFlags> set = EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class);
            float f = argYaw;
            f = MathHelper.wrapDegrees((float)f);
            float f1 = argPitch;
            f1 = MathHelper.wrapDegrees((float)f1);
            teleportingEntity.dismountRidingEntity();
            ((EntityPlayerMP)teleportingEntity).connection.setPlayerLocation(argX, argY, argZ, f, f1, set);
            teleportingEntity.setRotationYawHead(f);
        } else {
            float f2 = MathHelper.wrapDegrees((float)argYaw);
            float f3 = MathHelper.wrapDegrees((float)argPitch);
            f3 = MathHelper.clamp((float)f3, (float)-90.0f, (float)90.0f);
            teleportingEntity.setLocationAndAngles(argX, argY, argZ, f2, f3);
            teleportingEntity.setRotationYawHead(f2);
        }
        if (!(teleportingEntity instanceof EntityLivingBase) || !((EntityLivingBase)teleportingEntity).isElytraFlying()) {
            teleportingEntity.motionY = 0.0;
            teleportingEntity.onGround = true;
        }
    }

    @Override
    public String getSuggestion(String cmd, String[] args) {
        return Command.prefix.getString() + "tp";
    }
}

