package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SilkTouchEnchantment.class)
public abstract class SilkTouchEnchantmentMixin extends Enchantment {
    protected SilkTouchEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) && !(stack.getItem() instanceof ShovelItem && Reelism.CONFIG.noSilkTouchOnShovels);
    }
}
