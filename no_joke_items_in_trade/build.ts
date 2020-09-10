import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { run } from "../.malanya"

function pipeline(item) {

    if (item.data.profession && item.data.profession == "minecraft:farmer") {
        for (var i = item.data.trades.length - 1; i >= 0; --i) {
            if (item.data.trades[i].type == "minecraft:sell_suspicious_stew")
                item.data.trades.pop();
        }
        return item.data;

    }


    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: buildinfo.datapackify_datapack,
    pipeline: pipeline
};

run(packInfo);