package me.basiqueevangelist.reelism.ai;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.util.EntityUtils;
import me.basiqueevangelist.reelism.mixin.FishingBobberEntityAccessor;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;

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

        List<FishingBobberEntity> bobbers = fish.world.getEntitiesByClass(FishingBobberEntity.class, fish.getBoundingBox().expand(5, 5, 5), this::matchesBobber);
        bobber = EntityUtils.getClosestEntity(bobbers, this::matchesBobber, fish.getX(), fish.getY(), fish.getZ());
        return bobber != null;
    }

    @Override
    public boolean shouldContinue() {
        return !bobber.isRemoved() && matchesBobber(bobber) && bobber.squaredDistanceTo(fish) < 8 * 8 && fish.getNavigation().isFollowingPath();
    }

    @Override
    public void stop() {
        bobber = null;
        if (fish.getNavigation().isFollowingPath())
            fish.getNavigation().stop();
    }

    @Override
    public void start() {
        Path p = fish.getNavigation().findPathTo(bobber.getBlockPos().up(), 0);
        fish.getNavigation().startMovingAlong(p, 1.6D);
    }
}
