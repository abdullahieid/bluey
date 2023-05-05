package com.abdullahieid.bsky.embed;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Image implements Embeddable {

    private String alt;
    private Media image;

    public Image() {
    }

    public Image(@NonNull String alt, @NonNull Media image) {
        this.alt = alt;
        this.image = image;
    }
}
