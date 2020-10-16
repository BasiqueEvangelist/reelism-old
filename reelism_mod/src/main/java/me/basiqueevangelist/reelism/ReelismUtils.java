package me.basiqueevangelist.reelism;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

public class ReelismUtils {
    private ReelismUtils() {

    }

    public static void forEachEnchantment(ItemStack is, BiConsumer<Enchantment, Integer> c) {
        if (!is.isEmpty()) {
            ListTag enchs = is.getEnchantments();
            for (int i = 0; i < enchs.size(); i++) {
                CompoundTag ench = enchs.getCompound(i);
                Identifier enchId = new Identifier(ench.getString("id"));
                int enchLevel = ench.getInt("lvl");
                Registry.ENCHANTMENT.getOrEmpty(enchId).ifPresent(enchInst -> {
                    c.accept(enchInst, enchLevel);
                });
            }
        }
    }

    public static int getExperienceFromLevels(int levels) {
        if (levels < 0)
            return -getExperienceFromLevels(-levels);

        int total = 0;
        for (int i = 0; i < levels; i++) {
            if (i >= 30) {
                total += 112 + (i - 30) * 9;
            } else {
                total += i >= 15 ? 37 + (i - 15) * 5 : 7 + i * 2;
            }
        }
        return total;
    }
}