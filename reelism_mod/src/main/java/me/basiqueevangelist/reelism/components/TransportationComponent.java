package me.basiqueevangelist.reelism.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class TransportationComponent implements TransportationHolder {
    Identifier world = World.OVERWORLD.getValue();
    Vec3d position = Vec3d.ZERO;

    @Override
    public void fromTag(CompoundTag tag) {
        world = new Identifier(tag.getString("World"));
        position = new Vec3d(
            tag.getDouble("PosX"),
            tag.getDouble("PosY"),
            tag.getDouble("PosZ"));
    }

    @Override
    @NotNull
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("World", world.toString());
        tag.putDouble("PosX", position.getX());
        tag.putDouble("PosY", position.getY());
        tag.putDouble("PosZ", position.getZ());
        return tag;
    }

    @Override
    public Identifier getWorld() {
        return world;
    }

    @Override
    public void setWorld(Identifier w) {
        world = w;
    }

    @Override
    public Vec3d getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vec3d vec) {
        position = vec;
    }

}
