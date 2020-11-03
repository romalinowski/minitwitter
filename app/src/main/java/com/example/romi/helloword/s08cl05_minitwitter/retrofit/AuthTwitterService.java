package com.example.romi.helloword.s08cl05_minitwitter.retrofit;

import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweets>> getAllTweets();
}
