package me.basiqueevangelist.reelism.potion;

import me.basiqueevangelist.reelism.access.ExtendedStatusEffect;
import me.basiqueevangelist.reelism.components.ReeComponents;
import me.basiqueevangelist.reelism.components.TransportationHolder;
import me.basiqueevangelist.reelism.mixin.EntityAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransportationStatusEffect extends StatusEffect implements ExtendedStatusEffect {
    private static final Random RANDOM = new Random();
    private static final List<LivingEntity> toBeApplied = new ArrayList<>();
    private static final List<LivingEntity> toBeRemoved = new ArrayList<>();

    public static final ChunkTicketType<Integer> TICKET_TYPE = ChunkTicketType.create("reelism-mod:transportation",
            Integer::compareTo, 300);

    public static final RegistryKey<World> DESTINATION = World.END;

    protected TransportationStatusEffect() {
        super(StatusEffectType.NEUTRAL, 0);
    }

    private static void createCloudFor(LivingEntity e) {
        double speed = RANDOM.nextDouble();
        ServerWorld sw = (ServerWorld) e.getEntityWorld();
        sw.spawnParticles(ParticleTypes.EFFECT, e.getParticleX(0.5D), e.getRandomBodyY(), e.getParticleZ(0.5D), 100,
                0.5, 0.5, 0.5, speed);
    }

    private static Entity doTeleport(Entity e, ServerWorld to, double x, double y, double z, float yaw, float pitch) {
        to.getProfiler().push("doTeleport");
        ((ServerWorld) e.getEntityWorld()).getChunkManager().addTicket(TICKET_TYPE, new ChunkPos(e.getBlockPos()), 3,
                e.getEntityId());
        if (e instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) e).teleport(to, x, y, z, yaw, pitch);
        } else {
            e.detach();
            Entity old = e;
            e = e.getType().create(to);
            if (e == null) {
                to.getProfiler().pop();
                return e;
            }

            e.copyFrom(old);
            e.refreshPositionAndAngles(x, y, z, yaw, pitch);
            e.setHeadYaw(yaw);
            to.onDimensionChanged(e);
            old.removed = true;
        }
        to.getProfiler().pop();
        return e;
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((s) -> {
            for (LivingEntity e : toBeApplied) {
                if (toBeRemoved.contains(e))
                    continue;

                ServerWorld sw = s.getWorld(DESTINATION);
                if (e.getEntityWorld() != sw) {
                    TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                    holder.setWorld(e.getEntityWorld().getRegistryKey().getValue());
                    holder.setPosition(e.getPos());
                    createCloudFor(e);
                    TeleportTarget target = ((EntityAccessor) e).reelism$getTeleportTarget(sw);
                    e.setVelocity(target.velocity);
                    doTeleport(e, sw, target.position.x, target.position.y, target.position.z, target.yaw,
                            target.pitch);
                }
            }
            for (LivingEntity e : toBeRemoved) {
                if (toBeApplied.contains(e))
                    continue;
                TransportationHolder holder = ReeComponents.TRANSPORTATION.get(e);
                ServerWorld w = s.getWorld(RegistryKey.of(Registry.DIMENSION, holder.getWorld()));
                Vec3d pos = holder.getPosition();
                createCloudFor(e);
                e = (LivingEntity) doTeleport(e, w, pos.x, pos.y, pos.z, e.yaw, e.pitch);
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
        ServerWorld sw = e.getServer().getWorld(DESTINATION);
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
        if (holder.getWorld().equals(DESTINATION.getValue()))
            return;
        toBeRemoved.add(e);
    }
}
