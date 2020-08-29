package com.example.romi.helloword.s08cl05_minitwitter.retrofit;

import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInerceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //s08_16 2)
        String token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);
        //s08_16 2)
        Request request =chain.request().newBuilder().addHeader("Authorization","Bearer "+token).build();
        return chain.proceed(request);
    }
}
