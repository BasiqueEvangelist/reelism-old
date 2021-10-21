import * as path from "path";
import { run } from "../.malanya"

function pipeline(item) {
    return item.data;
}

var packInfo =
{
    basedOn: path.resolve("../Datapackify/src/main/resources/builtin_data"),
    pipeline: pipeline
};

run(packInfo);