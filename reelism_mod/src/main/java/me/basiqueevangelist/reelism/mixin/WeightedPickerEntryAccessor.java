package me.basiqueevangelist.reelism.mixin;

import net.minecraft.util.collection.WeightedPicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedPicker.Entry.class)
public interface WeightedPickerEntryAccessor {
    @Accessor
    int getWeight();
}
