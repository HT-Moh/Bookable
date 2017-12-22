
package com.habbat.bookable.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;


@Parcel
public class Pdf {

    @SerializedName("isAvailable")
    @Expose
    public Boolean isAvailable;
    @SerializedName("acsTokenLink")
    @Expose
    public String acsTokenLink;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAcsTokenLink() {
        return acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }

}
