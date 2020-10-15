package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.items.GemOfHoldingItem;
import me.basiqueevangelist.reelism.items.ReeItems;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Redirect(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamaged()Z"))
    public boolean isDamaged(ItemStack is) {
        if (is.getItem() == ReeItems.GEM_OF_HOLDING)
            return true;
        else
            return is.isDamaged();
    }

    @Redirect(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getDamage()I"))
    public int getDamage(ItemStack is) {
        if (is.getItem() == ReeItems.GEM_OF_HOLDING)
            return GemOfHoldingItem.MAX_CHARGE - GemOfHoldingItem.getCharge(is);
        else
            return is.getDamage();
    }

    @Redirect(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxDamage()I"))
    public int getMaxDamage(ItemStack is) {
        if (is.getItem() == ReeItems.GEM_OF_HOLDING)
            return GemOfHoldingItem.MAX_CHARGE;
        else
            return is.getMaxDamage();
    }
}
