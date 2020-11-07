package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ZombieEntity.class})
public abstract class ArmorScoreEntityMixin extends MobEntity {
    protected ArmorScoreEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Identifier getLootTableId() {
        if (!Reelism.CONFIG.armorBasedLoot || getType() != EntityType.ZOMBIE)
            return super.getLootTableId();

        float score = this.getArmor() + (float)this.getActiveItem().getDamage() / 2;
        Identifier original = super.getLootTableId();

        if (score > 9) {
            return new Identifier(original.getNamespace(), original.getPath() + "_high");
        } else if (score > 6) {
            return new Identifier(original.getNamespace(), original.getPath() + "_medium");
        } else {
            return original;
        }
    }
}
