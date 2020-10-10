package ml.porez.reelism;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "reelism")
public class ReelismConfig implements ConfigData {
    public boolean replaceXpOrbBehavior = true;
    public boolean disableMending = true;
    public boolean noEnchantedBooks = true;
    @ConfigEntry.Gui.CollapsibleObject
    public FunnyLightningConfig noFunnyLightning = new FunnyLightningConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public ToolDamageConfig toolDamage = new ToolDamageConfig();
    @ConfigEntry.Gui.RequiresRestart
    public boolean gemOfHoldingItem = true;
    public boolean disableSleep = true;
    public boolean noSilkTouchOnShovels = true;
    public boolean creeperPoisonCloud = true;

    public static class FunnyLightningConfig {
        public boolean forMooshrooms = true;
        public boolean forPigs = true;
        public boolean forVillagers = true;
        public boolean forCreepers = true;
    }

    public static class ToolDamageConfig {
        public boolean miningToolNotDamagedOnHit = true;
        public boolean miningToolNotDamagedOnNonEffectiveBreakBlock = true;
        public boolean swordNotDamagedOnBreakBlock = true;
    }
}
