package me.basiqueevangelist.reelism.mixin.client;

import com.google.common.collect.ImmutableList;
import me.basiqueevangelist.reelism.client.ClientReelism;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
    @Inject(method = "reload", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void loadAlternateTranslations(ResourceManager res, CallbackInfo cb, LanguageDefinition en,
            List<LanguageDefinition> lan, TranslationStorage translat) {
        ClientReelism.ENGLISH = TranslationStorage.load(res, ImmutableList.of(en));
    }
}
