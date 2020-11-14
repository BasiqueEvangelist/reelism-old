package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.enchantment.ReeEnchantments;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow protected abstract BlockState asBlockState();

    @Inject(method = "calcBlockBreakingDelta", at = @At("TAIL"), cancellable = true)
    public void checkTunneling(PlayerEntity player, BlockView world, BlockPos pos,
            CallbackInfoReturnable<Float> cb) {
        ItemStack main = player.getMainHandStack();
        Map<Enchantment, Integer> ench = EnchantmentHelper.get(main);
        if (ench.containsKey(ReeEnchantments.TUNNELING)) {
            BlockState other = world.getBlockState(pos.down());
            if (main.getMiningSpeedMultiplier(other) > 1.0F && main.getMiningSpeedMultiplier(asBlockState()) > 1.0F)
                cb.setReturnValue(Math.min(cb.getReturnValueF(), other.getBlock().calcBlockBreakingDelta(other, player, world, pos.down())));
        }
    }
}
