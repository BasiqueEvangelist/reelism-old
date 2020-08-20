const fs = require("fs");
const path = require("path");

const walkDir = function (dir) {
    var results = [];
    var list = fs.readdirSync(dir);
    list.forEach(function (file) {
        file = dir + '/' + file;
        var stat = fs.statSync(file);
        if (stat && stat.isDirectory()) {
            /* Recurse into a subdirectory */
            results = results.concat(walkDir(file));
        } else {
            /* Is a file */
            results.push(file);
        }
    });
    return results;
}

const createPath = function (filepath) {
    var dirname = path.dirname(filepath);
    if (!fs.existsSync(dirname))
        fs.mkdirSync(dirname, { recursive: true });

}

const copyDir = function (oldPath, newPath) {
    if (!fs.existsSync(oldPath))
        return;
    var tree = walkDir(oldPath);
    for (const file of tree) {
        const name = file.substring(oldPath.length);
        const newName = path.join(newPath, name);
        createPath(newName);
        fs.copyFileSync(file, newName);
    }
}

var devPath = JSON.parse(fs.readFileSync("buildinfo.json")).devpath;

var datapacks = fs.readdirSync(".");
for (const dp of datapacks) {

    if (dp.startsWith("."))
        continue;

    var stat = fs.statSync(dp);
    if (stat && !stat.isDirectory())
        continue;

    if (fs.existsSync(path.join(dp, "out")))
        copyDir(path.join(dp, "out", dp), path.join(devPath, dp));
    else
        copyDir(dp, path.join(devPath, dp));
    console.log(`Copied ${dp}`);
}
