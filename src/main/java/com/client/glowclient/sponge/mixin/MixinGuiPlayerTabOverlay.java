//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Ordering
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiIngame
 *  net.minecraft.client.gui.GuiPlayerTabOverlay
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraft.scoreboard.IScoreCriteria$EnumRenderType
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraft.world.GameType
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={GuiPlayerTabOverlay.class})
public abstract class MixinGuiPlayerTabOverlay
extends Gui {
    @Shadow
    private static Ordering<NetworkPlayerInfo> ENTRY_ORDERING;
    @Shadow
    private Minecraft mc;
    @Shadow
    private GuiIngame guiIngame;
    @Shadow
    private ITextComponent footer;
    @Shadow
    private ITextComponent header;
    @Shadow
    private long lastTimeOpened;
    @Shadow
    private boolean isBeingRendered;

    @Shadow
    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), (String)networkPlayerInfoIn.getGameProfile().getName());
    }

    @Shadow
    protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
    }

    @Shadow
    private void drawScoreboardValues(ScoreObjective objective, int p_175247_2_, String name, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo info) {
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void renderPlayerlist(int width2, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
        int width = width2;
        NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
        List list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        Iterator var9 = list.iterator();

        int j4;
        while(var9.hasNext()) {
            NetworkPlayerInfo networkplayerinfo = (NetworkPlayerInfo)var9.next();
            j4 = this.mc.fontRenderer.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, j4);
            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreCriteria.EnumRenderType.HEARTS) {
                j4 = this.mc.fontRenderer.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, j4);
            }
        }

        if (!HookUtils.isExtendedTabActivated) {
            list = list.subList(0, Math.min(list.size(), 80));
        } else {
            list = list.subList(0, Math.min(list.size(), 1000));
        }

        int l3 = list.size();
        int i4 = l3;

        for(j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
            ++j4;
        }

        boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
        int l;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
                l = 90;
            } else {
                l = j;
            }
        } else {
            l = 0;
        }

        int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width2 - 50) / j4;
        int j1 = width2 / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
        int k1 = 10;
        int l1 = i1 * j4 + (j4 - 1) * 5;
        List list1 = null;
        if (this.header != null) {
            list1 = this.mc.fontRenderer.listFormattedStringToWidth(this.header.getFormattedText(), width2 - 50);

            String s;
            for(Iterator var19 = list1.iterator(); var19.hasNext(); l1 = Math.max(l1, this.mc.fontRenderer.getStringWidth(s))) {
                s = (String)var19.next();
            }
        }

        List list2 = null;
        String s3;
        Iterator var36;
        if (this.footer != null) {
            list2 = this.mc.fontRenderer.listFormattedStringToWidth(this.footer.getFormattedText(), width2 - 50);

            for(var36 = list2.iterator(); var36.hasNext(); l1 = Math.max(l1, this.mc.fontRenderer.getStringWidth(s3))) {
                s3 = (String)var36.next();
            }
        }

        int j5;
        if (list1 != null) {
            drawRect(width2 / 2 - l1 / 2 - 1, k1 - 1, width2 / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);

            for(var36 = list1.iterator(); var36.hasNext(); k1 += this.mc.fontRenderer.FONT_HEIGHT) {
                s3 = (String)var36.next();
                j5 = this.mc.fontRenderer.getStringWidth(s3);
                this.mc.fontRenderer.drawStringWithShadow(s3, (float)(width / 2 - j5 / 2), (float)k1, -1);
            }

            ++k1;
        }

        drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, Integer.MIN_VALUE);

        for(int k4 = 0; k4 < l3; ++k4) {
            int l4 = k4 / i4;
            j5 = k4 % i4;
            int j2;
            if (HookUtils.isNoPlayerHeadsTabActivated) {
                j2 = j1 + l4 * (i1 - 9) + l4 * 5;
            } else {
                j2 = j1 + l4 * i1 + l4 * 5;
            }

            int k2 = k1 + j5 * 9;
            if (!HookUtils.isNoPlayerHeadsTabActivated) {
                drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
            } else {
                drawRect(j2, k2, j2 + i1 - 9, k2 + 8, 553648127);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (k4 < list.size()) {
                NetworkPlayerInfo networkplayerinfo1 = (NetworkPlayerInfo)list.get(k4);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();
                int l5;
                if (flag) {
                    EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    l5 = 8 + (flag1 ? 8 : 0);
                    int i3 = 8 * (flag1 ? -1 : 1);
                    if (!HookUtils.isNoPlayerHeadsTabActivated) {
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, (float)l5, 8, i3, 8, 8, 64.0F, 64.0F);
                    }

                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        int j3 = 8 + (flag1 ? 8 : 0);
                        int k3 = 8 * (flag1 ? -1 : 1);
                        if (!HookUtils.isNoPlayerHeadsTabActivated) {
                            Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, (float)j3, 8, k3, 8, 8, 64.0F, 64.0F);
                        }
                    }

                    if (!HookUtils.isNoPlayerHeadsTabActivated) {
                        j2 += 9;
                    }
                }

                String s4;
                if (!HookUtils.isNoNameTabActivated) {
                    s4 = this.getPlayerName(networkplayerinfo1);
                } else {
                    s4 = "";
                }

                if (networkplayerinfo1.getGameType() == GameType.SPECTATOR) {
                    this.mc.fontRenderer.drawStringWithShadow(TextFormatting.ITALIC + s4, (float)j2, (float)k2, -1862270977);
                } else {
                    this.mc.fontRenderer.drawStringWithShadow(s4, (float)j2, (float)k2, -1);
                }

                if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR) {
                    int k5 = j2 + i + 1;
                    l5 = k5 + l;
                    if (l5 - k5 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                    }
                }

                if (!HookUtils.isNoPingTabActivated) {
                    this.drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
                }
            }
        }

        if (list2 != null) {
            k1 = k1 + i4 * 9 + 1;
            drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);

            for(var36 = list2.iterator(); var36.hasNext(); k1 += this.mc.fontRenderer.FONT_HEIGHT) {
                s3 = (String)var36.next();
                j5 = this.mc.fontRenderer.getStringWidth(s3);
                this.mc.fontRenderer.drawStringWithShadow(s3, (float)(width / 2 - j5 / 2), (float)k1, -1);
            }
        }

    }
}

