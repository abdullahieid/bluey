package com.bluey;

public class Image implements Embeddable {
    private String image;
    private String alt;

    public Image() {
    }

    public Image(String image, String alt) {
        this.image = image;
        this.alt = alt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
