package ml.porez.reelism;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import ml.porez.reelism.items.ReeItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.item.Items;

public class Reelism implements ModInitializer {
	public static final String NAMESPACE = "reelism";

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "reelism"), () -> new ItemStack(Items.CREEPER_HEAD));

	public static final ReelismConfig CONFIG = AutoConfig.register(ReelismConfig.class, Toml4jConfigSerializer::new).getConfig();

	@Override
	public void onInitialize() {
		RuntimeResources.register();
		BookUpgradeRecipe.register();
		ReeItems.register();

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, success) -> {
			if (Reelism.CONFIG.autoUnlockRecipes)
				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					player.unlockRecipes(server.getRecipeManager().values());
				}
		});
	}
}
