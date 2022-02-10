//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.nbt.NBTTagList
 */
package com.client.glowclient.utils.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagList;

public class EnchantmentUtils {
    public static List<EntityEnchantment> getEnchantments(NBTTagList tags) {
        if (tags == null) {
            return null;
        }
        ArrayList list = Lists.newArrayList();
        for (int i = 0; i < tags.tagCount(); ++i) {
            list.add(new EntityEnchantment(tags.getCompoundTagAt(i).getShort("id"), (int)tags.getCompoundTagAt(i).getShort("lvl")));
        }
        return list;
    }

    public static List<EntityEnchantment> getEnchantmentsSorted(NBTTagList tags) {
        List<EntityEnchantment> list = EnchantmentUtils.getEnchantments(tags);
        if (list != null) {
            Collections.sort(list, new EnchantSort());
        }
        return list;
    }

    public static class EntityEnchantment {
        private static final Map<Integer, String> SHORT_ENCHANT_NAMES = Maps.newHashMap();
        private final Enchantment enchantment;
        private final int level;

        public EntityEnchantment(int id, int level) {
            this(Enchantment.getEnchantmentByID((int)id), level);
        }

        public EntityEnchantment(Enchantment enchantment, int level) {
            this.enchantment = enchantment;
            this.level = level;
        }

        public Enchantment getEnchantment() {
            return this.enchantment;
        }

        public int getLevel() {
            return this.level;
        }

        public String getShortName() {
            int id = Enchantment.getEnchantmentID((Enchantment)this.enchantment);
            if (SHORT_ENCHANT_NAMES.containsKey(id)) {
                if (this.enchantment.getMaxLevel() <= 1) {
                    return SHORT_ENCHANT_NAMES.get(id);
                }
                return SHORT_ENCHANT_NAMES.get(id) + " \u00a7b" + this.level;
            }
            return this.toString();
        }

        public String toString() {
            return this.enchantment.getTranslatedName(this.level);
        }

        static {
            SHORT_ENCHANT_NAMES.put(0, "pro");
            SHORT_ENCHANT_NAMES.put(1, "fip");
            SHORT_ENCHANT_NAMES.put(2, "fea");
            SHORT_ENCHANT_NAMES.put(3, "bla");
            SHORT_ENCHANT_NAMES.put(4, "prp");
            SHORT_ENCHANT_NAMES.put(5, "res");
            SHORT_ENCHANT_NAMES.put(6, "aqu");
            SHORT_ENCHANT_NAMES.put(7, "tho");
            SHORT_ENCHANT_NAMES.put(8, "dep");
            SHORT_ENCHANT_NAMES.put(9, "fro");
            SHORT_ENCHANT_NAMES.put(10, "\u00a7cbin");
            SHORT_ENCHANT_NAMES.put(16, "sha");
            SHORT_ENCHANT_NAMES.put(17, "smi");
            SHORT_ENCHANT_NAMES.put(18, "boa");
            SHORT_ENCHANT_NAMES.put(19, "kno");
            SHORT_ENCHANT_NAMES.put(20, "fir");
            SHORT_ENCHANT_NAMES.put(21, "loo");
            SHORT_ENCHANT_NAMES.put(22, "swe");
            SHORT_ENCHANT_NAMES.put(32, "eff");
            SHORT_ENCHANT_NAMES.put(33, "sil");
            SHORT_ENCHANT_NAMES.put(34, "unb");
            SHORT_ENCHANT_NAMES.put(35, "for");
            SHORT_ENCHANT_NAMES.put(48, "pow");
            SHORT_ENCHANT_NAMES.put(49, "pun");
            SHORT_ENCHANT_NAMES.put(50, "fla");
            SHORT_ENCHANT_NAMES.put(51, "inf");
            SHORT_ENCHANT_NAMES.put(61, "los");
            SHORT_ENCHANT_NAMES.put(62, "lur");
            SHORT_ENCHANT_NAMES.put(70, "men");
            SHORT_ENCHANT_NAMES.put(71, "\u00a7cvan");
        }
    }

    public static class EnchantSort
    implements Comparator<EntityEnchantment> {
        @Override
        public int compare(EntityEnchantment o1, EntityEnchantment o2) {
            int deltaEch2;
            int deltaEch1 = o1.getEnchantment().getMaxLevel() - o1.getEnchantment().getMinLevel();
            if (deltaEch1 == (deltaEch2 = o2.getEnchantment().getMaxLevel() - o2.getEnchantment().getMinLevel())) {
                return 0;
            }
            if (deltaEch1 < deltaEch2) {
                return 1;
            }
            return -1;
        }
    }
}

