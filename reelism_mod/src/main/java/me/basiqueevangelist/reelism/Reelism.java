package me.basiqueevangelist.reelism;

import me.basiqueevangelist.reelism.enchantment.ReeEnchantments;
import me.basiqueevangelist.reelism.items.ReeItems;
import me.basiqueevangelist.reelism.potion.ReePotions;
import me.basiqueevangelist.reelism.potion.ReeStatusEffects;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;

public class Reelism implements ModInitializer {
	public static final String NAMESPACE = "reelism-mod";

	public static final ReelismConfig CONFIG = AutoConfig.register(ReelismConfig.class, Toml4jConfigSerializer::new)
			.getConfig();

	@Override
	public void onInitialize() {
		LogManager.getLogger("ReelismMod").info("It's time for your fantasy flattening!");

		RuntimeResources.register();
		BookUpgradeRecipe.register();
		ReeItems.register();
		ReeEnchantments.register();
		ReeStatusEffects.register();
		ReePotions.register();

		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, success) -> {
			if (Reelism.CONFIG.autoUnlockRecipes)
				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					player.unlockRecipes(server.getRecipeManager().values());
				}
		});
	}
}
