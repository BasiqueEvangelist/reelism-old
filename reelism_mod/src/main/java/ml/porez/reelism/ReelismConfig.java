package ml.porez.reelism;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "reelism")
public class ReelismConfig implements ConfigData {
    public boolean replaceXpOrbBehavior = true;
    public boolean noEnchantedBooks = true;
    public boolean swordNotDamagedOnBreakBlock = true;
    public boolean toolNotDamagedOnHit = true;
    public boolean toolNotDamagedOnNonEffectiveBreakBlock = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean gemOfHoldingItem = true;
}
