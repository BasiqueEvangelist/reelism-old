package ml.porez.reelism.mixin;

import ml.porez.reelism.items.GemOfHoldingItem;
import ml.porez.reelism.items.ReeItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Shadow private PlayerEntity target;

    @Shadow private int amount;

    @Redirect(method = "tick", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;getClosestPlayer(Lnet/minecraft/entity/Entity;D)Lnet/minecraft/entity/player/PlayerEntity;"))
    public PlayerEntity onlyPlayersWithGemsOfHolding(World w, Entity e, double maxDistance) {
        return w.getClosestPlayer(e.getX(), e.getY(), e.getZ(), maxDistance, (en) -> {
            if (en instanceof PlayerEntity) {
                return GemOfHoldingItem.doGravitate((ExperienceOrbEntity)(Object) this, (PlayerEntity)en);
            }
            return false;
        });
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void checkTargetHasGem(CallbackInfo cb) {
        if (target != null)
            if (!GemOfHoldingItem.doGravitate((ExperienceOrbEntity)(Object)this, target))
                target = null;
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void checkCollidedHasGem(PlayerEntity pe, CallbackInfo cb) {
        if (pe.world.isClient)
            cb.cancel();
        if (!GemOfHoldingItem.doGravitate((ExperienceOrbEntity)(Object)this, pe))
            cb.cancel();
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;chooseEquipmentWith(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Map$Entry;"))
    public Map.Entry<EquipmentSlot, ItemStack> disableMending(Enchantment enchantment, LivingEntity entity, Predicate<ItemStack> condition) {
        return null;
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"))
    public void fillGem(PlayerEntity to, int amount) {
        ItemStack is = to.getOffHandStack();
        this.amount -= GemOfHoldingItem.fill(is, amount);
    }

    @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;remove()V"))
    public void removeIfEmpty(ExperienceOrbEntity experienceOrbEntity) {
        if (amount <= 0)
            ((ExperienceOrbEntity)(Object)this).remove();
    }
}
