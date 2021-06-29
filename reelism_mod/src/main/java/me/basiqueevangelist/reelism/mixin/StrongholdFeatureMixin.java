package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StrongholdFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StrongholdFeature.class)
public class StrongholdFeatureMixin {
    @Inject(method = "shouldStartAt", at = @At("HEAD"), cancellable = true)
    protected void shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig defaultFeatureConfig, HeightLimitView heightLimitView, CallbackInfoReturnable<Boolean> cir) {
        if (Reelism.CONFIG.surreelism.commonStrongholds)
            cir.setReturnValue(true);
    }

    @Mixin(StrongholdFeature.Start.class)
    public static abstract class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> feature, ChunkPos pos, int references, long seed) {
            super(feature, pos, references, seed);
        }

        @Inject(method = "init", at = @At("TAIL"))
        public void addSurfacePiece(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, DefaultFeatureConfig defaultFeatureConfig, HeightLimitView heightLimitView, CallbackInfo ci) {
            if (Reelism.CONFIG.surreelism.surfaceStrongholdPiece)
                StructurePoolBasedGenerator.method_30419(
                        dynamicRegistryManager,
                        new StructurePoolFeatureConfig(
                                () -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY).get(Reelism.id("stronghold/upper_base")),
                                1
                        ),
                        PoolStructurePiece::new,
                        chunkGenerator,
                        structureManager,
                        chunkPos.getBlockPos(0, 0, 0),
                        this,
                        random,
                        true,
                        true,
                        heightLimitView
                );
            setBoundingBoxFromChildren();
        }
    }
}
