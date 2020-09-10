import * as fs from "fs";
import { BuildInformation } from "../.malanya/types"
import { run } from "../.malanya"

function pipeline(item) {
    return item.data;
}

const buildinfo = <BuildInformation>JSON.parse(fs.readFileSync("../buildinfo.json", "utf-8"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

run(packInfo);