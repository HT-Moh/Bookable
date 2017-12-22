
package com.habbat.bookable.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class IndustryIdentifier {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("identifier")
    @Expose
    public String identifier;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
