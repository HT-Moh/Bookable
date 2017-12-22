
package com.habbat.bookable.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class VolumeInfo {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("authors")
    @Expose
    public List<String> authors = null;
    @SerializedName("publisher")
    @Expose
    public String publisher;
    @SerializedName("publishedDate")
    @Expose
    public String publishedDate;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("industryIdentifiers")
    @Expose
    public List<IndustryIdentifier> industryIdentifiers = null;
    @SerializedName("readingModes")
    @Expose
    public ReadingModes readingModes;
    @SerializedName("pageCount")
    @Expose
    public Integer pageCount;
    @SerializedName("printType")
    @Expose
    public String printType;
    @SerializedName("maturityRating")
    @Expose
    public String maturityRating;
    @SerializedName("allowAnonLogging")
    @Expose
    public Boolean allowAnonLogging;
    @SerializedName("contentVersion")
    @Expose
    public String contentVersion;
    @SerializedName("panelizationSummary")
    @Expose
    public PanelizationSummary panelizationSummary;
    @SerializedName("imageLinks")
    @Expose
    public ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("previewLink")
    @Expose
    public String previewLink;
    @SerializedName("infoLink")
    @Expose
    public String infoLink;
    @SerializedName("canonicalVolumeLink")
    @Expose
    public String canonicalVolumeLink;
    @SerializedName("subtitle")
    @Expose
    public String subtitle;
    @SerializedName("averageRating")
    @Expose
    public Double averageRating;
    @SerializedName("ratingsCount")
    @Expose
    public Integer ratingsCount;
    @SerializedName("categories")
    @Expose
    public List<String> categories = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public ReadingModes getReadingModes() {
        return readingModes;
    }

    public void setReadingModes(ReadingModes readingModes) {
        this.readingModes = readingModes;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public Boolean getAllowAnonLogging() {
        return allowAnonLogging;
    }

    public void setAllowAnonLogging(Boolean allowAnonLogging) {
        this.allowAnonLogging = allowAnonLogging;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    public PanelizationSummary getPanelizationSummary() {
        return panelizationSummary;
    }

    public void setPanelizationSummary(PanelizationSummary panelizationSummary) {
        this.panelizationSummary = panelizationSummary;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
