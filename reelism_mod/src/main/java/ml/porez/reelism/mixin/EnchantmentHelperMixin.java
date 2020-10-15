package ml.porez.reelism.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMinLevel()I"))
    private static int excludeUnacceptableEntries(Enchantment e, int power, ItemStack stack) {
        if (!e.isAcceptableItem(stack))
            return e.getMaxLevel() + 1;
        return e.getMinLevel();
    }
}
