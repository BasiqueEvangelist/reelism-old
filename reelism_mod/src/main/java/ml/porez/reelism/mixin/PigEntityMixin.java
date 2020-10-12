package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PigEntity.class)
public class PigEntityMixin {
    @Redirect(method = "onStruckByLightning", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    public Difficulty noFunnyLightning(ServerWorld w) {
        return Reelism.CONFIG.noFunnyLightning.forPigs ? Difficulty.PEACEFUL : w.getDifficulty();
    }
}
