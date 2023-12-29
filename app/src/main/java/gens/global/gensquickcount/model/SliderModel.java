package gens.global.gensquickcount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderModel {
    @SerializedName("images")
    @Expose
    private String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
