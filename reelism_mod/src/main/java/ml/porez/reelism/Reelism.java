package ml.porez.reelism;

import net.fabricmc.api.ModInitializer;

public class Reelism implements ModInitializer {
	public static final String NAMESPACE = "reelism";

	@Override
	public void onInitialize() {
		BookUpgradeRecipe.register();
	}
}
