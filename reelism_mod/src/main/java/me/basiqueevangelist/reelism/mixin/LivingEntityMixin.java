package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.access.ExtendedStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "blockedByShield", at = @At(value = "HEAD"), cancellable = true)
    public void disableExplosionBlock(DamageSource source, CallbackInfoReturnable<Boolean> cb) {
        if (source.isExplosive())
            cb.setReturnValue(false);
    }

    @Inject(method = "onStatusEffectApplied", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onApplied(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/attribute/AttributeContainer;I)V"))
    public void onApply(StatusEffectInstance inst, CallbackInfo cb) {
        if (inst.getEffectType() instanceof ExtendedStatusEffect) {
            ((ExtendedStatusEffect) inst.getEffectType()).reelism$onEffectApplied((LivingEntity)(Object) this, inst.getAmplifier());
        }
    }

    @Inject(method = "onStatusEffectRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onRemoved(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/attribute/AttributeContainer;I)V"))
    public void onRemove(StatusEffectInstance inst, CallbackInfo cb) {
        if (inst.getEffectType() instanceof ExtendedStatusEffect) {
            ((ExtendedStatusEffect) inst.getEffectType()).reelism$onEffectRemoved((LivingEntity)(Object) this, inst.getAmplifier());
        }
    }

    @Inject(method = "onStatusEffectUpgraded", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;onRemoved(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/attribute/AttributeContainer;I)V"))
    public void onUpgraded(StatusEffectInstance inst, boolean reapply, CallbackInfo cb) {
        if (inst.getEffectType() instanceof ExtendedStatusEffect) {
            ((ExtendedStatusEffect) inst.getEffectType()).reelism$onEffectUpgraded((LivingEntity)(Object) this, inst.getAmplifier());
        }
    }
}
