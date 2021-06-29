package me.basiqueevangelist.reelism.block;

import me.basiqueevangelist.reelism.Reelism;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.util.registry.Registry;

public final class ReeBlocks {
    private ReeBlocks() {
        throw new AssertionError();
    }

    public static ReeNetherPortalBlock NETHER_PORTAL = new ReeNetherPortalBlock(FabricBlockSettings.copyOf(Blocks.END_PORTAL).mapColor(MapColor.RED));
    public static NetherPortalFrameBlock NETHER_PORTAL_FRAME = new NetherPortalFrameBlock(FabricBlockSettings.copyOf(Blocks.END_PORTAL_FRAME).mapColor(MapColor.ORANGE));

    public static void register() {
        if (Reelism.CONFIG.surreelism.altNetherPortals) {
            Registry.register(Registry.BLOCK, Reelism.id("nether_portal"), NETHER_PORTAL);
            Registry.register(Registry.BLOCK, Reelism.id("nether_portal_frame"), NETHER_PORTAL_FRAME);
        }
    }
}
