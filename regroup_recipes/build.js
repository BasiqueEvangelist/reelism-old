function groupBanners(item) {
    var regex = /_banner_pattern/g;
    if (item.data.result
        && item.data.result.item
        && regex.test(item.data.result.item)) {
        item.data.group = "banner_pattern";
    }
}


function groupDyes(item) {
    var regex = /_dye/g;
    if (item.data.result
        && item.data.result.item
        && regex.test(item.data.result.item)) {
        item.data.group = "dye";
    }
}

function groupSigns(item) {
    var regex = /_sign/g;
    if (item.data.result
        && item.data.result.item
        && regex.test(item.data.result.item)) {
        item.data.group = "signs";
    }
}

function groupGlazedTerracotta(item) {
    var regex = /_glazed_terracotta/g;
    if (item.data.result
        && regex.test(item.data.result)) {
        item.data.group = "glazed_terracotta";
    }
}

function pipeline(item) {
    groupBanners(item);
    groupDyes(item);
    groupSigns(item);
    groupGlazedTerracotta(item);
    return item.data;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);