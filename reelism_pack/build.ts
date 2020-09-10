import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { run } from "../.malanya"

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

function pipeline(item) {
    noLowTempSmelting(item);
    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: [buildinfo.original_datapack, buildinfo.datapackify_datapack],
    pipeline: pipeline
};

run(packInfo);