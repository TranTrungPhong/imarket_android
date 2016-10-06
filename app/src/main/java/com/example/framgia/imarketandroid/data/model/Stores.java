package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 06/10/2016.
 */
public class Stores {
    @SerializedName("stores")
    private List<Store> mStoreList = new ArrayList<>();

    public Stores(List<Store> mStoreList) {
        this.mStoreList = mStoreList;
    }

    public Stores() {
    }

    public List<Store> getStoreList() {
        return mStoreList;
    }

    public void setStoreList(List<Store> storeList) {
        this.mStoreList = storeList;
    }
}
