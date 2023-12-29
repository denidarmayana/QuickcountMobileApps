package gens.global.gensquickcount.function.api;

import java.util.List;

import gens.global.gensquickcount.model.CalonModel;
import gens.global.gensquickcount.model.SliderModel;
import gens.global.gensquickcount.model.SurveyModel;
import gens.global.gensquickcount.model.UserModel;
import gens.global.gensquickcount.model.WilayahModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInterfaces {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<UserModel> loginUser(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<UserModel> register(@Body String body);

    @Headers("Content-Type: application/json")
    @GET("get-provinsi")
    Call<List<WilayahModel>> getProvinsi();
    @Headers("Content-Type: application/json")
    @POST("get-kabupaten")
    Call<List<WilayahModel>> getKabupaten(@Body String body);
    @Headers("Content-Type: application/json")
    @POST("get-kecamatan")
    Call<List<WilayahModel>> getKecamatan(@Body String body);
    @Headers("Content-Type: application/json")
    @POST("get-kelurahan")
    Call<List<WilayahModel>> getKelurahan(@Body String body);

    @Headers("Content-Type: application/json")
    @GET("get-slider")
    Call<List<SliderModel>> getSlider();

    @Headers("Content-Type: application/json")
    @POST("survey")
    Call<UserModel> inserSurvey(@Body String body);

    @Headers("Content-Type: application/json")
    @GET("calon")
    Call<CalonModel> getCalon();

    @Headers("Content-Type: application/json")
    @GET("show-survey")
    Call<List<SurveyModel>> getSurvey();
}
