package gens.global.gensquickcount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WilayahModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
