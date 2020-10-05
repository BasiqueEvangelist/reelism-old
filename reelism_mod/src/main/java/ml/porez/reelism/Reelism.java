package ml.porez.reelism;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
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
		AutoConfig.register(ReelismConfig.class, Toml4jConfigSerializer::new);

		BookUpgradeRecipe.register();
		ReeItems.register();
	}

	public static ReelismConfig getConfig() {
		return AutoConfig.getConfigHolder(ReelismConfig.class).getConfig();
	}
}
