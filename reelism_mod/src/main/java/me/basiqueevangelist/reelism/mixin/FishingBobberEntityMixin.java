package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.util.EntityUtils;
import me.basiqueevangelist.reelism.access.FishEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {
    @Shadow private FishingBobberEntity.State state;

    @Shadow private Entity hookedEntity;

    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    @Shadow private boolean inOpenWater;

    @Shadow protected abstract void updateHookedEntityId(@Nullable Entity entity);

    public FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickFishingLogic", at = @At("HEAD"), cancellable = true)
    private void tickFishingLogic(BlockPos pos, CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing)
            cb.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing && state == FishingBobberEntity.State.BOBBING) {
            if (!world.isClient) {
                List<FishEntity> fish = world.getEntitiesByType(TypeFilter.instanceOf(FishEntity.class), this.getBoundingBox().expand(0.5), this::isInWater);
                FishEntity closestFish = EntityUtils.getClosestEntity(fish, this::isInWater, getX(), getY(), getZ());
                if (closestFish != null) {
                    hookedEntity = closestFish;
                    updateHookedEntityId(closestFish);
                }
            }
            if (hookedEntity != null) {
                this.setVelocity(Vec3d.ZERO);
                this.state = FishingBobberEntity.State.HOOKED_IN_ENTITY;
            }
        }
    }

    @Unique
    private boolean isInWater(Entity e) {
        return e.getEntityWorld().getFluidState(e.getBlockPos()).isIn(FluidTags.WATER);
    }

    @Inject(method = "pullHookedEntity", at = @At("HEAD"), cancellable = true)
    private void pullEntity(CallbackInfo cb) {
        if (Reelism.CONFIG.betterFishing && getOwner() != null && hookedEntity instanceof FishEntity) {
            if (getOwner() instanceof PlayerEntity)
                ((PlayerEntity) getOwner()).increaseStat(Stats.FISH_CAUGHT, 1);
            if (Reelism.CONFIG.fishPulledOutAsItems) {
                Identifier lootTableId = ((LivingEntity)hookedEntity).getLootTable();
                LootTable lootTable = getServer().getLootManager().getTable(lootTableId);
                LootContext ctx = ((LivingEntityAccessor)hookedEntity).reelism$getLootContextBuilder(true, DamageSource.FALL).build(LootContextTypes.ENTITY);
                lootTable.generateLoot(ctx, (stack) -> {
                    Entity owner = getOwner();
                    double diffX = owner.getX() - this.getX();
                    double diffY = owner.getY() - this.getY();
                    double diffZ = owner.getZ() - this.getZ();
                    ItemEntity ie = new ItemEntity(world, getX(), getY(), getZ(), stack);
                    ie.setVelocity(hookedEntity.getVelocity().add(diffX * 0.1D, diffY * 0.1D + Math.sqrt(Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ)) * 0.08D, diffZ * 0.1D));
                    world.spawnEntity(ie);
                });
                hookedEntity.discard();
            }
            else {
                Entity owner = getOwner();
                double diffX = owner.getX() - this.getX();
                double diffY = owner.getY() - this.getY();
                double diffZ = owner.getZ() - this.getZ();
                hookedEntity.setVelocity(hookedEntity.getVelocity().add(diffX * 0.1D, diffY * 0.1D + Math.sqrt(Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ)) * 0.08D, diffZ * 0.1D));
                if (isInWater(hookedEntity))
                    ((FishEntityAccess) hookedEntity).reelism$pullOut();
            }
        }
    }

    @Inject(method = "use", at = @At("TAIL"), cancellable = true)
    private void transformDurabilityLost(CallbackInfoReturnable<Integer> cb) {
        if (Reelism.CONFIG.betterFishing && !this.world.isClient && getPlayerOwner() != null && hookedEntity != null) {
            cb.setReturnValue(1);
        }
    }
}
