package me.basiqueevangelist.reelism.ai;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.util.math.MathHelper;

public class NewFishMoveControl extends MoveControl {
    private final FishEntity fish;

    public NewFishMoveControl(FishEntity fish) {
        super(fish);
        this.fish = fish;
    }

    public void tick() {
        if (this.fish.isTouchingWater()) {
            this.fish.setVelocity(this.fish.getVelocity().add(0.0D, 0.005D, 0.0D));
        }

        if (this.state == MoveControl.State.MOVE_TO && !this.fish.getNavigation().isIdle()) {
            double d = this.targetX - this.fish.getX();
            double e = this.targetY - this.fish.getY();
            double f = this.targetZ - this.fish.getZ();
            double g = d * d + e * e + f * f;
            if (g < 2.500000277905201E-7D) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F;
                this.fish.yaw = this.changeAngle(this.fish.yaw, h, 10.0F);
                this.fish.bodyYaw = this.fish.yaw;
                this.fish.headYaw = this.fish.yaw;
                float i = (float)(this.speed * this.fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                if (this.fish.isTouchingWater()) {
                    this.fish.setMovementSpeed(i * 0.02F);
                    float j = -((float)(MathHelper.atan2(e, (double)MathHelper.sqrt(d * d + f * f)) * 57.2957763671875D));
                    j = MathHelper.clamp(MathHelper.wrapDegrees(j), -85.0F, 85.0F);
                    this.fish.pitch = this.changeAngle(this.fish.pitch, j, 5.0F);
                    float k = MathHelper.cos(this.fish.pitch * 0.017453292F);
                    float l = MathHelper.sin(this.fish.pitch * 0.017453292F);
                    this.fish.forwardSpeed = k * i;
                    this.fish.upwardSpeed = -l * i;
                } else {
                    this.fish.setMovementSpeed(i * 0.1F);
                }

            }
        } else {
            this.fish.setMovementSpeed(0.0F);
            this.fish.setSidewaysSpeed(0.0F);
            this.fish.setUpwardSpeed(0.0F);
            this.fish.setForwardSpeed(0.0F);
        }
    }
}
