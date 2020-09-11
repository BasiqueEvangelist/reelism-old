import * as fs from "fs";
import * as path from "path";
import * as child_process from "child_process";
import * as pathEx from "./.malanya/pathExtra";
import { BuildInformation } from "./.malanya/types";

var buildInfo = <BuildInformation>JSON.parse(fs.readFileSync("buildinfo.json", "utf-8"));

var datapacks = fs.readdirSync(".");
for (const dp of datapacks) {

    if (dp.startsWith("."))
        continue;
    if (dp == "node_modules")
        continue;

    var stat = fs.statSync(dp);
    if (stat && !stat.isDirectory())
        continue;

    if (fs.existsSync(path.join(dp, "build.js"))) {
        child_process.execSync("node build.js", { cwd: dp, stdio: "inherit" })
    }

    const outPath = path.join(buildInfo.devpath, dp);
    pathEx.removeDir(outPath)

    if (fs.existsSync(path.join(dp, "out")))
        pathEx.copyDir(path.join(dp, "out", dp), outPath);
    else
        pathEx.copyDir(dp, outPath);
    console.log(`[${dp}] Deployed to dev world`);
}
