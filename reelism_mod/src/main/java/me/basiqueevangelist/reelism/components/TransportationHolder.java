package me.basiqueevangelist.reelism.components;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public interface TransportationHolder extends Component {
    Identifier getWorld();

    void setWorld(Identifier dim);

    Vec3d getPosition();

    void setPosition(Vec3d vec);
}
