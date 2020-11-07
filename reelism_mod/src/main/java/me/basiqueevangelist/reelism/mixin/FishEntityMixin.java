package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.access.FishEntityAccess;
import me.basiqueevangelist.reelism.ai.FollowBobberGoal;
import me.basiqueevangelist.reelism.ai.NewFishMoveControl;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
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
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

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

    @ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/FleeEntityGoal;<init>(Lnet/minecraft/entity/mob/PathAwareEntity;Ljava/lang/Class;FDDLjava/util/function/Predicate;)V"))
    public Predicate<LivingEntity> fearPlayersInWater(Predicate<LivingEntity> old) {
        if (!Reelism.CONFIG.betterFishing)
            return old;

        return old.and(Entity::isTouchingWater);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void replaceMoveControl(CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing)
            moveControl = new NewFishMoveControl((FishEntity)(Object)this);
    }

    @Inject(method = "createFishAttributes", at = @At("TAIL"))
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cb) {
        if (Reelism.CONFIG.betterFishing)
            cb.getReturnValue().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2000000476837158D);
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
