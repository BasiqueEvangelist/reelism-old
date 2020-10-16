package me.basiqueevangelist.reelism.potion;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReePotions {
    public static final Potion TRANSPORTATION = new Potion(new StatusEffectInstance(ReeStatusEffects.TRANSPORTATION, 8 * 60 * 20));

    public static void register() {
        if (Reelism.CONFIG.transportationStatusEffect) {
            Registry.register(Registry.POTION, new Identifier(Reelism.NAMESPACE, "transportation"), TRANSPORTATION);
        }
    }
}
