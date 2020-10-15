package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.ReelismUtils;
import me.basiqueevangelist.reelism.access.ExtendedDamageEnchantment;
import me.basiqueevangelist.reelism.access.SpeedEnchantment;
import me.basiqueevangelist.reelism.items.BattleAxeItem;
import me.basiqueevangelist.reelism.items.GemOfHoldingItem;
import me.basiqueevangelist.reelism.items.ReeItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Final
    public PlayerInventory inventory;

    @Shadow
    public abstract void addExperience(int experience);

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
    public float voidedAttackDamage(ItemStack is, EntityGroup g) {
        return 0;
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"), ordinal = 1)
    public float addStuff(float f, Entity e) {
        ItemStack is = getMainHandStack();
        MutableFloat mut = new MutableFloat(f);
        ReelismUtils.forEachEnchantment(is, (en, lvl) -> {
            if (en instanceof ExtendedDamageEnchantment) {
                mut.add(((ExtendedDamageEnchantment) en).reelism$getAttackDamage(lvl, e));
            } else {
                mut.add(en.getAttackDamage(lvl,
                        e instanceof LivingEntity ? ((LivingEntity) e).getGroup() : EntityGroup.DEFAULT));
            }
        });
        return mut.floatValue();
    }

    @Redirect(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEfficiency(Lnet/minecraft/entity/LivingEntity;)I"))
    public int getEfficiency(LivingEntity liv) {
        // Disable vanilla efficiency logic.
        return -1;
    }

    @ModifyVariable(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEfficiency(Lnet/minecraft/entity/LivingEntity;)I"))
    public float changeSpeed(float f, BlockState bs) {
        MutableInt mut = new MutableInt(EnchantmentHelper.getEfficiency(this));
        ItemStack main = this.getMainHandStack();
        ReelismUtils.forEachEnchantment(main, (en, lvl) -> {
            if (en instanceof SpeedEnchantment)
                mut.add(((SpeedEnchantment) en).reelism$getEfficiencyLevels(main, lvl));
        });

        if (mut.getValue() == 0)
            return f;
        else if (mut.getValue() > 0)
            return f + (float) (mut.getValue() * mut.getValue() + 1);
        else
            return f - (float) (-mut.getValue() * -mut.getValue() + 1);
    }

    @Inject(method = "addExperienceLevels", at = @At("HEAD"), cancellable = true)
    public void addExperienceLevels(int levels, CallbackInfo cb) {
        if (Reelism.CONFIG.gemOfHoldingItem) {
            addExperience(ReelismUtils.getExperienceFromLevels(levels));
            cb.cancel();
        }
    }

    @Inject(method = "addExperience", at = @At("HEAD"), cancellable = true)
    public void replaceAddExperience(int xp, CallbackInfo cb) {
        if (Reelism.CONFIG.gemOfHoldingItem) {
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack is = inventory.getStack(i);
                if (is.getItem() == ReeItems.GEM_OF_HOLDING) {
                    xp -= GemOfHoldingItem.fill(is, xp);
                    if (xp <= 0)
                        return;
                }
            }
            cb.cancel();
        }
    }

}