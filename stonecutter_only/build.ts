import * as path from "path";
import { run } from "../.malanya"

function pipeline(item) {
    return item.data;
}

var packInfo =
{
    basedOn: path.resolve("../out"),
    pipeline: pipeline
};

run(packInfo);