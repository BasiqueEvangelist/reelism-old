package me.basiqueevangelist.reelism.util;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.List;
import java.util.function.Predicate;

public final class EntityUtils {
    public static final ChunkTicketType<Integer> TICKET_TYPE = ChunkTicketType.create("reelism-mod:do_teleport",
            Integer::compareTo, 300);

    private EntityUtils() {
        throw new AssertionError();
    }

    public static Entity doTeleport(Entity e, ServerWorld to, double x, double y, double z, float yaw, float pitch) {
        to.getProfiler().push("doTeleport");
        ((ServerWorld) e.getEntityWorld()).getChunkManager().addTicket(TICKET_TYPE, new ChunkPos(e.getBlockPos()), 3,
                e.getEntityId());
        if (e instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) e).teleport(to, x, y, z, yaw, pitch);
        } else {
            if (e.getEntityWorld() != to) {
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
            } else {
                e.teleport(x, y, z);
                e.setHeadYaw(yaw);
            }
        }
        to.getProfiler().pop();
        return e;
    }

    public static <T extends Entity> T getClosestEntity(List<? extends T> entities, Predicate<T> predicate, double x, double y, double z) {
        double d = Double.MAX_VALUE;
        T selected = null;
        for (T e : entities) {
            if (predicate.test(e)) {
                double dist = e.squaredDistanceTo(x, y, z);
                if (dist < d) {
                    d = e.squaredDistanceTo(x, y, z);
                    selected = e;
                }
            }
        }
        return selected;
    }
}
