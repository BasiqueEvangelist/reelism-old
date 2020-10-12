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
    @ConfigEntry.Gui.CollapsibleObject
    public BattleAxeConfig battleAxe = new BattleAxeConfig();
    public boolean disableSleep = true;
    public boolean noSilkTouchOnShovels = true;
    public boolean plantCreepers = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean searchByEnglishAlso = true;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
    public int spawnLightLevel = 8;

    public static class BattleAxeConfig {
        @ConfigEntry.Gui.RequiresRestart
        public boolean wooden = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean stone = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean golden = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean iron = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean diamond = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean netherite = true;
        public boolean breaksShields = true;
    }

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
