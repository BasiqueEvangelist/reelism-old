package ml.porez.reelism.enchantment;

import ml.porez.reelism.access.SpeedEnchantment;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TunnelingEnchantment extends Enchantment {
    protected TunnelingEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.DIGGER, slotTypes);
    }

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) -> {
            ItemStack main = player.getMainHandStack();
            Map<Enchantment, Integer> ench = EnchantmentHelper.get(main);
            if (ench.containsKey(ReeEnchantments.TUNNELING)
             && main.getMiningSpeedMultiplier(state) > 1.0F
             && main.getMiningSpeedMultiplier(world.getBlockState(pos.down())) > 1.0F) {
                world.breakBlock(pos.down(), true, player);
            }
        });
    }
}
