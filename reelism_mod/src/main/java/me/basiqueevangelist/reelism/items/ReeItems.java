package me.basiqueevangelist.reelism.items;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.block.ReeBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ReeItems {
    public static final GemOfHoldingItem GEM_OF_HOLDING = new GemOfHoldingItem(
            new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).group(ItemGroup.MISC));
    public static final BattleAxeItem WOODEN_BATTLE_AXE = new BattleAxeItem(ToolMaterials.WOOD, 6.0F, -3.2F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BattleAxeItem STONE_BATTLE_AXE = new BattleAxeItem(ToolMaterials.STONE, 7.0F, -3.2F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BattleAxeItem GOLDEN_BATTLE_AXE = new BattleAxeItem(ToolMaterials.GOLD, 6.0F, -3.0F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BattleAxeItem IRON_BATTLE_AXE = new BattleAxeItem(ToolMaterials.IRON, 6.0F, -3.1F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BattleAxeItem DIAMOND_BATTLE_AXE = new BattleAxeItem(ToolMaterials.DIAMOND, 5.0F, -3.0F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BattleAxeItem NETHERITE_BATTLE_AXE = new BattleAxeItem(ToolMaterials.NETHERITE, 5.0F, -3.0F,
            new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final BlockItem NETHER_PORTAL_FRAME = new BlockItem(ReeBlocks.NETHER_PORTAL_FRAME, new FabricItemSettings().group(ItemGroup.DECORATIONS));

    public static void register() {
        if (Reelism.CONFIG.gemOfHoldingItem)
            Registry.register(Registry.ITEM, Reelism.id("gem_of_holding"), GEM_OF_HOLDING);

        if (Reelism.CONFIG.battleAxe.wooden)
            Registry.register(Registry.ITEM, Reelism.id("wooden_battle_axe"), WOODEN_BATTLE_AXE);
        if (Reelism.CONFIG.battleAxe.stone)
            Registry.register(Registry.ITEM, Reelism.id("stone_battle_axe"), STONE_BATTLE_AXE);
        if (Reelism.CONFIG.battleAxe.golden)
            Registry.register(Registry.ITEM, Reelism.id("golden_battle_axe"), GOLDEN_BATTLE_AXE);
        if (Reelism.CONFIG.battleAxe.iron)
            Registry.register(Registry.ITEM, Reelism.id("iron_battle_axe"), IRON_BATTLE_AXE);
        if (Reelism.CONFIG.battleAxe.diamond)
            Registry.register(Registry.ITEM, Reelism.id("diamond_battle_axe"),
                    DIAMOND_BATTLE_AXE);
        if (Reelism.CONFIG.battleAxe.netherite)
            Registry.register(Registry.ITEM, Reelism.id("netherite_battle_axe"),
                    NETHERITE_BATTLE_AXE);

        if (Reelism.CONFIG.surreelism.altNetherPortals)
            Registry.register(Registry.ITEM, Reelism.id("nether_portal_frame"), NETHER_PORTAL_FRAME);
    }
}
