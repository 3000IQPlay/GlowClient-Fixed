//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.event.ClickEvent
 *  net.minecraft.util.text.event.ClickEvent$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.event.ForgeEventFactory
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.branches.SettingString;
import com.client.glowclient.utils.base.setting.builder.SettingManager;
import com.client.glowclient.utils.client.Globals;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.LinkedList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends EntityLivingBase {
    @Shadow
    private GameProfile gameProfile;
    @Shadow
    private final Collection<ITextComponent> suffixes = new LinkedList<ITextComponent>();
    @Shadow
    private final Collection<ITextComponent> prefixes = new LinkedList<ITextComponent>();
    @Shadow
    private String displayname;
    @Shadow
    public float eyeHeight = this.getDefaultEyeHeight();

    public MixinEntityPlayer() {
        super((World)Globals.MC.world);
    }

    @Shadow
    public float getDefaultEyeHeight() {
        return 1.62f;
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public String getName() {
        if (ModuleManager.getModuleFromName("NameChanger").isEnabled()) {
            for (SettingString setting : SettingManager.getSettingStrings()) {
                if (!setting.getModName().equals("NameChanger") || !this.gameProfile.getName().equals(setting.getName())) continue;
                return setting.getString();
            }
        }
        return this.gameProfile.getName();
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public ITextComponent getDisplayName() {
        TextComponentString itextcomponent = new TextComponentString("");
        if (!this.prefixes.isEmpty()) {
            for (ITextComponent prefix : this.prefixes) {
                itextcomponent.appendSibling(prefix);
            }
        }
        itextcomponent.appendSibling((ITextComponent)new TextComponentString(ScorePlayerTeam.formatPlayerName((Team)this.getTeam(), (String)this.getDisplayNameString())));
        if (!this.suffixes.isEmpty()) {
            for (ITextComponent suffix : this.suffixes) {
                itextcomponent.appendSibling(suffix);
            }
        }
        itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
        itextcomponent.getStyle().setInsertion(this.getName());
        return itextcomponent;
    }

    public String getDisplayNameString() {
        if (ModuleManager.getModuleFromName("NameChanger").isEnabled()) {
            for (SettingString setting : SettingManager.getSettingStrings()) {
                if (!setting.getModName().equals("NameChanger") || !this.getName().equals(setting.getName())) continue;
                this.displayname = setting.getString();
            }
        } else {
            this.displayname = ForgeEventFactory.getPlayerDisplayName((EntityPlayer)((EntityPlayer)EntityPlayer.class.cast((Object)this)), (String)this.getName());
        }
        return this.displayname;
    }
}

