function pipeline(item) {
    return item;
}

var packInfo =
{
    pipeline: pipeline
};

require("../.malanya")(packInfo);