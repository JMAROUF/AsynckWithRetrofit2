package com.example.jamal.tpasynckandretrofit;

import retrofit2.Call;
import retrofit2.http.Field;

/**
 * Created by jamal on 19/02/2018.
 */

public interface MyService {


    @retrofit2.http.FormUrlEncoded
    @retrofit2.http.POST("/connexion.php")
    Call<Result> connectUser(@Field("email") String email, @Field("password") String password);


    // get Modules

    @retrofit2.http.GET("/lister_modules.php")
    Call<Result> getModules();






}
