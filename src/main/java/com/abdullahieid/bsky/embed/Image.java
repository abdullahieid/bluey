package com.abdullahieid.bsky.embed;

public class Image implements Embeddable {

    private String alt;
    private Media image;

    public Image() {
    }

    public Image(String alt, Media image) {
        this.alt = alt;
        this.image = image;
    }

    public Media getImage() {
        return image;
    }

    public void setImage(Media image) {
        this.image = image;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
