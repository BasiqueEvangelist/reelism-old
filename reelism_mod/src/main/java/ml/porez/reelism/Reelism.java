package ml.porez.reelism;

import ml.porez.reelism.items.ReeItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.item.Items;

public class Reelism implements ModInitializer {
	public static final String NAMESPACE = "reelism";

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "reelism"), () -> new ItemStack(Items.CREEPER_HEAD));

	@Override
	public void onInitialize() {
		BookUpgradeRecipe.register();
		ReeItems.register();
	}
}
