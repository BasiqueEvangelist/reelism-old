import { get } from "https"
import { VersionList } from "./types"
import { createWriteStream } from "fs"

export function pull(url: string): Promise<any> {
    return new Promise((ok, err) => {
        get(url, resp => {
            if (resp.statusCode == 404) {
                resp.resume();
                err(new Error("404 Not Found"));
            }

            resp.setEncoding("utf-8");
            let data = "";
            resp.on("data", chunk => {
                data += chunk;
            });
            resp.on("error", err);
            resp.on("end", () => {
                try {
                    ok(JSON.parse(data));
                }
                catch (e) {
                    err(e);
                }
            });
        }).on("error", err);
    });
}

export function download(url: string, to: string): Promise<void> {
    return new Promise((ok, err) => {
        get(url, resp => {
            if (resp.statusCode == 404) {
                resp.resume();
                err(new Error("404 Not Found"));
            }

            const toStream = createWriteStream(to);
            resp.pipe(toStream);

            resp.on("end", ok);
        }).on("error", err);
    });
}

export function getVersions(): Promise<VersionList> {
    return pull("https://launchermeta.mojang.com/mc/game/version_manifest.json");
}