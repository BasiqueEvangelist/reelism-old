package me.basiqueevangelist.reelism.components;

import it.unimi.dsi.fastutil.doubles.DoubleLists;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.Objects;

public class SpawnFrequencyComponentImpl implements SpawnFrequencyComponent {
    private final Object2FloatMap<Identifier> spawnFrequencies = new Object2FloatOpenHashMap<>();
    private float average = 0;
    private final WorldChunk provider;

    public SpawnFrequencyComponentImpl(WorldChunk provider) {
        this.provider = provider;
    }

    @Override
    public float getSpawnFrequencyFor(Identifier type) {
        if (spawnFrequencies.isEmpty())
            generate(((StructureWorldAccess)provider.getWorld()).getSeed(), provider.getPos().x, provider.getPos().z);

        return spawnFrequencies.getOrDefault(type, getAverage());
    }

    @Override
    public Object2FloatMap<Identifier> getSpawnFrequencies() {
        if (spawnFrequencies.isEmpty())
            generate(((StructureWorldAccess)provider.getWorld()).getSeed(), provider.getPos().x, provider.getPos().z);

        return spawnFrequencies;
    }

    @Override
    public float getAverage() {
        return average;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        spawnFrequencies.clear();
        average = 0;
        for (String key : tag.getKeys()) {
            average *= spawnFrequencies.size();
            spawnFrequencies.put(new Identifier(key), tag.getFloat(key));
            average /= spawnFrequencies.size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpawnFrequencyComponentImpl that = (SpawnFrequencyComponentImpl) o;
        return Objects.equals(spawnFrequencies, that.spawnFrequencies);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        for (Object2FloatMap.Entry<Identifier> entry : spawnFrequencies.object2FloatEntrySet()) {
            tag.putFloat(entry.getKey().toString(), entry.getFloatValue());
        }
    }

    public void generate(long worldSeed, int chunkX, int chunkZ) {
        spawnFrequencies.clear();
        average = 0;
        ChunkRandom r = new ChunkRandom();

        for (EntityType<?> et : Registry.ENTITY_TYPE) {
            if (et.getSpawnGroup() != SpawnGroup.MISC && et.getSpawnGroup() != SpawnGroup.AMBIENT) {
                Identifier id = Registry.ENTITY_TYPE.getId(et);

                r.setPopulationSeed(worldSeed * id.hashCode(), chunkX, chunkZ);
                DoublePerlinNoiseSampler s = DoublePerlinNoiseSampler.method_30846(r, 1, DoubleLists.singleton(2));
                average *= spawnFrequencies.size();
                spawnFrequencies.put(id, Math.abs((float)s.sample(chunkX, 0, chunkZ)));
                average /= spawnFrequencies.size();
            }

        }
    }
}
