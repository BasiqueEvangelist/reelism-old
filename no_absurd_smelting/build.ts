import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { run } from "../.malanya"

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

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

run(packInfo);