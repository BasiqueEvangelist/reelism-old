package me.basiqueevangelist.reelism.mixin;

import com.mojang.datafixers.util.Either;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "trySleep", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/server/network/ServerPlayerEntity;setSpawnPoint(Lnet/minecraft/util/registry/RegistryKey;Lnet/minecraft/util/math/BlockPos;FZZ)V"), cancellable = true)
    public void disableSleep(CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cb) {
        if (Reelism.CONFIG.disableSleep)
            cb.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));
    }
}
