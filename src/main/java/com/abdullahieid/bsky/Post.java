package com.abdullahieid.bsky;

import com.abdullahieid.NSID;
import com.abdullahieid.bsky.embed.Embed;
import com.abdullahieid.bsky.embed.Image;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Post {
    private String text;
    private String createdAt;
    private Embed embed;

    public Post() {
    }

    public Post(@NonNull String text, @NonNull String createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public Post(@NonNull String text, @NonNull String createdAt, @NonNull Embed embed) {
        this.text = text;
        this.createdAt = createdAt;
        this.embed = embed;
    }

    public static Post createPost(String text){
        return new Post(text, Instant.now().toString());
    }
    public static Post createImagePost(String text, Image[] images){
        if((images.length < 1) || (images == null) || (images.length > 4)){
            throw new IllegalArgumentException("Must have a minimum of 1 image and a maximum 4.");
        }

        Embed embed = new Embed(NSID.IMAGES.nsid, images);

        return new Post(text, Instant.now().toString(), embed);
    }
}
