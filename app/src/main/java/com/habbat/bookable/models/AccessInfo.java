
package com.habbat.bookable.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class AccessInfo {

    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("viewability")
    @Expose
    public String viewability;
    @SerializedName("embeddable")
    @Expose
    public Boolean embeddable;
    @SerializedName("publicDomain")
    @Expose
    public Boolean publicDomain;
    @SerializedName("textToSpeechPermission")
    @Expose
    public String textToSpeechPermission;
    @SerializedName("epub")
    @Expose
    public Epub epub;
    @SerializedName("pdf")
    @Expose
    public Pdf pdf;
    @SerializedName("webReaderLink")
    @Expose
    public String webReaderLink;
    @SerializedName("accessViewStatus")
    @Expose
    public String accessViewStatus;
    @SerializedName("quoteSharingAllowed")
    @Expose
    public Boolean quoteSharingAllowed;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    public Boolean getPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(Boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public Boolean getQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(Boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }

}
