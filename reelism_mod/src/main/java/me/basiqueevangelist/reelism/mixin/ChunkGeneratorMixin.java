package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Redirect(method = "locateStructure", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/feature/StructureFeature;STRONGHOLD:Lnet/minecraft/world/gen/feature/StructureFeature;"))
    private StructureFeature<?> getStronghold() {
        return Reelism.CONFIG.surreelism.commonStrongholds ? null : StructureFeature.STRONGHOLD;
    }
}
