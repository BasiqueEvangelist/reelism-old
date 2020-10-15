package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(HostileEntity.class)
public class HostileEntityMixin {
    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 1))
    private static int getMaxSpawn(Random r, int i) {
        return r.nextInt(Reelism.CONFIG.spawnLightLevel);
    }
}
