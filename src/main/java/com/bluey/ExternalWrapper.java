package com.bluey;

public class ExternalWrapper<T> implements Embeddable {
    private String type;
    private T ref;

    public ExternalWrapper() {
    }

    public ExternalWrapper(String type, T ref) {
        this.type = type;
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }
}
