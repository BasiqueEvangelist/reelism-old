package me.basiqueevangelist.reelism.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Set;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem {
    protected AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks,
            Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static float replaceAttackDamage(float attackDamage, ToolMaterial tm) {
        return (tm.getAttackDamage() + attackDamage + 1) / 2 - tm.getAttackDamage() - 1;
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 1)
    private static float replaceAttackSpeed(float attackSpeed) {
        return (attackSpeed + 4.0F) / 2 - 4.0F;
    }
}
