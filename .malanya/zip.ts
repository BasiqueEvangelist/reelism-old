import * as path from "path"
import * as child_process from "child_process"

export function zip(inputDir: string, outputFile: string) {
    child_process.execSync(`zip -r ${path.relative(inputDir, outputFile)} .`, { cwd: inputDir });
}