package me.basiqueevangelist.reelism.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class GemOfHoldingItem extends Item {
    public static final int MAX_CHARGE = 100;

    public static boolean doGravitate(ExperienceOrbEntity exp, PlayerEntity player) {
        ItemStack offh = player.getOffHandStack();
        return !offh.isEmpty() && offh.getItem() == ReeItems.GEM_OF_HOLDING && getCharge(offh) < MAX_CHARGE;
    }

    public static int fill(ItemStack stack, int amount) {
        int prevAmount = getCharge(stack);

        setCharge(stack, MathHelper.clamp(prevAmount + amount, 0, MAX_CHARGE));

        return getCharge(stack) - prevAmount;
    }

    public static int getTotalExperience(PlayerInventory inventory) {
        int total = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack is = inventory.getStack(i);
            if (is.getItem() == ReeItems.GEM_OF_HOLDING) {
                total += getCharge(is);
            }
        }
        return total;
    }

    public static int getCharge(ItemStack is) {
        NbtCompound tag = is.getTag();
        return tag == null ? 0 : tag.getInt("Charge");
    }

    public static void setCharge(ItemStack is, int charge) {
        is.getOrCreateTag().putInt("Charge", charge);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        int current = getCharge(stack);
        tooltip.add(new LiteralText(
                (float) current / (float) MAX_CHARGE * 100 + "%" + (context.isAdvanced() ? " (" + current + ")" : "")));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float)getCharge(stack) * 13.0F / MAX_CHARGE);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float hue = Math.max(0.0F, (float)getCharge(stack) / MAX_CHARGE);
        return MathHelper.hsvToRgb(hue / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand != Hand.MAIN_HAND)
            return TypedActionResult.pass(user.getStackInHand(hand));
        ItemStack mainHand = user.getStackInHand(hand);

        if (getCharge(mainHand) < 5)
            return TypedActionResult.fail(user.getStackInHand(hand));

        setCharge(mainHand, getCharge(mainHand) - 5);
        if (!world.isClient)
            world.spawnEntity(new ExperienceOrbEntity(world, user.getX(), user.getY(), user.getZ(), 5));
        return TypedActionResult.success(mainHand);
    }

    public GemOfHoldingItem(Settings settings) {
        super(settings);
    }
}
