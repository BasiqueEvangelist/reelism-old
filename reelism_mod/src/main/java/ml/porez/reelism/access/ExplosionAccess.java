package ml.porez.reelism.access;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface ExplosionAccess {
    List<Entity> reelism$getAffectedEntities();
    List<BlockPos> reelism$getAffectedBlocks();
}
