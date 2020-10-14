import * as fs from "fs";
import * as path from "path";
import * as child_process from "child_process";
import * as pathEx from "./.malanya/pathExtra";
import { BuildInformation } from "./.malanya/types";
import { zip } from "./.malanya/zip"

var buildInfo = <BuildInformation>JSON.parse(fs.readFileSync("buildinfo.json", "utf-8"));

pathEx.removeDir("out");
const resultPath = path.join("out", "reelism_data");
fs.mkdirSync(resultPath, { recursive: true });

let includedMods = [];

var datapacks = fs.readdirSync(".");
for (const dp of datapacks) {

    if (dp.startsWith("."))
        continue;
    if (dp == "node_modules" || dp == "out")
        continue;

    var stat = fs.statSync(dp);
    if (stat && !stat.isDirectory())
        continue;
    if (fs.existsSync(path.join(dp, "build.gradle"))) {
        child_process.execSync(process.platform === "win32" ? ".\\gradlew.bat build" : "./gradlew build", { cwd: dp, stdio: "inherit" })

        const binsPath = path.join(dp, "build", "libs");
        const bins = fs.readdirSync(binsPath);
        const bin = bins.find(x => /[a-z0-9]+-[0-9]+\.[0-9]+\.[0-9]+\.jar/.test(x));
        const binPath = path.join(binsPath, bin);
        const outPath = path.join(buildInfo.modpath, bin);

        if (fs.existsSync(outPath))
            fs.unlinkSync(outPath);
        fs.copyFileSync(binPath, path.join(resultPath, bin));
        includedMods.push(bin);
        console.log(`[${dp}] Copied to output directory`);
    } else {
        if (fs.existsSync(path.join(dp, "build.js"))) {
            child_process.execSync("node build.js", { cwd: dp, stdio: "inherit" })
        }
        if (fs.existsSync(path.join(dp, "out"))) {
            pathEx.copyDir(path.join(dp, "out", dp), resultPath);
        }
        if (fs.existsSync(path.join(dp, "assets"))) {
            pathEx.copyDir(dp, resultPath);
        }
        console.log(`[${dp}] Copied to output directory`);
    }
}

fs.unlinkSync(path.join(resultPath, "pack.mcmeta"));
let fabricModJson = {
    schemaVersion: 1,
    id: "reelism-data",
    version: "1.0.0",
    name: "Reelism Data",
    description: "It's time for your fantasy flattening! (resources and data)",
    authors: [
        "BasiqueEvangelist",
        "Jetsparrow"
    ],
    environment: "*",
    custom: {
        "modmenu:parent": "reelism"
    },
    depends: {
        fabricloader: ">=0.7.4",
        fabric: "*",
        minecraft: "1.16.x",
        reelism: "*",
        datapackify: "*"
    },
    jars: []
};
for (const file of includedMods) {
    fabricModJson.jars.push({
        "file": file
    });
}
fs.writeFileSync(path.join(resultPath, "fabric.mod.json"), JSON.stringify(fabricModJson, null, 2));
fs.copyFileSync("LICENSE", path.join(resultPath, "LICENSE"));
zip(resultPath, path.join("out", "reelism_data.jar"));
fs.copyFileSync(path.join("out", "reelism_data.jar"), path.join(buildInfo.modpath, "reelism_data.jar"));
console.log("Copied to output directory");