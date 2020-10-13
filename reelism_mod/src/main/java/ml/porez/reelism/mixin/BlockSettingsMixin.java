package ml.porez.reelism.mixin;

import ml.porez.reelism.access.BlockSettingsAccess;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Function;

@Mixin(AbstractBlock.Settings.class)
public class BlockSettingsMixin implements BlockSettingsAccess {
    @Shadow private Material material;

    @Shadow private Function<BlockState, MaterialColor> materialColorFactory;

    @Override
    public BlockSettingsAccess reelism$setMaterial(Material material) {
        this.material = material;
        return this;
    }

    @Override
    public BlockSettingsAccess reelism$setMaterialColorFactory(Function<BlockState, MaterialColor> factory) {
        materialColorFactory = factory;
        return this;
    }
}
