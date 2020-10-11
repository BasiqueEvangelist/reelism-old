package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import ml.porez.reelism.items.BattleAxeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Redirect(method = "takeShieldHit", at = @At(value = "JUMP", shift = At.Shift.BEFORE))
    public boolean onInstanceOf(Object o, Class<?> axe) {
        return o instanceof AxeItem || (Reelism.getConfig().battleAxe.breaksShields && o instanceof BattleAxeItem);
    }
}
