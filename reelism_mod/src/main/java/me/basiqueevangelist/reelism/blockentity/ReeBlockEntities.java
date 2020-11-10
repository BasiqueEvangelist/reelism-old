package me.basiqueevangelist.reelism.blockentity;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.block.ReeBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public final class ReeBlockEntities {
    private ReeBlockEntities() {
        throw new AssertionError();
    }

    public static BlockEntityType<ReeNetherPortalBlockEntity> NETHER_PORTAL = BlockEntityType.Builder.create(ReeNetherPortalBlockEntity::new, ReeBlocks.NETHER_PORTAL).build(null);

    public static void register() {
        if (Reelism.CONFIG.surreelism.altNetherPortals) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, Reelism.id("nether_portal"), NETHER_PORTAL);
        }
    }
}