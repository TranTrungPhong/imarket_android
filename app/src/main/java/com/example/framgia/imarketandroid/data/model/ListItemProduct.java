package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 05/10/2016.
 */
public class ListItemProduct {
    @SerializedName("products")
    private List<ItemProduct> mItemProductList = new ArrayList<>();

    public List<ItemProduct> getItemProductList() {
        return mItemProductList;
    }

    public void setItemProductList(List<ItemProduct> itemProductList) {
        this.mItemProductList = itemProductList;
    }
}
