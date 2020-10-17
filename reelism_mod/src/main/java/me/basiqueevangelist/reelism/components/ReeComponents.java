package me.basiqueevangelist.reelism.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ReeComponents implements EntityComponentInitializer {
    public static final ComponentKey<TransportationHolder> TRANSPORTATION = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Reelism.NAMESPACE, "transportation"), TransportationHolder.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        if (Reelism.CONFIG.transportationStatusEffect) {
            registry.registerFor(LivingEntity.class, TRANSPORTATION, (e) -> new TransportationComponent());
        }
    }
}
