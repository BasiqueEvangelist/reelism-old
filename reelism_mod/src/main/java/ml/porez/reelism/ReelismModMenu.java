package ml.porez.reelism;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ReelismModMenu implements ModMenuApi {
    @Override
    public String getModId() {
        return Reelism.NAMESPACE;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ReelismConfig.class, parent).get();
    }
}
