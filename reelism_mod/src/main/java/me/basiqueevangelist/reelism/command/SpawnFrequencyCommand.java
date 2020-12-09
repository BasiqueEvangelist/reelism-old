package me.basiqueevangelist.reelism.command;

import com.mojang.brigadier.CommandDispatcher;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import me.basiqueevangelist.reelism.components.ReeComponents;
import me.basiqueevangelist.reelism.components.SpawnFrequencyComponent;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

import static net.minecraft.server.command.CommandManager.literal;

public class SpawnFrequencyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> disp) {
        disp.register(
            literal("reelism")
                .then(literal("spawnfrequency").executes(s -> {
                    ServerCommandSource source = s.getSource();
                    WorldChunk chunk = source.getWorld().getWorldChunk(new BlockPos(source.getPosition()));
                    SpawnFrequencyComponent sfc = ReeComponents.SPAWN_FREQUENCY.get(chunk);
                    for (Object2FloatMap.Entry<Identifier> entry : sfc.getSpawnFrequencies().object2FloatEntrySet()) {
                        String text = entry.getKey().toString() + ": " + entry.getFloatValue();
                        source.sendFeedback(new LiteralText(text), false);
                    }
                    return 1;
                }))
        );
    }
}
