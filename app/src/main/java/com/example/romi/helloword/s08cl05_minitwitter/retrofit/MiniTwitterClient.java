package com.example.romi.helloword.s08cl05_minitwitter.retrofit;

import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {
    //a) (Letras referentes a clase 12)
    private static MiniTwitterClient instance = null;
    //b)
    private static MiniTwitterService miniTwitterService;
    //d)
    private Retrofit retrofit;

    //c)
    public MiniTwitterClient() {
        //e)
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //f)
        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    //Patron Singleton
    //i)
    //(Es mas practico para la gestion de memoria)
    public static  MiniTwitterClient getInstance(){
        if(instance == null){
            instance = new MiniTwitterClient();
        }

        return instance;
    }

    //j)
    public MiniTwitterService getMiniTwitterService(){
     return miniTwitterService;
    }
}
