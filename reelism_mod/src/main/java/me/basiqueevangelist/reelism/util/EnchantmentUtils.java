package me.basiqueevangelist.reelism.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public final class EnchantmentUtils {
    private EnchantmentUtils() {
        throw new AssertionError();
    }

    public static void forEachEnchantment(ItemStack is, BiConsumer<Enchantment, Integer> c) {
        if (!is.isEmpty()) {
            NbtList enchs = is.getEnchantments();
            for (int i = 0; i < enchs.size(); i++) {
                NbtCompound ench = enchs.getCompound(i);
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

    public static int getRepairCostFor(ItemStack stack) {
        MutableInt base = new MutableInt(0);
        forEachEnchantment(stack, (ench, lvl) -> {
            switch(ench.getRarity()) {
                case COMMON:
                    base.add(lvl);
                    break;
                case UNCOMMON:
                    base.add(2 * lvl);
                    break;
                case RARE:
                    base.add(4 * lvl);
                    break;
                case VERY_RARE:
                    base.add(8 * lvl);
            }
        });
        return base.getValue();
    }
}
