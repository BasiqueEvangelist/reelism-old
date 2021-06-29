package me.basiqueevangelist.reelism.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;

public final class DimensionUtils {
    private DimensionUtils() {
        throw new AssertionError();
    }

    public static BlockPos getDimensionScaled(BlockPos original, WorldBorder wb, DimensionType from, DimensionType to) {
        double westBound = Math.max(-2.9999872E7D, wb.getBoundWest() + 16.0D);
        double northBound = Math.max(-2.9999872E7D, wb.getBoundNorth() + 16.0D);
        double eastBound = Math.min(2.9999872E7D, wb.getBoundEast() - 16.0D);
        double southBound = Math.min(2.9999872E7D, wb.getBoundSouth() - 16.0D);
        double ratio = DimensionType.getCoordinateScaleFactor(from, to);
        return new BlockPos(MathHelper.clamp(original.getX() * ratio, westBound, eastBound), original.getY(), MathHelper.clamp(original.getZ() * ratio, northBound, southBound));
    }
}
