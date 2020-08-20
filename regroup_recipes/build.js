
function groupBanners(item) {
    var regex = /_banner_pattern/g;
    if (item.result
        && item.result.item
        && regex.test(item.result.item)) {
        item.group = "banner_pattern";
    }
    return;
}


function groupDyes(item) {
    var regex = /_dye/g;
    if (item.result
        && item.result.item
        && regex.test(item.result.item)) {
        item.group = "dye";
    }
    return;
}

function pipeline(item) {
    groupBanners(item);
    groupDyes(item);
    return item;
}

var packInfo =
{
    basedOn: "C:/Prog/Minecraft/minecraft_1.16.2_datapack",
    pipeline: pipeline
};

require("../.malanya")(packInfo);