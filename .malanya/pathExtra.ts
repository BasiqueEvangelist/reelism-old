import * as fs from "fs";
import * as path from "path";

export function walkDir(dir: fs.PathLike): string[] {
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

export function removeDir(dir: fs.PathLike) {
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

export function createPath(filepath: string) {
    var dirname = path.dirname(filepath);
    if (!fs.existsSync(dirname))
        fs.mkdirSync(dirname, { recursive: true });

}

export function copyDir(oldPath: string, newPath: string) {
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
