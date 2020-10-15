package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.passive.MooshroomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(MooshroomEntity.class)
public class MooshroomEntityMixin {
    @Redirect(method = "onStruckByLightning", at = @At(value = "INVOKE", target = "Ljava/util/UUID;equals(Ljava/lang/Object;)Z"))
    public boolean noFunnyLightning(UUID u, Object other) {
        if (Reelism.CONFIG.noFunnyLightning.forMooshrooms)
            return true;
        return u.equals(other);
    }
}
