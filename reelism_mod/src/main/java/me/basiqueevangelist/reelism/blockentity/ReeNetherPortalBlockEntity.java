package me.basiqueevangelist.reelism.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.BlockPos;

public class ReeNetherPortalBlockEntity extends EndPortalBlockEntity {
    public ReeNetherPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ReeBlockEntities.NETHER_PORTAL, pos, state);
    }
}
