package me.basiqueevangelist.reelism.client;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.items.GemOfHoldingItem;
import me.basiqueevangelist.reelism.items.ReeItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.Identifier;

public class ClientReelism implements ClientModInitializer {
    public static TranslationStorage ENGLISH;

    @Override
    public void onInitializeClient() {
        ClientRuntimeResources.register();
        if (Reelism.CONFIG.gemOfHoldingItem)
            FabricModelPredicateProviderRegistry.register(ReeItems.GEM_OF_HOLDING, Reelism.id("charge"),
                    (stack, world, entity) -> (float) GemOfHoldingItem.getCharge(stack) / (float) GemOfHoldingItem.MAX_CHARGE);
    }
}
