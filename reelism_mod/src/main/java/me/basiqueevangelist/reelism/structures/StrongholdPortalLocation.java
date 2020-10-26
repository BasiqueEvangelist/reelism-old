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

    public void lightPortal(ServerWorld in) {
        int offX = orientation.getOffsetX();
        int offZ = orientation.getOffsetZ();
        BlockPos.Mutable firstPortalBlock = position.mutableCopy();
        firstPortalBlock.move(0, -1, 0);
        if (in.getBlockState(firstPortalBlock).getBlock() != Blocks.END_PORTAL_FRAME) {
            firstPortalBlock.move(offX, 0, offZ);
        }

        BlockPos.Mutable selector = firstPortalBlock.mutableCopy();

        // Horizontal
        selector.move(-offZ, 0, -offX);
        for (int i = 0; i < 3; i++) {
            if (!tryLightPos(in, selector))
                return;
            selector.move(offX * 4, 0, offZ * 4);
            if (!tryLightPos(in, selector))
                return;
            selector.move(-offX * 4, 0, -offZ * 4);
            selector.move(offZ, 0, offX);
        }
        selector.move(-offZ * 2, 0, -offX * 2);

        //Vertical
        selector.move(offX - 2*offZ, 0, offZ - 2*offX);
        for (int i = 0; i < 3; i++) {
            if (!tryLightPos(in, selector))
                return;
            selector.move(offZ * 4, 0, offX * 4);
            if (!tryLightPos(in, selector))
                return;
            selector.move(-offZ * 4, 0, -offX * 4);
            selector.move(offX, 0, offZ);
        }

        BlockPattern.Result result = EndPortalFrameBlock.getCompletedFramePattern().searchAround(in, firstPortalBlock);
        if (result != null) {
            BlockPos from = result.getFrontTopLeft().add(-3, 0, -3);

            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 3; ++j) {
                    in.setBlockState(from.add(i, 0, j), Blocks.END_PORTAL.getDefaultState(), 2);
                }
            }
        }
    }

    private static boolean tryLightPos(ServerWorld in, BlockPos pos) {
        BlockState state = in.getBlockState(pos);
        if (state.getBlock() == Blocks.END_PORTAL_FRAME) {
            if (state.getBlock() == Blocks.END_PORTAL_FRAME && !state.get(EndPortalFrameBlock.EYE)) {
                in.setBlockState(pos, state.with(EndPortalFrameBlock.EYE, true), 2);
                in.updateComparators(pos, Blocks.END_PORTAL_FRAME);
            }
            return true;
        }
        return false;
    }

    public static StrongholdPortalLocation findPortal(ServerWorld in, BlockPos stronghold) {
        Chunk c = in.getChunk(stronghold);
        Map<StructureFeature<?>, StructureStart<?>> structureStarts = c.getStructureStarts();
        StructureStart<?> strongholdStart = structureStarts.get(StructureFeature.STRONGHOLD);
        for (StructurePiece p : strongholdStart.getChildren()) {
            if (p instanceof StrongholdGenerator.PortalRoom) {
                BlockBox b = p.getBoundingBox();
                return new StrongholdPortalLocation(new BlockPos(
                        b.minX + (b.maxX - b.minX) / 2,
                        b.minY + (b.maxY - b.minY) / 2 + 1,
                        b.minZ + (b.maxZ - b.minZ) / 2), p.getFacing());
            }
        }
        throw new RuntimeException("Could not find stronghold portal room!");
    }
}
