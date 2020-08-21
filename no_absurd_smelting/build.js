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

    if (banned_types.includes(item.type)
        && banned_items.includes(item.result))
        return {};

    return item;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);