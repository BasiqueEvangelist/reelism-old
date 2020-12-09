package me.basiqueevangelist.reelism.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public interface TransportationComponent extends ComponentV3 {
    Identifier getWorld();

    void setWorld(Identifier dim);

    Vec3d getPosition();

    void setPosition(Vec3d vec);
}
