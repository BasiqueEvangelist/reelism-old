package me.basiqueevangelist.reelism.components;

import me.basiqueevangelist.reelism.Reelism;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.util.Identifier;

public class ReeComponents {
    public static final ComponentType<TransportationComponent> TRANSPORTATION = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(Reelism.NAMESPACE, "transportation"), TransportationComponent.class);
}
