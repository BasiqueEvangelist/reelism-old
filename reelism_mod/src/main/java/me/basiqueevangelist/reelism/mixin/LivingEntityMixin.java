package me.basiqueevangelist.reelism.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "blockedByShield", at = @At(value = "HEAD"), cancellable = true)
    public void disableExplosionBlock(DamageSource source, CallbackInfoReturnable<Boolean> cb) {
        if (source.isExplosive())
            cb.setReturnValue(false);
    }
}
