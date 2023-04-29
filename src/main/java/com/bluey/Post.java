package com.bluey;

public class Post {
    private String text;
    private String createdAt;
    private Embeddable embeddable;

    public Post() {
    }

    public Post(String text, String createdAt) {
        this.text = text;
        this.createdAt = createdAt;
    }

    public Post(String text, String createdAt, Embeddable embed) {
        this.text = text;
        this.createdAt = createdAt;
        this.embeddable = embed;
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

    public Embeddable getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Embeddable embeddable) {
        this.embeddable = embeddable;
    }
}
