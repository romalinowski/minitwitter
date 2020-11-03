package com.example.romi.helloword.s08cl05_minitwitter.retrofit;

import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {
    //a) (Letras referentes a clase 12)
    private static AuthTwitterClient instance = null;
    //b)
    private static AuthTwitterService miniTwitterService;
    //d)
    private Retrofit retrofit;


    public AuthTwitterClient() {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInerceptor());
        OkHttpClient cliente = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build();

        miniTwitterService = retrofit.create(AuthTwitterService.class);
    }

    public static AuthTwitterClient getInstance(){
        if(instance == null){
            instance = new AuthTwitterClient();
        }

        return instance;
    }

    public AuthTwitterService getAuthTwitterService(){
     return miniTwitterService;
    }
}
