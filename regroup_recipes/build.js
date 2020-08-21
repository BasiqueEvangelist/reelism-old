function groupBanners(item) {
    var regex = /_banner_pattern/g;
    if (item.result
        && item.result.item
        && regex.test(item.result.item)) {
        item.group = "banner_pattern";
    }
}


function groupDyes(item) {
    var regex = /_dye/g;
    if (item.result
        && item.result.item
        && regex.test(item.result.item)) {
        item.group = "dye";
    }
}

function groupSigns(item) {
    var regex = /_sign/g;
    if (item.result
        && item.result.item
        && regex.test(item.result.item)) {
        item.group = "signs";
    }
}

function groupGlazedTerracotta(item) {
    var regex = /_glazed_terracotta/g;
    if (item.result
        && regex.test(item.result)) {
        item.group = "glazed_terracotta";
    }
}

function pipeline(item) {
    groupBanners(item);
    groupDyes(item);
    groupSigns(item);
    groupGlazedTerracotta(item);
    return item;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);