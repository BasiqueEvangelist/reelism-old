const fs = require("fs");
const path = require("path");

exports.walkDir = function (dir) {
    var results = [];
    var list = fs.readdirSync(dir);
    list.forEach(function (file) {
        file = dir + '/' + file;
        var stat = fs.statSync(file);
        if (stat && stat.isDirectory()) {
            /* Recurse into a subdirectory */
            results = results.concat(exports.walkDir(file));
        } else {
            /* Is a file */
            results.push(file);
        }
    });
    return results;
}

exports.removeDir = function (dir) {
    if (!fs.existsSync(dir))
        return;

    var list = fs.readdirSync(dir);
    list.forEach(function (file) {
        file = dir + '/' + file;
        var stat = fs.statSync(file);
        if (stat && stat.isDirectory()) {
            /* Recurse into a subdirectory */
            exports.removeDir(file);
        } else {
            fs.unlinkSync(file);
        }
    });
    fs.rmdirSync(dir);
}

exports.createPath = function (filepath) {
    var dirname = path.dirname(filepath);
    if (!fs.existsSync(dirname))
        fs.mkdirSync(dirname, { recursive: true });

}

exports.copyDir = function (oldPath, newPath) {
    if (!fs.existsSync(oldPath))
        return;
    var tree = exports.walkDir(oldPath);
    for (const file of tree) {
        const name = file.substring(oldPath.length);
        const newName = path.join(newPath, name);
        exports.createPath(newName);
        fs.copyFileSync(file, newName);
    }
}


