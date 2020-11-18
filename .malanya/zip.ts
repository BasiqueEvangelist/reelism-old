import * as path from "path"
import * as child_process from "child_process"

export function zip(inputDir: string, outputFile: string) {
    child_process.execSync(`zip -r ${path.relative(inputDir, outputFile)} .`, { cwd: inputDir });
}

export function unzipAt(dir: string, zipFile: string, files: string[]) {
    child_process.execSync(`unzip ${path.relative(dir, zipFile)} ${files.join(" ")}`, { cwd: dir });
}