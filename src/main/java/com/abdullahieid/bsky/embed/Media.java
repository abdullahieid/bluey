package com.abdullahieid.bsky.embed;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Media {
    private String cid;
    private String mimeType;

    public Media() {
    }

    public Media(@NonNull String cid, @NonNull String mimeType) {
        this.cid = cid;
        this.mimeType = mimeType;
    }
}
