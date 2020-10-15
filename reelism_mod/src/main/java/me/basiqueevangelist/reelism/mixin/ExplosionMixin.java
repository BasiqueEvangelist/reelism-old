package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.access.ExplosionAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin implements ExplosionAccess {
    @Unique
    private List<Entity> affectedEntities;
    @Unique
    private List<BlockPos> hiddenAffectedBlocks;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void init(CallbackInfo cb) {
        affectedEntities = new ArrayList<>();
        hiddenAffectedBlocks = new ArrayList<>();
    }

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public boolean addAffectedEntity(Entity e, DamageSource src, float amount) {
        affectedEntities.add(e);
        return e.damage(src, amount);
    }

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"))
    public boolean addHiddenAffectedBlocks(List<BlockPos> aff, Collection<BlockPos> blocks) {
        hiddenAffectedBlocks.addAll(blocks);
        return aff.addAll(blocks);
    }

    @Override
    public List<Entity> reelism$getAffectedEntities() {
        return affectedEntities;
    }

    @Override
    public List<BlockPos> reelism$getAffectedBlocks() {
        return hiddenAffectedBlocks;
    }
}
