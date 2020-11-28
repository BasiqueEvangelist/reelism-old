package me.basiqueevangelist.reelism.mixin.client;

import me.basiqueevangelist.reelism.Reelism;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {
    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40))
    public int maxLevel(int def) {
        if (!Reelism.CONFIG.noMaxAnvilLevel)
            return def;

        return Integer.MAX_VALUE;
    }
}
