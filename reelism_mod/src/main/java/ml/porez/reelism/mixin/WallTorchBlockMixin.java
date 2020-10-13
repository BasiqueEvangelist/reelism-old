package ml.porez.reelism.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WallTorchBlock.class)
public abstract class WallTorchBlockMixin extends Block {
    public WallTorchBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("HEAD"))
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo cb) {
        super.appendProperties(builder);
    }

    @Environment(EnvType.CLIENT)
    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    public void particleReplace(World w, ParticleEffect par, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState bs) {
        if (((TorchBlockMixin)(Object)this).isTorch && bs.get(Properties.AGE_25) == 25)
            return;
        w.addParticle(par, x, y, z, velocityX, velocityY, velocityZ);
    }
}
