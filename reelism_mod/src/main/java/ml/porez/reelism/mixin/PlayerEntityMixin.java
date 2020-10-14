package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import ml.porez.reelism.access.ExtendedDamageEnchantment;
import ml.porez.reelism.items.BattleAxeItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "takeShieldHit", at = @At(value = "JUMP", shift = At.Shift.BEFORE))
    public boolean onInstanceOf(Object o, Class<?> axe) {
        return o instanceof AxeItem || (Reelism.CONFIG.battleAxe.breaksShields && o instanceof BattleAxeItem);
    }

    // Code duplication FTW
    /**
     * @reason Gets replaced with our implementation.
     */
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    public float voidedAttackDamage(ItemStack is, EntityGroup g)  {
        return 0;
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"), ordinal = 1)
    public float addStuff(float f, Entity e) {
        LivingEntity liv = (LivingEntity)e; // We already know it's a LivingEntity.
        ItemStack is = getMainHandStack();
        MutableFloat mut = new MutableFloat(f);
        if (!is.isEmpty()) {
            ListTag enchs = is.getEnchantments();
            for (int i = 0; i < enchs.size(); i++) {
                CompoundTag ench = enchs.getCompound(i);
                Identifier enchId = new Identifier(ench.getString("id"));
                int enchLevel = ench.getInt("lvl");
                Registry.ENCHANTMENT.getOrEmpty(enchId).ifPresent(enchInst -> {
                    if (enchInst instanceof ExtendedDamageEnchantment) {
                        mut.add(((ExtendedDamageEnchantment) enchInst).reelism$getAttackDamage(enchLevel, liv));
                    }
                    else {
                        mut.add(enchInst.getAttackDamage(enchLevel, liv.getGroup()));
                    }
                });
            }
        }
        return mut.floatValue();
    }
}
