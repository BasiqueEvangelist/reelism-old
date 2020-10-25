package me.basiqueevangelist.reelism.mixin;

import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultBiomeCreator.class)
public class DefaultBiomeCreatorMixin {
    @Redirect(method = {"createNetherWastes", "createWarpedForest", "createCrimsonForest", "createBasaltDeltas", "createSoulSandValley"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/GenerationSettings$Builder;structureFeature(Lnet/minecraft/world/gen/feature/ConfiguredStructureFeature;)Lnet/minecraft/world/biome/GenerationSettings$Builder;", ordinal = 0))
    private static GenerationSettings.Builder addNetherStructures(GenerationSettings.Builder b, ConfiguredStructureFeature<?, ?> f) {
        b.structureFeature(ConfiguredStructureFeatures.STRONGHOLD);
        return b.structureFeature(f);
    }
}
