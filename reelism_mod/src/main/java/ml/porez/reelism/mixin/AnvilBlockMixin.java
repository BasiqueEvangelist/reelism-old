package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
    @Inject(method = "getLandingState", at = @At("HEAD"), cancellable = true)
    private static void unbreakableAnvils(BlockState state, CallbackInfoReturnable<BlockState> cb) {
        if (Reelism.CONFIG.unbreakableAnvils)
            cb.setReturnValue(state);
    }
}
