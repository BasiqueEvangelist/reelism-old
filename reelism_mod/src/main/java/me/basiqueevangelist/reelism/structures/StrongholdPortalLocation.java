package me.basiqueevangelist.reelism.structures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Map;

public class StrongholdPortalLocation {
    public final BlockPos position;
    public final Direction orientation;

    public StrongholdPortalLocation(BlockPos position, Direction orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public static StrongholdPortalLocation findPortal(ServerWorld in, BlockPos stronghold) {
        Chunk c = in.getChunk(stronghold);
        Map<StructureFeature<?>, StructureStart<?>> structureStarts = c.getStructureStarts();
        StructureStart<?> strongholdStart = structureStarts.get(StructureFeature.STRONGHOLD);
        for (StructurePiece p : strongholdStart.getChildren()) {
            if (p instanceof StrongholdGenerator.PortalRoom) {
                BlockBox b = p.getBoundingBox();
                return new StrongholdPortalLocation(new BlockPos(
                        b.getMinX() + (b.getMaxX() - b.getMinX()) / 2,
                        b.getMinY() + (b.getMaxY() - b.getMinY()) / 2 + 1,
                        b.getMinZ() + (b.getMaxZ() - b.getMinZ()) / 2), p.getFacing());
            }
        }
        throw new RuntimeException("Could not find stronghold portal room!");
    }
}
