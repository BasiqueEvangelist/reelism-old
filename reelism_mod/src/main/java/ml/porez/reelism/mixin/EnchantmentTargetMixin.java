package ml.porez.reelism.mixin;

import ml.porez.reelism.items.BattleAxeItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.enchantment.EnchantmentTarget$11")
public class EnchantmentTargetMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void battleAxe(Item i, CallbackInfoReturnable<Boolean> cb) {
        if (i instanceof BattleAxeItem)
            cb.setReturnValue(true);
    }
}
