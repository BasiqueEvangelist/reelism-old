package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin {
    @Redirect(method = "fillRecipesFromPool", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/TradeOffers$Factory;create(Lnet/minecraft/entity/Entity;Ljava/util/Random;)Lnet/minecraft/village/TradeOffer;"))
    public TradeOffer convertTradeOffer(TradeOffers.Factory factory, Entity merchant, Random r) {
        TradeOffer o = factory.create(merchant, r);
        if (Reelism.CONFIG.merchantsUseGold && o != null) {
            if (o.getOriginalFirstBuyItem() != null && o.getOriginalFirstBuyItem().getItem() == Items.EMERALD)
                ((ItemStackAccessor)(Object)o.getOriginalFirstBuyItem()).reelism$setItem(Items.GOLD_NUGGET);
            if (o.getSecondBuyItem() != null && o.getSecondBuyItem().getItem() == Items.EMERALD)
                ((ItemStackAccessor)(Object)o.getSecondBuyItem()).reelism$setItem(Items.GOLD_NUGGET);
            if (o.getMutableSellItem() != null && o.getMutableSellItem().getItem() == Items.EMERALD)
                ((ItemStackAccessor)(Object)o.getMutableSellItem()).reelism$setItem(Items.GOLD_NUGGET);
        }
        return o;
    }
}
