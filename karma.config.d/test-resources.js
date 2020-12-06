config.files.push({
    pattern: __dirname + "/kotlin/day*.txt",
    watched: false,
    included: false,
    served: true,
    nocache: false
});
config.set({
    "proxies": {
        "/test/resources/": __dirname + "/kotlin/"
    }
});
