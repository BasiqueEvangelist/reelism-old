package me.basiqueevangelist.reelism.mixin.client;

import me.basiqueevangelist.reelism.Reelism;
import me.basiqueevangelist.reelism.client.ClientReelism;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = { "method_1581",
            "method_1557" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;"))
    private static String stringifyTooltip(Text t) {
        StringBuilder s = new StringBuilder().append(t.getString());
        if (Reelism.CONFIG.searchByEnglishAlso && ClientReelism.ENGLISH != null) {
            Language l = Language.getInstance();
            Language.setInstance(ClientReelism.ENGLISH);
            s.append(";");
            s.append(t.getString());
            Language.setInstance(l);
        }
        return s.toString();
    }
}
