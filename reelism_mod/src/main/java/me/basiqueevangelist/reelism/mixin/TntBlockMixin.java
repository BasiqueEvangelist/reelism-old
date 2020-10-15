package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.BlockSettingsAccess;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.TntBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TntBlock.class)
public class TntBlockMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static AbstractBlock.Settings changeSettings(AbstractBlock.Settings set) {
        if (!Reelism.CONFIG.tntIsPowderKeg)
            return set;

        set.strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD);
        ((BlockSettingsAccess) set).reelism$setMaterial(Material.WOOD)
                .reelism$setMaterialColorFactory(state -> MaterialColor.WOOD);
        return set;
    }
}
