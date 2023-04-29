package com.bluey;

public enum NSID {
    POST("app.bsky.feed.post");
    String nsid;
    NSID(String nsid){
        this.nsid = nsid;
    }
}
