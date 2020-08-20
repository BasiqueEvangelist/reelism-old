const fs = require("fs");
const path = require("path");
const child_process = require("child_process");
const pathEx = require("./.malanya/pathExtra");

var devPath = JSON.parse(fs.readFileSync("buildinfo.json")).devpath;

var datapacks = fs.readdirSync(".");
for (const dp of datapacks) {

    if (dp.startsWith("."))
        continue;

    var stat = fs.statSync(dp);
    if (stat && !stat.isDirectory())
        continue;

    if (fs.existsSync(path.join(dp, "build.js"))) {
        child_process.execSync("node build.js", { cwd: dp, stdio: "inherit" })
    }

    const outPath = path.join(devPath, dp);
    pathEx.removeDir(outPath)

    if (fs.existsSync(path.join(dp, "out")))
        pathEx.copyDir(path.join(dp, "out", dp), outPath);
    else
        pathEx.copyDir(dp, outPath);
    console.log(`[${dp}] Deployed to dev world`);
}
