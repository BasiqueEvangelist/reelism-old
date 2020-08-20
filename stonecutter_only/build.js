const fs = require("fs");

function pipeline(item) {
    return item;
}

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);