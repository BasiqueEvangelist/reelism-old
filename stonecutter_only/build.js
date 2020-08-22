const fs = require("fs");

function pipeline(item) {
    return item.data;
}

const buildinfo = JSON.parse(fs.readFileSync("../buildinfo.json"));

var packInfo =
{
    basedOn: buildinfo.original_datapack,
    pipeline: pipeline
};

require("../.malanya")(packInfo);