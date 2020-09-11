package ml.porez.reelism.mixin;

import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BookItem.class)
public class BookItemMixin {
    @Inject(method = "isEnchantable(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    public void notEnchantable(CallbackInfoReturnable<Boolean> cb) {
        cb.setReturnValue(false);
    }
}