package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if (Reelism.getConfig().creeperPoisonCloud) {
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
}
