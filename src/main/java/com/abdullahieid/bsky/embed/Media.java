package com.abdullahieid.bsky.embed;

public class Media {
    private String cid;
    private String mimeType;

    public Media() {
    }

    public Media(String cid, String mimeType) {
        this.cid = cid;
        this.mimeType = mimeType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
