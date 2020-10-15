package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.SpeedEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilkTouchEnchantment.class)
public abstract class SilkTouchEnchantmentMixin extends Enchantment implements SpeedEnchantment {
    protected SilkTouchEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack)
                && !(stack.getItem() instanceof ShovelItem && Reelism.CONFIG.enchantments.noSilkTouchOnShovels);
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    protected void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> b) {
        if (other == Enchantments.EFFICIENCY && Reelism.CONFIG.enchantments.silkTouchEfficiencyConflict)
            b.setReturnValue(false);
    }

    @Override
    public int reelism$getEfficiencyLevels(ItemStack is, int lvl) {
        if (!Reelism.CONFIG.enchantments.silkTouchDecreasesSpeed)
            return 0;
        return -1;
    }
}
