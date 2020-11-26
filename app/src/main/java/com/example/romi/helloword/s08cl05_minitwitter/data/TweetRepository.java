package com.example.romi.helloword.s08cl05_minitwitter.data;

import android.widget.Toast;

import com.example.romi.helloword.s08cl05_minitwitter.MyTweetRecyclerViewAdapter;
import com.example.romi.helloword.s08cl05_minitwitter.common.MyApp;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterClient;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterService;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    LiveData<List<Tweets>> allTweets;

    TweetRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
    }

    public  LiveData<List<Tweets>> getAllTweets(){
        final MutableLiveData<List<Tweets>> data = new MutableLiveData<>();

        Call<List<Tweets>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweets>>() {
            @Override
            public void onResponse(Call<List<Tweets>> call, Response<List<Tweets>> response) {
                if(response.isSuccessful()){
                    //Esa es una de las variables que cambie del original
                   // allTweets  = response.body();
                    data.setValue(response.body());

                }else{
                    Toast.makeText(MyApp.getContext(),"Error al Listar los Tweets",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Tweets>> call, Throwable throwable) {
                Toast.makeText(MyApp.getContext(),"Error en la conexión.",Toast.LENGTH_LONG).show();
            }
        });

        return data;
    }
}
