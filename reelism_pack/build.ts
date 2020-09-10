import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { Item, run } from "../.malanya"
import { purgeFromLootTables } from "../.malanya/transformers";

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
        item.data = purgeFromLootTables("minecraft:golden_helmet")(item);
        item.data = purgeFromLootTables("minecraft:golden_chestplate")(item);
        item.data = purgeFromLootTables("minecraft:golden_leggings")(item);
        item.data = purgeFromLootTables("minecraft:golden_boots")(item);
    }
}


function pipeline(item) {
    noLowTempSmelting(item);
    noOverworldGoldArmor(item);
    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: [buildinfo.original_datapack, buildinfo.datapackify_datapack],
    pipeline: pipeline
};

run(packInfo);