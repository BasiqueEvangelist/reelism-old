function pipeline(item) {
    return item.data;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);