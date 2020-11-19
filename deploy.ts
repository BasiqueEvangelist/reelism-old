import * as fs from "fs";
import * as path from "path";
import * as child_process from "child_process";
import * as pathEx from "./.malanya/pathExtra";
import { BuildInformation, VersionManifest } from "./.malanya/types";
import { zip, unzipAt } from "./.malanya/zip"
import { getVersions, pull, download } from "./.malanya/versions"

(async function () {
    pathEx.removeDir("out/reelism");
    const resultPath = path.join("out", "reelism");
    fs.mkdirSync(resultPath, { recursive: true });

    if (!fs.existsSync("out/data")) {
        const versionList = await getVersions();
        console.log("> Downloaded version list");
        const version = versionList.versions.find(x => x.id == versionList.latest.release);
        const versionManifest = <VersionManifest>await pull(version.url);
        console.log("> Downloaded version manifest");
        await download(versionManifest.downloads["client"].url, path.join("out", "client.jar"));
        console.log("> Downloaded client jar");
        unzipAt("out", "out/client.jar", ["data/*"]);
        console.log("> Unzipped data");
        fs.unlinkSync("out/client.jar");
        console.log("> Removed client jar");
    }

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
        id: "reelism",
        version: "1.0.0",
        name: "Reelism",
        description: "It's time for your fantasy flattening!",
        authors: [
            "BasiqueEvangelist",
            "Jetsparrow"
        ],
        environment: "*",
        depends: {
            fabricloader: ">=0.7.4",
            fabric: "*",
            minecraft: "1.16.x",
            "reelism-mod": "*",
            datapackify: "*"
        },
        license: "LGPL-3.0-only",
        jars: []
    };
    for (const file of includedMods) {
        fabricModJson.jars.push({
            "file": file
        });
    }
    fs.writeFileSync(path.join(resultPath, "fabric.mod.json"), JSON.stringify(fabricModJson, null, 2));
    fs.copyFileSync("LICENSE", path.join(resultPath, "LICENSE"));
    zip(resultPath, path.join("out", "reelism.jar"));
    if (fs.existsSync("buildinfo.json")) {
        var buildInfo = <BuildInformation>JSON.parse(fs.readFileSync("buildinfo.json", "utf-8"));

        fs.copyFileSync(path.join("out", "reelism.jar"), path.join(buildInfo.modpath, "reelism.jar"));
        console.log("> Copied to output directory");
    }
})().catch(e => {
    console.error(e);
});