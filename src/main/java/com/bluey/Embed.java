package com.bluey;

public class Embed {
    private Embeddable refs;

    public Embed() {
    }

    public Embed(Embeddable refs) {
        this.refs = refs;
    }

    public Embeddable getRefs() {
        return refs;
    }

    public void setRefs(Embeddable refs) {
        this.refs = refs;
    }
}
