package com.bluey;

public class Images implements Embeddable {
    private Image[] images;

    public Images() {
    }

    public Images(Image image) {
        this.images = new Image[]{image};
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }
}
