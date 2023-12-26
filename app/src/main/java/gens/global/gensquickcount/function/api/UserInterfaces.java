package gens.global.gensquickcount.function.api;

import gens.global.gensquickcount.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInterfaces {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<UserModel> loginUser(@Body String body);
    @Headers("Content-Type: application/json")
    @POST("cek-santri")
    Call<UserModel> cekUser(@Body String body);
    @Headers("Content-Type: application/json")
    @POST("register")
    Call<UserModel> register(@Body String body);
}
