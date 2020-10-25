package me.basiqueevangelist.reelism.mixin;

import com.google.common.collect.ImmutableMap;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructuresConfig.class)
public class StructuresConfigMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;", ordinal = 6))
    private static ImmutableMap.Builder<StructureFeature<?>, StructureConfig> putStronghold(ImmutableMap.Builder<StructureFeature<?>, StructureConfig> b, Object feature, Object config) {
        if (!Reelism.CONFIG.surreelism.commonStrongholds)
            return b.put((StructureFeature<?>) feature, (StructureConfig) config);
        return b.put((StructureFeature<?>) feature, new StructureConfig(32, 8, 42131317));
    }
}
