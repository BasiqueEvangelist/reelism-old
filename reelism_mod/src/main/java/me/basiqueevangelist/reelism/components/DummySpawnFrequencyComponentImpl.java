package me.basiqueevangelist.reelism.components;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class DummySpawnFrequencyComponentImpl implements SpawnFrequencyComponent {
    @Override
    public float getSpawnFrequencyFor(Identifier type) {
        return 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object2FloatMap<Identifier> getSpawnFrequencies() {
        return (Object2FloatMap<Identifier>)Object2FloatMaps.EMPTY_MAP;
    }

    @Override
    public float getAverage() {
        return 1;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {

    }

    @Override
    public void writeToNbt(CompoundTag tag) {

    }
}
