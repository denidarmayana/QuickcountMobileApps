package gens.global.gensquickcount.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyModel {
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("tps")
    @Expose
    private String tps;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kabupaten")
    @Expose
    private String kabupaten;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kelurahan")
    @Expose
    private String kelurahan;

    public String getCount() {
        return count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getTps() {
        return tps;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public void setTps(String tps) {
        this.tps = tps;
    }
}
