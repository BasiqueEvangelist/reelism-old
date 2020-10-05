package ml.porez.reelism.items;

import ml.porez.reelism.Reelism;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ReeItems {
    public static GemOfHoldingItem GEM_OF_HOLDING;

    public static void register() {
        if (Reelism.getConfig().gemOfHoldingItem)
        GEM_OF_HOLDING = Registry.register(Registry.ITEM, new Identifier(Reelism.NAMESPACE, "gem_of_holding"), new GemOfHoldingItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).group(Reelism.GROUP)));
    }
}
