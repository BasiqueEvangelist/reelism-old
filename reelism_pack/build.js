const fs = require("fs");

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

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);