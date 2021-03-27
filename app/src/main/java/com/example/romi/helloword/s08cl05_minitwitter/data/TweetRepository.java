package com.example.romi.helloword.s08cl05_minitwitter.data;

import android.widget.Toast;

import com.example.romi.helloword.s08cl05_minitwitter.MyTweetRecyclerViewAdapter;
import com.example.romi.helloword.s08cl05_minitwitter.common.MyApp;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterClient;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterService;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.request.RequestCreateTweet;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    MutableLiveData<List<Tweets>> allTweets;

    TweetRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
    }

    public  MutableLiveData<List<Tweets>> getAllTweets(){
        if(allTweets == null){
            allTweets = new MutableLiveData<>();
        }

        Call<List<Tweets>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweets>>() {
            @Override
            public void onResponse(Call<List<Tweets>> call, Response<List<Tweets>> response) {
                if(response.isSuccessful()){
                    //Esa es una de las variables que cambie del original
                   // allTweets  = response.body();
                     allTweets.setValue(response.body());

                }else{
                    Toast.makeText(MyApp.getContext(),"Error al Listar los Tweets",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Tweets>> call, Throwable throwable) {
                Toast.makeText(MyApp.getContext(),"Error en la conexión.",Toast.LENGTH_LONG).show();
            }
        });

        return allTweets;
    }

    public void createTweet(String mensaje){
        RequestCreateTweet requestCreateTweet = new RequestCreateTweet(mensaje);
        Call<Tweets> call = authTwitterService.createTweeT(requestCreateTweet);

        call.enqueue(new Callback<Tweets>() {
            @Override
            public void onResponse(Call<Tweets> call, Response<Tweets> response) {
                //Metodo donde recibimos la respuesta si salio all ook con la comunicacion con la API
                if(response.isSuccessful()){
                    List<Tweets> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    //Este es un for que recorre toda la lista de tweets que recibio
                    for(int i = 0; i < allTweets.getValue().size() ; i++){
                        listaClonada.add( new Tweets(allTweets.getValue().get(i)));
                    }

                    allTweets.setValue(listaClonada);
                }else{
                    Toast.makeText(MyApp.getContext(), "Algo Salio mal, intentelo nuevamente",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tweets> call, Throwable throwable) {
                Toast.makeText(MyApp.getContext(),"Error en la conexión",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
