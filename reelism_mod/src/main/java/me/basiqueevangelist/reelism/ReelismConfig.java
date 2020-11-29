package me.basiqueevangelist.reelism;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "reelism-mod")
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
    public boolean plantCreepers = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean searchByEnglishAlso = true;
    @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
    public int spawnLightLevel = 8;
    public boolean autoUnlockRecipes = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean torchesBurnOut = true;
    @ConfigEntry.Gui.RequiresRestart
    public boolean tntIsPowderKeg = true;
    public boolean merchantsUseGold = false;
    @ConfigEntry.Gui.CollapsibleObject
    public EnchantmentConfig enchantments = new EnchantmentConfig();
    public boolean unbreakableAnvils = true;
    public boolean betterFishing = true;
    public boolean fishPulledOutAsItems = true;
    @ConfigEntry.Category("surreelism")
    @ConfigEntry.Gui.TransitiveObject
    public SurreelismConfig surreelism = new SurreelismConfig();
    public boolean armorBasedLoot = true;
    public boolean noMaxAnvilLevel = true;
    public boolean altRepairCosts = true;
    public double ladderSpeedMultiplier = 3;
    @ConfigEntry.BoundedDiscrete(min = -90, max = 90)
    public float ladderDownPitch = 45;

    public static class SurreelismConfig {
        @ConfigEntry.Gui.RequiresRestart
        public boolean transportationStatusEffect = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean commonStrongholds = true;
        public boolean netherPortalsDisabled = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean altNetherPortals = true;
        public boolean endPortalsGenerateOpen = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean surfaceStrongholdPiece = true;
    }

    public static class BattleAxeConfig {
        @ConfigEntry.Gui.RequiresRestart
        public boolean wooden = false;
        @ConfigEntry.Gui.RequiresRestart
        public boolean stone = false;
        @ConfigEntry.Gui.RequiresRestart
        public boolean golden = false;
        @ConfigEntry.Gui.RequiresRestart
        public boolean iron = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean diamond = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean netherite = false;
        public boolean breaksShields = true;
    }

    public static class EnchantmentConfig {
        public boolean silkTouchEfficiencyConflict = true;
        public boolean fortuneEfficiencyConflict = true;
        public boolean noSilkTouchOnShovels = true;
        public boolean fortuneDecreasesSpeed = true;
        public boolean silkTouchDecreasesSpeed = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean tunnelingEnchantment = true;
        @ConfigEntry.Gui.RequiresRestart
        public boolean smiteIsFungicide = true;
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
