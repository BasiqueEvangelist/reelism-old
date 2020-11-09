package me.basiqueevangelist.reelism.potion;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReeStatusEffects {
    public static final TransportationStatusEffect TRANSPORTATION = new TransportationStatusEffect();

    public static void register() {
        if (Reelism.CONFIG.surreelism.transportationStatusEffect) {
            Registry.register(Registry.STATUS_EFFECT, Reelism.id("transportation"), TRANSPORTATION);
            TransportationStatusEffect.register();
        }
    }
}
