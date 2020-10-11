package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import ml.porez.reelism.access.ExplosionAccess;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.potion.Potions;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "onStruckByLightning", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"))
    public void noFunnyLightning(DataTracker tracker, TrackedData<Object> data, Object to) {
        if (!Reelism.getConfig().noFunnyLightning.forCreepers)
            tracker.set(data, to);
    }

    @Inject(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;remove()V"))
    public void poisonCloud(CallbackInfo cb) {
        if (Reelism.getConfig().plantCreepers) {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getX(), getY() + 0.25, getZ());
            cloud.setOwner(this);
            cloud.setRadius(4.0F);
            cloud.setRadiusOnUse(0);
            cloud.setWaitTime(15);
            cloud.setRadiusGrowth(-cloud.getRadius() / (float)cloud.getDuration());
            cloud.setPotion(Potions.STRONG_POISON);
            world.spawnEntity(cloud);
        }
    }

    @Unique
    private static final List<Block> REPLACABLE_BLOCKS = ImmutableList.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH, Blocks.PODZOL);

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    public Explosion diffExplosion(World w, Entity e, double x, double y, double z, float power, Explosion.DestructionType type) {
        if (!Reelism.getConfig().plantCreepers)
            return w.createExplosion(e, x, y, z, power, type);
        Explosion expl = w.createExplosion(e, x, y, z, power, Explosion.DestructionType.NONE);
        for (Entity aff : ((ExplosionAccess)expl).reelism$getAffectedEntities()) {
            if (aff instanceof LivingEntity) {
                LivingEntity liv = (LivingEntity) aff;
                if (!liv.isBlocking())
                    liv.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 10 * 20));

                int poisonLength = w.getDifficulty() == Difficulty.HARD ? 15 : (w.getDifficulty() == Difficulty.NORMAL ? 7 : 0);
                if (poisonLength > 0)
                    liv.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, poisonLength * 20));
            }
        }
        for (BlockPos block : ((ExplosionAccess) expl).reelism$getAffectedBlocks()) {
            if (REPLACABLE_BLOCKS.contains(w.getBlockState(block).getBlock())) {
                w.setBlockState(block, Blocks.MYCELIUM.getDefaultState(), 2);
                w.updateNeighbors(block, Blocks.MYCELIUM);
            }
        }
        return expl;
    }
}
