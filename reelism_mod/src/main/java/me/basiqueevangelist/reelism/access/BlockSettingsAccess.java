package me.basiqueevangelist.reelism.access;

import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

import java.util.function.Function;

public interface BlockSettingsAccess {
    BlockSettingsAccess reelism$setMaterial(Material material);

    BlockSettingsAccess reelism$setMapColorFactory(Function<BlockState, MapColor> factory);
}
