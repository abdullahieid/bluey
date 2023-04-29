package com.bluey;

public class Record<T> {
    private String type = "union";
    private String repo;
    private String collection;
    private String rkey;
    private String validate;
    private T record;
    private String swapCommit;

    public Record() {
    }

    public Record(String repo, NSID collection, T record) {
        this.repo = repo;
        this.collection = collection.nsid;
        this.record = record;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getRkey() {
        return rkey;
    }

    public void setRkey(String rkey) {
        this.rkey = rkey;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    public String getSwapCommit() {
        return swapCommit;
    }

    public void setSwapCommit(String swapCommit) {
        this.swapCommit = swapCommit;
    }
}
