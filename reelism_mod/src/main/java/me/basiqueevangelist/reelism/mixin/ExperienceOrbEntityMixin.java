package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.items.GemOfHoldingItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {
    @Shadow
    private PlayerEntity target;

    @Shadow
    private int amount;

    @Shadow protected abstract int repairPlayerGears(PlayerEntity player, int amount);

    @Shadow private int pickingCount;

    @Redirect(method = "expensiveUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getClosestPlayer(Lnet/minecraft/entity/Entity;D)Lnet/minecraft/entity/player/PlayerEntity;"))
    public PlayerEntity onlyPlayersWithGemsOfHolding(World w, Entity e, double maxDistance) {
        return w.getClosestPlayer(e.getX(), e.getY(), e.getZ(), maxDistance,
                delegateToVanilla() ? EntityPredicates.EXCEPT_SPECTATOR : (en) -> {
                    if (en instanceof PlayerEntity) {
                        return GemOfHoldingItem.doGravitate((ExperienceOrbEntity) (Object) this, (PlayerEntity) en);
                    }
                    return false;
                });
    }

    @Inject(method = "expensiveUpdate", at = @At("HEAD"))
    public void checkTargetHasGem(CallbackInfo cb) {
        if (!delegateToVanilla() && target != null)
            if (!GemOfHoldingItem.doGravitate((ExperienceOrbEntity) (Object) this, target))
                target = null;
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void checkCollidedHasGem(PlayerEntity pe, CallbackInfo cb) {
        if (delegateToVanilla())
            return;

        if (pe.world.isClient)
            cb.cancel();
        if (!GemOfHoldingItem.doGravitate((ExperienceOrbEntity) (Object) this, pe))
            cb.cancel();
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/entity/player/PlayerEntity;I)I"))
    public int disableMending(ExperienceOrbEntity experienceOrbEntity, PlayerEntity player, int amount) {
        return Reelism.CONFIG.disableMending ? amount
                : repairPlayerGears(player, amount);
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"))
    public void fillGem(PlayerEntity to, int amount) {
        if (delegateToVanilla()) {
            to.addExperience(amount);
            return;
        }
        ItemStack is = to.getOffHandStack();
        this.amount -= GemOfHoldingItem.fill(is, amount);
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;discard()V"))
    public void removeIfEmpty(ExperienceOrbEntity experienceOrbEntity) {
        pickingCount = 0;
        if (delegateToVanilla() || amount <= 0)
            ((ExperienceOrbEntity) (Object) this).discard();
    }

    @Unique
    private boolean delegateToVanilla() {
        return !Reelism.CONFIG.replaceXpOrbBehavior;
    }
}
