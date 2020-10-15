package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import ml.porez.reelism.ReelismUtils;
import ml.porez.reelism.items.GemOfHoldingItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Shadow @Final private Property levelCost;

    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
    public void canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cb) {
        if (Reelism.CONFIG.gemOfHoldingItem)
            cb.setReturnValue((player.abilities.creativeMode || GemOfHoldingItem.getTotalExperience(player.inventory) >= ReelismUtils.getExperienceFromLevels(levelCost.get())) && levelCost.get() > 0);
    }
}
