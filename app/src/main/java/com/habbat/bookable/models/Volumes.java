
package com.habbat.bookable.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;


//All fields are made public for the Parcel
@Parcel
public class Volumes {

    @SerializedName("kind")
    @Expose
    public String kind;

    //totalitem expect double this why we won't use the google api directly

    @SerializedName("totalItems")
    @Expose
    public Double totalItems;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Double getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Double totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
