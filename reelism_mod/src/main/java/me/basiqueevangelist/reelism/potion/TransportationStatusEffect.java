package me.basiqueevangelist.reelism.potion;

import me.basiqueevangelist.reelism.components.ReeComponents;
import me.basiqueevangelist.reelism.components.TransportationComponent;
import me.basiqueevangelist.reelism.components.TransportationHolder;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransportationStatusEffect extends StatusEffect {
    private final Random r = new Random();
    private static final List<LivingEntity> toBeApplied = new ArrayList<>();
    private static final List<LivingEntity> toBeRemoved = new ArrayList<>();

    protected TransportationStatusEffect() {
        super(StatusEffectType.NEUTRAL, 0);
    }

    public static void register() {
        EntityComponentCallback.event(LivingEntity.class).register((e, components) -> components.put(ReeComponents.TRANSPORTATION, new TransportationComponent()));
        ServerTickEvents.END_SERVER_TICK.register((s) -> {
            for (LivingEntity e : toBeApplied) {
                if (toBeRemoved.contains(e))
                    continue;

                ServerWorld sw = s.getWorld(World.END);
                if (e.getEntityWorld() != sw) {
                    TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                    holder.setWorld(e.getEntityWorld().getRegistryKey().getValue());
                    holder.setPosition(e.getPos());
                    e.moveToWorld(sw);
                }
                else {
                    e.removeStatusEffect(ReeStatusEffects.TRANSPORTATION);
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10*20));
                }
            }
            for (LivingEntity e : toBeRemoved) {
                if (toBeApplied.contains(e))
                    continue;
                TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                ServerWorld w = s.getWorld(RegistryKey.of(Registry.DIMENSION, holder.getWorld()));
                if (holder.getWorld().equals(World.END.getValue()))
                    continue;
                Vec3d pos = holder.getPosition();
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10*20));
                if (e instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) e).teleport(w, pos.x, pos.y, pos.z, e.yaw, e.pitch);
                }
                else {
                    e.detach();
                    Entity old = e;
                    e = (LivingEntity) e.getType().create(w);
                    if (e == null) {
                        return;
                    }

                    e.copyFrom(old);
                    e.refreshPositionAfterTeleport(pos);
                    w.onDimensionChanged(e);
                    old.removed = true;
                }
                e.setPos(pos.x, pos.y, pos.z);
            }
            toBeApplied.clear();
            toBeRemoved.clear();
        });
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        toBeRemoved.add(entity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        toBeApplied.add(entity);
    }

    @Override
    public int getColor() {
        return 0xFFFFFF;
    }
}
