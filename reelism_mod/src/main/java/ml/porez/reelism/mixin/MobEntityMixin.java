package ml.porez.reelism.mixin;

import ml.porez.reelism.ReelismUtils;
import ml.porez.reelism.access.ExtendedDamageEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @reason Gets replaced with our implementation.
     */
    @Redirect(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    public float voidedAttackDamage(ItemStack is, EntityGroup g)  {
        return 0;
    }

    @ModifyVariable(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F", shift = At.Shift.AFTER), ordinal = 0)
    public float addStuff(float f, Entity e) {
        LivingEntity liv = (LivingEntity)e; // We already know it's a LivingEntity.
        ItemStack is = getMainHandStack();
        MutableFloat mut = new MutableFloat(f);
        ReelismUtils.forEachEnchantment(is, (en, lvl) -> {
            if (en instanceof ExtendedDamageEnchantment) {
                mut.add(((ExtendedDamageEnchantment) en).reelism$getAttackDamage(lvl, liv));
            }
            else {
                mut.add(en.getAttackDamage(lvl, liv.getGroup()));
            }
        });
        return mut.floatValue();
    }
}
