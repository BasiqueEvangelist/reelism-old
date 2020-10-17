package me.basiqueevangelist.reelism.potion;

import me.basiqueevangelist.reelism.access.ExtendedStatusEffect;
import me.basiqueevangelist.reelism.components.ReeComponents;
import me.basiqueevangelist.reelism.components.TransportationHolder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransportationStatusEffect extends StatusEffect implements ExtendedStatusEffect {
    private static final Random RANDOM = new Random();
    private static final List<LivingEntity> toBeApplied = new ArrayList<>();
    private static final List<LivingEntity> toBeRemoved = new ArrayList<>();

    protected TransportationStatusEffect() {
        super(StatusEffectType.NEUTRAL, 0);
    }

    private static void createCloudFor(LivingEntity e) {
        double speed = RANDOM.nextDouble();
        ServerWorld sw = (ServerWorld) e.getEntityWorld();
        sw.spawnParticles(ParticleTypes.EFFECT, e.getParticleX(0.5D), e.getRandomBodyY(), e.getParticleZ(0.5D), 100,
                0.5, 0.5, 0.5, speed);
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((s) -> {
            for (LivingEntity e : toBeApplied) {
                if (toBeRemoved.contains(e))
                    continue;

                ServerWorld sw = s.getWorld(World.END);
                if (e.getEntityWorld() != sw) {
                    TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                    holder.setWorld(e.getEntityWorld().getRegistryKey().getValue());
                    holder.setPosition(e.getPos());
                    createCloudFor(e);
                    e.moveToWorld(sw);
                } else {

                }
            }
            for (LivingEntity e : toBeRemoved) {
                if (toBeApplied.contains(e))
                    continue;
                TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                ServerWorld w = s.getWorld(RegistryKey.of(Registry.DIMENSION, holder.getWorld()));
                Vec3d pos = holder.getPosition();
                createCloudFor(e);
                if (e instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) e).teleport(w, pos.x, pos.y, pos.z, e.yaw, e.pitch);
                } else {
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
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20));
                e.setPos(pos.x, pos.y, pos.z);
            }
            toBeApplied.clear();
            toBeRemoved.clear();
        });
    }

    @Override
    public int getColor() {
        return 0xFFFFFF;
    }

    @Override
    public void reelism$onEffectApplied(LivingEntity e, int amplifier) {
        ServerWorld sw = e.getServer().getWorld(World.END);
        if (e.getEntityWorld() == sw) {
            e.removeStatusEffectInternal(ReeStatusEffects.TRANSPORTATION);
            e.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20));
        } else
            toBeApplied.add(e);
    }

    @Override
    public void reelism$onEffectRemoved(LivingEntity e, int amplifier) {
        TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
        ServerWorld w = e.getServer().getWorld(RegistryKey.of(Registry.DIMENSION, holder.getWorld()));
        if (holder.getWorld().equals(World.END.getValue()))
            return;
        toBeRemoved.add(e);
    }
}
