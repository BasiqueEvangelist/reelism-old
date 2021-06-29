package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.components.ReeComponents;
import me.basiqueevangelist.reelism.components.SpawnFrequencyComponent;
import me.basiqueevangelist.reelism.spawn.WeightedSpawnEntry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {
    // TODO: fix this.

//    @Redirect(method = "pickRandomSpawnEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/WeightedPicker;getRandom(Ljava/util/Random;Ljava/util/List;)Lnet/minecraft/util/collection/WeightedPicker$Entry;"))
//    private static WeightedPicker.Entry pickEntry(Random r, List<SpawnSettings.SpawnEntry> entries, ServerWorld serverWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, Random random, BlockPos blockPos) {
//        if (!Reelism.CONFIG.randomSpawnFrequencies)
//            return WeightedPicker.getRandom(r, entries);
//
//        WorldChunk chunk = serverWorld.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
//        SpawnFrequencyComponent sfc = ReeComponents.SPAWN_FREQUENCY.get(chunk);
//
//        ArrayList<WeightedSpawnEntry> newEntries = new ArrayList<>();
//        for (SpawnSettings.SpawnEntry se : entries) {
//            newEntries.add(new WeightedSpawnEntry(se, sfc));
//        }
//        return WeightedPicker.getRandom(r, newEntries).getEntry();
//    }
}
