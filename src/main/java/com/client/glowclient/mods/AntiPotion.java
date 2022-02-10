//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.MobEffects
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiPotion
extends ToggleMod {
    public static final SettingBoolean nausea = SettingUtils.settingBoolean("AntiPotion", "Nausea", "Removes Nausea", true);
    public static final SettingBoolean blindness = SettingUtils.settingBoolean("AntiPotion", "Blindness", "Removes Blindness", true);
    public static final SettingBoolean invisibility = SettingUtils.settingBoolean("AntiPotion", "Invisibility", "Removes Invisibility", true);
    public static final SettingBoolean fatigue = SettingUtils.settingBoolean("AntiPotion", "Fatigue", "Removes Fatigue", true);
    public static final SettingBoolean levitation = SettingUtils.settingBoolean("AntiPotion", "Levitation", "Removes Levitation", true);
    public static final SettingBoolean slowness = SettingUtils.settingBoolean("AntiPotion", "Slowness", "Removes Slowness", true);
    public static final SettingBoolean weakness = SettingUtils.settingBoolean("AntiPotion", "Weakness", "Removes Weakness", true);

    public AntiPotion() {
        super(Category.PLAYER, "AntiPotion", false, -1, "Removes bad effects");
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        try {
            EntityLivingBase living = event.getEntityLiving();
            if (living.equals((Object)Globals.MC.player)) {
                if (invisibility.getBoolean()) {
                    living.removePotionEffect(MobEffects.INVISIBILITY);
                    living.setInvisible(false);
                }
                if (nausea.getBoolean()) {
                    living.removePotionEffect(MobEffects.NAUSEA);
                }
                if (blindness.getBoolean()) {
                    living.removePotionEffect(MobEffects.BLINDNESS);
                }
                if (slowness.getBoolean()) {
                    living.removePotionEffect(MobEffects.SLOWNESS);
                }
                if (fatigue.getBoolean()) {
                    living.removePotionEffect(MobEffects.MINING_FATIGUE);
                }
                if (levitation.getBoolean()) {
                    living.removePotionEffect(MobEffects.LEVITATION);
                }
                if (weakness.getBoolean()) {
                    living.removePotionEffect(MobEffects.WEAKNESS);
                }
                living.resetPotionEffectMetadata();
            } else if (living != null) {
                if (invisibility.getBoolean()) {
                    living.setInvisible(false);
                }
                living.resetPotionEffectMetadata();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

