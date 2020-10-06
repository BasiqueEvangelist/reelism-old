package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
    @Redirect(method = "onStruckByLightning", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"))
    public void noFunnyLightning(DataTracker tracker, TrackedData<Object> data, Object to) {
        if (!Reelism.getConfig().noFunnyLightning.forCreepers)
            tracker.set(data, to);
    }
}
