package com.examples.SunatRetrofit.SunatRetrofit.retrofit.impl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.examples.SunatRetrofit.SunatRetrofit.aggregates.constants.Constants.BASE_URL;

public class ClientSunatServiceImpl {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
