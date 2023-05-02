package com.bluey;

import com.bluey.embed.Embed;
import com.bluey.embed.Embeddable;
import com.bluey.embed.Image;
import com.bluey.embed.Media;

import java.time.Instant;

public class Post {
    private String text;
    private String createdAt;
    private Embeddable embed;

    public Post() {
    }

    public Post(String text, String createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public Post(String text, String createdAt, Embeddable embed) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Embeddable getEmbed() {
        return embed;
    }

    public void setEmbed(Embeddable embed) {
        this.embed = embed;
    }
}
