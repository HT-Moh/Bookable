
package com.habbat.bookable.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Offer {

    @SerializedName("finskyOfferType")
    @Expose
    public Integer finskyOfferType;
    @SerializedName("listPrice")
    @Expose
    public ListPrice_ listPrice;
    @SerializedName("retailPrice")
    @Expose
    public RetailPrice_ retailPrice;
    @SerializedName("giftable")
    @Expose
    public Boolean giftable;

    public Integer getFinskyOfferType() {
        return finskyOfferType;
    }

    public void setFinskyOfferType(Integer finskyOfferType) {
        this.finskyOfferType = finskyOfferType;
    }

    public ListPrice_ getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice_ listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice_ getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice_ retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Boolean getGiftable() {
        return giftable;
    }

    public void setGiftable(Boolean giftable) {
        this.giftable = giftable;
    }

}
