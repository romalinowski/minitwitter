package com.example.romi.helloword.s08cl05_minitwitter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.romi.helloword.s08cl05_minitwitter.dummy.DummyContent;
import com.example.romi.helloword.s08cl05_minitwitter.dummy.DummyContent.DummyItem;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterClient;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.AuthTwitterService;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import java.util.List;


public class TweetListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    MyTweetRecyclerViewAdapter adapter;
    List<Tweets> tweetsList;

    AuthTwitterService authTwitterService;
    AuthTwitterClient  authTwitterClient;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TweetListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TweetListFragment newInstance(int columnCount) {
        TweetListFragment fragment = new TweetListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            retrofitInit();
            loadTweetData();

        }
        return view;
    }

    private void retrofitInit() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
    }

    private void loadTweetData() {

        Call<List<Tweets>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweets>>() {
            @Override
            public void onResponse(Call<List<Tweets>> call, Response<List<Tweets>> response) {
                if(response.isSuccessful()){
                    tweetsList  = response.body();
                    adapter = new MyTweetRecyclerViewAdapter(
                            getActivity(),
                            tweetsList
                    );
                    recyclerView.setAdapter(adapter);

                }else{
                    Toast.makeText(getActivity(),"Error al Listar los Tweets",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Tweets>> call, Throwable throwable) {
                Toast.makeText(getActivity(),"Error en la conexión.",Toast.LENGTH_LONG).show();
            }
        });


    }


}
