package me.basiqueevangelist.reelism;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

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

    public static <T extends Entity> T getClosestEntity(List<? extends T> entities, Predicate<T> predicate, double x, double y, double z) {
        double d = Double.MAX_VALUE;
        T selected = null;
        for (T e : entities) {
            if (predicate.test(e)) {
                double dist = e.squaredDistanceTo(x, y, z);
                if (dist < d) {
                    d = e.squaredDistanceTo(x, y, z);
                    selected = e;
                }
            }
        }
        return selected;
    }

    public static BlockPos getDimensionScaled(BlockPos original, WorldBorder wb, DimensionType from, DimensionType to) {
        double westBound = Math.max(-2.9999872E7D, wb.getBoundWest() + 16.0D);
        double northBound = Math.max(-2.9999872E7D, wb.getBoundNorth() + 16.0D);
        double eastBound = Math.min(2.9999872E7D, wb.getBoundEast() - 16.0D);
        double southBound = Math.min(2.9999872E7D, wb.getBoundSouth() - 16.0D);
        double ratio = DimensionType.method_31109(from, to);
        return new BlockPos(MathHelper.clamp(original.getX() * ratio, westBound, eastBound), original.getY(), MathHelper.clamp(original.getZ() * ratio, northBound, southBound));
    }
}
