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
    protected void shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig, CallbackInfoReturnable<Boolean> cb) {
        if (Reelism.CONFIG.surreelism.commonStrongholds)
            cb.setReturnValue(true);
    }

    @Mixin(StrongholdFeature.Start.class)
    public static abstract class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
            super(feature, chunkX, chunkZ, box, references, seed);
        }

        @Inject(method = "init", at = @At("TAIL"))
        public void addSurfacePiece(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, DefaultFeatureConfig defaultFeatureConfig, CallbackInfo cb) {
            if (Reelism.CONFIG.surreelism.surfaceStrongholdPiece)
                StructurePoolBasedGenerator.method_30419(
                        dynamicRegistryManager,
                        new StructurePoolFeatureConfig(
                                () -> dynamicRegistryManager.get(Registry.TEMPLATE_POOL_WORLDGEN).get(Reelism.id("stronghold/upper_base")),
                                1
                        ),
                        PoolStructurePiece::new,
                        chunkGenerator,
                        structureManager,
                        new BlockPos(i << 4, 0, j << 4),
                        children,
                        random,
                        true,
                        true
                );
            setBoundingBoxFromChildren();
        }
    }
}
