package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.structures.StrongholdPortalLocation;
import me.basiqueevangelist.reelism.util.DimensionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;

    @Shadow public abstract BlockPos getBlockPos();

    @Inject(method = "getTeleportTarget", at = @At("HEAD"), cancellable = true)
    public void netherTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> tp) {
        if (destination.getRegistryKey() == World.NETHER && world.getRegistryKey() == World.OVERWORLD
         || destination.getRegistryKey() == World.OVERWORLD && world.getRegistryKey() == World.NETHER) {
            BlockPos to = destination.locateStructure(StructureFeature.STRONGHOLD, DimensionUtils.getDimensionScaled(getBlockPos(), world.getWorldBorder(), world.getDimension(), destination.getDimension()), 1000, false);

            if (to != null) {
                StrongholdPortalLocation found = StrongholdPortalLocation.findPortal(destination, to);
                if (!Reelism.CONFIG.surreelism.altNetherPortals)
                    found.lightPortal(destination);
                tp.setReturnValue(new TeleportTarget(Vec3d.ofCenter(found.position), Vec3d.ZERO, found.orientation.getOpposite().asRotation(), 0));
            }
        }
    }
}
