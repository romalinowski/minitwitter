package com.example.romi.helloword.s08cl05_minitwitter.retrofit;

import com.example.romi.helloword.s08cl05_minitwitter.retrofit.request.RequestCreateTweet;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweets>> getAllTweets();

    @POST("tweets/create")
    Call<Tweets> createTweeT(@Body RequestCreateTweet requestCreateTweet);
}
