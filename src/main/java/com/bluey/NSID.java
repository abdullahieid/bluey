package com.bluey;

public enum NSID {
    POST("app.bsky.feed.post"),
    IMAGES("app.bsky.embed.images");
    String nsid;
    NSID(String nsid){
        this.nsid = nsid;
    }
}
