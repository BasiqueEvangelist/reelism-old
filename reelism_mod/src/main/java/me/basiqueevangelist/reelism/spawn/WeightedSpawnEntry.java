package me.basiqueevangelist.reelism.spawn;

import me.basiqueevangelist.reelism.components.SpawnFrequencyComponent;
import me.basiqueevangelist.reelism.mixin.WeightedPickerEntryAccessor;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.SpawnSettings;

public class WeightedSpawnEntry extends WeightedPicker.Entry {
    private SpawnSettings.SpawnEntry entry;

    public WeightedSpawnEntry(SpawnSettings.SpawnEntry se, SpawnFrequencyComponent sfh) {
        super(Math.max((int) (sfh.getSpawnFrequencyFor(Registry.ENTITY_TYPE.getId(se.type)) * 10000), 1) * ((WeightedPickerEntryAccessor)se).getWeight());
        entry = se;
    }

    public SpawnSettings.SpawnEntry getEntry() {
        return entry;
    }
}
