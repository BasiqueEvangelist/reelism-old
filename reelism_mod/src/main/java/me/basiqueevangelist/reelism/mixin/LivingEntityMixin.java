package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.ExtendedStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean isClimbing();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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

    @Redirect(method = "applyClimbingSpeed", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"))
    public double doubleIfLookingDown(double motY, double y) {
        double orig = Math.max(motY, y);
        return orig * (pitch >= Reelism.CONFIG.ladderDownPitch && orig < 0 ? Reelism.CONFIG.ladderSpeedMultiplier : 1);
    }
}
