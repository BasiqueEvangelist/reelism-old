package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {
    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getWorld(Lnet/minecraft/util/registry/RegistryKey;)Lnet/minecraft/server/world/ServerWorld;"))
    private ServerWorld getDestinationWorld(MinecraftServer server, RegistryKey<World> original, BlockState state, World world, BlockPos pos, Entity entity) {
        if (Reelism.CONFIG.surreelism.endPortalsTpIntoNether) {
            entity.setInNetherPortal(pos); // Prevent NPE.
            entity.resetNetherPortalCooldown();
            return server.getWorld(world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER);
        }

        return server.getWorld(original);
    }
}
