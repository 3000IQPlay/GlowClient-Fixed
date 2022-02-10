//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityDonkey
 *  net.minecraft.entity.passive.EntityMule
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.world.entity.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HorseMod
extends ToggleMod {
    public static final SettingDouble jumpHeight = SettingUtils.settingDouble("HorseMod", "Jump", "Horse Jump Attribute", 1.0, 0.01, 0.0, 1.0);
    public static final SettingDouble speed = SettingUtils.settingDouble("HorseMod", "Speed", "Horse Speed Attribute", 0.3375, 0.001, 0.0, 0.3375);
    public static SettingBoolean jumppower = SettingUtils.settingBoolean("HorseMod", "JumpPower", "Always jump with max power", true);

    public HorseMod() {
        super(Category.MOVEMENT, "HorseMod", false, -1, "Changes stats or jump power of a horse");
    }

    @Override
    public String getHUDTag() {
        String mode = String.format("J:%.1f,", jumpHeight.getDouble());
        String mode2 = String.format("S:%.1f", speed.getDouble());
        return mode + mode2;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        try {
            if (jumppower.getBoolean()) {
                Globals.MC.player.horseJumpPower = 1.0f;
            }
            if (EntityUtils.isDrivenByPlayer(event.getEntity()) && (Utils.getRidingEntity() instanceof AbstractHorse || Globals.MC.player.getRidingEntity() instanceof EntityDonkey || Globals.MC.player.getRidingEntity() instanceof EntityMule)) {
                AbstractHorse cfr_ignored_0 = (AbstractHorse)Utils.getRidingEntity();
                IAttribute JUMP_STRENGTH = AbstractHorse.JUMP_STRENGTH;
                IAttribute MOVEMENT_SPEED = (IAttribute)((AbstractHorse)Utils.getRidingEntity()).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                ((EntityLivingBase)Utils.getRidingEntity()).getEntityAttribute(JUMP_STRENGTH).setBaseValue(jumpHeight.getDouble());
                ((EntityLivingBase)Utils.getRidingEntity()).getEntityAttribute(MOVEMENT_SPEED).setBaseValue(speed.getDouble());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

