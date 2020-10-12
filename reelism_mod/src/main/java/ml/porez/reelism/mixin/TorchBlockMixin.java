package ml.porez.reelism.mixin;

import ml.porez.reelism.Reelism;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block {
    @Unique
    private boolean isTorch = false;

    public TorchBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Settings modifySettings(Settings set, Settings other, ParticleEffect type) {
        if (type != ParticleTypes.FLAME || !Reelism.CONFIG.torchesBurnOut)
            return set;
        return set.ticksRandomly().luminance(state -> state.get(Properties.AGE_25) < 25 ? 14 : 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void setIsTorch(Settings s, ParticleEffect type, CallbackInfo cb) {
        isTorch = type == ParticleTypes.FLAME;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isTorch || !Reelism.CONFIG.torchesBurnOut)
            return;
        if (state.get(Properties.AGE_25) < 25) {
            world.setBlockState(pos, state.with(Properties.AGE_25, state.get(Properties.AGE_25) + 1));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        if (Reelism.CONFIG.torchesBurnOut)
            builder.add(Properties.AGE_25);
    }
}
