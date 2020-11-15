package me.basiqueevangelist.reelism.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("getLootContextBuilder")
    LootContext.Builder reelism$getLootContextBuilder(boolean causedByPlayer, DamageSource source);
}
