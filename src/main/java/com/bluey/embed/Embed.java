package com.bluey.embed;

import com.bluey.embed.Embeddable;
import com.bluey.embed.Image;

public class Embed implements Embeddable {
    private String $type;
    private Embeddable[] images;

    public Embed() {
    }

    public Embed(String type, Image[] embeddables) {
        this.$type = type;
        this.images = embeddables;
    }

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public Embeddable[] getImages() {
        return images;
    }

    public void setImages(Embeddable[] images) {
        this.images = images;
    }
}
