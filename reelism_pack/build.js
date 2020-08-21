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

    if (item.type == "minecraft:smelting"
        && forbidden.includes(item.result)) {
        return {}
    }
    return item;
}

function pipeline(item) {
    item = noLowTempSmelting(item);
    return item;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);