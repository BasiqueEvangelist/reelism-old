package ml.porez.reelism.access;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

import java.util.function.Function;

public interface BlockSettingsAccess {
    BlockSettingsAccess reelism$setMaterial(Material material);
    BlockSettingsAccess reelism$setMaterialColorFactory(Function<BlockState, MaterialColor> factory);
}
