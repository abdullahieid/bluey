package com.abdullahieid;

public enum NSID {
    POST("app.bsky.feed.post"),
    IMAGES("app.bsky.embed.images");
    public String nsid;
    NSID(String nsid){
        this.nsid = nsid;
    }
}
