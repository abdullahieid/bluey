package com.abdullahieid.bsky.embed;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Embed {
    private String $type;
    private Embeddable[] images;

    public Embed() {
    }

    public Embed(@NonNull String type, @NonNull Image[] embeddables) {
        this.$type = type;
        this.images = embeddables;
    }
}
