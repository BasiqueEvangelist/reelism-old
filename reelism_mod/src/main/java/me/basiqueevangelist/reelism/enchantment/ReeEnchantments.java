package me.basiqueevangelist.reelism.enchantment;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReeEnchantments {
    public static final TunnelingEnchantment TUNNELING = new TunnelingEnchantment(Enchantment.Rarity.UNCOMMON,
            EquipmentSlot.MAINHAND);

    public static void register() {
        if (Reelism.CONFIG.enchantments.tunnelingEnchantment) {
            Registry.register(Registry.ENCHANTMENT, Reelism.id("tunneling"), TUNNELING);
            TunnelingEnchantment.register();
        }
    }
}
