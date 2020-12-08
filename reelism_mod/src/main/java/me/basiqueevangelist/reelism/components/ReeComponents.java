package me.basiqueevangelist.reelism.components;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.chunk.WorldChunk;

public class ReeComponents implements EntityComponentInitializer, ChunkComponentInitializer {
    public static final ComponentKey<TransportationHolder> TRANSPORTATION = ComponentRegistryV3.INSTANCE.getOrCreate(Reelism.id("transportation"), TransportationHolder.class);
    public static final ComponentKey<SpawnFrequencyHolder> SPAWN_FREQUENCY = ComponentRegistryV3.INSTANCE.getOrCreate(Reelism.id("spawn_frequency"), SpawnFrequencyHolder.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        if (Reelism.CONFIG.surreelism.transportationStatusEffect) {
            registry.registerFor(LivingEntity.class, TRANSPORTATION, (e) -> new TransportationComponent());
        }
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        if (Reelism.CONFIG.randomSpawnFrequencies) {
            registry.register(SPAWN_FREQUENCY, chunk -> {
                if (chunk instanceof WorldChunk) {
                    return new SpawnFrequencyComponent((WorldChunk) chunk);
                } else {
                    return new DummySpawnFrequencyComponent();
                }
            });
        }
    }
}
