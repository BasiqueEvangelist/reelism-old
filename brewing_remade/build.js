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
    if (item.namespace == "minecraft" && item.type == "brewing_recipes") {
        if (item.data.input == "minecraft:awkward") {
            if (THICK_POTIONS.includes(item.data.output)) {
                item.data.input = "minecraft:thick";
            }
            else if (!AWKWARD_POTIONS.includes(item.data.output)) {
                item.data.input = "minecraft:mundane";
            }
        }
        if (item.data.input == "minecraft:water")
            return {};
        if (item.data.output == "minecraft:harming" && item.data.input == "minecraft:healing")
            return {};
        if (item.data.output == "minecraft:strong_harming" && item.data.input == "minecraft:strong_healing")
            return {};
    }
    return item.data;
}

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.datapackify_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);