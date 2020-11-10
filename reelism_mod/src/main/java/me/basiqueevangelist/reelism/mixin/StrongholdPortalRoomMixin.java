package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.block.ReeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Property;
import net.minecraft.structure.StrongholdGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Random;

@Mixin(StrongholdGenerator.PortalRoom.class)
public class StrongholdPortalRoomMixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    public float alwaysGenerateEyes(Random random) {
        if (!Reelism.CONFIG.surreelism.endPortalsGenerateOpen)
            return random.nextFloat();

        return 1;
    }

    @Redirect(method = "generate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StrongholdGenerator$PortalRoom;applyYTransform(I)I")
            ))
    public Object removeEye(BlockState state, Property<Boolean> property, Comparable<?> c) {
        if (!Reelism.CONFIG.surreelism.altNetherPortals)
            return state.with(property, (Boolean) c);

        return state;
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;END_PORTAL_FRAME:Lnet/minecraft/block/Block;"))
    public Block getFrame() {
        return Reelism.CONFIG.surreelism.altNetherPortals ? ReeBlocks.NETHER_PORTAL_FRAME : Blocks.END_PORTAL_FRAME;
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;END_PORTAL:Lnet/minecraft/block/Block;"))
    public Block getPortal() {
        return Reelism.CONFIG.surreelism.altNetherPortals ? ReeBlocks.NETHER_PORTAL : Blocks.END_PORTAL;
    }
}
