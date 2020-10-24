package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.FishEntityAccess;
import me.basiqueevangelist.reelism.ai.FollowBobberGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishEntity.class)
public abstract class FishEntityMixin extends WaterCreatureEntity implements FishEntityAccess {
    @Unique
    private int pulledOut = 2;

    protected FishEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void reelism$pullOut() {
        this.pulledOut = 0;
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addGoals(CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing)
            goalSelector.add(1, new FollowBobberGoal((FishEntity)(Object) this));
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tickMov(CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing) {
            if (pulledOut == 0 && !world.getFluidState(getBlockPos()).isIn(FluidTags.WATER)) {
                pulledOut = 1;
            } else if (pulledOut == 1 && world.getFluidState(getBlockPos()).isIn(FluidTags.WATER)) {
                pulledOut = 2;
            }
        }
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        super.fall(heightDifference, onGround, landedState, landedPosition);
        if (Reelism.CONFIG.betterFishing && onGround && pulledOut == 1) {
            damage(DamageSource.FALL, Float.MAX_VALUE);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        if (Reelism.CONFIG.betterFishing && pulledOut == 1) {
            damage(DamageSource.FALL, Float.MAX_VALUE);
        }
    }
}
