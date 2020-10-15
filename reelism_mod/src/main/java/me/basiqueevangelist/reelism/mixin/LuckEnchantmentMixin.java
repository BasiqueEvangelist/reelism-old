package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.SpeedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LuckEnchantment.class)
public class LuckEnchantmentMixin implements SpeedEnchantment {
    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    protected void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> b) {
        if (other == Enchantments.EFFICIENCY && Reelism.CONFIG.enchantments.fortuneEfficiencyConflict)
            b.setReturnValue(false);
    }

    @Override
    public int reelism$getEfficiencyLevels(ItemStack is, int lvl) {
        if (!Reelism.CONFIG.enchantments.fortuneDecreasesSpeed)
            return 0;
        return -1;
    }
}
