package com.abdullahieid.atproto;

import com.abdullahieid.NSID;

public class Record<T> {
    private String $type;
    private String repo;
    private String collection;
    private String rkey;
    private String validate;
    private T record;
    private String swapCommit;

    public Record() {
    }

    public Record(String $type, String repo, NSID collection, T record) {
        this.$type = $type;
        this.repo = repo;
        this.collection = collection.nsid;
        this.record = record;
    }

    public static <T> Record<T> createRecord(String $type, String repo, NSID collection, T record){
        return new Record<T>($type, repo, collection, record);
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
