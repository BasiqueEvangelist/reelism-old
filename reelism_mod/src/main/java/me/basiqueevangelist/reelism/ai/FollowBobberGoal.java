package me.basiqueevangelist.reelism.ai;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.ReelismUtils;
import me.basiqueevangelist.reelism.mixin.FishingBobberEntityAccessor;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class FollowBobberGoal extends Goal {
    private final FishEntity fish;
    private FishingBobberEntity bobber;

    public FollowBobberGoal(FishEntity fish) {
        this.fish = fish;
    }

    private boolean matchesBobber(FishingBobberEntity bobber) {
        return bobber.isInOpenWater() && ((FishingBobberEntityAccessor)bobber).reelism$getState() == FishingBobberEntity.State.BOBBING;
    }

    @Override
    public boolean canStart() {
        if (!Reelism.CONFIG.betterFishing)
            return false;

        List<FishingBobberEntity> bobbers = fish.world.getEntitiesIncludingUngeneratedChunks(FishingBobberEntity.class, fish.getBoundingBox().expand(5, 5, 5));
        bobber = ReelismUtils.getClosestEntity(bobbers, this::matchesBobber, fish.getX(), fish.getY(), fish.getZ());
        return bobber != null;
    }

    @Override
    public boolean shouldContinue() {
        return !bobber.removed && matchesBobber(bobber) && bobber.squaredDistanceTo(fish) < 8 * 8 && fish.getNavigation().isFollowingPath();
    }

    @Override
    public void stop() {
        bobber = null;
        if (fish.getNavigation().isFollowingPath())
            fish.getNavigation().stop();
    }

    private BlockPos getBobberPos(FishingBobberEntity bobber) {
        BlockPos pos = bobber.getBlockPos();
        while (bobber.world.getFluidState(pos).isIn(FluidTags.WATER)) {
            pos = pos.up();
        }
        return pos.down();
    }

    @Override
    public void start() {
        Path p = fish.getNavigation().findPathTo(getBobberPos(bobber), 0);
        fish.getNavigation().startMovingAlong(p, 1.6D);
    }
}