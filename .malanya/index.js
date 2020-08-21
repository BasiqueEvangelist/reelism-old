const path = require("path");
const fs = require("fs");
const pathEx = require("./pathExtra");

var startTs = Date.now();
var lastTs = startTs;
function stopwatchLap() {
    const curTs = Date.now();
    const lap = curTs - lastTs;
    lastTs = curTs;
    return lap;
}
function stopwatchTotal() {
    return Date.now() - startTs;
}
var packname;
function log(msg) {
    console.log(`[${packname}] ${msg}`)
}

module.exports = function (packinfo) {

    if (!packinfo.name)
        packinfo.name = path.basename(process.cwd());

    packname = packinfo.name;

    const basedOn = JSON.parse(fs.readFileSync("../buildinfo.json")).original_datapack;

    log(`Preparing...`);

    const baseFiles = pathEx.walkDir(basedOn + "/data");
    const outPath = path.join("out", packinfo.name);
    pathEx.removeDir("out");
    fs.mkdirSync(outPath, { recursive: true });
    fs.writeFileSync(path.join("out", "!!!warning.txt"),
        "This folder is generated by a tool.\n" +
        "All edits or additions will be rolled back on rebuild.");



    log(`Prepared for processing in ${stopwatchLap()}ms`);

    for (const file of baseFiles) {
        const newFileName = file.substring(basedOn.length)
        try {
            if (path.extname(file) != ".json")
                continue;

            const newFilePath = path.join(outPath, newFileName);
            const origData = fs.readFileSync(file);

            // newFilePath == "data/{namespace}/{type}/{path}"
            const splitted = newFileName.split(path.sep).filter(x => x != "");

            const namespace = splitted[1];
            const type = splitted[2];
            const subpath = splitted.slice(3).join(path.sep);

            const transformed = packinfo.pipeline({
                namespace,
                type,
                subpath,
                data: JSON.parse(origData)
            });
            const transformedString = JSON.stringify(transformed, null, 1);

            if (JSON.stringify(JSON.parse(origData), null, 1) == transformedString)
                continue;

            log(`Processing ${newFileName}`);

            pathEx.createPath(newFilePath);
            fs.writeFileSync(newFilePath, transformedString, { encoding: "UTF-8" });
        }
        catch (ex) {
            log(`Error when processing ${newFileName}! Skipping;\n` + ex);
            //process.exit(1);
        }
    }

    log(`Processed in ${stopwatchLap()}ms`)

    fs.copyFileSync("pack.mcmeta", path.join(outPath, "pack.mcmeta"));

    pathEx.copyDir("data", path.join(outPath, "data"));

    log(`Copied in ${stopwatchLap()}ms`)
    log(`Finished successfully in ${stopwatchTotal()}ms total`)
}