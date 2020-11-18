export interface BuildInformation {
    modpath: string
}

export interface VersionList {
    latest: {
        release: string,
        snapshot: string
    },
    versions: {
        id: string,
        type: string,
        url: string,
        time: string,
        releaseTime: string
    }[]
}

// Or at least, what we need of it.
export interface VersionManifest {
    downloads: {
        [id: string]: {
            url: string
        }
    }
}