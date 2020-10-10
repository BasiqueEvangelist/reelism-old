package ml.porez.reelism.mixin;

import ml.porez.reelism.access.ExplosionAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin implements ExplosionAccess {
    @Unique
    private List<Entity> affectedEntities;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void init(CallbackInfo cb) {
        affectedEntities = new ArrayList<>();
    }

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public boolean addAffectedEntity(Entity e, DamageSource src, float amount) {
        affectedEntities.add(e);
        return e.damage(src, amount);
    }

    @Override
    public List<Entity> reelism$getAffectedEntities() {
        return affectedEntities;
    }
}
