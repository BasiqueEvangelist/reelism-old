import * as path from "path";
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

var packInfo =
{
    basedOn: path.resolve("../Datapackify/src/main/resources"),
    pipeline: pipeline
};

run(packInfo);