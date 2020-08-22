const fs = require("fs");

function pipeline(item) {
    const banned_items = [
        "minecraft:coal",
        "minecraft:diamond",
        "minecraft:emerald",
        "minecraft:lapis_lazuli",
        "minecraft:quartz",
        "minecraft:redstone",
    ];
    const banned_types = [
        "minecraft:blasting",
        "minecraft:smelting"
    ]

    if (banned_types.includes(item.data.type)
        && banned_items.includes(item.data.result))
        return {};

    return item.data;
}

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);