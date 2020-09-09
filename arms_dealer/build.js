const fs = require("fs");

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

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.datapackify_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);