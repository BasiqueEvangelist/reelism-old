package ml.porez.reelism.mixin;

import ml.porez.reelism.access.ExtendedDamageEnchantment;
import ml.porez.reelism.items.BattleAxeItem;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DamageEnchantment.class)
public abstract class DamageEnchantmentMixin extends Enchantment implements ExtendedDamageEnchantment {
    @Shadow @Final public int typeIndex;

    protected DamageEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;<init>(Lnet/minecraft/enchantment/Enchantment$Rarity;Lnet/minecraft/enchantment/EnchantmentTarget;[Lnet/minecraft/entity/EquipmentSlot;)V"))
    private static EnchantmentTarget changeTarget(EnchantmentTarget target) {
        return EnchantmentTarget.BREAKABLE;
    }

    @Override
    public float reelism$getAttackDamage(int level, Entity e) {
        if (e.getType() == EntityType.CREEPER && typeIndex == 1)
            return level * 2.5F;
        if (e instanceof LivingEntity)
            return getAttackDamage(level, ((LivingEntity) e).getGroup());
        return 0;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof BattleAxeItem;
    }
}
