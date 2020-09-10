import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { run } from "../.malanya"

const THICK_POTIONS = [
    "minecraft:healing",
    "minecraft:strength",
    "minecraft:slow_falling",
    "minecraft:fire_resistance",
    "minecraft:weakness"
];

const AWKWARD_POTIONS = [
    "minecraft:harming",
    "minecraft:poison",
    "minecraft:regeneration"
];

function pipeline(item) {
    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: buildinfo.datapackify_datapack,
    pipeline: pipeline
};

run(packInfo);