//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.server.SPacketSpawnPlayer
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.AutoReconnect;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoLog
extends ToggleMod {
    public static final SettingDouble threshold = SettingUtils.settingDouble("AutoLog", "Health", "Amount before log", 5.0, 1.0, 1.0, 20.0);
    public static SettingBoolean totem = SettingUtils.settingBoolean("AutoLog", "TotemCount", "Logs out when specified totem count is reached or below", false);
    public static final SettingDouble totemThreshold = SettingUtils.settingDouble("AutoLog", "TotemCount", "Amount of totems to log off at", 1.0, 1.0, 0.0, 10.0);
    public final boolean disconnectOnNewPlayer = false;

    public AutoLog() {
        super(Category.COMBAT, "AutoLog", false, -1, "Disconnect on events");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(PlayerUpdateEvent event) {
        if (Globals.MC.player != null) {
            int health = (int)(Globals.MC.player.getHealth() + Globals.MC.player.getAbsorptionAmount());
            if (health <= threshold.getInt()) {
                AutoReconnect.hasAutoLogged = true;
                Utils.getNetworkManager().closeChannel((ITextComponent)new TextComponentString("You were logged out because your health was lower than the specified health [" + health + "]"));
                this.disable();
            }
            int count = 0;
            int total = 0;
            for (int slot = 0; slot < Globals.MC.player.inventory.getSizeInventory(); ++slot) {
                ItemStack Stack = Globals.MC.player.inventory.getStackInSlot(slot);
                if (Stack == null || !Stack.getItem().equals((Object)Items.TOTEM_OF_UNDYING)) continue;
                total = count += Stack.getCount();
            }
            if (total <= totemThreshold.getInt() && totem.getBoolean()) {
                AutoReconnect.hasAutoLogged = true;
                Utils.getNetworkManager().closeChannel((ITextComponent)new TextComponentString("You were logged out due to you having less than the specified amount of totems"));
                this.disable();
            }
        }
    }

    @SubscribeEvent
    public void onPacketRecieved(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnPlayer) {
            // empty if block
        }
    }
}

