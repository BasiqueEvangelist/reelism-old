import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { Item, run } from "../.malanya"
import { assumeConditionTrue, purgeItem } from "../.malanya/lootTables";

const forbidden = [
    "minecraft:iron_ingot",
    "minecraft:iron_nugget",
    "minecraft:gold_ingot",
    "minecraft:gold_nugget",
    "minecraft:glass",
    "minecraft:smooth_stone",
    "minecraft:nether_brick",
    "minecraft:netherite_scrap"
];

function noLowTempSmelting(item) {

    if (item.data.type == "minecraft:smelting"
        && forbidden.includes(item.data.result)) {
        item.data = {};
    }
}

const WHITELISTED_GOLD = [
    "chests/bastion_bridge.json",
    "chests/bastion_other.json",
    "chests/nether_bridge.json",
    "chests/ruined_portal.json"
];

function noOverworldGoldArmor(item: Item) {
    if (!WHITELISTED_GOLD.includes(item.subpath)) {
        item.data = purgeItem("minecraft:golden_helmet")(item);
        item.data = purgeItem("minecraft:golden_chestplate")(item);
        item.data = purgeItem("minecraft:golden_leggings")(item);
        item.data = purgeItem("minecraft:golden_boots")(item);
    }
}

const BLACKLISTED_SILKTOUCH = [
    "blocks/grass_block.json",
    "blocks/glass.json",
    "blocks/grass_path.json",
    "blocks/ender_chest.json",
    "blocks/bookshelf.json",
    "blocks/campfire.json",
    "blocks/soul_campfire.json",
];

function noStrangeSilkTouch(item: Item) {
    if (BLACKLISTED_SILKTOUCH.includes(item.subpath)) {
        item.data = assumeConditionTrue(c => {
            if (c.condition != "minecraft:match_tool")
                return false;
            if (!(c.predicate.enchantments))
                return false;
            if (!c.predicate.enchantments.some(e => e.enchantment == "minecraft:silk_touch"))
                return false;
            return true;
        })(item);
    }
}

const ALWAYS_SURVIVE_EXPLOSION = [
    "blocks/coarse_dirt.json",
    "blocks/dirt.json",
    "blocks/farmland.json",
    "blocks/gold_ore.json",
    "blocks/iron_ore.json",
    "blocks/sand.json"
];

function removeSurvivesExplosion(item: Item) {
    if (ALWAYS_SURVIVE_EXPLOSION.includes(item.subpath))
        item.data = assumeConditionTrue(c => c.condition == "minecraft:survives_explosion")(item);
}

const BLACKLISTED_ADVANCEMENTS = [
    "minecraft:husbandry/break_diamond_hoe.json",
    "minecraft:husbandry/obtain_netherite_hoe.json",
    "minecraft:story/enchant_item.json",
    "minecraft:story/enter_the_nether.json",
    "minecraft:story/form_obsidian.json",
    "minecraft:story/enchant_item.json",
    "minecraft:story/shiny_gear.json"
];

function deleteAdvancements(item: Item) {
    if (item.type == "advancements" && BLACKLISTED_ADVANCEMENTS.includes(item.namespace + ":" + item.subpath))
        item.data = {};
}

function pipeline(item) {
    noLowTempSmelting(item);
    noOverworldGoldArmor(item);
    noStrangeSilkTouch(item);
    removeSurvivesExplosion(item);
    deleteAdvancements(item);
    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: [buildinfo.original_datapack, buildinfo.datapackify_datapack],
    pipeline: pipeline
};

run(packInfo);