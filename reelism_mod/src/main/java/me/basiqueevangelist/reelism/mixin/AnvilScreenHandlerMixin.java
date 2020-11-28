package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.util.EnchantmentUtils;
import me.basiqueevangelist.reelism.items.GemOfHoldingItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Shadow
    @Final
    private Property levelCost;

    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    public void canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cb) {
        if (Reelism.CONFIG.gemOfHoldingItem)
            cb.setReturnValue((player.abilities.creativeMode || GemOfHoldingItem
                    .getTotalExperience(player.inventory) >= EnchantmentUtils.getExperienceFromLevels(levelCost.get()))
                    && levelCost.get() > 0);
    }

    @Redirect(method = "updateResult",
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setCustomName(Lnet/minecraft/text/Text;)Lnet/minecraft/item/ItemStack;"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;getNextCost(I)I")
        ),
        at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I"))
    public int getLevelCost(Property p) {
        if (!Reelism.CONFIG.noMaxAnvilLevel)
            return p.get();

        return 1;
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 0))
    public int getMaxxedLevel(int prev) {
        if (!Reelism.CONFIG.noMaxAnvilLevel)
            return prev;

        return Integer.MAX_VALUE;
    }
}
