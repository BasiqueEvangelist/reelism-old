package me.basiqueevangelist.reelism.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;

public class ReeStructurePools {
    public static void register() {
        if (Reelism.CONFIG.surreelism.surfaceStrongholdPiece)
            StructurePools.register(new StructurePool(
                Reelism.id("stronghold/upper_base"),
                new Identifier("empty"),
                ImmutableList.of(Pair.of(StructurePoolElement.method_30434("reelism-mod:stronghold/base_low"), 1)),
                StructurePool.Projection.RIGID
            ));
    }
}
