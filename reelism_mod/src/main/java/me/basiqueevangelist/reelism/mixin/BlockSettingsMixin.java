package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.access.BlockSettingsAccess;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Function;

@Mixin(AbstractBlock.Settings.class)
public class BlockSettingsMixin implements BlockSettingsAccess {
    @Shadow
    private Material material;

    @Shadow private Function<BlockState, MapColor> mapColorProvider;

    @Override
    public BlockSettingsAccess reelism$setMaterial(Material material) {
        this.material = material;
        return this;
    }

    @Override
    public BlockSettingsAccess reelism$setMapColorFactory(Function<BlockState, MapColor> factory) {
        mapColorProvider = factory;
        return this;
    }
}
