package me.basiqueevangelist.reelism.access;

import net.minecraft.entity.LivingEntity;

public interface ExtendedStatusEffect {
    default void reelism$onEffectApplied(LivingEntity e, int amplifier) {

    }
    default void reelism$onEffectRemoved(LivingEntity e, int amplifier) {

    }
    default void reelism$onEffectUpgraded(LivingEntity e, int amplifier) {

    }
}
