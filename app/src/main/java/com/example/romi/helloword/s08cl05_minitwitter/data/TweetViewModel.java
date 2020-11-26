package com.example.romi.helloword.s08cl05_minitwitter.data;

import android.app.Application;

import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TweetViewModel extends AndroidViewModel {
    //S08_CL22--3(Instancia del repositorio)
    private TweetRepository tweetRepository;
    private LiveData<List<Tweets>> tweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        //S08_CL22--4(Instancia del repositorio)
        tweetRepository = new TweetRepository();
        tweets = tweetRepository.getAllTweets();
    }
    //S08_CL22--5
    public LiveData<List<Tweets>> getTweets(){return tweets;}
}
