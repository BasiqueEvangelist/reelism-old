package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.structure.StrongholdGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(StrongholdGenerator.PortalRoom.class)
public class StrongholdPortalRoomMixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    public float alwaysGenerateEyes(Random random) {
        if (!Reelism.CONFIG.surreelism.endPortalsGenerateOpen)
            return random.nextFloat();

        return 1;
    }
}
