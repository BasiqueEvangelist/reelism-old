package me.basiqueevangelist.reelism.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.util.Identifier;

public interface SpawnFrequencyHolder extends ComponentV3 {
    float getSpawnFrequencyFor(Identifier type);
    Object2FloatMap<Identifier> getSpawnFrequencies();
    float getAverage();
}
