package gens.global.gensquickcount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("nis")
    @Expose
    private String nis;
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public String getNis() {
        return nis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
