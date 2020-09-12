package ml.porez.reelism.client;

import ml.porez.reelism.Reelism;
import ml.porez.reelism.items.GemOfHoldingItem;
import ml.porez.reelism.items.ReeItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ClientReelism implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(ReeItems.GEM_OF_HOLDING, new Identifier(Reelism.NAMESPACE, "charge"), (stack, world, entity) ->
            (float)GemOfHoldingItem.getCharge(stack) / (float)GemOfHoldingItem.MAX_CHARGE
        );
    }
}
