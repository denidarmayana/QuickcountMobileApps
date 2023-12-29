package gens.global.gensquickcount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalonModel {
    @SerializedName("nama")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("biografi")
    @Expose
    private String biografi;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBiografi() {
        return biografi;
    }

    public String getImages() {
        return images;
    }

    public void setBiografi(String biografi) {
        this.biografi = biografi;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
