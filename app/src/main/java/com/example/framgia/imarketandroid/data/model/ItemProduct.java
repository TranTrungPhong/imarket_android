package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoavt on 20/07/2016.
 */
public class ItemProduct {
    @SerializedName("name_product")
    private String mNameProduct;
    @SerializedName("promotion_percent")
    private String mPromotionPercent;
    @SerializedName("present_icon")
    private int mPresentIcon;
    @SerializedName("price_promotion")
    private String mPricePromotion;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("product_id")
    private int mProductId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("category_id")
    private int mCategoryId;

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon,
                       String pricePromotion, String price) {
        mNameProduct = nameProduct;
        mPromotionPercent = promotionPercent;
        mPresentIcon = presentIcon;
        mPricePromotion = pricePromotion;
        mPrice = price;
    }

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon) {
        mNameProduct = nameProduct;
        mPromotionPercent = promotionPercent;
        mPresentIcon = presentIcon;
    }

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon,
                       String pricePromotion, String price, int productId,
                       String description, int categoryId) {
        this.mNameProduct = nameProduct;
        this.mPromotionPercent = promotionPercent;
        this.mPresentIcon = presentIcon;
        this.mPricePromotion = pricePromotion;
        this.mPrice = price;
        this.mProductId = productId;
        this.mDescription = description;
        this.mCategoryId = categoryId;
    }

    public String getPricePromotion() {
        return mPricePromotion;
    }

    public void setPricePromotion(String pricePromotion) {
        mPricePromotion = pricePromotion;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public void setNameProduct(String nameProduct) {
        mNameProduct = nameProduct;
    }

    public String getPromotionPercent() {
        return mPromotionPercent;
    }

    public void setPromotionPercent(String promotionPercent) {
        mPromotionPercent = promotionPercent;
    }

    public int getPresentIcon() {
        return mPresentIcon;
    }

    public void setPresentIcon(int presentIcon) {
        mPresentIcon = presentIcon;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        this.mCategoryId = categoryId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        this.mProductId = productId;
    }
}
