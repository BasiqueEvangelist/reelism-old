package ml.porez.reelism;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Map;

public class BookUpgradeRecipe extends SmithingRecipe {
    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Reelism.NAMESPACE, "smithing_special_book_upgrade"), Serializer.INSTANCE);
    }

    public BookUpgradeRecipe(Identifier id) {
        super(id, Ingredient.ofItems(Items.ENCHANTED_BOOK), Ingredient.ofItems(Items.DIAMOND), new ItemStack(Items.ENCHANTED_BOOK));
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        ItemStack book = inv.getStack(0);
        ItemStack diamonds = inv.getStack(1);
        if (diamonds.isEmpty() || diamonds.getItem() != Items.DIAMOND)
            return false;
        if (book.isEmpty() || book.getItem() != Items.ENCHANTED_BOOK)
            return false;
        Map<Enchantment, Integer> ench = EnchantmentHelper.get(book);
        if (diamonds.getCount() < ench.size())
            return false;
        for (Map.Entry<Enchantment, Integer> e : ench.entrySet()) {
            if (e.getValue() >= e.getKey().getMaxLevel())
                return false;
        }
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        ItemStack book = inv.getStack(0).copy();
        Map<Enchantment, Integer> enchs = EnchantmentHelper.get(book);
        for (Map.Entry<Enchantment, Integer> e : enchs.entrySet()) {
            e.setValue(e.getValue() + 1);
        }
        EnchantmentHelper.set(enchs, book);
        return book;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    public enum Serializer implements RecipeSerializer<BookUpgradeRecipe> {
        INSTANCE;

        @Override
        public BookUpgradeRecipe read(Identifier id, JsonObject json) {
            return new BookUpgradeRecipe(id);
        }

        @Override
        public BookUpgradeRecipe read(Identifier id, PacketByteBuf buf) {
            return new BookUpgradeRecipe(id);
        }

        @Override
        public void write(PacketByteBuf buf, BookUpgradeRecipe recipe) { }
    }
}
