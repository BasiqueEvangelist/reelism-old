package me.basiqueevangelist.reelism.components;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TransportationComponentImpl implements TransportationComponent {
    Identifier world = World.OVERWORLD.getValue();
    Vec3d position = Vec3d.ZERO;

    @Override
    public void readFromNbt(NbtCompound tag) {
        world = new Identifier(tag.getString("World"));
        position = new Vec3d(
                tag.getDouble("PosX"),
                tag.getDouble("PosY"),
                tag.getDouble("PosZ"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("World", world.toString());
        tag.putDouble("PosX", position.getX());
        tag.putDouble("PosY", position.getY());
        tag.putDouble("PosZ", position.getZ());
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
