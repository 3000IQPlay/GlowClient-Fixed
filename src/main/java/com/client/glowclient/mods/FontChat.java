//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.mc.PacketHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FontChat
extends ToggleMod {
    private static final String[] MODE = new String[]{"FULL", "CIRCLE", "PARENTHESES", "SMALL"};
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int[][] FONT = new int[][]{{65313, 65314, 65315, 65316, 65317, 65318, 65319, 65320, 65321, 65322, 65323, 65324, 65325, 65326, 65327, 65328, 65329, 65330, 65331, 65332, 65333, 65334, 65335, 65336, 65337, 65338, 65345, 65346, 65347, 65348, 65349, 65350, 65351, 65352, 65353, 65354, 65355, 65356, 65357, 65358, 65359, 65360, 65361, 65362, 65363, 65364, 65365, 65366, 65367, 65368, 65369, 65370, 65296, 65297, 65298, 65299, 65300, 65301, 65302, 65303, 65304, 65305}, {9398, 9399, 9400, 9401, 9402, 9403, 9404, 9405, 9406, 9407, 9408, 9409, 9410, 9411, 9412, 9413, 9414, 9415, 9416, 9417, 9418, 9419, 9420, 9421, 9422, 9423, 9424, 9425, 9426, 9427, 9428, 9429, 9430, 9431, 9432, 9433, 9434, 9435, 9436, 9437, 9438, 9439, 9440, 9441, 9442, 9443, 9444, 9445, 9446, 9447, 9448, 9449, 9450, 9312, 9313, 9314, 9315, 9316, 9317, 9318, 9319, 9320}, {9372, 9373, 9374, 9375, 9376, 9377, 9378, 9379, 9380, 9381, 9382, 9383, 9384, 9385, 9386, 9387, 9388, 9389, 9390, 9391, 9392, 9393, 9394, 9395, 9396, 9397, 9372, 9373, 9374, 9375, 9376, 9377, 9378, 9379, 9380, 9381, 9382, 9383, 9384, 9385, 9386, 9387, 9388, 9389, 9390, 9391, 9392, 9393, 9394, 9395, 9396, 9397, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}, {7491, 7495, 7580, 7496, 7497, 7584, 7501, 688, 7588, 690, 7503, 737, 7504, 7599, 7506, 7510, 7587, 691, 738, 7511, 7512, 7515, 695, 739, 696, 7611, 7491, 7495, 7580, 7496, 7497, 7584, 7501, 688, 7588, 690, 7503, 737, 7504, 7599, 7506, 7510, 7587, 691, 738, 7511, 7512, 7515, 695, 739, 696, 7611, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}};
    String font = MODE[0];
    private String inputMessage = "";
    private String message = "";
    private String recipient = "";
    private Boolean isWhisper = false;
    private Boolean isPM = false;
    private Boolean isIgnore = false;
    private int fontMode;

    public FontChat() {
        super(Category.SERVER, "FontChat", false, -1, "Type in unicode fonts in chat");
    }

    @SubscribeEvent
    public void onPacketSent(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage && !PacketHelper.isIgnored(event.getPacket())) {
            this.inputMessage = ((CPacketChatMessage)event.getPacket()).getMessage();
            for (int i = 0; i < MODE.length; ++i) {
                if (!this.font.toUpperCase().equals(MODE[i])) continue;
                this.fontMode = i;
            }
            if (this.inputMessage.startsWith("/r ")) {
                this.message = this.inputMessage.substring(3);
                this.isWhisper = true;
                this.isPM = false;
            } else if (this.inputMessage.startsWith("/pm ")) {
                Pattern pattern = Pattern.compile("\\s\\w*\\s");
                Matcher matcher = pattern.matcher(this.inputMessage);
                if (matcher.find()) {
                    this.recipient = this.inputMessage.substring(matcher.start(), matcher.end()).trim();
                }
                this.message = this.inputMessage.replace("/pm " + this.recipient + " ", "");
                this.isPM = true;
                this.isWhisper = false;
                this.isIgnore = false;
            } else if (this.inputMessage.startsWith("/ignore")) {
                this.isIgnore = true;
                this.isWhisper = false;
                this.isPM = false;
            } else {
                this.message = this.inputMessage;
                this.isWhisper = false;
                this.isPM = false;
                this.isIgnore = false;
            }
            char[] out = this.message.toCharArray();
            for (int i = 0; i < this.message.toCharArray().length; ++i) {
                out[i] = alphabet.indexOf(this.message.charAt(i)) != -1 && this.message.toCharArray()[i] != '>' ? (char)FONT[this.fontMode][alphabet.indexOf(this.message.charAt(i))] : this.message.toCharArray()[i];
            }
            String messageOut = new String(out);
            if (this.isWhisper.booleanValue()) {
                messageOut = "/r " + messageOut;
            } else if (this.isPM.booleanValue()) {
                messageOut = "/pm " + this.recipient + " " + messageOut;
            } else if (this.isIgnore.booleanValue()) {
                messageOut = this.inputMessage;
            }
            CPacketChatMessage packet = new CPacketChatMessage(messageOut);
            PacketHelper.ignore((Packet)packet);
            Utils.getNetworkManager().sendPacket((Packet)packet);
            event.setCanceled(true);
        }
    }
}

